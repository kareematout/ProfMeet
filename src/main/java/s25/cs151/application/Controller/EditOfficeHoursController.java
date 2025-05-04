package s25.cs151.application.Controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import s25.cs151.application.Model.OfficeHourEntry;

public class EditOfficeHoursController extends NavigationController {
    @FXML private TextField studentNameField;
    @FXML private TextArea reasonField, commentField;
    @FXML private DatePicker scheduleDatePicker;
    @FXML private ComboBox<String> timeSlotComboBox, courseComboBox;
    @FXML private TextField searchField;
    @FXML private TableView<OfficeHourEntry> scheduleTable;
    @FXML private TableColumn<OfficeHourEntry, String> nameCol, dateCol, slotCol, courseCol, reasonCol, commentCol;
    @FXML private StackPane editForm = new StackPane();
    @FXML private Label editFormLabel;

    private final ObservableList<OfficeHourEntry> fullList = FXCollections.observableArrayList();
    private final ObservableList<OfficeHourEntry> filteredList = FXCollections.observableArrayList();
    private final ObservableList<String> timeSlots = FXCollections.observableArrayList();
    private final ObservableList<String> courses = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        this.nameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStudentName()));
        this.dateCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getScheduleDate()));
        this.slotCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTimeSlot()));
        this.courseCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCourse()));
        this.reasonCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getReason()));
        this.commentCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getComment()));
        this.loadCSVData();
        this.loadCoursesFromCSV();
        this.loadTimeSlotsFromCSV();
        this.scheduleTable.setItems(this.filteredList);
        this.searchField.addEventHandler(KeyEvent.KEY_RELEASED, event -> this.handleSearch());
    }

    private void loadCSVData() {
        File file = new File("data/office_hour_schedule.csv");
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                if (scanner.hasNextLine()) scanner.nextLine(); // skip header

                while (scanner.hasNextLine()) {
                    String[] row = scanner.nextLine().split(",", -1);
                    if (row.length >= 6) {
                        this.fullList.add(new OfficeHourEntry(row[0], row[1], row[2], row[3], row[4], row[5]));
                    }
                }

                this.sortList(this.fullList);
                this.filteredList.setAll(this.fullList);
            } catch (Throwable e) {
                e.printStackTrace();
                this.showErrorMessage("Failed to load office hour schedule.");
            }
        }
    }

    private void sortList(ObservableList<OfficeHourEntry> list) {
        list.sort((a, b) -> {
            int dateCompare = b.getParsedDate().compareTo(a.getParsedDate());
            return (dateCompare != 0) ? dateCompare : b.getStartTime().compareTo(a.getStartTime());
        });
    }

    @FXML
    private void handleSearch() {
        String query = this.searchField.getText().toLowerCase().trim();
        if (query.isEmpty()) {
            this.filteredList.setAll(this.fullList);
        } else {
            this.filteredList.setAll(this.fullList.stream()
                    .filter(e -> e.getStudentName().toLowerCase().contains(query))
                    .collect(Collectors.toList()));
        }
        this.sortList(this.filteredList);
    }

    @FXML
    private void handleEdit() {
        OfficeHourEntry selected = scheduleTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showErrorMessage("Please select a schedule to edit.");
            return;
        }

        studentNameField.setText(selected.getStudentName());
        scheduleDatePicker.setValue(selected.getParsedDate());
        timeSlotComboBox.setValue(selected.getTimeSlot());
        courseComboBox.setValue(selected.getCourse());
        reasonField.setText(selected.getReason());
        commentField.setText(selected.getComment());

        if (editFormLabel != null) {
            editFormLabel.setText("Editing " + selected.getStudentName() + "'s Office Hours Schedule");
        }

        toggleVisibility(editForm);
    }

    @FXML
    private void handleSaveEdit() {
        OfficeHourEntry selected = scheduleTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showErrorMessage("No entry selected to save.");
            return;
        }

        selected.setStudentName(studentNameField.getText());
        selected.setScheduleDate(scheduleDatePicker.getValue().toString());
        selected.setTimeSlot(timeSlotComboBox.getValue());
        selected.setCourse(courseComboBox.getValue());
        selected.setReason(reasonField.getText());
        selected.setComment(commentField.getText());

        saveListToCSV();

        sortList(fullList);
        filteredList.setAll(fullList);
        toggleVisibility(editForm);
    }

    private void saveListToCSV() {
        File file = new File("data/office_hour_schedule.csv");

        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("Student Name,Schedule Date,Time Slot,Course,Reason,Comment");
            for (OfficeHourEntry entry : fullList) {
                writer.printf("%s,%s,%s,%s,%s,%s%n",
                        entry.getStudentName(),
                        entry.getScheduleDate(),
                        entry.getTimeSlot(),
                        entry.getCourse(),
                        entry.getReason(),
                        entry.getComment());
            }
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Failed to save changes to schedule.");
        }
    }

    @FXML
    private void toggleVisibility(Node node) {
        node.setVisible(!node.isVisible());
    }

    private void loadTimeSlotsFromCSV() {
        File file = new File("data/time_slots.csv");
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) scanner.nextLine();

            List<String[]> slotList = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data.length >= 2) {
                    slotList.add(new String[]{data[0].trim(), data[1].trim()});
                }
            }

            slotList.sort(Comparator.comparing(slot -> LocalTime.parse(slot[0])));

            timeSlots.clear();
            for (String[] slot : slotList) {
                timeSlots.add(slot[0] + " - " + slot[1]);
            }

            timeSlotComboBox.setItems(timeSlots);
        } catch (IOException | DateTimeParseException e) {
            e.printStackTrace();
            showErrorMessage("Failed to load time slots: " + e.getMessage());
        }
    }

    private void loadCoursesFromCSV() {
        File file = new File("data/course_info.csv");
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) scanner.nextLine();

            courses.clear();
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data.length >= 3) {
                    String course = data[0] + "-" + data[2];
                    courses.add(course);
                }
            }

            courseComboBox.setItems(courses);
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Failed to load courses: " + e.getMessage());
        }
    }
}
