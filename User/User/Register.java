package TaskManagement10.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Register {
    private static final String ADMIN_FILE_PATH = "/Users/keomunin/Documents/Java/TaskManagement10/User/Registered_Admin.txt";
    private static final String USER_FILE_PATH = "/Users/keomunin/Documents/Java/TaskManagement10/User/Registered_regUser.txt";

    public static void register(Scanner sc) {
        String tmpName;
        String tmpPassword;
        sc.nextLine();

        System.out.println("\nInput name: ");
        tmpName = sc.nextLine();
        System.out.println("Input password: ");
        tmpPassword = sc.nextLine();

        System.out.println("");
        System.out.println("Input number");
        System.out.println("Register as: 1. Admin / 2. Regular User");
        int userType = sc.nextInt();
        sc.nextLine(); // Consume newline

        if (userType == 1) {
            // Register as Admin
            registerAsAdmin(tmpName, tmpPassword, sc);
        } else {
            // Register as Regular User
            registerAsUser(tmpName, tmpPassword);
        }
    }

       private static void registerAsAdmin(String username, String password, Scanner sc) {
        System.out.println("Security Question: Name your favorite animal.");
        String securityKey = sc.nextLine();

        String adminData = username + " / " + password + " / " + securityKey + "\n";
        writeToFile(ADMIN_FILE_PATH, adminData);
    }

    private static void registerAsUser(String username, String password) {
        String userData = username + " / " + password + "\n";
        writeToFile(USER_FILE_PATH, userData);
    }

    private static void writeToFile(String filePath, String data) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {
            bufferedWriter.write(data);
            System.out.println("Data has been appended to the file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}