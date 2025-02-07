package services;

import clients.MoodleClient;
import repositories.CourseRepository;
import repositories.ScheduleRepository;
import services.interfaces.IAttendanceService;
import utilities.WeekUtils;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

public class AttendanceService implements IAttendanceService {
    private MoodleClient moodleClient;
    private ScheduleRepository scheduleRepository;

    List<LocalDate> holidays = List.of(
            LocalDate.of(2024, 10, 25),
            LocalDate.of(2024, 12, 16),
            LocalDate.of(2025, 1, 1),
            LocalDate.of(2025, 1, 2),
            LocalDate.of(2025, 1, 7)
    );

    List<String> dayOfHolidays = List.of(
            holidays.get(0).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
            holidays.get(1).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
            holidays.get(2).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
            holidays.get(3).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
            holidays.get(4).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH)
    );

    public AttendanceService(MoodleClient moodleClient, ScheduleRepository scheduleRepository) {
        this.moodleClient = moodleClient;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public double getBasicAttendance(int course_id) {
        return 0;
    }

    @Override
    public int getMaxAttendancePoints(int course_id) {
        int points = scheduleRepository.getWeeklyClassCountForCourse(moodleClient.getUserId(), course_id) * 20;
        ArrayList<String> days = scheduleRepository.getWeeklyClassDaysForCourse(moodleClient.getUserId(), course_id);
        for (String day : days) {
            for (String hday : dayOfHolidays) {
                if (Objects.equals(day, hday)) {
                    System.out.println("Day: " + day + " Hday: " + hday);
                    points -= 2;
                }
            }
        }

        return points;
    }

    @Override
    public int getAttendedAttendancePoints(int course_id) {
        int points = scheduleRepository.getWeeklyClassCountForCourse(moodleClient.getUserId(), course_id) * WeekUtils.getCurrentWeek() * 2;
        ArrayList<String> days = scheduleRepository.getWeeklyClassDaysForCourse(moodleClient.getUserId(), course_id);

        ArrayList<DayOfWeek> weekDays = WeekUtils.convertToDayOfWeek(days);
        for (DayOfWeek day : weekDays) {
            if (WeekUtils.getUnixTimestampForWeekday(day) > (Date.from(Instant.now()).getTime() / 1000)) {
                points -= 2;
            }
        }

        return (int) (points * (moodleClient.getAttendance(course_id) / 100));
    }

    @Override
    public String getTrueAttendance(int course_id) {
        double value = (double) getAttendedAttendancePoints(course_id) / getMaxAttendancePoints(course_id);
        return String.format("%.1f%%", value * 100);
    }
}
