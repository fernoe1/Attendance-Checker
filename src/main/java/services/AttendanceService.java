package services;

import clients.MoodleClient;
import repositories.ScheduleRepository;
import services.interfaces.IAttendanceService;
import utilities.WeekUtils;

public class AttendanceService implements IAttendanceService {
    private MoodleClient moodleClient;
    private ScheduleRepository scheduleRepository;

    public AttendanceService(MoodleClient moodleClient, ScheduleRepository scheduleRepository) {
        this.moodleClient = moodleClient;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public double getBasicAttendance(int course_id) {
        return 0;
    }

    @Override
    public double getTrueAttendance(int course_id) {
        System.out.println("Fetching true attendance..");
        int classCount = scheduleRepository.getWeeklyClassCountForCourse(moodleClient.getUserId(), course_id);
        double x = (WeekUtils.getCurrentWeek() * classCount) * (moodleClient.getAttendance(course_id) / 100);

        return x / (classCount * 10);
    }
}
