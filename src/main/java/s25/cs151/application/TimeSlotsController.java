package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;

public class TimeSlotsController extends NavigationController {

    final private ObservableList<TimeSlot> timeSlots = FXCollections.observableArrayList();
    @FXML private ComboBox<String> fromTimeSlotComboBox;
    @FXML private ComboBox<String> toTimeSlotComboBox;

    @FXML
    public void initialize() {
        fillTimeSlotComboBox(fromTimeSlotComboBox);
        fillTimeSlotComboBox(toTimeSlotComboBox);
    }

    private void fillTimeSlotComboBox(ComboBox<String> comboBox) {
        String[] minuteRange = {"00", "15", "30", "45"};
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < 24; i++) {
            for (String s : minuteRange) {
                if (i < 10) {
                    current.append(0);
                }
                current.append(i);
                current.append(":");
                current.append(s);
                comboBox.getItems().add(current.toString());
                current.setLength(0);
            }
        }
    }

    @FXML
    private void onSubmitTimeSlots(ActionEvent event) {
        if (fromTimeSlotComboBox.getSelectionModel().getSelectedItem() == null ||
                toTimeSlotComboBox.getSelectionModel().getSelectedItem() == null) {
            showErrorMessage("Please select from/to");
            return;
        }
        TimeSlot timeSlot = new TimeSlot(fromTimeSlotComboBox.getValue(), toTimeSlotComboBox.getValue());
        timeSlots.add(timeSlot);

        FXCollections.sort(timeSlots, Comparator.comparing(TimeSlot::getFromTime));

        saveTimeSlotsToCSV(timeSlot);
        showSuccessMessage("TimeSlots saved");
    }

    private void saveTimeSlotsToCSV(TimeSlot timeSlot) {
        File file = new File("data/time_slots.csv");
        try {
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                if (file.length() == 0) {
                    writer.write("From Time,To Time\n");
                }
                writer.write(timeSlot.getFromTime() + "," + timeSlot.getToTime() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Failed to save course: " + e.getMessage());
        }
    }
}
