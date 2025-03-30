package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * A controller class for TimeSlotViewer.fxml
 * that reads data from a CSV file in the data/ folder
 * and displays it in a TableView.
 */
public class TimeSlotViewerController {

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
    }

    /**
     * Reads time slot data from data/time_slots.csv and populates the TableView.
     * Triggered by a button in TimeSlotViewer.fxml with onAction="#loadData".
     */
    @FXML
    public void loadData() {
        // Change the file path if necessary
        String filePath = "data/time_slots.csv";

        // Clear existing data in case the user clicks the button multiple times
        timeSlotList.clear();

        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            // Skip the header row if your CSV has a header
            lines.skip(1)
                    .map(line -> line.split(","))
                    .filter(values -> values.length == 2) // Must have 2 columns
                    .forEach(values -> {
                        String fromTime = values[0].trim();
                        String toTime = values[1].trim();
                        timeSlotList.add(new TimeSlot(fromTime, toTime));
                    });

        } catch (IOException e) {
            e.printStackTrace();
            // You might want to display an alert dialog here
        }
    }
}
