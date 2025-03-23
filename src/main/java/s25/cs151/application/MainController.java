package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MainController {
    
    @FXML
    private ComboBox<String> semesterComboBox;

    @FXML
    private TextField yearTextField;

    @FXML 
    private CheckBox mondayCheckBox, tuesdayCheckBox, wednesdayCheckBox, thursdayCheckBox, fridayCheckBox;

    @FXML
    private ObservableList<OfficeHour> officeHoursList = FXCollections.observableArrayList();

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

        // Check for duplicate semester-year combination
        if (isDuplicateEntry(semester, year)) {
            showErrorMessage("This semester and year combination already exists.");
            return;
        }

        // Save the data to CSV
        saveToCSV(semester, year, selectedDays);

        // Create a new OfficeHour object and add it to the list
        OfficeHour officeHour = new OfficeHour(semester, year, selectedDays);
        officeHoursList.add(officeHour);
        
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

    // Method to check for duplicate semester-year combination
    private boolean isDuplicateEntry(String semester, String year) {
        for (OfficeHour officeHour : officeHoursList) {
            if (officeHour.getSemester().equals(semester) && officeHour.getYear().equals(year)) {
                return true; // Duplicate found
            }
        }
        return false; // No duplicate found
    }

    // Method to save data to a CSV file
    private void saveToCSV(String semester, String year, String days) {
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/office_hours.csv");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            // Write the header if the file is empty
            if (file.length() == 0) {
                writer.write("Semester,Year,Day(s)\n");
            }

            // Write the user input (semester, year, and days)
            writer.write(semester + "," + year + "," + days + "\n");

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error (could show an alert in the UI)
        }
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
    @FXML
    private void handleOfficeHours(ActionEvent event) {
        // Get the current stage from the event source
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        // Use Main's method to create the Office Hours scene
        Scene officeHoursScene = Main.OfficeHoursScene(stage);
        stage.setScene(officeHoursScene);
    }


}
