package services;

import models.Course;
import repositories.DatabaseRepository;
import clients.MoodleClient;
import services.interfaces.ICourseService;

import java.util.ArrayList;

public class CourseService implements ICourseService {
    private MoodleClient moodleClient;
    private DatabaseRepository databaseRepository;

    public CourseService(MoodleClient MoodleClient, DatabaseRepository databaseRepository) {
        this.moodleClient = moodleClient;
        this.databaseRepository = databaseRepository;
    }

    @Override
    public void saveCourses() {
        databaseRepository.saveCourses(moodleClient.getAllCourses());
    }

    @Override
    public void updateCourses() {
        databaseRepository.updateCourses(moodleClient.getAllCourses());
    }

    @Override
    public ArrayList<Course> getCourses() {
        return moodleClient.getAllCourses();
    }
}