package services.interfaces;

public interface IAttendanceService {
    double getBasicAttendance(int course_id);

    double getTrueAttendance(int course_id);
}
