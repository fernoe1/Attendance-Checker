package clients;

import clients.interfaces.IDUClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Course;
import models.Schedule;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpResponse;

import java.util.ArrayList;
import java.util.Objects;

public class DUClient implements IDUClient {
    private HttpClient httpClient = HttpClient.newHttpClient();
    private ObjectMapper mapper = new ObjectMapper();
    private String bearerToken;


    public DUClient(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    @Override
    public ArrayList<Schedule> getGroupSchedules(int groupNumber, ArrayList<Course> ongoingCourses, int user_id) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        String url = "https://du.astanait.edu.kz:8765/astanait-schedule-module/api/v1/schedule/groupName/SE-" + groupNumber;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Authorization", "Bearer " + bearerToken)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String jsonResponse = response.body();
            JsonNode jsonNode = mapper.readTree(jsonResponse);

            JsonNode body = jsonNode.get("body");
            for (Course course : ongoingCourses) {
                for (JsonNode node : body) {
                    if (Objects.equals(course.getName(), node.get("subject").asText()) && !Objects.equals(node.get("tutor").asText(), "https://learn.astanait.edu.kz/")) {
                        schedules.add(new Schedule(course.getCourse_id(), user_id, jsonDayConverter(node.get("classtime_day").asText()), jsonTimeConverter(node.get("classtime_time").asText())));
                    }
                }
            }

            return schedules;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String jsonDayConverter(String day) {
        if (Objects.equals(day, "d1")) {

            return "Monday";
        } else if (Objects.equals(day, "d2")) {

            return "Tuesday";
        } else if (Objects.equals(day, "d3")) {

            return "Wednesday";
        } else if (Objects.equals(day, "d4")) {

            return "Thursday";
        } else if (Objects.equals(day, "d5")) {

            return "Friday";
        } else if (Objects.equals(day, "d6")) {

            return "Saturday";
        }

        return "";
    }

    private String jsonTimeConverter(String time) {
        if (Objects.equals(time, "a")) {

            return "8:00-8:50";
        } else if (Objects.equals(time, "b")) {

            return "9:00-9:50";
        } else if (Objects.equals(time, "c")) {

            return "10:00-10:50";
        } else if (Objects.equals(time, "d")) {

            return "11:00-11:50";
        } else if (Objects.equals(time, "e")) {

            return "12:00-12:50";
        } else if (Objects.equals(time, "f")) {

            return "13:05-13:55";
        } else if (Objects.equals(time, "g")) {

            return "14:00-14:50";
        } else if (Objects.equals(time, "h")) {

            return "15:00-15:50";
        } else if (Objects.equals(time, "i")) {

            return "16:00-16:50";
        } else if (Objects.equals(time, "j")) {

            return "17:00-17:50";
        } else if (Objects.equals(time, "k")) {

            return "18:00-18:50";
        } else if (Objects.equals(time, "l")) {

            return "19:00-19:50";
        } else if (Objects.equals(time, "m")) {

            return "20:00-20:50";
        } else if (Objects.equals(time, "n")) {

            return "21:00-21:50";
        }

        return "";
    }
}
