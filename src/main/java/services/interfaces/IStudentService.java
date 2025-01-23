package services.interfaces;

import models.Course;
import models.Schedule;
import models.Student;

import java.util.ArrayList;

public interface IStudentService {

    boolean doesUserExist();

    void saveStudent();

    void saveCourses();

    void updateCourses();

    void saveSchedules(ArrayList<Schedule> schedules);

    ArrayList<Course> getCourses();

    int getUserId();

    ArrayList<Schedule> getSchedules();

    double calculateTrueAttendance(int course_id);
}
