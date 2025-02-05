package clients.interfaces;

import models.Course;
import models.Schedule;

import java.util.ArrayList;

public interface IDUClient {
    ArrayList<Schedule> getGroupSchedules(int groupNumber, ArrayList<Course> ongoingCourses, int user_id);
}
