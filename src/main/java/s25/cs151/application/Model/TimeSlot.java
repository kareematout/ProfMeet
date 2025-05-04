package s25.cs151.application.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TimeSlot {

    private final StringProperty fromTime;
    private final StringProperty toTime;

    public TimeSlot(String from, String to) {
        this.fromTime = new SimpleStringProperty(from);
        this.toTime = new SimpleStringProperty(to);
    }

    // JavaFX property getters (used by TableColumn's cellValueFactory)
    public StringProperty fromTimeProperty() {
        return fromTime;
    }

    public StringProperty toTimeProperty() {
        return toTime;
    }

    // Convenience getters/setters if you also need them
    public String getFromTime() {
        return fromTime.get();
    }

    public void setFromTime(String value) {
        fromTime.set(value);
    }

    public String getToTime() {
        return toTime.get();
    }

    public void setToTime(String value) {
        toTime.set(value);
    }
}
