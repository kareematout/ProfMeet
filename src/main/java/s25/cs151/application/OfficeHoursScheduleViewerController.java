package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.util.Scanner;

public class OfficeHoursScheduleViewerController {

    @FXML private TableView<OfficeHourEntry> scheduleTable;
    @FXML private TableColumn<OfficeHourEntry, String> nameCol;
    @FXML private TableColumn<OfficeHourEntry, String> dateCol;
    @FXML private TableColumn<OfficeHourEntry, String> slotCol;
    @FXML private TableColumn<OfficeHourEntry, String> courseCol;
    @FXML private TableColumn<OfficeHourEntry, String> reasonCol;
    @FXML private TableColumn<OfficeHourEntry, String> commentCol;

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

            // Sort by date then start time
            scheduleList.sort((a, b) -> {
                int dateComparison = a.getParsedDate().compareTo(b.getParsedDate());
                if (dateComparison != 0) return dateComparison;
                return a.getStartTime().compareTo(b.getStartTime());
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
