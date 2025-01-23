package repositories.interfaces;

import models.Course;
import models.Schedule;
import models.Student;

import java.util.ArrayList;

public interface IDatabaseRepository {

    boolean doesUserExist(int user_id);

    void saveStudent(Student student);

    void saveCourses(ArrayList<Course> courses);

    void saveSchedules(ArrayList<Schedule> schedules);

    void updateCourses(ArrayList<Course> courses);

    ArrayList<Schedule> getSchedules();

    int getWeeklyClassCountForCourse(int userId, int courseId);
}
