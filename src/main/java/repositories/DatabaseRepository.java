package repositories;

import database.Database;
import models.Course;
import models.Schedule;
import models.Student;
import repositories.interfaces.IDatabaseRepository;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseRepository implements IDatabaseRepository {
    private Database database;

    public DatabaseRepository(Database database) {
        this.database = database;
    }

    @Override
    public boolean doesUserExist(int user_id) {
        String query = "SELECT 1 FROM Students WHERE user_id = ?";
        try (Connection con = database.getConnection();
             PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, user_id);
            ResultSet rs = st.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }

        return false;
    }

    @Override
    public void saveStudent(Student student) {
        String sql = "INSERT INTO Students (user_id, name, surname, barcode) VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (user_id) DO NOTHING;";

        try (Connection con = database.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, student.getUser_id());
            st.setString(2, student.getName());
            st.setString(3, student.getSurname());
            st.setInt(4, student.getBarcode());

            st.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error occurred while saving user data: " + e.getMessage());
        }
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
    public void saveSchedules(ArrayList<Schedule> schedules) {
        String sql = "INSERT INTO Schedules (course_id, user_id, day, time_slot) VALUES (?, ?, ?, ?)";

        try (Connection con = database.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            for (Schedule schedule : schedules) {
                st.setInt(1, schedule.getCourse_id());
                st.setInt(2, schedule.getUser_id());
                st.setString(3, schedule.getDay());
                st.setString(4, schedule.getTime_slot());

                st.addBatch();
            }

            st.executeBatch();
        } catch (Exception e) {
            System.out.println("Error occurred while saving schedules: " + e.getMessage());
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

    @Override
    public ArrayList<Schedule> getSchedules() {
        ArrayList<Schedule> schedules = new ArrayList<>();
        String query = "SELECT schedule_id, course_id, user_id, day, time_slot FROM Schedules";

        try (Connection con = database.getConnection();
             PreparedStatement st = con.prepareStatement(query);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                int scheduleId = rs.getInt("schedule_id");
                int courseId = rs.getInt("course_id");
                int userId = rs.getInt("user_id");
                String day = rs.getString("day");
                String timeSlot = rs.getString("time_slot");

                Schedule schedule = new Schedule(courseId, userId, day, timeSlot);
                schedules.add(schedule);
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while getting schedules: " + e.getMessage());
        }

        return schedules;
    }

    @Override
    public int getWeeklyClassCountForCourse(int userId, int courseId) {
        String sql = "SELECT COUNT(DISTINCT day) AS class_count " +
                "FROM schedules " +
                "WHERE user_id = ? AND course_id = ?;";

        try (Connection con = database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, courseId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("class_count");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while getting weekly classes: " + e.getMessage());
        }

        return 0;
    }

}
