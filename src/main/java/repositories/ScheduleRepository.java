package repositories;

import database.Database;
import models.Schedule;
import repositories.interfaces.IScheduleRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ScheduleRepository implements IScheduleRepository {
    private Database database;
    private static ScheduleRepository instance;

    public ScheduleRepository() {
        this.database = Database.getInstance();
    }

    public static ScheduleRepository getInstance() {
        if (instance == null) {
            synchronized(Database.class) {
                if (instance == null) {
                    instance = new ScheduleRepository();
                }
            }
        }

        return instance;
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
