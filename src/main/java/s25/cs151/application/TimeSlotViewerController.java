package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * A controller class for TimeSlotViewer.fxml
 * that reads data from a CSV file in the data/ folder
 * and displays it in a TableView.
 */
public class TimeSlotViewerController extends NavigationController {

    @FXML
    private TableView<TimeSlot> timeSlotTable;

    @FXML
    private TableColumn<TimeSlot, String> fromTimeColumn;

    @FXML
    private TableColumn<TimeSlot, String> toTimeColumn;

    // Use an ObservableList so the UI updates automatically when items change
    private final ObservableList<TimeSlot> timeSlotList = FXCollections.observableArrayList();

    /**
     * Called automatically by JavaFX after the FXML file is loaded.
     * Sets up the table columns to use properties from the TimeSlot class.
     */
    @FXML
    public void initialize() {
        // Link the TableColumn to the properties in the TimeSlot model
        fromTimeColumn.setCellValueFactory(
                cellData -> cellData.getValue().fromTimeProperty()
        );
        toTimeColumn.setCellValueFactory(
                cellData -> cellData.getValue().toTimeProperty()
        );

        // Assign the ObservableList to the table
        timeSlotTable.setItems(timeSlotList);
        loadTimesFromCSV();
    }

    /**
     * Reads time slot data from data/time_slots.csv and populates the TableView.
     */
    private void loadTimesFromCSV() {
        File file = new File("data/time_slots.csv");
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) scanner.nextLine();

            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data.length >= 2) {
                    String fromTime = data[0];
                    String toTime = data[1];
                    TimeSlot timeslot = new TimeSlot(fromTime, toTime);
                    timeSlotList.add(timeslot);
                }
            }

            FXCollections.sort(timeSlotList, Comparator.comparing(TimeSlot::getFromTime));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
