package TaskManagement10.TaskManagement;

import TaskManagement10.User.Admin;
import TaskManagement10.User.UserManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AdminFunctions {

    public static void handleAdminActions(Admin admin, UserManager userManager, Scanner scanner) {
        //Scanner scanner = new Scanner(System.in);
        ArrayList<Project> adminProjects = new ArrayList<>();

        System.out.println("Welcome, " + admin.getUsername());

        do {
            int numProjects = 0;
            boolean validNumProjects = false;
            while (!validNumProjects) {
                try {
                    System.out.println("\nHow many projects do you want to create? (Enter 0 to quit)");
                    numProjects = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    validNumProjects = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter an integer.");
                    scanner.nextLine(); // Clear the scanner buffer
                }
            }

            if (numProjects == 0) {
                break;
            }

            for (int i = 0; i < numProjects; i++) {
                System.out.println("Enter the name of project " + (i + 1) + ":");
                String projectName = scanner.nextLine();

                System.out.println("Enter the status of project " + (i + 1) + ":");
                String projectStatus = scanner.nextLine();

                Project project = new Project(projectName, projectStatus);

                int numTasks = 0;
                boolean validNumTasks = false;
                while (!validNumTasks) {
                    try {
                        System.out.println("How many tasks do you want to create for this project?");
                        numTasks = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        validNumTasks = true;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter an integer.");
                        scanner.nextLine(); // Clear the scanner buffer
                    }
                }

                for (int j = 0; j < numTasks; j++) {
                    String taskId = "0"+(j+1);

                    System.out.println("Enter the name of task " + (j + 1) + ":");
                    String taskName = scanner.nextLine();

                    String taskStatus = "null"; // Default task status set to empty

                    System.out.println("Enter the deadline for task " + (j + 1) + " (yyyy-mm-dd):");
                    String deadline = scanner.nextLine();

                    System.out.println("Assign this task to (username):");
                    String taskOwner = scanner.nextLine();

                    Tasks task = new Tasks(taskName, taskOwner, taskId, taskStatus, deadline);
                    project.addTask(task);
                }
                adminProjects.add(project);
            }
            System.out.println("Do you want to continue adding projects? (yes/no)");
        } while (scanner.nextLine().equalsIgnoreCase("yes"));

        printAdminReport(adminProjects);
        appendReportToFile(admin, adminProjects, "/Users/keomunin/Documents/Java/TaskManagement10/User/Registered_Admin.txt");

        for (Project project : adminProjects) {
            for (Tasks task : project.getTasks()) {
                userManager.appendTaskToUserAccount(task, project.getProjectName(), project.getProjectStatus());
            }
        }

        // Ask if the admin wants to sort tasks by deadline
        System.out.println("Do you want to sort tasks in any project by deadline? (yes/no)");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            // Let the admin choose which project to sort
            System.out.println("Enter the name of the project to sort tasks:");
            String projectName = scanner.nextLine();
            for (Project project : adminProjects) {
                if (project.getProjectName().equals(projectName)) {
                    // Call method to sort tasks in the chosen project
                    sortTasksByDeadline(project);
                    System.out.println("Tasks in project " + projectName + " have been sorted by deadline.");
                    System.out.println("\n\nSorted Tasks by Deadline:");
                    for (Tasks task : project.getTasks()) {
                    System.out.println("Task: " + task.getTaskName() 
                    + "\nDeadline: " + task.getDeadline()
                    + "\nOwner: " + task.getTaskOwner() 
                    + "\nStatus: " + task.getTaskStatus() + "\n");
                }
                    break;
                }

                
                
            }
        }

        
    }



    private static void printAdminReport(ArrayList<Project> projects) {
        System.out.println("\nAdmin Report:");

        for (Project project : projects) {
            System.out.println("Project Name: " + project.getProjectName());
            System.out.println("Project Status: " + project.getProjectStatus());
            System.out.println("Tasks:");
            for (Tasks task : project.getTasks()) {
                System.out.println("- Task ID: " + task.getTaskId());
                System.out.println("  Task Name: " + task.getTaskName());
                // String taskStatus = task.getTaskStatus().isEmpty() ? " " : task.getTaskStatus();
                System.out.println("  Task Deadline: " + task.getDeadline());
                System.out.println("  Task Owner: " + task.getTaskOwner());
                System.out.println("");
            
            }
        }
    }

    private static String formatReportForFile(ArrayList<Project> projects) {
        StringBuilder reportBuilder = new StringBuilder("[");
        for (Project project : projects) {
            reportBuilder.append("Project Name: ").append(project.getProjectName()).append("; ")
                         .append("Project Status: ").append(project.getProjectStatus()).append("; ")
                         .append("Tasks: [");
            for (Tasks task : project.getTasks()) {
                reportBuilder.append("Task ID: ").append(task.getTaskId()).append("; ")
                             .append("Task Name: ").append(task.getTaskName()).append("; ")
                             .append("Task Deadline: ").append(task.getDeadline()).append("; ")
                             // .append("Task Status: ").append(task.getTaskStatus()).append("; ")
                             .append("Task Owner: ").append(task.getTaskOwner()).append("; ");
            }
            reportBuilder.append("]; ");
        }
        reportBuilder.append("]");
        return reportBuilder.toString();
    }


    public static void appendReportToFile(Admin admin, ArrayList<Project> projects, String filePath) {
        String reportData = formatReportForFile(projects);
        Path path = Paths.get(filePath);
        try {
            List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
            boolean found = false;

            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).startsWith(admin.getUsername() + " / ")) {
                    String existingContent = fileContent.get(i);
                    String updatedContent = existingContent + " / " + reportData;
                    fileContent.set(i, updatedContent);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Admin not found in file.");
                return;
            }

            Files.write(path, fileContent, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Error updating admin data in file: " + e.getMessage());
        }
    }


    static void displayAdminReportFromFile(Admin admin, String filePath) {
        Path path = Paths.get(filePath);
        try {
            List<String> fileContent = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (String line : fileContent) {
                if (line.startsWith(admin.getUsername() + " / ")) {
                    String[] adminData = line.split(" / ");
                    if (adminData.length > 3) {
                        System.out.println("Previous Report for " + admin.getUsername() + ":");
                        System.out.println(adminData[3]); // Assuming the report is the fourth part
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
        


    // New method for sorting tasks by deadline
   private static void sortTasksByDeadline(Project project) {
        Collections.sort(project.getTasks(), new Comparator<Tasks>() {
            @Override
            public int compare(Tasks task1, Tasks task2) {
                // descending sorting
                return task2.getDeadline().compareTo(task1.getDeadline());
            }
        });
   }
}








