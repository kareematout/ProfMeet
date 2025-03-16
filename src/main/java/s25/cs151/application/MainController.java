package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {
    @FXML
    private ComboBox<String> semesterComboBox;

    @FXML
    private TextField yearTextField;

    @FXML 
    private CheckBox mondayCheckBox, tuesdayCheckBox, wednesdayCheckBox, thursdayCheckBox, fridayCheckBox;

    @FXML
    private Button submitButton;

}