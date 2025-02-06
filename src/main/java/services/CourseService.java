package services;

import models.Course;
import repositories.CourseRepository;
import clients.MoodleClient;
import services.interfaces.ICourseService;

import java.util.ArrayList;

public class CourseService implements ICourseService {
    private MoodleClient moodleClient;
    private CourseRepository courseRepository;

    public CourseService(MoodleClient moodleClient, CourseRepository courseRepository) {
        this.moodleClient = moodleClient;
        this.courseRepository = courseRepository;
    }

    @Override
    public void saveCourses() {
        System.out.println("Saving courses..");
        courseRepository.saveCourses(moodleClient.getAllCourses());
    }

    @Override
    public void updateCourses() {
        System.out.println("Updating courses..");
        courseRepository.updateCourses(moodleClient.getAllCourses());
    }

    @Override
    public ArrayList<Course> getCourses() {
        System.out.println("Fetching courses..");
        return moodleClient.getAllCourses();
    }

    @Override
    public ArrayList<Course> getFormattedOngoingCourses() {
        System.out.println("Fetching formatted ongoing courses..");
        return moodleClient.getAllFormattedOngoingCourses();
    }

    @Override
    public Course getCourseById(int course_id) {
        System.out.println("Fetching course by ID..");
        return courseRepository.getCourseById(course_id);
    }

    @Override
    public ArrayList<Course> getOngoingCourses() {
        System.out.println("Fetching ongoing courses..");
        return moodleClient.getOngoingCourses();
    }
}