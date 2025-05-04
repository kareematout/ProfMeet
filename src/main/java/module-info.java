module s25.cs151.application {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.dlsc.formsfx;

    opens s25.cs151.application to javafx.fxml;
    opens s25.cs151.application.Controller to javafx.fxml;
    opens s25.cs151.application.Model to javafx.base;

    exports s25.cs151.application;
    exports s25.cs151.application.Controller;
    exports s25.cs151.application.Model;
}
