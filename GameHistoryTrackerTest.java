import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import org.junit.Test;


/**
 * Tests for GameHistoryTracker
 *
 */

public class GameHistoryTrackerTest {
     private GameHistoryTracker tracker = new GameHistoryTracker();

    @Test
    public void TestRecordPlay() {
        tracker.recordPlay("Jotto", 0);
        tracker.recordPlay("Jotto", null);
        tracker.recordPlay("Minesweeper", 100);

        String filename = "testHistory.dat";
        try {
            tracker.saveHistory(filename);
            GameHistoryTracker loaded = GameHistoryTracker.loadHistory(filename);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));
            loaded.displayHistory();
            String output = out.toString();

            assertTrue(output.contains("Jotto"));
            assertTrue(output.contains("Minesweeper"));
            assertTrue(output.contains("Played: 2"));
            assertTrue(output.contains("Played: 1"));
            assertTrue(output.contains("Avg Score: 100"));

        } catch (IOException ex) {
            fail("Unexpected IOException: " + ex.getMessage());
        } finally {
            new File(filename).delete();
        }
    }


    @Test
    public void TestDisplayHistory() {


    }

    @Test
    public void TestSaveHistory() {


    }

    @Test
    public void TestLoadHistory() {


    }

    @Test
    public void TestClearHistory() {


    }
}
