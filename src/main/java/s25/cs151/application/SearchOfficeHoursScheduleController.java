package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Comparator;

public class SearchOfficeHoursScheduleController extends NavigationController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<OfficeHourEntry> scheduleTable;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<OfficeHourEntry, String> nameCol;
    @FXML
    private TableColumn<OfficeHourEntry, String> dateCol;
    @FXML
    private TableColumn<OfficeHourEntry, String> slotCol;
    @FXML
    private TableColumn<OfficeHourEntry, String> courseCol;
    @FXML
    private TableColumn<OfficeHourEntry, String> reasonCol;
    @FXML
    private TableColumn<OfficeHourEntry, String> commentCol;

    private ObservableList<OfficeHourEntry> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up column
        nameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStudentName()));
        dateCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getScheduleDate()));
        slotCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTimeSlot()));
        courseCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCourse()));
        reasonCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getReason()));
        commentCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getComment()));
        loadScheduleData();
        System.out.println("Loaded entries: " + masterData.size());
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

            // Sort masterData by date, then name
            masterData.sort(Comparator
                    .comparing(OfficeHourEntry::getParsedDate) // first by date
                    .thenComparing(entry -> entry.getStudentName().toLowerCase()) // then by name ignoring case
            );


            scheduleTable.setItems(masterData);
            System.out.println("Loaded entries: " + masterData.size());

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
        OfficeHourEntry selected = scheduleTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            masterData.remove(selected);
            scheduleTable.setItems(masterData); // Refresh table view
            saveScheduleData(); // Save changes permanently
        } else {
            System.out.println("No entry selected for deletion.");
        }
    }

    private void saveScheduleData() {
        File file = new File("data/office_hour_schedule.csv");

        try (java.io.PrintWriter writer = new java.io.PrintWriter(file)) {
            // Write header first
            writer.println("Student Name,Schedule Date,Time Slot,Course,Reason,Comment");

            // Write each entry
            for (OfficeHourEntry entry : masterData) {
                writer.printf("%s,%s,%s,%s,%s,%s\n",
                        entry.getStudentName(),
                        entry.getScheduleDate(),
                        entry.getTimeSlot(),
                        entry.getCourse(),
                        entry.getReason(),
                        entry.getComment()
                );
            }

            System.out.println("Saved updated schedule to CSV!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
