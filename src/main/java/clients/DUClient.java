package clients;

import clients.interfaces.IDUClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Schedule;

import java.net.http.HttpClient;
import java.util.ArrayList;

public class DUClient implements IDUClient {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String bearerToken;

    public DUClient(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    @Override
    public ArrayList<Schedule> getGroupSchedules(int groupNumber) {
        ArrayList<Schedule> schedules = new ArrayList<>();

    }
}
