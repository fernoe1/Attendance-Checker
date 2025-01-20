package models;

public class Course {
    private int course_id;
    private String name;
    private Double attendance;

    public Course(){

    }

    public Course(int course_id,String name,Double attendance){
        this.course_id = course_id;
        this.name = name;
        this.attendance = attendance;
    }
}
