package services.interfaces;

import models.Course;

import java.util.ArrayList;

public interface ICourseService {

    void saveCourses();

    void updateCourses();

    ArrayList<Course> getCourses();

    ArrayList<Course> getFormattedOngoingCourses();

    Course getCourseById(int course_id);

    ArrayList<Course> getOngoingCourses();
}
