package repositories.interfaces;

import models.Course;

import java.util.ArrayList;

public interface ICourseRepository {
    void saveCourses(ArrayList<Course> courses);

    void updateCourses(ArrayList<Course> courses);
}
