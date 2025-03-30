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

public class CourseController {

    @FXML
    private TextField courseCodeField, courseNameField, sectionField;

    @FXML
    private ObservableList<Course> courseList = FXCollections.observableArrayList();

    public void initialize() {
        // Load existing courses from CSV
        loadCoursesFromCSV();
    }

    // Event handler for submit button
    @FXML
    private void submitCourse() {
        String courseCode = courseCodeField.getText().trim();
        String courseName = courseNameField.getText().trim();
        String section = sectionField.getText().trim();

        // Validate inputs
        if (courseCode.isEmpty() || courseName.isEmpty() || section.isEmpty()) {
            showErrorMessage("All fields must be filled.");
            return;
        }

        // Check for duplicate course entries
        if (isDuplicateEntry(courseCode, courseName, section)) {
            showErrorMessage("This course entry already exists.");
            return;
        }

        // Save the course to CSV
        saveToCSV(courseCode, courseName, section);

        // Add new course to the list
        Course course = new Course(courseCode, courseName, section);
        courseList.add(course);

        showSuccessMessage("Course successfully added.");
    }

    // Check for duplicate course entries
    private boolean isDuplicateEntry(String courseCode, String courseName, String section) {
        for (Course course : courseList) {
            if (course.getCourseCode().equals(courseCode) &&
                    course.getCourseName().equals(courseName) &&
                    course.getSectionNumber().equals(section)) {
                return true;
            }
        }
        return false;
    }

    // Save course to CSV file
    private void saveToCSV(String courseCode, String courseName, String section) {
        File file = new File("data/course_info.csv");
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
                    writer.write("Course Code,Course Name,Section\n");
                }
                writer.write(courseCode + "," + courseName + "," + section + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Failed to save course: " + e.getMessage());
        }
    }

    // Load existing courses from CSV
    private void loadCoursesFromCSV() {
        File file = new File("data/course_info.csv");
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) scanner.nextLine();

            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data.length >= 3) {
                    String courseCode = data[0];
                    String courseName = data[1];
                    String section = data[2];
                    Course course = new Course(courseCode, courseName, section);
                    courseList.add(course);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
    private void goToHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CoursePage.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 600);
            Stage stage = (Stage) courseCodeField.getScene().getWindow();
            stage.setTitle("ProfMeet - Home");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
