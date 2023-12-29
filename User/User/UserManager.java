package TaskManagement10.User;

import TaskManagement10.TaskManagement.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class UserManager {
    private final String ADMIN_FILE_PATH = "/Users/keomunin/Documents/Java/TaskManagement10/User/Registered_Admin.txt";
    private final String USER_FILE_PATH = "/Users/keomunin/Documents/Java/TaskManagement10/User/Registered_regUser.txt";

    public User promptLogin(Scanner scanner) {
            scanner.nextLine();

            System.out.println("\nEnter username: ");
            String username = scanner.nextLine();
            System.out.println("Enter password: ");
            String password = scanner.nextLine();

            User user = authenticateUser(username, password, ADMIN_FILE_PATH);
            if (user == null) {
                user = authenticateUser(username, password, USER_FILE_PATH);
            } else {
                System.out.println("User not found");
            }
            return user;
        }


    public void registerUser(Scanner scanner) {
        Register.register(scanner);
    }


    private User authenticateUser(String username, String password, String filePath) {
        try (Scanner fileScanner = new Scanner(new File(filePath))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                // Split the line into parts - username, password, and possibly tasks
                String[] userData = line.split(" / ");
                if (userData.length >= 2 && userData[0].equals(username) && userData[1].equals(password)) {
                    if (filePath.equals(ADMIN_FILE_PATH)) {
                        return new Admin(username, password);
                    } else if (filePath.equals(USER_FILE_PATH)) {
                        // Return RegularUser with task data if present
                        String taskData = userData.length > 2 ? userData[2] : "";
                        return new RegularUser(username, password, taskData);
                    } else {
                        System.out.println("User not found");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }
        return null;
    }


    public void displayAdminReportFromFile(Admin admin) {   // For Admin
        Path path = Paths.get(ADMIN_FILE_PATH);
        try {
            List<String> fileContent = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (String line : fileContent) {
                if (line.startsWith(admin.getUsername() + " / ")) {
                    String[] adminData = line.split(" / ");
                    if (adminData.length > 3) {
                        System.out.println("\nPrevious Report for " + admin.getUsername() + ":");
                        String reportData = adminData[3];
                        String formattedReport = formatReportForDisplay(reportData);
                        System.out.println(formattedReport);
                    } else {
                        System.out.println("No previous report found for " + admin.getUsername());
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading admin report from file: " + e.getMessage());
        }
    }


    // Format report in Admin account
    private String formatReportForDisplay(String reportData) {  
        String formattedReport = reportData
        .replace("[Project Name: ", "Project Name: ")
        .replace("; Project Status: ", "\nProject Status: ")
        .replace("; Tasks: [Task ID: ", "\nTasks:\n\n- Task ID: ") // Correct handling for the first task
        .replace("; Task ID: ", "\n\n- Task ID: ") // Ensure each new task starts on a new line
        .replace("; Task Name: ", "\n  Task Name: ")
        .replace("; Task Deadline: ", "\n  Task Deadline: ")
        .replace("; Task Status: ", "\n  Task Status: ")
        .replace("; Task Owner: ", "\n  Task Owner: ")
        .replace("; ]; ]", "\n");

        return formattedReport;
    }


    // For RegUser Account
    public void appendTaskToUserAccount(Tasks task, String projectName, String projectStatus) {
        Path path = Paths.get(USER_FILE_PATH);
        try {
            List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
            String taskData = formatTaskForUserAccount(task);
            boolean userFound = false;

            for (int i = 0; i < fileContent.size(); i++) {
                String line = fileContent.get(i);
                if (line.startsWith(task.getTaskOwner() + " / ")) {
                    userFound = true;
                    String[] userParts = line.split(" / ");
                    if (userParts.length > 2) {
                        // Append new task to the existing task data
                        String existingTasks = userParts[2];
                        if (!existingTasks.contains("Project name: " + projectName)) {
                            // If the project does not exist, add a new project section
                            existingTasks += " [Project Name: " + projectName + "; Project Status: " + projectStatus + "; Tasks: " + taskData + "; ]";
                        } else {
                            // If the project exists, append the task to the project's task list
                            int projectIndex = existingTasks.indexOf("Project name: " + projectName);
                            int insertIndex = existingTasks.indexOf("; ]", projectIndex);
                            existingTasks = existingTasks.substring(0, insertIndex) + taskData + existingTasks.substring(insertIndex);
                        }
                        line = userParts[0] + " / " + userParts[1] + " / " + existingTasks;
                    } else {
                        // First task for this user
                        String projectData = "[Project name: " + projectName + "; Project status: " + projectStatus + "; Tasks: " + taskData + "; ]";
                        line += " / " + projectData;
                    }
                    fileContent.set(i, line);
                    break;
                }
            }
            if (!userFound) {
                // Handle case where user is not found in the file
                System.out.println("User " + task.getTaskOwner() + " not found.");
            }
            Files.write(path, fileContent, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Error updating user data in file: " + e.getMessage());
        }
    }


    // For RegUser Account
    private String formatTaskForUserAccount(Tasks task) {
        return String.format("Task: [Task ID: %s; Task Name: %s; Task Deadline: %s; Task Owner: %s;]",
        task.getTaskId(), task.getTaskName(), task.getDeadline(), task.getTaskOwner());
    }

    
    // For RegUser Account
    public void displayUserTasks(RegularUser user) {
        Path path = Paths.get(USER_FILE_PATH);
        try {
            List<String> fileContent = Files.readAllLines(path, StandardCharsets.UTF_8);

            for (String line : fileContent) {
                if (line.startsWith(user.getUsername() + " / ")) {
                    String[] parts = line.split(" / ");
                    if (parts.length > 2) {
                        System.out.println("Tasks assigned to " + user.getUsername() + ":");
                        formatAndDisplayTasks(parts[2]);
                    } else {
                        System.out.println("No tasks assigned to " + user.getUsername());
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading tasks from file: " + e.getMessage());
        }
    }


    // Format and Disaplay for RegUser Account
    private void formatAndDisplayTasks(String projectData) {
        // Removing the brackets at the beginning and end of the project data
        projectData = projectData.substring(1, projectData.length() - 2);

        // Splitting project data into project details and tasks
        String[] projectParts = projectData.split("; Tasks: ");
        if (projectParts.length > 0) {
            // Displaying project details
            System.out.println("Project Name: " + projectParts[0].split(";")[0].split(":")[1].trim());
            System.out.println("Project Status: " + projectParts[0].split(";")[1].split(":")[1].trim());
        }

        if (projectParts.length > 1) {
            System.out.println("Tasks:");
            String[] tasks = projectParts[1].split("Task: ");
            for (int i = 1; i < tasks.length; i++) { // Start from 1 to skip the first empty split
                String[] taskDetails = tasks[i].trim().split("; ");
                for (String detail : taskDetails) {
                    String[] keyValue = detail.split(": ");
                    if (keyValue.length > 1) {
                        System.out.print("- " + keyValue[0] + ": ");
                        System.out.println(keyValue[1]);
                    } else {
                        System.out.println("- " + keyValue[0]);
                    }
                }
                if (i < tasks.length - 1) {
                    System.out.println();
                }
            }
        }
    }


    public void markTaskAsComplete(RegularUser user, String taskId) {
        // Logic to mark the task as complete in Registered_regUser.txt
        updateTaskStatusInFile(USER_FILE_PATH, user.getUsername(), taskId, "Complete");

        // Logic to mark the task as complete in Registered_Admin.txt
        updateTaskStatusInFile(ADMIN_FILE_PATH, user.getUsername(), taskId, "Complete");
    }

    private void updateTaskStatusInFile(String filePath, String username, String taskId, String newStatus) {
        Path path = Paths.get(filePath);
        try {
            List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
            for (int i = 0; i < fileContent.size(); i++) {
                String line = fileContent.get(i);
                if (line.contains("Task Owner: " + username + ";")) {
                    String updatedLine = updateTaskStatusInAdminLine(line, taskId, newStatus);
                    fileContent.set(i, updatedLine);
                }
            }
            Files.write(path, fileContent, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Error updating task status in file: " + e.getMessage());
        }
    }
    
    private String updateTaskStatusInAdminLine(String line, String taskId, String newStatus) {
        int taskStartIndex = line.indexOf("Task ID: " + taskId + ";");
        if (taskStartIndex != -1) {
            int taskEndIndex = line.indexOf(";", taskStartIndex + 1);
            String taskSubstring = line.substring(taskStartIndex, taskEndIndex);
            if (taskSubstring.contains("Task Status:")) {
                taskSubstring = taskSubstring.replaceAll("Task Status: [^;]*;", "Task Status: " + newStatus);
            } else {
                taskSubstring += "; Task Status: " + newStatus;
            }
            line = line.replace(line.substring(taskStartIndex, taskEndIndex), taskSubstring);
        }
        return line;
    }
    

    
    







}



