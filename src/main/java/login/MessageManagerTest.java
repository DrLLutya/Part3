package login;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Test class for Part 3 MessageManager functionality.
 * Uses the specific test data from the rubric.
 */
public class MessageManagerTest {

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        MessageManager mm = new MessageManager(5);
        mm.populateTestData();
        List<String> sent = mm.getAllSentMessageSenderRecipient();
        // Expect two "Sent" entries (message 1 and message 4)
        assertEquals(2, sent.size());
        assertTrue(sent.get(0).contains("+27834557896")); // message 1 recipient
        assertTrue(sent.get(1).contains("0838884567"));   // message 4 developer entry
        // Check the message texts for sent messages using report
        List<String> report = mm.getSentMessagesReport();
        assertTrue(report.get(0).contains("Did you get the cake?"));
        assertTrue(report.get(1).contains("It is dinner time"));
    }

    @Test
    public void testDisplayLongestMessage() {
        MessageManager mm = new MessageManager(5);
        mm.populateTestData();
        String longest = mm.getLongestMessage();
        // according to rubric the longest between messages 1-4 is message 2
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    @Test
    public void testSearchByMessageID() {
        MessageManager mm = new MessageManager(5);
        mm.populateTestData();
        String res = mm.searchByMessageID("MSG004");
        assertTrue(res.contains("0838884567"));
        assertTrue(res.contains("It is dinner time"));
    }

    @Test
    public void testSearchByRecipient() {
        MessageManager mm = new MessageManager(5);
        mm.populateTestData();
        List<String> list = mm.searchByRecipient("+27838884567");
        // messages 2 and 5 belong to this recipient
        assertEquals(2, list.size());
        assertTrue(list.get(0).contains("Where are you?"));
        assertTrue(list.get(1).contains("Ok, I am leaving without you."));
    }

    @Test
    public void testDeleteByHash() {
        MessageManager mm = new MessageManager(5);
        mm.populateTestData();
        // compute the hash for message 2 (index 1) deterministically the same way manager does
        String hashToDelete = mm.getHashes()[1];
        String confirmation = mm.deleteByHash(hashToDelete);
        assertTrue(confirmation.contains("successfully deleted"));
        // Now searching MSG002 should not find it
        String searchAfter = mm.searchByMessageID("MSG002");
        assertTrue(searchAfter.contains("Message ID not found."));
    }

    @Test
    public void testReportDisplaysHashesAndMessages() {
        MessageManager mm = new MessageManager(5);
        mm.populateTestData();
        List<String> report = mm.getSentMessagesReport();
        // Each report line must contain "Hash: " and "Recipient: " and "Message: "
        for (String line : report) {
            assertTrue(line.contains("Hash: "));
            assertTrue(line.contains("Recipient: "));
            assertTrue(line.contains("Message: "));
        }
    }
}
