package s25.cs151.application.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import s25.cs151.application.Model.OfficeHourEntry;

public class EditOfficeHoursPageController extends SearchOfficeHoursScheduleController {

    @FXML private StackPane editForm = new StackPane();
    @FXML private TableView<OfficeHourEntry> scheduleTable;

    @Override
    public void initialize() {
        super.initialize();
    }

    @FXML
    private void handleEdit() {
        OfficeHourEntry selectedEntry = scheduleTable.getSelectionModel().getSelectedItem();
        if (selectedEntry == null) {
            System.out.println("Please select an entry to Edit.");
            return;
        }
        editForm.setVisible(true);
    }
}
