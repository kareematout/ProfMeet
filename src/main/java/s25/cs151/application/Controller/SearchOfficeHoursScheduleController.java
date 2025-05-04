package s25.cs151.application.Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import s25.cs151.application.Model.OfficeHourEntry;

public class SearchOfficeHoursScheduleController extends NavigationController {

    @FXML private TableView<OfficeHourEntry> scheduleTable;
    @FXML private TableColumn<OfficeHourEntry, String> nameCol;
    @FXML private TableColumn<OfficeHourEntry, String> dateCol;
    @FXML private TableColumn<OfficeHourEntry, String> slotCol;
    @FXML private TableColumn<OfficeHourEntry, String> courseCol;
    @FXML private TableColumn<OfficeHourEntry, String> reasonCol;
    @FXML private TableColumn<OfficeHourEntry, String> commentCol;
    @FXML private TextField searchField;
    @FXML private Button deleteButton;

    private final ObservableList<OfficeHourEntry> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        nameCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getStudentName()));
        dateCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getScheduleDate()));
        slotCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getTimeSlot()));
        courseCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getCourse()));
        reasonCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getReason()));
        commentCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getComment()));

        loadScheduleData();
        scheduleTable.setItems(masterData);

        searchField.setOnAction(event -> handleSearch());
    }

    private void loadScheduleData() {
        File file = new File("data/office_hour_schedule.csv");
        if (!file.exists()) {
            System.out.println("office_hour_schedule.csv not found!");
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) scanner.nextLine(); // skip header

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",", -1);

                if (fields.length >= 5) {
                    String comment = (fields.length >= 6) ? fields[5].trim() : "";

                    OfficeHourEntry entry = new OfficeHourEntry(
                            fields[0].trim(),
                            fields[1].trim(),
                            fields[2].trim(),
                            fields[3].trim(),
                            fields[4].trim(),
                            comment
                    );
                    masterData.add(entry);
                }
            }

            masterData.sort((a, b) -> {
                int dateCompare = b.getParsedDate().compareTo(a.getParsedDate());
                if (dateCompare != 0) return dateCompare;

                return b.getStartTime().compareTo(a.getStartTime());
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().toLowerCase().trim();

        if (searchText.isEmpty()) {
            scheduleTable.setItems(masterData);
            return;
        }

        ObservableList<OfficeHourEntry> filteredData = FXCollections.observableArrayList();

        for (OfficeHourEntry entry : masterData) {
            if (entry.getStudentName().toLowerCase().contains(searchText)) {
                filteredData.add(entry);
            }
        }

        scheduleTable.setItems(filteredData);
    }

    @FXML
    private void handleDelete() {
        OfficeHourEntry selectedEntry = scheduleTable.getSelectionModel().getSelectedItem();
        if (selectedEntry == null) {
            System.out.println("Please select an entry to delete.");
            return;
        }

        masterData.remove(selectedEntry);
        scheduleTable.setItems(masterData); // Refresh table view
        saveScheduleData();
        System.out.println("Schedule successfully deleted.");
    }

    private void saveScheduleData() {
        File file = new File("data/office_hour_schedule.csv");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Student Name,Schedule Date,Time Slot,Course,Reason,Comment\n");

            for (OfficeHourEntry entry : masterData) {
                writer.write(entry.getStudentName() + "," +
                        entry.getScheduleDate() + "," +
                        entry.getTimeSlot() + "," +
                        entry.getCourse() + "," +
                        entry.getReason() + "," +
                        entry.getComment() + "\n");
            }

            System.out.println("Saved updated schedule to CSV!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save updated schedule: " + e.getMessage());
        }
    }
}
