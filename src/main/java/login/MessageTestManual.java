/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

/**
 *
 * @author RC_Student_Lab
 * PROG5121 POE - Part 2
 * Manual testing class for Message.java.
 * Tests methods and prints outputs to console.
 */
public class MessageTestManual {
    public static void main(String[] args) {
        Message m1 = new Message("MSG123", "+27831234567", "Hello there!");
        Message m2 = new Message("MSG123456789", "+27123456789", "This message is too long...".repeat(20));

        System.out.println("===== TESTING MESSAGE 1 =====");
        System.out.println("Check ID: " + m1.checkMessageID());
        System.out.println("Check Cell: " + m1.checkRecipientCell());
        System.out.println("Message Length: " + m1.checkMessageLength());
        System.out.println("Hash: " + m1.createMessageHash());
        System.out.println("Send Option: " + m1.sendMessageOption(1));
        System.out.println("Total Sent: " + m1.returnTotalMessages());
        System.out.println(m1.printMessageDetails());

        System.out.println("\n===== TESTING MESSAGE 2 =====");
        System.out.println("Check ID: " + m2.checkMessageID());
        System.out.println("Check Cell: " + m2.checkRecipientCell());
        System.out.println("Message Length: " + m2.checkMessageLength());
        System.out.println("Hash: " + m2.createMessageHash());
    }
}