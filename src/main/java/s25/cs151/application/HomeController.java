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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/s25/cs151/application/OfficeHoursPage.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleTimeSlots(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("TimeSlotsPage.fxml"));
            stage.setScene(new Scene(loader.load(), 1000, 600));
            stage.setTitle("ProfMeet - Time Slots");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleTimeSlotViewer(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/s25/cs151/application/TimeSlotViewer.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("ProfMeet - Stored Time Slots");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleCourseViewer(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/s25/cs151/application/CourseViewer.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("ProfMeet - Stored Courses");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void goToCoursePage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene coursePageScene = Main.coursePageScene(stage);
        stage.setScene(coursePageScene);
    }
}
