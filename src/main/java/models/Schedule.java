package models;

public class Schedule {
    private int schedule_id;
    private int course_id;
    private int user_id;
    private String day;
    private String time_slot;

    public Schedule() {}

    public Schedule(int course_id, int user_id, String day, String time_slot) {
        this.course_id = course_id;
        this.user_id = user_id;
        this.day = day;
        this.time_slot = time_slot;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime_slot() {
        return time_slot;
    }

    public void setTime_slot(String time_slot) {
        this.time_slot = time_slot;
    }
}
