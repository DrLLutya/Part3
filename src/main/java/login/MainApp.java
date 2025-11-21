/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package login;

import java.util.Scanner;

/**
 *
 * @author RC_Student_lab
 * PROG5121 POE - Part 1
 * Main class to interactively test the Login functionality.
 */
public class MainApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         Scanner scanner = new Scanner(System.in);

        // Ask user for registration details
        System.out.println("=== User Registration ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter cell phone number (e.g. +27831234567): ");
        String cellPhone = scanner.nextLine();

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        // Create a new Login object with the entered details
        Login user = new Login(username, password, cellPhone, firstName, lastName);

        // Run registration and show result
        String regMessage = user.registerUser();
        System.out.println("\nRegistration Result: " + regMessage);

        // If registration was successful, allow login attempt
        if (regMessage.equals("User registered successfully.")) {
            System.out.println("\n=== Login ===");
            System.out.print("Enter username to login: ");
            String enteredUsername = scanner.nextLine();

            System.out.print("Enter password to login: ");
            String enteredPassword = scanner.nextLine();

            boolean loginSuccess = user.loginUser(enteredUsername, enteredPassword);
            System.out.println(user.returnLoginStatus(loginSuccess));
        } else {
            System.out.println("Registration failed. Please restart the program.");
        }

        scanner.close();
    
    }
    
}
