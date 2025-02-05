package clients.interfaces;

import models.Course;
import models.Student;

import java.util.ArrayList;

public interface IMoodleClient {

    Student getUserInfo();

    ArrayList<Course> getAllCourses();

    ArrayList<Course> getAllFormattedOngoingCourses();

    double getAttendance(int course_id);

    int getUserId();
}
