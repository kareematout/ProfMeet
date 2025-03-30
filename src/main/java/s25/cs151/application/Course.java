package s25.cs151.application;

public class Course {
    private String courseCode;
    private String courseName;
    private String sectionNumber;

    public Course(String courseCode, String courseName, String sectionNumber) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.sectionNumber = sectionNumber;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getSectionNumber() {
        return sectionNumber;
    }
}
