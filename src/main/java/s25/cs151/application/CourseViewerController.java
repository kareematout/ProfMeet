package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CourseViewerController extends NavigationController{

    @FXML
    private TableView<Course> courseTable;

    @FXML
    private TableColumn<Course, String> courseCodeColumn;

    @FXML
    private TableColumn<Course, String> courseNameColumn;

    @FXML
    private TableColumn<Course, String> sectionNumberColumn;

    private final ObservableList<Course> coursesList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        sectionNumberColumn.setCellValueFactory(new PropertyValueFactory<>("sectionNumber"));

        // Load existing courses from CSV or data source
        loadCoursesFromCSV();

        // Sort by Course Code in descending order
        sortCoursesByCodeDescending();

        // Set items in TableView
        courseTable.setItems(coursesList);
    }

    private void sortCoursesByCodeDescending() {
        // Sort courses by course code in descending order
        coursesList.sort((course1, course2) -> course2.getCourseCode().compareTo(course1.getCourseCode()));
    }

    private void loadCoursesFromCSV() {
        File file = new File("data/course_info.csv");

        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // skip header
                }

                String[] parts = line.split(",", 3); // split into 3 parts
                if (parts.length == 3) {
                    Course course = new Course(parts[0].trim(), parts[1].trim(), parts[2].trim());
                    coursesList.add(course);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
