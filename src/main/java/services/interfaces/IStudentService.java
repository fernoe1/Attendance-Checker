package services.interfaces;

import models.Student;

import java.util.ArrayList;

public interface IStudentService {

    boolean doesUserExist();

    void saveStudent();

    int getUserId();

    Student getStudent();
}
