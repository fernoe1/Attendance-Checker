import clients.DUClient;
import clients.MoodleClient;

import models.Course;
import models.Schedule;
import models.Student;

import repositories.CourseRepository;
import repositories.ScheduleRepository;
import repositories.StudentRepository;

import services.AttendanceService;
import services.CourseService;
import services.ScheduleService;
import services.StudentService;
import utilities.WeekUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Menu {
    private String wsToken;
    private String bearerToken;
    private int group_id;
    private Scanner sc = new Scanner(System.in);

    private AttendanceService attendanceService;
    private CourseService courseService;
    private ScheduleService scheduleService;
    private StudentService studentService;

    private Student student;
    private ArrayList<Course> courses;
    private ArrayList<Schedule> schedules;

    public void Start() {
        System.out.println("Please enter your Group Number.");
        group_id = sc.nextInt();
        System.out.println("Please enter your Moodle Mobile Web Service key.");
        wsToken = sc.next();
        System.out.println("Please enter your Bearer token.");
        bearerToken = sc.next();
        MoodleClient moodleClient = new MoodleClient(wsToken);
        DUClient duClient = new DUClient(bearerToken);

        attendanceService = new AttendanceService(moodleClient, ScheduleRepository.getInstance());
        courseService = new CourseService(moodleClient, CourseRepository.getInstance());
        scheduleService = new ScheduleService(ScheduleRepository.getInstance(), duClient, moodleClient);
        studentService = new StudentService(moodleClient, StudentRepository.getInstance());

        if (!studentService.doesUserExist()) {
            System.out.println("New user detected!");
            System.out.println("Fetching required info..");
            student = studentService.getStudent();
            studentService.saveStudent();
            courses = courseService.getCourses();
            courseService.saveCourses();
            schedules = scheduleService.getSchedules(group_id, courseService.getFormattedOngoingCourses());
            scheduleService.saveSchedules(schedules);
        } else {
            System.out.println("Fetching required info..");
            student = studentService.getStudent();
            courses = courseService.getCourses();
            schedules = scheduleService.getSchedules(group_id, courseService.getFormattedOngoingCourses());
        }

        System.out.println("-----------------------------------------");

        if (studentService.doesUserExist()) {
            System.out.println("Welcome back, " + student.getName() + " " + student.getSurname() + "!");

        }

        Date date = Date.from(Instant.now());
        System.out.println("Current date: " + date);
        System.out.println("Current trimester: " + WeekUtils.getCurrentTrimester());
        System.out.println("Current week: " + WeekUtils.getCurrentWeek());

        int option;
        do {
            System.out.println("-----------------------------------------");
            System.out.println("Menu:");
            System.out.println("[1] View schedule");
            System.out.println("[2] View normal attendance");
            System.out.println("[3] View true attendance");
            System.out.println("[4] View past courses"); // categories
            System.out.println("[5] View current courses"); // categories
            System.out.println("[6] View general info"); // Implementation of JOIN's
            System.out.println("[7] Exit");
            option = sc.nextInt();

            switch (option) {
                case 1:
                    System.out.println("-----------------------------------------");
                    schedules.forEach(schedule -> System.out.println("Course: " + courseService.getCourseById(schedule.getCourse_id()).getName() + "\n"
                    + "Day: " + schedule.getDay() + " " + "Time: " + schedule.getTime_slot() + "\n"));
                    break;
                case 2:
                    System.out.println("-----------------------------------------");
                    courses.forEach(course -> System.out.println("Course: " + course.getName() + "\n"
                    + "Attendance: " + course.getAttendance() + "%" + "\n"));
                    break;
                case 3:
                    System.out.println("-----------------------------------------");
                    courseService.getOngoingCourses().forEach(course -> System.out.println("Course: " + course.getName() + "\n"
                    + "True Attendance: " + attendanceService.getTrueAttendance(course.getCourse_id()) + "\n"));
                    break;
                case 4:
                    System.out.println("-----------------------------------------");
                    viewAttendanceOfPastCourses();
                    break;
                case 5:
                    System.out.println("-----------------------------------------");
                    viewAttendanceOfOngoingCourses();
                    break;
                case 6:
                    System.out.println("-----------------------------------------");
                    System.out.println("Student Full Name: " + student.getName() + " " + student.getSurname() + "\n"
                    + "Barcode: " + student.getBarcode() + "\n"
                    + "Email: " + student.getBarcode() + "@astanait.edu.kz" + "\n"
                    + "-----------------------------------------" + "\n"
                    + "Normal attendance of ongoing courses: " + "\n");
                    viewAttendanceOfOngoingCourses();
                    System.out.println("-----------------------------------------" + "\n"
                    + "True attendance of ongoing courses: " + "\n");
                    courseService.getOngoingCourses().forEach(course -> System.out.println("Course: " + course.getName() + "\n"
                            + "True Attendance: " + attendanceService.getTrueAttendance(course.getCourse_id()) + "\n"));
                    break;
                case 7:
                    System.out.println("-----------------------------------------");
                    System.out.println("Exiting..");
                    break;
            }
        } while (option != 7);
    }

    private void viewAttendanceOfPastCourses() {
        for (Course pastCourse : courses) {
            if (!pastCourse.getStatus()) {
                System.out.println("Course: " + pastCourse.getName() + "\n"
                        + "Attendance: " + pastCourse.getAttendance() + "%" + "\n");
            }
        }
    }

    private void viewAttendanceOfOngoingCourses() {
        for (Course ongoingCourse : courses) {
            if (ongoingCourse.getStatus()) {
                System.out.println("Course: " + ongoingCourse.getName() + "\n"
                        + "Attendance: " + ongoingCourse.getAttendance() + "%" + "\n");
            }
        }
    }
}
