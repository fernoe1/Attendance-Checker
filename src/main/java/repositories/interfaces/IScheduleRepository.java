package repositories.interfaces;

import models.Schedule;

import java.util.ArrayList;

public interface IScheduleRepository {
    void saveSchedules(ArrayList<Schedule> schedules);

    ArrayList<Schedule> getSchedules();

    int getWeeklyClassCountForCourse(int userId, int courseId);
}
