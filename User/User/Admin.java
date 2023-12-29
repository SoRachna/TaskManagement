package TaskManagement10.User;

import TaskManagement10.TaskManagement.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Admin extends User {
    private boolean isAdminVerified = false;
    private String reportData; // Field to store the report data

    // Constructor
    public Admin(String username, String password) {
        super(username, password);
    }

    // Method to set the report data
    public void setReportData(String reportData) {
        this.reportData = reportData;
    }


    // Method to get the report data
    public String getReportData() {
        return reportData;
    }


    // Override the login method
    @Override
    public void login(UserManager userManager, Scanner scanner) {
        super.commonLogin();

        isAdminVerified = verifyAdmin(scanner);
        if (isAdminVerified) {
            displayUserInfo(true);
            System.out.println("\nAdmin logged in successfully.");
            // Additional admin-specific functionalities here
            userManager.displayAdminReportFromFile(this);  // it is casting here
            AdminFunctions.handleAdminActions(this, userManager, scanner);// Handle Admin actions
            
        } 
        else {
            displayUserInfo(false);
            System.out.println("Failed to verify admin identity.");
        }
    }


    @Override
    public String toString() {
        return "Admin{username='" + username + "', isAdminVerified=" + isAdminVerified + "}";
    }
    

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Admin admin = (Admin) obj;
        return isAdminVerified == admin.isAdminVerified;
    }
    

    //Overload
    public void displayUserInfo(boolean includeSecurityStatus) {
        super.displayUserInfo(); // Call the base class method
        if (includeSecurityStatus) {
            System.out.println("Admin Security Status: " + (isAdminVerified() ? "Verified" : "Unverified"));
        }
    }


    public boolean isAdminVerified() {
        return isAdminVerified;
    }


    private boolean verifyAdmin(Scanner scanner) {
        //Scanner scanner = new Scanner(System.in);
        System.out.println("Security Verification: Please enter security key");
        String answer = scanner.nextLine();
        return checkAdminAnswer(getUsername(), answer);
    }


    private boolean checkAdminAnswer(String username, String answer) {
        final String ADMIN_FILE_PATH = "/Users/keomunin/Documents/Java/TaskManagement10/User/Registered_Admin.txt";
        try (Scanner fileScanner = new Scanner(new File(ADMIN_FILE_PATH))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] adminData = line.split(" / ");
                if (adminData.length >= 3 && adminData[0].equals(username) && adminData[2].equalsIgnoreCase(answer)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Admin data file not found: " + e.getMessage());
        }
        return false;
    }

}



