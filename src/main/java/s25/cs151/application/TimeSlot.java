package s25.cs151.application;

public class TimeSlot {
    private String fromTime;
    private String toTime;

    public TimeSlot(String fromHour, String toTime) {
        this.fromTime = fromHour;
        this.toTime = toTime;
    }

    // getters
    public String getFromTime() { return fromTime; }
    public String getToTime() { return toTime; }

    // setters
    public void setFromHour(String fromHour) { this.fromTime = fromHour; }
    public void setToTime(String toTime) { this.toTime = toTime; }
}
