package services;

import models.Schedule;
import repositories.DatabaseRepository;
import services.interfaces.IScheduleService;

import java.util.ArrayList;

public class ScheduleService implements IScheduleService{
    private DatabaseRepository databaseRepository;

    public ScheduleService(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    @Override
    public void saveSchedules(ArrayList<Schedule> schedules) {
        databaseRepository.saveSchedules(schedules);
    }

    @Override
    public ArrayList<Schedule> getSchedules() {
        return databaseRepository.getSchedules();
    }
}
