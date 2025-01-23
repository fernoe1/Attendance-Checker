package models;

public class Course {
    private int course_id;
    private String name;
    private Double attendance;
    private long endDate;

    public Course() {

    }

    public Course(int course_id,String name,Double attendance,long startDate,long endDate) {
        setCourse_id(course_id);
        setName(name);
        setAttendance(attendance);
        setEndDate(endDate);
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

    public void setAttendance(Double attendance) {
        this.attendance = attendance;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }
}
