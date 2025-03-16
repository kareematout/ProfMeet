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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("HomePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);

        stage.setTitle("ProfMeet - Home");
        stage.setScene(scene);
        stage.show();

    }

    public static Scene OfficeHoursScene(Stage stage) {
        Label header = new Label("Create Office\nHours");
        header.setStyle("-fx-font-weight: bold; -fx-font-size: 50px;");

        HBox profEmail = CreateEmailHBox("Professor Email:", "professor@sjsu.edu");
        HBox stuEmail = CreateEmailHBox("Student Email:", "student@sjsu.edu");

        VBox emails = new VBox(profEmail, stuEmail);
        emails.setSpacing(5);
        emails.setMaxWidth(300);

        VBox form = CreateFormVBox();


        VBox infoPanel = new VBox(header, emails);
        infoPanel.setStyle("-fx-padding: 100 0 0 120;");
        infoPanel.setSpacing(50);

        HBox layout = new HBox(infoPanel, form);
        layout.setSpacing(50);

        return new Scene(layout, 1000, 600);
    }

    private static HBox CreateEmailHBox(String label, String email) {
        Text labelText = new Text(label + "\n");
        Text emailText = new Text(email);
        emailText.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

        TextFlow emailFlow = new TextFlow(labelText, emailText);
        emailFlow.setPrefWidth(350);

        ImageView emailIcon = new ImageView(new Image(
                Main.class.getResourceAsStream("/images/email-icon.png"),
                40,
                40,
                true,
                true));

        HBox layout = new HBox(emailIcon, emailFlow);
        layout.setSpacing(15);

        String emailStyle =
                "-fx-font-size: 15px;" +
                " -fx-background-color: rgba(100, 0, 100, 0.05);" +
                " -fx-padding: 50 20 20 20px;" +
                " -fx-background-radius: 20px;";

        layout.setStyle(emailStyle);

        return layout;
    }

    private static VBox CreateFormVBox() {
        try{
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainView.fxml"));
            VBox dropdownContent = loader.load();
            VBox layout = new VBox();
            layout.setStyle(dropdownContent.getStyle());
            layout.setSpacing(dropdownContent.getSpacing());
            layout.setAlignment(dropdownContent.getAlignment());
            layout.getChildren().addAll(dropdownContent.getChildren());
            return layout;
        }
        catch (IOException e) {
            // Handle the exception
            e.printStackTrace();
            return new VBox(new Text("Error loading form"));
        }
    }

    public static void main(String[] args) {
        launch();
    }
}