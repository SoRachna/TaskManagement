package TaskManagement10.TaskManagement;

import TaskManagement10.User.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();
        boolean loggedIn = false;
        boolean registered = false;

        do {
            System.out.println("\n\nWelcome!");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            int choice = 0;
            boolean validInput = false;
            while (!validInput) {
                try {
                    choice = scanner.nextInt();
                    validInput = true; // Input is valid, exit the loop
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine(); // Clear the invalid input
                }
}
            switch (choice) {
                case 1:
                    User user = userManager.promptLogin(scanner);
                    if (user != null) {
                        loggedIn = true;
                        user.login(userManager, scanner);
                    } else {
                        System.out.println("\nUser not found \nLogin failed.");
                    }
                    break;

                case 2:
                    userManager.registerUser(scanner);
                    registered = true;
                    break;

                case 3:
                    System.out.println("\nExiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (!loggedIn && !registered);

        scanner.close();
    }
}
