import models.Course;
import models.Schedule;
import services.StudentService;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private StudentService studentService;

    public Menu(StudentService studentService) {
        this.studentService = studentService;
    }

    public void Start() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nMenu:");
            System.out.println("[1] View Attendance");
            System.out.println("[2] View True Attendance");
            System.out.println("[3] View Schedule");
            System.out.println("[4] Redo Schedule");
            System.out.println("[5] Refresh Courses");
            System.out.println("[6] Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewAttendance();
                    break;
                case 2:
                    viewTrueAttendance();
                    break;
                case 3:
                    viewSchedule();
                    break;
                case 4:
                    redoSchedule();
                    break;
                case 5:
                    refreshCourses();
                    break;
                case 6:
                    System.out.println("Exiting.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);

        scanner.close();
    }

    private void viewAttendance() {
        System.out.println("Fetching attendance...");
        ArrayList<Course> courses = studentService.getCourses();
        if (courses != null && !courses.isEmpty()) {
            System.out.println("\nAttendance Summary:");
            for (Course course : courses) {
                if (course.getStatus()) {
                    System.out.println("Course: " + course.getName() + " | Attendance: " + course.getAttendance() + "%");
                }
            }
        } else {
            System.out.println("No courses found or attendance data unavailable.");
        }
    }

    private void viewTrueAttendance() {
        System.out.println("Fetching true attendance...");
        ArrayList<Course> courses = studentService.getCourses();
        if (courses != null && !courses.isEmpty()) {
            System.out.println("\nAttendance Summary:");
            for (Course course : courses) {
                if (course.getStatus()) {
                    System.out.println("Course: " + course.getName() + " | True attendance: " + studentService.calculateTrueAttendance(course.getCourse_id()) + "%");
                }
            }
        } else {
            System.out.println("No courses found or attendance data unavailable.");
        }

    }

    private void viewSchedule() {
        System.out.println("Fetching schedule...");
        ArrayList<Schedule> schedules = studentService.getSchedules();
        if (schedules != null && !schedules.isEmpty()) {
            System.out.println("\nYour Schedule:");
            for (Schedule schedule : schedules) {
                System.out.println("Course ID: " + schedule.getCourse_id() +
                        " | Day: " + schedule.getDay() +
                        " | Time: " + schedule.getTime_slot());
            }
        } else {
            System.out.println("No schedules found.");
        }
    }

    private void redoSchedule() {
        System.out.println("Redoing schedule...");
        studentService.saveStudent();
        studentService.saveCourses();

        ArrayList<Course> ongoingCourses = new ArrayList<>();
        for (Course course : studentService.getCourses()) {
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

                schedules.add(new Schedule(course.getCourse_id(), studentService.getUserId(), day, timeSlot));
                allocatedHours += enteredHours;

                if (allocatedHours < totalHours) {
                    System.out.println("You have " + (totalHours - allocatedHours) + " hours left to allocate for " + course.getName() + ".");
                }
            }

            System.out.println("Schedule for " + course.getName() + " completed.");
        }

        studentService.saveSchedules(schedules);
        System.out.println("Schedules saved successfully!");
    }

    private void refreshCourses() {
        System.out.println("Refreshing courses...");
        studentService.updateCourses();
        System.out.println("Courses refreshed successfully.");
    }
}
