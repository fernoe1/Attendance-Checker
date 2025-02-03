package services;

import clients.MoodleClient;
import repositories.StudentRepository;
import services.interfaces.IStudentService;

public class StudentService implements IStudentService {
    private MoodleClient moodleClient;
    private StudentRepository studentRepository;
    private int week;

    public StudentService(MoodleClient moodleClient, StudentRepository studentRepository, int week) {
        this.moodleClient = moodleClient;
        this.studentRepository = studentRepository;
        this.week = week;
    }


    @Override
    public boolean doesUserExist() {
        return studentRepository.doesUserExist(moodleClient.getUserId());
    }

    @Override
    public void saveStudent() {
        studentRepository.saveStudent(moodleClient.getUserInfo());
    }

    @Override
    public int getUserId() {
        return moodleClient.getUserId();
    }
}
