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


    // toString and equal polymerphism
    @Override
    public String toString() {
    return "User{username='" + username + "'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return username.equals(user.username);
    }


    // Common login functionality (if any) can be implemented here
    protected void commonLogin() {
        // Implement any common login logic here
        System.out.println(username + " is logging in...");
    }

    // Abstract method for user-specific login
    public abstract void login(UserManager userManager, Scanner scanner);

    public void displayUserInfo() {
        System.out.println("\n// This is overload method ");
        System.out.println("Username: " + username);
    }

    // Getter methods
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setter methods
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
