package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class OfficeHoursScheduleController extends NavigationController {

    @FXML private TextField studentNameField;
    @FXML private TextArea reasonField, commentField;
    @FXML private DatePicker scheduleDatePicker;
    @FXML private ComboBox<String> timeSlotComboBox, courseComboBox;

    private ObservableList<String> timeSlots = FXCollections.observableArrayList();
    private ObservableList<String> courses = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Load time slots and courses from CSV files
        loadTimeSlotsFromCSV();
        loadCoursesFromCSV();

        // Set default values for combo boxes
        if (!timeSlots.isEmpty()) {
            timeSlotComboBox.getSelectionModel().select(0);  // Select the first time slot
        }
        if (!courses.isEmpty()) {
            courseComboBox.getSelectionModel().select(0);  // Select the first course
        }

        // Set the default date as today
        scheduleDatePicker.setValue(java.time.LocalDate.now());
    }

    // Event handler for submit button
    @FXML
    private void submitSchedule(ActionEvent event) {
        String studentName = studentNameField.getText().trim();
        String reason = reasonField.getText().trim();
        String comment = commentField.getText().trim();
        String scheduleDate = scheduleDatePicker.getValue().toString();
        String timeSlot = timeSlotComboBox.getValue();
        String course = courseComboBox.getValue();

        // Validate inputs
        if (studentName.isEmpty()) {
            showErrorMessage("Student's full name is required.");
            return;
        }
        if (timeSlot == null || timeSlot.isEmpty()) {
            showErrorMessage("Please select a time slot.");
            return;
        }
        if (course == null || course.isEmpty()) {
            showErrorMessage("Please select a course.");
            return;
        }

        // Save office hours schedule to CSV
        saveToCSV(studentName, scheduleDate, timeSlot, course, reason, comment);

        showSuccessMessage("Office hours schedule successfully added.");
    }

    // Save office hours schedule to CSV
    private void saveToCSV(String studentName, String scheduleDate, String timeSlot, String course,
                           String reason, String comment) {
        File file = new File("data/office_hour_schedule.csv");
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
                    writer.write("Student Name,Schedule Date,Time Slot,Course,Reason,Comment\n");
                }
                writer.write(studentName + "," + scheduleDate + "," + timeSlot + "," + course + "," + reason + "," + comment + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Failed to save office hours schedule: " + e.getMessage());
        }
    }

    // Load time slots from CSV
    private void loadTimeSlotsFromCSV() {
        File file = new File("data/time_slots.csv");
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) scanner.nextLine(); // Skip the header

            List<String[]> slotList = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data.length >= 2) {
                    slotList.add(new String[]{data[0].trim(), data[1].trim()});
                }
            }

            // Sort by fromTime
            slotList.sort(Comparator.comparing(slot -> LocalTime.parse(slot[0])));

            // Clear and add to observable list
            timeSlots.clear();
            for (String[] slot : slotList) {
                timeSlots.add(slot[0] + " - " + slot[1]);
            }

            timeSlotComboBox.setItems(timeSlots);

        } catch (IOException | DateTimeParseException e) {
            e.printStackTrace();
            showErrorMessage("Failed to load time slots: " + e.getMessage());
        }
    }

    // Load courses from CSV
    private void loadCoursesFromCSV() {
        File file = new File("data/course_info.csv");
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) scanner.nextLine();  // Skip the header line

            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data.length >= 3) {
                    String course = data[0] + "-" + data[2];  // Course Code - Section
                    courses.add(course);
                }
            }
            courseComboBox.setItems(courses);
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Failed to load courses: " + e.getMessage());
        }
    }
}
