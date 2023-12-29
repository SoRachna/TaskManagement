package TaskManagement10.User;

import java.util.Scanner;

import TaskManagement10.TaskManagement.*;

public class RegularUser extends User {
    private String taskData;

    public RegularUser(String username, String password, String taskData) {
        super(username, password);
        this.taskData = taskData;
    }

    // Implementation of login method for RegularUser
    @Override
    public void login(UserManager userManager, Scanner scanner) {
        System.out.println("Regular User logged in");
        displayUserInfo("Welcome to the user dashboard!");

        // Specific implementation for RegularUser login
        RegularUserFunctions.handleRegularUserActions(this, userManager, scanner);
    }


    @Override
    public String toString() {
        return "RegularUser{username='" + username + "'}";
    }


    // overload
    public void displayUserInfo(String additionalMessage) {
        super.displayUserInfo(); // Call the base class method
        System.out.println(additionalMessage);
    }

    // Additional methods specific to RegularUser can be added here
    // For example:
    public void viewProfile() {
        System.out.println("Regular User viewing profile");
    }
    

    // Additional method to get task data
    public String getTaskData() {
        return taskData;
    }
    
    /* 
    private String updateTaskStatusInLine(String line, String taskId, String newStatus) {
        String taskPattern = "Task ID: " + taskId + ";";
        int taskStartIndex = line.indexOf(taskPattern);
        if (taskStartIndex != -1) {
            int taskEndIndex = line.indexOf("];", taskStartIndex); // Find end of task block
            if (taskEndIndex == -1) {
                taskEndIndex = line.length(); // Handle case for last task
            }
            String taskSubstring = line.substring(taskStartIndex, taskEndIndex);
    
            // Update the task status
            String updatedTaskSubstring;
            if (taskSubstring.contains("Task Status:")) {
                updatedTaskSubstring = taskSubstring.replaceAll("Task Status: [^;]*;", "Task Status: " + newStatus + ";");
            } else {
                updatedTaskSubstring = taskSubstring.replaceFirst(";", "; Task Status: " + newStatus + ";");
            }
            line = line.replace(taskSubstring, updatedTaskSubstring);
        }
        return line;
    }
    */

}
