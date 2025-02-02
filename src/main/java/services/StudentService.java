package services;

import models.Schedule;
import models.Student;
import repositories.DatabaseRepository;
import repositories.MoodleRepository;
import services.interfaces.IStudentService;

import java.util.ArrayList;

public class StudentService implements IStudentService {
    private MoodleRepository moodleRepository;
    private DatabaseRepository databaseRepository;
    private int week;

    public StudentService(MoodleRepository moodleRepository, DatabaseRepository databaseRepository, int week) {
        this.moodleRepository = moodleRepository;
        this.databaseRepository = databaseRepository;
        this.week = week;
    }


    @Override
    public boolean doesUserExist() {
        return databaseRepository.doesUserExist(moodleRepository.getUserId());
    }

    @Override
    public void saveStudent() {
        databaseRepository.saveStudent(moodleRepository.getUserInfo());
    }

    @Override
    public int getUserId() {
        return moodleRepository.getUserId();
    }

    @Override
    public double calculateTrueAttendance(int course_id) {
        int classCount = databaseRepository.getWeeklyClassCountForCourse(moodleRepository.getUserId(), course_id);
        double x = (week * classCount) * (moodleRepository.getAttendance(course_id) / 100);

        return x / (classCount * 10);
    }
}
