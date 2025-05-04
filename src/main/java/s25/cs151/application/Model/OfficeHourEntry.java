package s25.cs151.application.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OfficeHourEntry {
    private String studentName, scheduleDate, timeSlot, course, reason, comment;

    public OfficeHourEntry(String studentName, String scheduleDate, String timeSlot, String course, String reason, String comment) {
        this.studentName = studentName;
        this.scheduleDate = scheduleDate;
        this.timeSlot = timeSlot;
        this.course = course;
        this.reason = reason;
        this.comment = comment;
    }

    public String getStudentName() { return studentName; }
    public String getScheduleDate() { return scheduleDate; }
    public String getTimeSlot() { return timeSlot; }
    public String getCourse() { return course; }
    public String getReason() { return reason; }
    public String getComment() { return comment; }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getParsedDate() {
        return LocalDate.parse(scheduleDate, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public String getStartTime() {
        // Assume timeSlot is in the format "HH:MM - HH:MM"
        String[] parts = timeSlot.split(" - ");
        return parts.length > 0 ? parts[0] : "00:00";
    }
}
