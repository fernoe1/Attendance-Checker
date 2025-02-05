package services;

import clients.MoodleClient;
import models.Student;
import repositories.StudentRepository;
import services.interfaces.IStudentService;

public class StudentService implements IStudentService {
    private MoodleClient moodleClient;
    private StudentRepository studentRepository;

    public StudentService(MoodleClient moodleClient, StudentRepository studentRepository) {
        this.moodleClient = moodleClient;
        this.studentRepository = studentRepository;
    }


    @Override
    public boolean doesUserExist() {
        return studentRepository.doesUserExist(moodleClient.getUserId());
    }

    @Override
    public void saveStudent() {
        System.out.println("Saving data..");
        studentRepository.saveStudent(moodleClient.getUserInfo());
    }

    @Override
    public int getUserId() {
        System.out.println("Fetching user ID..");
        return moodleClient.getUserId();
    }

    @Override
    public Student getStudent() {
        System.out.println("Fetching student..");
        return moodleClient.getUserInfo();
    }
}
