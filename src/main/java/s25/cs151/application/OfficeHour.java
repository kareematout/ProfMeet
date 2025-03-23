package s25.cs151.application;

public class OfficeHour {
    private String semester;
    private String year;
    private String days;

    // Constructor
    public OfficeHour(String semester, String year, String days) {
        this.semester = semester;
        this.year = year;
        this.days = days;
    }

    // Getter for semester
    public String getSemester() {
        return semester;
    }

    // Setter for semester
    public void setSemester(String semester) {
        this.semester = semester;
    }

    // Getter for year
    public String getYear() {
        return year;
    }

    // Setter for year
    public void setYear(String year) {
        this.year = year;
    }

    // Getter for days
    public String getDays() {
        return days;
    }

    // Setter for days
    public void setDays(String days) {
        this.days = days;
    }
}
