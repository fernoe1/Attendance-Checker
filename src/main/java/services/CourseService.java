package services;

import models.Course;
import repositories.DatabaseRepository;
import repositories.MoodleRepository;
import services.interfaces.ICourseService;

import java.util.ArrayList;

public class CourseService {
    private MoodleRepository moodleRepository;
    private DatabaseRepository databaseRepository;

    public CourseService(MoodleRepository MoodleRepository, DatabaseRepository databaseRepository) {
        this.moodleRepository = moodleRepository;
        this.databaseRepository = databaseRepository;
    }

    @Override
    public void saveCourses() {
        databaseRepository.saveCourses(moodleRepository.getAllCourses());
    }

    @Override
    public void updateCourses() {
        databaseRepository.updateCourses(moodleRepository.getAllCourses());
    }

    @Override
    public ArrayList<Course> getCourses() {
        return moodleRepository.getAllCourses();
    }
}