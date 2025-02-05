package clients;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Course;
import models.Student;

import clients.interfaces.IMoodleClient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.Instant;

import java.util.ArrayList;

public class MoodleClient implements IMoodleClient {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String token;
    private final int user_id;

    public MoodleClient(String token) {
        this.token = token;
        this.user_id = getUserId();
    }


    @Override
    public int getUserId() {
        String url = "https://moodle.astanait.edu.kz//webservice/rest/server.php?" +
                "wstoken=" + token +
                "&moodlewsrestformat=" + "json" +
                "&wsfunction=" + "core_webservice_get_site_info";

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String jsonResponse = response.body();
            JsonNode jsonNode = mapper.readTree(jsonResponse);

            int user_id = jsonNode.get("userid").asInt();

            return user_id;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Student getUserInfo() {
        String url = "https://moodle.astanait.edu.kz//webservice/rest/server.php?" +
                "wstoken=" + token +
                "&moodlewsrestformat=" + "json" +
                "&wsfunction=" + "core_webservice_get_site_info";

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String jsonResponse = response.body();
            JsonNode jsonNode = mapper.readTree(jsonResponse);

            String name = jsonNode.get("firstname").asText();
            String surname = jsonNode.get("lastname").asText();
            int user_id = jsonNode.get("userid").asInt();
            String digits = jsonNode.get("username").asText().substring(0,6);
            int barcode = Integer.parseInt(digits);

            Student student = new Student(name, surname, user_id, barcode);

            return student;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        String url = "https://moodle.astanait.edu.kz//webservice/rest/server.php?" +
                "wstoken=" + token +
                "&moodlewsrestformat=" + "json" +
                "&wsfunction=" + "core_enrol_get_users_courses" +
                "&userid=" + user_id;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String jsonResponse = response.body();

            JsonNode jsonNode = mapper.readTree(jsonResponse);
            if (jsonNode != null && jsonNode.isArray()) {
                for (JsonNode jsonPart : jsonNode) {
                    int course_id = jsonPart.get("id").asInt();
                    String name = jsonPart.get("fullname").asText();
                    double attendance = getAttendance(course_id);

                    long currentDate = Instant.now().getEpochSecond();
                    long endDate = jsonPart.get("enddate").asLong();
                    boolean status;
                    if (currentDate < endDate) {
                        status = true;
                    } else {
                        status = false;
                    }

                    Course course = new Course(course_id, name, attendance, status);
                    courses.add(course);
                }
            }

            return courses;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<Course> getAllFormattedOngoingCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        String url = "https://moodle.astanait.edu.kz//webservice/rest/server.php?" +
                "wstoken=" + token +
                "&moodlewsrestformat=" + "json" +
                "&wsfunction=" + "core_enrol_get_users_courses" +
                "&userid=" + user_id;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String jsonResponse = response.body();

            JsonNode jsonNode = mapper.readTree(jsonResponse);
            if (jsonNode != null && jsonNode.isArray()) {
                for (JsonNode jsonPart : jsonNode) {
                    int course_id = jsonPart.get("id").asInt();
                    String fullname = jsonPart.get("fullname").asText();
                    String[] parts = fullname.split("\\|");
                    String name = parts[0].trim();
                    double attendance = getAttendance(course_id);

                    long currentDate = Instant.now().getEpochSecond();
                    long endDate = jsonPart.get("enddate").asLong();
                    if (currentDate < endDate) {
                        Course course = new Course(course_id, name, attendance, true);
                        courses.add(course);
                    }
                }
            }

            return courses;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public double getAttendance(int course_id) {
        String url = "https://moodle.astanait.edu.kz//webservice/rest/server.php?" +
                "wstoken=" + token +
                "&moodlewsrestformat=" + "json" +
                "&wsfunction=" + "gradereport_user_get_grade_items" +
                "&courseid=" + course_id +
                "&userid=" + user_id;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String jsonResponse = response.body();

            JsonNode jsonNode = mapper.readTree(jsonResponse);
            if (jsonNode.has("usergrades") && jsonNode.get("usergrades").isArray()) {
                for (JsonNode userGrades : jsonNode.get("usergrades")) {
                    if (userGrades.has("gradeitems") && userGrades.get("gradeitems").isArray()) {
                        for (JsonNode gradeItems : userGrades.get("gradeitems")) {
                            if ("Attendance".equals(gradeItems.get("itemname").asText())) {
                                String attendance = gradeItems.get("gradeformatted").asText();
                                if (attendance != null && !attendance.isEmpty()) {

                                    return Double.parseDouble(attendance);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
