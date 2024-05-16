package app.model.course;

public class Course {
    private String courseId;
    private String courseName;
    private String creditNumber;

    public Course() {
    }

    public Course(String courseId, String courseName, String creditNumber) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.creditNumber = creditNumber;
    }

    public String getCourseId() {
        return this.courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCreditNumber() {
        return this.creditNumber;
    }

    public void setCreditNumber(String creditNumber) {
        this.creditNumber = creditNumber;
    }

    public Course courseId(String courseId) {
        setCourseId(courseId);
        return this;
    }

    public Course courseName(String courseName) {
        setCourseName(courseName);
        return this;
    }

    public Course creditNumber(String creditNumber) {
        setCreditNumber(creditNumber);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                " courseId='" + getCourseId() + "'" +
                ", courseName='" + getCourseName() + "'" +
                ", creditNumber='" + getCreditNumber() + "'" +
                "}";
    }

}
