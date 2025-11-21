package login;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * PROG5121 POE - Part 3
 * MessageManager handles parallel arrays of messages, searching, deleting and reporting.
 *
 * 
 * Usage:
 * - Call populateTestData() to fill arrays with the 5 test messages.
 * - Then use the search/report/delete methods below.
 */
public class MessageManager {

    // Parallel arrays (fixed length as per test data requirement)
    private String[] messageIDs;
    private String[] recipients;
    private String[] messages;
    private String[] flags; // "Sent", "Stored", "Disregard", "Developer"
    private String[] hashes;
    private int capacity; // number of slots (5 for test data)

    public MessageManager(int capacity) {
        this.capacity = capacity;
        messageIDs = new String[capacity];
        recipients = new String[capacity];
        messages = new String[capacity];
        flags = new String[capacity];
        hashes = new String[capacity];
    }

    // Populate arrays using test data from the rubric (messages 1-5)
    public void populateTestData() {
        // Using deterministic message IDs (MSG001 ... MSG005) so hashes are deterministic
        messageIDs[0] = "MSG001";
        recipients[0] = "+27834557896";
        messages[0] = "Did you get the cake?";
        flags[0] = "Sent";

        messageIDs[1] = "MSG002";
        recipients[1] = "+27838884567";
        messages[1] = "Where are you? You are late! I have asked you to be on time.";
        flags[1] = "Stored";

        messageIDs[2] = "MSG003";
        recipients[2] = "+27834484567";
        messages[2] = "Yohoooo, I am at your gate.";
        flags[2] = "Disregard";

        messageIDs[3] = "MSG004";
        recipients[3] = "0838884567"; // Developer entry (not +27)
        messages[3] = "It is dinner time !";
        flags[3] = "Sent";

        messageIDs[4] = "MSG005";
        recipients[4] = "+27838884567";
        messages[4] = "Ok, I am leaving without you.";
        flags[4] = "Stored";

        // generate deterministic hashes for each message slot
        for (int i = 0; i < capacity; i++) {
            hashes[i] = generateDeterministicHash(messageIDs[i], i, messages[i]);
        }
    }

    // Deterministic hash creation so tests can reference it.
    // Format used: lastTwoDigitsOfID:index:FIRSTLAST (uppercase, no spaces)
    // e.g. MSG001 -> "01:0:DIDYOU?"
    private String generateDeterministicHash(String id, int index, String text) {
        String idDigits = id.length() >= 3 ? id.substring(id.length() - 2) : id;
        String[] words = text.trim().split("\\s+");
        String first = words.length > 0 ? words[0].replaceAll("[^A-Za-z0-9]", "").toUpperCase() : "";
        String last = words.length > 1 ? words[words.length - 1].replaceAll("[^A-Za-z0-9]", "").toUpperCase() : "";
        return idDigits + ":" + index + ":" + first + last;
    }

    // 3.a Display the sender and recipient of all sent messages.
    public List<String> getAllSentMessageSenderRecipient() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            if (flags[i] != null && flags[i].equalsIgnoreCase("Sent")) {
                // sender: we don't store a sender name in the test data; use "Developer" for dev entry,
                // otherwise show "UnknownSender" (rubric focuses on recipient/message)
                String sender = (recipients[i] != null && recipients[i].equals("0838884567")) ? "Developer" : "Sender";
                result.add("Sender: " + sender + " | Recipient: " + recipients[i]);
            }
        }
        return result;
    }

    // 3.b Display the longest message among the available dataset (searching across the arrays)
    public String getLongestMessage() {
        int longestIndex = -1;
        int maxLen = -1;
        for (int i = 0; i < capacity; i++) {
            if (messages[i] != null && messages[i].length() > maxLen) {
                maxLen = messages[i].length();
                longestIndex = i;
            }
        }
        return longestIndex >= 0 ? messages[longestIndex] : "";
    }

    // 3.c Search for a message ID and display the corresponding recipient and message.
    public String searchByMessageID(String id) {
        for (int i = 0; i < capacity; i++) {
            if (Objects.equals(messageIDs[i], id)) {
                return "Recipient: " + recipients[i] + " | Message: " + messages[i];
            }
        }
        return "Message ID not found.";
    }

    // 3.d Search for all the messages sent or stored regarding a particular recipient
    public List<String> searchByRecipient(String recipient) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            if (recipients[i] != null && recipients[i].equals(recipient)) {
                result.add(messages[i]);
            }
        }
        return result;
    }

    // 3.e Delete a message using a message hash (returns confirmation text)
    // When deleted, we null-out the message and its hash/recipient/flag (keeps arrays aligned).
    public String deleteByHash(String hash) {
        for (int i = 0; i < capacity; i++) {
            if (hashes[i] != null && hashes[i].equals(hash)) {
                String deletedMessage = messages[i];
                // remove by setting to null
                messageIDs[i] = null;
                recipients[i] = null;
                messages[i] = null;
                flags[i] = null;
                hashes[i] = null;
                return "Message \"" + deletedMessage + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    // 3.fDisplay a report that lists the full details of all the sent messages
    // (Message Hash, Recipient, Message)
    public List<String> getSentMessagesReport() {
        List<String> report = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            if (flags[i] != null && flags[i].equalsIgnoreCase("Sent")) {
                report.add("Hash: " + hashes[i] + " | Recipient: " + recipients[i] + " | Message: " + messages[i]);
            }
        }
        return report;
    }

    // Return arrays as copies (for tests)
    public String[] getMessageIDs() { return Arrays.copyOf(messageIDs, capacity); }
    public String[] getRecipients() { return Arrays.copyOf(recipients, capacity); }
    public String[] getMessages() { return Arrays.copyOf(messages, capacity); }
    public String[] getFlags() { return Arrays.copyOf(flags, capacity); }
    public String[] getHashes() { return Arrays.copyOf(hashes, capacity); }

    // --------------- JSON read/write (using org.json.simple) -------------------
    // Writes current arrays to JSON file (array of objects)
    public boolean writeToJson(String filepath) {
        try (FileWriter file = new FileWriter(filepath)) {
            JSONArray list = new JSONArray();
            for (int i = 0; i < capacity; i++) {
                JSONObject obj = new JSONObject();
                obj.put("messageID", messageIDs[i]);
                obj.put("recipient", recipients[i]);
                obj.put("message", messages[i]);
                obj.put("flag", flags[i]);
                obj.put("hash", hashes[i]);
                list.add(obj);
            }
            file.write(list.toJSONString());
            file.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Reads JSON file into arrays (overwrites current arrays)
    @SuppressWarnings("unchecked")
    public boolean readFromJson(String filepath) {
        try (FileReader reader = new FileReader(filepath)) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(reader);
            JSONArray arr = (JSONArray) obj;
            // ensure capacity equals arr.size() or only load up to capacity
            int i = 0;
            for (Object o : arr) {
                if (i >= capacity) break;
                JSONObject jobj = (JSONObject) o;
                messageIDs[i] = (String) jobj.get("messageID");
                recipients[i] = (String) jobj.get("recipient");
                messages[i] = (String) jobj.get("message");
                flags[i] = (String) jobj.get("flag");
                hashes[i] = (String) jobj.get("hash");
                i++;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
