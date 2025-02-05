package services.interfaces;

import models.Course;
import models.Schedule;

import java.util.ArrayList;

public interface IScheduleService {

    void saveSchedules(ArrayList<Schedule> schedules);

    ArrayList<Schedule> getSchedules(int group_id, ArrayList<Course> courses);

    int getWeeklyClassCount(int course_id);
}
