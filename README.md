# Project Name: Task Management 
#### Member: Keo Munin & Kry Sorachna 

## I. Introduction
#### A. About the Project
The project focuses on task management, offering users an improved solution for managing tasks with effective functions. With more visibility of the teamwork environment, the system allows users to work and communicate confidentially within the association.
#### B. About the functions
This project will provide authentication to two different users, admin and normal users. 
##### 1. Register/ Log in
The system will take username and password as the user information saved within the database. After registering or logging in the system will serve the user based on the following commands and functionalities. 
##### 2. Task management:
The system's core value, Task management, is where all the implementation and communication are made. This function allows users to create tasks, assign tasks, and set necessary parameters as we will take the following data as required: 

Admin input: 
- Project name
- Add task 
- Assigned task owner
- Set/view deadline 
- Set/view task status

Normal User input:
- Add/view task
- View deadline
- Update task status
  
## II. Directory Structure
To maintain friendly access and organized code, this code structure was separated into two different folders, Task management and User. In the Task Management folder, you will find the main file such as Program.java, and other necessary files of functions stored. Whereas, inside the User folder, you will find all the functions that mainly focus on taking in and storing user information as txt.file. Regarding the separation, however, all the classes remain related and all are part of the Program.java  file. 
#### -->Folder Task management
To access to files in "TaskManagement" directory navigate to: [TaskManagement](https://shorturl.at/aEJR9)
##### 1. Classes
###### a. Program.java
Java class that contains the main () method to compile and execute the program. In this main file, the program will ask for user input on three main options based on  their purpose to use the program as the entry point for the application. Regarding the three options of login, register, and exit, functions such as .login() and .registerUser() were called for further implementation. 
###### a. AdminFunction.java
The main purpose of this class is to allow Admin to create projects and tasks. It has two functions: .handleAdminActions( ) and .printAdminReport( ).  
###### b. RegularUserFunction.java
This class is created to allow to user to perform actions such as view and update tasks as complete by creating .handelUserActions( ) function. 
###### c. Project.java and Task.java
These classes represent the core entities of the program.
##### 2. Function
i. .handleAdminActions(): A function inside the AdminFunction class that implements project and task creation for admin. 
ii. .printAdminReport( ): A function inside the AdminFuncion class that takes the initiative to generate all the existing projects and tasks after the admin ends the implementation of creating or adding them.
iii. .handleUseActions( ): A function inside the RegularUserFunction class that gives the user access to implement view and update their task as well as its status. 
#### --> Folder User
To access files in "Use" directory, navigate to: [User](https://shorturl.at/bsL17)
##### 1. Classes
###### a. User (Superclass) 
An abstract superclass class that has two objects, username and password. The reason that set User as a superclass is that we expect to have multiple types of users using our program for example we have admin and regular user. As for the abstract class, since all the users need to log in before using our application therefore we use the abstract method to make sure all the subclasses that inherit from the superclass need to also implement login. 
 ###### b.Admin (subclass)
 A subclass inheritance from the Superclass User. Admin is the type of user that can perform more actions than regular users such as creating projects, adding tasks, assigning task owners, and viewing the task status that has been updated by the task owner or regular user.
 ###### c. RegularUser (Inheritance)
 A subclass that also the inheritance of the User class. 
 ###### d.Register.java
We create this class for all the related functions for the register process including the function for users who register as regular users and the function for users who register as admin. In this class, we store the user's data to paths that we had created and the register function will be called to use with registerUser function in the Usermanager.java class. 
 ###### Usernamanager.java
This class was created to store all the functions that are used to manage  user data. For example, registeruser, athenticateUser , promtlogin, etc. 
#### Essential part of the project
##### Polymorphism: Casting (Overriding and Overloading method)
###### casting
	Code line: userManager.displayAdminReportFromFile((Admin) user); 
        *User being cast to Admin
###### Overriding 

	The overriding method was used on both the Admin and the RegularUser class because we want to override the login function User and add on some special conditions such as secrete password in the admin class. 
#### Overloading


	We use Overload method both inside the Admin and RegularUser class as function displayUserInfo.() was called to use but they have different parameters. 
#### Encapsulation: (Private, Public, protected)


	We set username as protected because we only want to class that extends from the user can access it. 
 	We set password as private because we don't want to show it or anyone can access it without permission. 
#### Abstraction
Abstract class used to define a common structure, specify behavior that subclasses must implement, and encapsulate common functionality shared among multiple subclasses.
**User** is the abstract class. 
```ruby

package TaskManagement10.User;

import java.util.Scanner;

public abstract class User {
    protected String username;
    private String password;

    // Constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
```
We applied abstract method to provid the common structure for the subclass to implement.

```ruby
 public abstract void login(UserManager userManager, Scanner scanner);
``` 

#### Exception Handling
An exception is an object representing an error or an unexpected event that occurs during program execution. In this program we use handling expection to handle error such as **File is not found** and **Reading file problem**. 

**File is not found**: If the **FileNotFoundException** occurs during the attempt to open the file, the catch block executes the code to handle this specific exception. By catching the **FileNotFoundException**, the code ensures that if the file isn’t found or there’s a problem accessing it, the program doesn't crash abruptly. 

```ruby
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
```
**Report/Task not found in admin flie**: The function is created to diplay report from the admin file. **IOException e** occurs when there're problem reading file. the catch block will execute the code that handling the error part.  
```ruby
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
```

#### File I/O

We have 2 file: 

- **Registered_Admin.txt**: Use to store admin data

- **Registered_regUser.txt**: Use to store User data

#### Static method
We use static method to access class without creating object. 

```ruby
public static void handleAdminActions(Admin admin, UserManager userManager, Scanner scanner)
public static void appendReportToFile(Admin admin, ArrayList<Project> projects, String filePath)
public static void handleRegularUserActions(RegularUser user, UserManager userManager, Scanner scanner)
```



	

