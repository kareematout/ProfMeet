package s25.cs151.application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SearchOfficeHoursScheduleController extends NavigationController {

    @FXML private TableView<OfficeHourEntry> scheduleTable;
    @FXML private TableColumn<OfficeHourEntry, String> nameCol;
    @FXML private TableColumn<OfficeHourEntry, String> dateCol;
    @FXML private TableColumn<OfficeHourEntry, String> slotCol;
    @FXML private TableColumn<OfficeHourEntry, String> courseCol;
    @FXML private TableColumn<OfficeHourEntry, String> reasonCol;
    @FXML private TableColumn<OfficeHourEntry, String> commentCol;
    @FXML private TextField searchField;

    private final ObservableList<OfficeHourEntry> scheduleList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        nameCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getStudentName()));
        dateCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getScheduleDate()));
        slotCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getTimeSlot()));
        courseCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getCourse()));
        reasonCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getReason()));
        commentCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getComment()));

        loadCSVData();
        scheduleTable.setItems(scheduleList);
    }

    private void loadCSVData() {
        File file = new File("data/office_hour_schedule.csv");
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) scanner.nextLine(); // Skip header

            while (scanner.hasNextLine()) {
                String[] row = scanner.nextLine().split(",", -1);
                if (row.length >= 6) {
                    scheduleList.add(new OfficeHourEntry(row[0], row[1], row[2], row[3], row[4], row[5]));
                }
            }

            // Sort by date (descending) then time slot (descending)
            scheduleList.sort((a, b) -> {
                // First, compare by the schedule date in descending order
                int dateComparison = b.getParsedDate().compareTo(a.getParsedDate()); // Descending order
                if (dateComparison != 0) return dateComparison;

                // If dates are the same, compare by start time in descending order
                return b.getStartTime().compareTo(a.getStartTime()); // Descending order
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteSchedule() {
        OfficeHourEntry selectedEntry = scheduleTable.getSelectionModel().getSelectedItem();
        if (selectedEntry == null) {
            showErrorMessage("Please select an entry to delete.");
            return;
        }

        scheduleList.remove(selectedEntry);
        saveCSVData();
        showSuccessMessage("Schedule successfully deleted.");
    }

    private void saveCSVData() {
        File file = new File("data/office_hour_schedule.csv");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Student Name,Schedule Date,Time Slot,Course,Reason,Comment\n");
            for (OfficeHourEntry entry : scheduleList) {
                writer.write(entry.getStudentName() + "," +
                        entry.getScheduleDate() + "," +
                        entry.getTimeSlot() + "," +
                        entry.getCourse() + "," +
                        entry.getReason() + "," +
                        entry.getComment() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Failed to save updated schedule: " + e.getMessage());
        }
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase().trim();

        if (keyword.isEmpty()) {
            scheduleTable.setItems(scheduleList); // Reset the table if search field is empty
            return;
        }

        ObservableList<OfficeHourEntry> filteredList = FXCollections.observableArrayList();
        for (OfficeHourEntry entry : scheduleList) {
            if (entry.getStudentName().toLowerCase().contains(keyword)) {
                filteredList.add(entry);
            }
        }

        scheduleTable.setItems(filteredList); // Update the table with filtered items
    }
}
