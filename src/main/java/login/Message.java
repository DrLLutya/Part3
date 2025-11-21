/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

import java.util.Random;
/**
 *
 * @author RC_Student_Lab
 * PROG5121 POE - Part 2
 * Message class handles all message creation, validation and formatting logic.
 *
 * References:
 * Oracle. (2023). Java Regular Expressions. Oracle.
 * Available at: https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html [Accessed 15 Sept. 2025].
 */
public class Message {

    private String messageID;
    private String recipientCell;
    private String messageText;
    private static int totalMessages = 0;

    // Constructor
    public Message(String messageID, String recipientCell, String messageText) {
        this.messageID = messageID;
        this.recipientCell = recipientCell;
        this.messageText = messageText;
    }

    // Validates that the message ID is no more than 10 characters long
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // Checks recipient cell format (+27 and 9 digits)
    public boolean checkRecipientCell() {
        return recipientCell.matches("^\\+27\\d{9}$");
    }

    // Checks if message is within 250 characters
    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + " characters.";
        }
    }

    // Creates a message hash format: XX:X:FIRSTLAST
    public String createMessageHash() {
        String[] words = messageText.trim().split(" ");
        String first = words[0].toUpperCase();
        String last = words[words.length - 1].toUpperCase();
        Random rand = new Random();
        int firstPart = rand.nextInt(90) + 10; // two digits
        int secondPart = rand.nextInt(10); // one digit
        return firstPart + ":" + secondPart + ":" + first + last;
    }

    // Simulates sending, deleting or storing a message
    public String sendMessageOption(int option) {
        switch (option) {
            case 1:
                totalMessages++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete message.";
            case 3:
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    // Returns total messages sent
    public int returnTotalMessages() {
        return totalMessages;
    }

    // Displays message details
    public String printMessageDetails() {
        return "Message ID: " + messageID + "\nRecipient: " + recipientCell +
                "\nMessage: " + messageText;
    }

    // Generates random message ID (utility method)
    public static String generateMessageID() {
        Random rand = new Random();
        int num = 100000 + rand.nextInt(900000);
        return "MSG" + num;
    }
}
