# Project Name: Task Management 
## Member: Keo Munin & Kry Sorachna 

## Table of content

### 1. Introduction


## Introduction

The project focuses on task management, offering users an improved solution for managing tasks with effective functions. With more visibility of the teamwork environment, the system allows users to work and communicate confidentially within the association.


## Feature

1. **Authentication**
    
    This project will provide authentication to two different users, admin and normal users.
    
    - **Login (as Admin / as RegularUser)**
        - Input Username
        - Input Password
        - Input verification key for Admin login.
    - **Register (as Admin / as RegularUser)**
        - Input Username
        - Input Password
        - Input verification key for Admin account registration.
2. **Admin Functions**
    - Input Project names
    - Input tasks
    - Input deadline
    - Input Status
    - Assign task owner
3. **RegularUser Functions**
    - View assigned Project and Tasks
    - Update task status (Mark task done)


## Classes and Objects

We a total of 10 classes. To maintain friendly access and organized code, this code structure was separated into two different packages, “**`TaskManagement`**” and “**`User`**”. 

In the TaskManagement package, you will find “**`Program.java`**” where the program will run, and other necessary classes of functions stored. Whereas, inside the User folder, you will find all the classes needed for both user types (Admin and RegularUser) such as Login, Register, Authentication. 

Moreover, This Project is using simple data storing, where the action of user input will be store in text file. To convenience in checking up stored data, the file are separate into two file which are one for Admin action storage, the other for RegularUser action storage. (”**`Registered_Admin.txt`**” & “**`Registered_regUser.txt`**”).

### TaskManagement Package

1. **`Program.java`**: To run the program.
2. **`AdminFunctions.java`**: To perform admin’s functions.
3. **`RegularUserFunctions.java`**: To perform regular user’s functions.
4. **`Project.java`**: Represents a project entity within the program. This class likely includes details such as project name, status, associated tasks, and any other relevant information that defines a project.
5. **`Tasks.java`**: Represents a task entity. This class likely includes task-specific information such as task name, description, deadlines, status, and the user responsible for the task.

### 2. User Package

1. **`UserManager.java`**: Manages user-related operations. This class might handle tasks like user authentication, user registration, and maintaining user records.
2. **`Register.java`**: Handles the registration process for users. This could involve collecting user information, validating it, and storing new user records.
3. **`User.java`**: Represents a generic user in the system. This class serves as a base class for different types of users, defining common attributes and methods.
4. **`Admin.java`**: Extends the **`User`** class to represent an administrative user. It likely adds additional admin-specific properties and methods to the basic user functionalities.
5. **`RegularUser.java`:** A subclass of **`User`** that represents a regular user in the system, typically with more limited access and capabilities compared to an admin.
6. **`Registered_Admin.txt`**: A text file likely used for storing data related to admin users. 
7. **`Registered_regUser.txt`**: A text file likely used for storing data related to regular users. 

## Inheritance

**Inheritance** is an “is-a” relationship between super class and sub classes. It allows a new class to extend an existing class. The new class inherits the members of the class it extends.

The idea behind the use of inheritance brings benefits such as:

- **Code reusability**: The code that is present in the parent class can be directly used by the child class, reducing code duplication.
- **Method Overriding**: Subclasses can override methods of the superclass to provide specific implementation.
- **Polymorphism Support**: Enables polymorphic behavior where objects of different subclasses can be treated as objects of the superclass.
- **Maintainability**: Changes in the superclass can benefit all subclasses, making it easier to maintain and update the code.

**There is a clear example of inheritance in the User package in the project:**

- Admin is a user
- RegularUser is a user.

Hence, the inheritance  applied between “`User.java`”  “`Admin.java`” and “`RegularUser.java`”. 

1. **`User` Class (Superclass/Base Class)**:
    - This class likely defines common properties and methods that are shared by all types of users, such as username, password, and common methods like **`login`**.
2. **`Admin` Class (Subclass)**:
    - Inherits from the **`User`** class.
    - It extends the **`User`** class by adding admin-specific properties and methods, such as admin privileges.
3. **`RegularUser` Class (Subclass)**:
    - Also inherits from the **`User`** class.
    - Tailors the **`User`** class for regular users, potentially adding or overriding methods to fit the needs of a regular user, such as limited access compared to an admin.
    
    ```ruby
    public abstract class User {
        protected String username;
        private String password;
    
        // Constructor
        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }
    
    ...
    
        // Common login functionality
        protected void commonLogin() {
            System.out.println(username + " is logging in...");
        }
    
        // Abstract method for user-specific login
        public abstract void login(UserManager userManager, Scanner scanner);
    
    ...
    ```
    
    ```java
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
            ...
        }
    
        @Override
        public String toString() {
            return "Admin{username='" + username + "', isAdminVerified=" + isAdminVerified + "}";
        }
    
        @Override
        public boolean equals(Object obj) {
            ...
        }
    
        //Overload
        public void displayUserInfo(boolean includeSecurityStatus) {
            super.displayUserInfo(); // Call the base class method
            ...
        }
    
        public boolean isAdminVerified() {
            return isAdminVerified;
        }
    
        private boolean verifyAdmin(Scanner scanner) {
            ...
        }
    
        private boolean checkAdminAnswer(String username, String answer) {
            ...
        }
    }
    ```
    
    ```java
    public class RegularUser extends User {
        private String taskData;
    
        public RegularUser(String username, String password, String taskData) {
            super(username, password);
            this.taskData = taskData;
        }
    
        // Implementation of login method for RegularUser
        @Override
        public void login(UserManager userManager, Scanner scanner) {
            ...
        }
    
        @Override
        public String toString() {
            return "RegularUser{username='" + username + "'}";
        }
    
        // overload
        public void displayUserInfo(String additionalMessage) {
            ...
        }
    
        public void viewProfile() {
            ...
        }
    
        // Additional method to get task data
        public String getTaskData() {
            return taskData;
        }
    }
    ```
    
## Polymorphism
    
**Polymorphism** in Object-Oriented Programming (OOP) is a concept that refers to the ability of a single interface to represent different underlying forms (data types). In Java, polymorphism manifests in two primary forms: method overloading and method overriding.
    
**The idea behind the use of inheritance brings benefits such as:**   
- **Flexibility**: Polymorphism allows methods to be written that can operate on objects of multiple types (as long as they share a common superclass or interface).
- **Simplicity**: It simplifies code by allowing the same method to operate on different types of data.
- **Extensibility**: New classes that are subclasses of a polymorphic superclass can be added to a program without modifying the program.
- **Interchangeability**: Objects of different classes can be interchanged if they are polymorphic, reducing the coupling between classes.
    
**Casting:**

    ```java
    public class Admin extends User {
        private boolean isAdminVerified = false;
        private String reportData; 
    
    	...
    
    	@Override
        public boolean equals(Object obj) {
            if (!super.equals(obj)) return false;
            Admin admin = (Admin) obj;
            return isAdminVerified == admin.isAdminVerified;
        }
    }
    ```


## Encapsulation
    
**Encapsulation** is an access modifier  that involves bundling the data (variables) and methods that operate on the data into a single unit or class. It also involves restricting direct access to some of an object's components, which is typically achieved using access modifiers: public, private, protected, and default (package-private).
    
**The idea behind the use of inheritance brings benefits such as:**
    
- **Control of Data**: Encapsulation allows you to control who can access and modify your data. This is crucial for maintaining the integrity of the data.
- **Reducing Complexity**: By hiding the internal workings of objects, encapsulation makes software easier to use and understand.
- **Flexibility and Maintainability**: Encapsulation allows changing the internal implementation of the class without affecting the classes that use it.
- **Increased Security**: By limiting the access to the data and methods, encapsulation helps in preventing unauthorized or harmful modifications.
    
**There is a clear example of inheritance in the User package in the project:**
    
1. **Public**
   - **Usage**: Members (variables or methods) declared as public can be accessed from any other class.
   - **Example**: **`public void login()`** in the **`User`** class.
2. **Private**
   - **Usage**: Private members can only be accessed within the class they are declared.
   - **Example**:  **`User`** classes, like `**private String password;`.**
3. **Protected**
   - **Usage**: Protected members can be accessed within the same package and also in subclasses.
   - **Example**:**`protected String username;`** in the **`User`** class.
    
## Abstraction
    
**Abstraction** in OOP is the concept of hiding the complex implementation details and showing only the necessary features of an object. It can be achieved through abstract classes and interfaces.
    
- **Abstract Class**: An abstract class cannot be instantiated (you cannot create objects of an abstract class). It is used to provide a base for subclasses to extend and implement the abstract methods.
- **Abstract Method**: An abstract method is a method that is declared without an implementation. Subclasses are required to provide an implementation for these methods.

**There is a clear example of inheritance in the User package in the project:** 
- **Reducing Complexity**: Abstraction allows you to focus on what the object does instead of how it does it.
- **Reusability**: Abstract classes provide a template for other classes to use and extend.
- **Design Flexibility**: It provides a way to plan for dynamic polymorphism in your design.
- **Control**: Abstract classes can enforce a contract for subclasses, ensuring certain methods are implemented.
    
**In the project, there is a use abstraction in the `User` class:**
    
1. **`User` as an Abstract Class**:
   - The **`User`** class is likely declared as abstract, indicating it cannot be instantiated on its own and is meant to be a base class for other types of users.
    
	    ```java
	    public abstract class User {
	        // ... Fields and methods ...
	    
	        public abstract void login(); // Abstract method
	    }
	    ```
    
2. **Abstract Methods**:
   - Within the **`User`** class, there is a  abstract methods (like **`login`**).
            
            ```java
                public abstract void login(UserManager userManager, Scanner scanner);
            ```
            
   - Each subclass of **`User`** (like **`Admin`** and **`RegularUser`**) is required to provide its own implementation of these abstract methods.
3. **Subclasses Implementing Abstract Methods**:
   - The **`Admin`** and **`RegularUser`** classes would extend the **`User`** class and provide concrete implementations o the abstract methods.
            
            ```java
            public class Admin extends User {
                @Override
                public void login() {
                    // Implementation specific to Admin
                }
            }
            ```
            
            ```java
            public class RegularUser extends User {
                @Override
                public void login() {
                    // Implementation specific to RegularUser
                }
            }
            ```

## Exception Handling
**Expection Handling** is an object representing an error or an unexpected event that occurs during program execution. In this program we use handling exceptions in 6 different cases to handle errors such as **File is not found** and **Reading file problem**. 

- **File is not found**: If the **FileNotFoundException** occurs during the attempt to open the file, the catch block executes the code to handle this specific exception. By catching the **FileNotFoundException**, the code ensures that if the file isn’t found or there’s a problem accessing it, the program doesn't crash abruptly. 

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
- **Report/Task not found in admin flie**: The function is created to diplay report from the admin file. **IOException e** occurs when there're problem reading file. the catch block will execute the code that handling the error part.  
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

## File I/O
**File I/O** (Input/Output) refers to the interaction between a program and files on a storage device such as a hard drive, solid-state drive, or external storage media.
Including file I/O in our program is essential as we need to store user data once they Login or Register. Again based on our case, we separated the user into two types, therefore, we have two files as below. In the admin file, we store 3 types of data: "**Username**", "**Password**", and "**secret_code**", which are separated by "\". On the other hand, for regularUser files, we store two types of data: "**Username**" and "**Password**". We use these data for future use such as Login case. 

We have 2 files: 

- **Registered_Admin.txt**: Use to store admin data

- **Registered_regUser.txt**: Use to store User data

## Static method
**Static method**: It's a method that can be called on the class itself, without requiring the instantiation of an object of that class.

**Usage**: We use static methods to access classes without creating an object. 

```ruby
public static void handleAdminActions(Admin admin, UserManager userManager, Scanner scanner)
public static void appendReportToFile(Admin admin, ArrayList<Project> projects, String filePath)
public static void handleRegularUserActions(RegularUser user, UserManager userManager, Scanner scanner)
```



	

