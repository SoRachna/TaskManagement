package TaskManagement10.TaskManagement;

import TaskManagement10.User.*;
import java.util.Scanner;

public class RegularUserFunctions {

    public static void handleRegularUserActions(RegularUser user, UserManager userManager, Scanner scanner) {
        //Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome, " + user.getUsername());
        boolean exit = false;

        while (!exit) {
            System.out.println("\nChoose an action:");
            System.out.println("1. View My Tasks");
            System.out.println("2. Mark Task as Complete");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    userManager.displayUserTasks(user);
                    break;
                case 2:
                    System.out.println("Enter the ID of the task to mark as complete:");
                    String taskId = scanner.nextLine();
                    userManager.markTaskAsComplete(user, taskId);
                    System.out.println("Task " + taskId + " marked as complete.");
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
