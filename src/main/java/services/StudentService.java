package services;

import repositories.DatabaseRepository;
import clients.MoodleClient;
import services.interfaces.IStudentService;

public class StudentService implements IStudentService {
    private MoodleClient moodleClient;
    private DatabaseRepository databaseRepository;
    private int week;

    public StudentService(MoodleClient moodleClient, DatabaseRepository databaseRepository, int week) {
        this.moodleClient = moodleClient;
        this.databaseRepository = databaseRepository;
        this.week = week;
    }


    @Override
    public boolean doesUserExist() {
        return databaseRepository.doesUserExist(moodleClient.getUserId());
    }

    @Override
    public void saveStudent() {
        databaseRepository.saveStudent(moodleClient.getUserInfo());
    }

    @Override
    public int getUserId() {
        return moodleClient.getUserId();
    }

    @Override
    public double calculateTrueAttendance(int course_id) {
        int classCount = databaseRepository.getWeeklyClassCountForCourse(moodleClient.getUserId(), course_id);
        double x = (week * classCount) * (moodleClient.getAttendance(course_id) / 100);

        return x / (classCount * 10);
    }
}
