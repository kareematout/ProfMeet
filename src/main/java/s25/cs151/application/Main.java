package s25.cs151.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);

        //test new page
        Button testPageButton = new Button("test");
        testPageButton.setOnAction(event -> {
            Scene officeHoursScene = OfficeHoursScene(stage);
            stage.setScene(officeHoursScene);
        });
        scene.setRoot(testPageButton);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    private Scene OfficeHoursScene(Stage stage) {
        Label header = new Label("Create Office\nHours");
        header.setStyle("-fx-font-weight: bold; -fx-font-size: 50px;");

        HBox profEmail = createEmailHbox("Professor Email:", "professor@sjsu.edu");
        HBox stuEmail = createEmailHbox("Student Email:", "student@sjsu.edu");

        VBox emails = new VBox(profEmail, stuEmail);
        emails.setSpacing(5);
        emails.setMaxWidth(300);

        VBox layout = new VBox(header, emails);
        layout.setStyle("-fx-padding: 100 0 0 120;");
        layout.setSpacing(50);

        return new Scene(layout, 1000, 600);
    }

    private HBox createEmailHbox(String label, String email) {
        Text labelText = new Text(label + "\n");
        Text emailText = new Text(email);
        emailText.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

        TextFlow emailFlow = new TextFlow(labelText, emailText);
        emailFlow.setPrefWidth(350);

        ImageView emailIcon = new ImageView(new Image(
                getClass().getResourceAsStream("/images/email-icon.png"),
                40,
                40,
                true,
                true));

        HBox layout = new HBox(emailIcon, emailFlow);
        layout.setSpacing(15);

        String emailStyle =
                "-fx-font-size: 15px;" +
                " -fx-background-color: rgba(100, 0, 100, 0.05);" +
                " -fx-padding: 20px;" +
                " -fx-background-radius: 20px;";

        layout.setStyle(emailStyle);

        return layout;
    }

    public static void main(String[] args) {
        launch();
    }
}