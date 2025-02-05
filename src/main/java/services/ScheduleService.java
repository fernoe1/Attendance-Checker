package services;

import clients.DUClient;
import clients.MoodleClient;

import models.Course;
import models.Schedule;

import repositories.ScheduleRepository;

import services.interfaces.IScheduleService;

import java.util.ArrayList;

public class ScheduleService implements IScheduleService{
    private ScheduleRepository scheduleRepository;
    private DUClient duClient;
    private MoodleClient moodleClient;

    public ScheduleService(ScheduleRepository scheduleRepository, DUClient duClient, MoodleClient moodleClient) {
        this.scheduleRepository = scheduleRepository;
        this.duClient = duClient;
        this.moodleClient = moodleClient;
    }

    @Override
    public void saveSchedules(ArrayList<Schedule> schedules) {
        System.out.println("Saving schedules..");
        scheduleRepository.saveSchedules(schedules);
    }

    @Override
    public ArrayList<Schedule> getSchedules(int group_id, ArrayList<Course> ongoingCourses) {
        System.out.println("Fetching schedules..");
        return duClient.getGroupSchedules(group_id, ongoingCourses, moodleClient.getUserId());
    }

    @Override
    public int getWeeklyClassCount(int course_id) {
        System.out.println("Fetching weekly class count..");
        return scheduleRepository.getWeeklyClassCountForCourse(moodleClient.getUserId(), course_id);
    }
}
