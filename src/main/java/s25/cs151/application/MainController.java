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

    public void initialize() {
        // Initialize the combo box with the semesters and set the default value
        semesterComboBox.getItems().addAll("Spring", "Summer", "Fall", "Winter");
        semesterComboBox.setValue("Spring");
    }
    
    // Event handler for submit button
    @FXML
    private void submitOfficeHours() {
        // Get the values
        String semester = semesterComboBox.getValue();
        String year = yearTextField.getText();

        // Validate year input is 4 digits
        if(!year.matches("\\d{4}")) {
            showErrorMessage("You must enter a valid year.");
            return;
        }

        // Get the selected days
        String selectedDays = getSelectedDays();

        // Show an error message if no days are selected
        if(selectedDays.isEmpty()) {
            showErrorMessage("You must select at least one day.");
            return;
        }
        
        String officeHourCombination = semester + " " + year + ": " + selectedDays;
        
        // Show a successful submission message
        showSuccessMessage("Your office hours for " + officeHourCombination + " have been submitted successfully.");      
    }

    private String getSelectedDays() {
        String days = "";
        if(mondayCheckBox.isSelected()) days += "Monday, ";
        if(tuesdayCheckBox.isSelected()) days += "Tuesday, ";
        if(wednesdayCheckBox.isSelected()) days += "Wednesday, ";
        if(thursdayCheckBox.isSelected()) days += "Thursday, ";
        if(fridayCheckBox.isSelected()) days += "Friday, ";

        // Remove any extra comma and space
        if(days.endsWith(", ")) {
            days = days.substring(0, days.length() - 2);
        }
        return days;
    }

    // Show an error message
    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Show a success message
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
