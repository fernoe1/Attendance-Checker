package clients.interfaces;

import models.Schedule;

import java.util.ArrayList;

public interface IDUClient {
    ArrayList<Schedule> getGroupSchedules(int groupNumber);
}
