package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.Scanner;

public class MainController extends NavigationController{

    @FXML
    private ComboBox<String> semesterComboBox;

    @FXML
    private TextField yearTextField;

    @FXML
    private CheckBox mondayCheckBox, tuesdayCheckBox, wednesdayCheckBox, thursdayCheckBox, fridayCheckBox;

    @FXML
    private ObservableList<OfficeHour> officeHoursList = FXCollections.observableArrayList();

    public void initialize() {
        // Initialize the combo box with semester options
        semesterComboBox.getItems().addAll("Spring", "Summer", "Fall", "Winter");
        semesterComboBox.setValue("Spring");

        // Load existing office hours from CSV
        loadOfficeHoursFromCSV();
    }

    // Event handler for submit button
    @FXML
    private void submitOfficeHours() {
        // Get values from form
        String semester = semesterComboBox.getValue();
        String year = yearTextField.getText();

        // Validate year input (must be 4-digit number)
        if (!year.matches("\\d{4}")) {
            showErrorMessage("You must enter a valid year (e.g., 2025).");
            return;
        }

        // Get selected days
        String selectedDays = getSelectedDays();

        // Show an error message if no days are selected
        if (selectedDays.isEmpty()) {
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

        // Add new office hour to the list
        OfficeHour officeHour = new OfficeHour(semester, year, selectedDays);
        officeHoursList.add(officeHour);

        // Show success message
        showSuccessMessage("Your office hours for " + semester + " " + year + " have been submitted successfully.");
    }

    // Get selected days from checkboxes
    private String getSelectedDays() {
        String days = "";
        if(mondayCheckBox.isSelected()) days += "Monday, ";
        if(tuesdayCheckBox.isSelected()) days += "Tuesday, ";
        if(wednesdayCheckBox.isSelected()) days += "Wednesday, ";
        if(thursdayCheckBox.isSelected()) days += "Thursday, ";
        if(fridayCheckBox.isSelected()) days += "Friday, ";

        // Remove trailing comma and space
        if(days.endsWith(", ")) {
            days = days.substring(0, days.length() - 2);
        }
        return days;
    }

    // Check for duplicate semester-year entries
    private boolean isDuplicateEntry(String semester, String year) {
        for (OfficeHour officeHour : officeHoursList) {
            if (officeHour.getSemester().equals(semester) &&
                    officeHour.getYear().equals(year)) {
                return true; // Duplicate found
            }
        }
        return false; // No duplicate found
    }

    // Save office hours to CSV file
    private void saveToCSV(String semester, String year, String days) {
        File file = new File("data/office_hours.csv");
        try {
            // Ensure directory exists
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            // Create file if it does not exist
            if (!file.exists()) {
                file.createNewFile();
            }

            // Append data to the CSV file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                if (file.length() == 0) {
                    writer.write("Semester,Year,Day(s)\n"); // Write header if empty
                }
                writer.write(semester + "," + year + "," + days + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Failed to save office hours: " + e.getMessage());
        }
    }

    // Load existing office hours from CSV
    private void loadOfficeHoursFromCSV() {
        File file = new File("data/office_hours.csv");
        if (!file.exists()) return; // No file to load

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) scanner.nextLine(); // Skip header

            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data.length >= 2) {
                    String semester = data[0];
                    String year = data[1];
                    OfficeHour officeHour = new OfficeHour(semester, year, "");
                    officeHoursList.add(officeHour); // Add only semester and year
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
