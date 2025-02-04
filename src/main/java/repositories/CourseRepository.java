package repositories;

import database.Database;
import models.Course;
import repositories.interfaces.ICourseRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class CourseRepository implements ICourseRepository {
    private Database database;

    public CourseRepository(Database database) {
        this.database = database;
    }

    @Override
    public void saveCourses(ArrayList<Course> courses) {
        String sql = "INSERT INTO Courses (course_id, name, attendance, status) VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (course_id) DO NOTHING;";

        try (Connection con = database.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            for (Course course : courses) {
                st.setInt(1, course.getCourse_id());
                st.setString(2, course.getName());
                st.setDouble(3, course.getAttendance());
                st.setBoolean(4, course.getStatus());

                st.addBatch();
            }

            st.executeBatch();
        } catch (Exception e) {
            System.out.println("Error occurred while saving courses: " + e.getMessage());
        }
    }

    @Override
    public void updateCourses(ArrayList<Course> courses) {
        String sql = "UPDATE Courses SET attendance = ? WHERE course_id = ?";

        try (Connection con = database.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            for (Course course : courses) {
                st.setDouble(1, course.getAttendance());
                st.setInt(2, course.getCourse_id());

                st.addBatch();
            }

            st.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while updating courses: " + e.getMessage());
        }
    }
}
