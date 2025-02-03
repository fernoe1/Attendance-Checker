package clients.interfaces;

import models.Course;
import models.Student;

import java.util.ArrayList;

public interface IMoodleClient {

    Student getUserInfo();

    ArrayList<Course> getAllCourses();

    double getAttendance(int course_id);

    int getUserId();
}
