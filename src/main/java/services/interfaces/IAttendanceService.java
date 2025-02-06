package services.interfaces;

public interface IAttendanceService {
    double getBasicAttendance(int course_id);

    int getMaxAttendancePoints(int course_id);

    int getAttendedAttendancePoints(int course_id);

    double getTrueAttendance(int course_id);
}
