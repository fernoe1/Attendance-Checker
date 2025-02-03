import database.Database;
import models.Course;
import models.Schedule;
import repositories.DatabaseRepository;
import clients.MoodleClient;
import services.StudentService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your moodle mobile webservice token.");
        String token = sc.next();
        System.out.println("Which week is it?");
        int week = sc.nextInt();
        MoodleClient moodleClient = new MoodleClient(token);
        DatabaseRepository databaseRepository = new DatabaseRepository(new Database());
        StudentService studentService = new StudentService(moodleClient, databaseRepository, week);
        Menu menu = new Menu(studentService);

        if (studentService.doesUserExist()) {
            System.out.println("Welcome back!");
            Date date = Date.from(Instant.now());
            System.out.println("Current date: " + date);
            menu.Start();
        } else {
            System.out.println("New user detected! Let's build your schedule!");
            studentService.saveStudent();
            studentService.saveCourses();

            ArrayList<Course> ongoingCourses = new ArrayList<>();
            for (Course course : moodleClient.getAllCourses()) {
                if (course.getStatus()) {
                    ongoingCourses.add(course);
                }
            }

            System.out.println("You have " + ongoingCourses.size() + " ongoing courses.");
            ArrayList<Schedule> schedules = new ArrayList<>();
            Scanner scanner = new Scanner(System.in);

            for (Course course : ongoingCourses) {
                System.out.println("Building schedule for course: " + course.getName());
                System.out.print("How many hours per week do you want to allocate for " + course.getName() + "? ");
                int totalHours = scanner.nextInt();

                int allocatedHours = 0;

                while (allocatedHours < totalHours) {
                    System.out.print("Enter the day of the week (e.g., Monday): ");
                    String day = scanner.next();

                    System.out.print("Enter the time slot (e.g., 8-10): ");
                    String timeSlot = scanner.next();
                    String[] timeParts = timeSlot.split("-");
                    int startHour = Integer.parseInt(timeParts[0]);
                    int endHour = Integer.parseInt(timeParts[1]);

                    int enteredHours = endHour - startHour;

                    if (enteredHours <= 0) {
                        System.out.println("Invalid time slot. End time must be greater than start time.");
                        continue;
                    }

                    if (totalHours < enteredHours) {
                        System.out.println("You are trying to allocate more hours than remaining. Please re-enter.");
                        System.out.println("Hours left to allocate: " + (totalHours - allocatedHours));
                        continue;
                    }

                    schedules.add(new Schedule(course.getCourse_id(), moodleClient.getUserId(), day, timeSlot));
                    allocatedHours += enteredHours;

                    if (allocatedHours < totalHours) {
                        System.out.println("You have " + (totalHours - allocatedHours) + " hours left to allocate for " + course.getName() + ".");
                    }
                }

                System.out.println("Schedule for " + course.getName() + " completed.");
            }

            studentService.saveSchedules(schedules);
            System.out.println("Schedules saved successfully!");

            menu.Start();
        }
    }
}