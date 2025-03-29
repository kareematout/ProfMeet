package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class TimeSlotsController extends NavigationController {

    private ObservableList<TimeSlot> timeSlots = FXCollections.observableArrayList();
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
    void onSubmitTimeSlots(TimeSlot timeSlot) {
        timeSlots.add(timeSlot);
    }
}
