package models;

public class Course {
    private int course_id;
    private String name;
    private double attendance;
    private boolean status;

    public Course() {

    }

    public Course(int course_id, String name, double attendance, boolean status) {
        setCourse_id(course_id);
        setName(name);
        setAttendance(attendance);
        setStatus(status);
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAttendance() {
        return attendance;
    }

    public void setAttendance(double attendance) {
        this.attendance = attendance;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
