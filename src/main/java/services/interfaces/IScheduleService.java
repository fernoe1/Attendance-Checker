package services.interfaces;

import models.Schedule;

import java.util.ArrayList;

public interface IScheduleService {

    void saveSchedules(ArrayList<Schedule> schedules);

    ArrayList<Schedule> getSchedules();
}
