package repositories.interfaces;

import models.Course;
import models.Student;

import java.util.ArrayList;

public interface IMoodleRepository {

    Student getUserInfo();

    ArrayList<Course> getAllCourses();

    double getAttendance(int course_id);

    int getUserId();
}
