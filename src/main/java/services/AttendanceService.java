package services;

import clients.MoodleClient;
import repositories.ScheduleRepository;
import services.interfaces.IAttendanceService;

public class AttendanceService implements IAttendanceService {
    private MoodleClient moodleClient;
    private ScheduleRepository scheduleRepository;

    public AttendanceService(MoodleClient mooodleCLient, ScheduleRepository scheduleRepository) {
        this.moodleClient = moodleClient;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public double getBasicAttendance(int course_id) {
        return 0;
    }

    @Override
    public double getTrueAttendance(int course_id) {
//        int classCount = scheduleRepository.getWeeklyClassCountForCourse(moodleClient.getUserId(), course_id);
//        double x = (week * classCount) * (moodleClient.getAttendance(course_id) / 100);
//
//        return x / (classCount * 10);
    }
}
