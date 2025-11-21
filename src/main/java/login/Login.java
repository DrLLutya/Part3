/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

/**
 *
 * @author RC_Student_lab
 *
 * PROG5121 POE - Part 1 Login class: handles registration and login logic.
 *
 * References: Oracle. (2023). Java Regular Expressions. Oracle. Available at:
 * https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
 * [Accessed 15 Sept. 2025].
 */
public class Login {
    
    private String username;
    private String password;
    private String cellPhone;
    private String firstName;
    private String lastName;

    // Constructor - creates a new Login object with user details
    public Login(String username, String password, String cellPhone, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.cellPhone = cellPhone;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Method checks if username contains an underscore and is <= 5 characters
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    // Method checks if password meets complexity rules:
    // - at least 8 characters
    // - contains one capital letter
    // - contains one number
    // - contains one special character
    public boolean checkPasswordComplexity() {
        boolean length = password.length() >= 8;
        boolean capital = password.matches(".*[A-Z].*");
        boolean number = password.matches(".*[0-9].*");
        boolean special = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");

        return length && capital && number && special;
    }

    // Method checks if cell phone number starts with +27 and has 9 more digits
    // Regex pattern is based on Oracle Java documentation for regular expressions
    public boolean checkCellPhoneNumber() {
        return cellPhone.matches("^\\+27\\d{9}$");
    }

    // Method returns the correct registration message depending on checks
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        return "User registered successfully.";
    }

    // Method verifies if login details match the stored details
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return this.username.equals(enteredUsername) && this.password.equals(enteredPassword);
    }

    // Method returns correct login message
    public String returnLoginStatus(boolean success) {
        if (success) {
            return "Welcome " + firstName + " " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}