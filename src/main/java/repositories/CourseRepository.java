package repositories;

import database.Database;
import models.Course;
import repositories.interfaces.ICourseRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseRepository implements ICourseRepository {
    private Database database;
    private static CourseRepository instance;

    public CourseRepository() {
        database = Database.getInstance();
    }

    public static CourseRepository getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new CourseRepository();
                }
            }
        }

        return instance;
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
        } catch (SQLException e) {
            System.out.println("SQL Error occurred while saving courses: " + e.getMessage());
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
        } catch (SQLException e) {
            System.out.println("SQL Error occurred while updating courses: " + e.getMessage());
        }
    }

    @Override
    public Course getCourseById(int course_id) {
        String sql = "SELECT name, attendance, status FROM courses WHERE course_id = ?";
        try {
            Connection con = database.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, course_id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                double attendance = rs.getDouble("attendance");
                boolean status = rs.getBoolean("status");


                return new Course(course_id, name, attendance, status);
            } else {
                System.out.println("No course found with the ID " + course_id);
                return null;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error occurred while getting course by ID: " + e.getMessage());

            return null;
        }
    }
}
