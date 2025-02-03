package repositories.interfaces;

import models.Student;

public interface IStudentRepository {
    boolean doesUserExist(int user_id);

    void saveStudent(Student student);
}
