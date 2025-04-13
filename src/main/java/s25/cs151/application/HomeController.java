package s25.cs151.application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class HomeController {

    @FXML
    private void handleOfficeHours(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene officeHoursScene = Main.OfficeHoursScene(stage);
        stage.setScene(officeHoursScene);
    }

    @FXML
    private void handleViewStoredOfficeHours(ActionEvent event) {
        handleSceneSwitch(event, "/s25/cs151/application/OfficeHoursPage.fxml", "ProfMeet - Stored Office Hours");
    }

    @FXML
    private void handleTimeSlots(ActionEvent event) {
        handleSceneSwitch(event, "/s25/cs151/application/TimeSlotsPage.fxml", "ProfMeet - Time Slots");
    }

    @FXML
    private void handleTimeSlotViewer(ActionEvent event) {
        handleSceneSwitch(event, "/s25/cs151/application/TimeSlotViewer.fxml", "ProfMeet - Stored Time Slots");
    }

    @FXML
    private void handleCourseViewer(ActionEvent event) {
        handleSceneSwitch(event, "/s25/cs151/application/CourseViewer.fxml", "ProfMeet - Stored Courses");
    }

    @FXML
    private void handleCourses(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene coursePageScene = Main.coursePageScene(stage);
        stage.setScene(coursePageScene);
    }

    @FXML
    private void handleOfficeHoursSchedule(ActionEvent event) {
        handleSceneSwitch(event, "/s25/cs151/application/OfficeHoursSchedulePage.fxml", "ProfMeet - Office Hours Schedule");
    }

    @FXML
    private void handleOfficeHoursScheduleViewer(ActionEvent event) {
        handleSceneSwitch(event, "/s25/cs151/application/OfficeHoursScheduleTableViewer.fxml", "ProfMeet - Office Hours Schedule Viewer");
    }


    private void handleSceneSwitch(ActionEvent event, String fxmlPath, String windowTitle) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(windowTitle);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
