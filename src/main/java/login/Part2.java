/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

import javax.swing.JOptionPane;
/**
 *
 * @author RC_Student_Lab
 * PROG5121 POE - Part 2
 * Main driver class that interacts with the user to send and view messages.
 */
public class Part2 {

    public static void main(String[] args) {
        boolean exit = false;

        JOptionPane.showMessageDialog(null, "Welcome to QuickChat!");

        while (!exit) {
            String menu = JOptionPane.showInputDialog("""
                    Please select an option:
                    1) Send Message
                    2) View Total Messages Sent
                    3) Quit
                    """);

            if (menu == null) break; // Cancelled dialog

            switch (menu) {
                case "1" -> sendMessage();
                case "2" -> JOptionPane.showMessageDialog(null,
                        "Total Messages Sent: " + Message.generateMessageID());
                case "3" -> {
                    exit = true;
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                }
                default -> JOptionPane.showMessageDialog(null, "Invalid option, try again.");
            }
        }
    }

    private static void sendMessage() {
        String messageID = Message.generateMessageID();
        String cell = JOptionPane.showInputDialog("Enter recipient cell number (+27...)");
        String text = JOptionPane.showInputDialog("Enter your message (max 250 characters):");

        Message m = new Message(messageID, cell, text);

        if (!m.checkMessageID()) {
            JOptionPane.showMessageDialog(null, "Invalid Message ID length.");
            return;
        }

        if (!m.checkRecipientCell()) {
            JOptionPane.showMessageDialog(null, "Invalid cell number format.");
            return;
        }

        JOptionPane.showMessageDialog(null, m.checkMessageLength());

        String hash = m.createMessageHash();
        JOptionPane.showMessageDialog(null, "Message Hash: " + hash);

        String option = JOptionPane.showInputDialog("""
                Select an option:
                1 - Send Message
                2 - Delete Message
                3 - Store Message
                """);

        try {
            int opt = Integer.parseInt(option);
            JOptionPane.showMessageDialog(null, m.sendMessageOption(opt));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input.");
        }

        JOptionPane.showMessageDialog(null, m.printMessageDetails());
    }
}