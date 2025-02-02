package services.interfaces;

import models.Course;

import java.util.ArrayList;

public interface ICourseService {

    void saveCourses();

    void updateCourses();

    ArrayList<Course> getCourses();
}
