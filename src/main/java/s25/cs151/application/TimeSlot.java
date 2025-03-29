package s25.cs151.application;

public class TimeSlot {
    private String fromHour;
    private String toHour;

    public TimeSlot(String fromHour, String toHour) {
        this.fromHour = fromHour;
        this.toHour = toHour;
    }

    // getters
    public String getFromHour() { return fromHour; }
    public String getToHour() { return toHour; }

    // setters
    public void setFromHour(String fromHour) { this.fromHour = fromHour; }
    public void setToHour(String toHour) { this.toHour = toHour; }
}
