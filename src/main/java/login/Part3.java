package login;

import javax.swing.JOptionPane;
import java.util.List;

/**
 * PROG5121 POE - Part 3
 * Interactive menu to demonstrate the Part 3 features:
 * - populate test data
 * - display sent messages
 * - display longest message
 * - search by messageID
 * - search by recipient
 * - delete by hash
 * - display full sent report
 */
public class Part3 {

    private static final String JSON_PATH = "messages_output.json"; // relative to project root

    public static void main(String[] args) {
        MessageManager mm = new MessageManager(5);
        mm.populateTestData();

        boolean exit = false;
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat - Part 3");

        while (!exit) {
            String input = JOptionPane.showInputDialog("""
                    Part 3 Menu:
                    1) Display sender & recipient of sent messages
                    2) Display longest message
                    3) Search by message ID
                    4) Search messages by recipient
                    5) Delete message by hash
                    6) Display sent messages report
                    7) Save messages to JSON
                    8) Load messages from JSON
                    9) Quit
                    """);
            if (input == null) break;

            switch (input) {
                case "1" -> {
                    List<String> list = mm.getAllSentMessageSenderRecipient();
                    JOptionPane.showMessageDialog(null, String.join("\n", list));
                }
                case "2" -> JOptionPane.showMessageDialog(null, mm.getLongestMessage());
                case "3" -> {
                    String id = JOptionPane.showInputDialog("Enter message ID (e.g. MSG001):");
                    JOptionPane.showMessageDialog(null, mm.searchByMessageID(id));
                }
                case "4" -> {
                    String rec = JOptionPane.showInputDialog("Enter recipient (e.g. +27838884567):");
                    List<String> found = mm.searchByRecipient(rec);
                    JOptionPane.showMessageDialog(null, String.join("\n", found));
                }
                case "5" -> {
                    String hash = JOptionPane.showInputDialog("Enter message hash to delete (exact):");
                    JOptionPane.showMessageDialog(null, mm.deleteByHash(hash));
                }
                case "6" -> {
                    List<String> report = mm.getSentMessagesReport();
                    JOptionPane.showMessageDialog(null, String.join("\n", report));
                }
                case "7" -> {
                    boolean ok = mm.writeToJson(JSON_PATH);
                    JOptionPane.showMessageDialog(null, ok ? "Saved to " + JSON_PATH : "Save failed.");
                }
                case "8" -> {
                    boolean ok = mm.readFromJson(JSON_PATH);
                    JOptionPane.showMessageDialog(null, ok ? "Loaded from " + JSON_PATH : "Load failed.");
                }
                case "9" -> { exit = true; JOptionPane.showMessageDialog(null, "Exiting Part 3."); }
                default -> JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        }
    }
}
