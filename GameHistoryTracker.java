import java.util.HashMap;
import java.util.ArrayList;
import java.io.IOException;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Track history and stats of games played.
 * @author Jody Paul (assisted by chatGPT)
 * @author Cesar Soto, Mason Proctor, Luke Ross
 * @version 2
 */
class GameHistoryTracker implements Serializable {
    private static final long serialVersionUID = 2L;
    /** Collection of play stats for each game. */
    private final HashMap<String, GameStats> statsMap = new HashMap<>();

    /**
     * Records a play session for a game.
     * @param gameName the name of the game played
     * @param score optional numeric score (nullable)
     */
    public void recordPlay(final String gameName, final Integer score) {
        GameStats stats = statsMap.getOrDefault(gameName, new GameStats());
        stats.incrementTimesPlayed();
        if (score != null) {
            stats.totalScore += score;
            stats.scores.add(score);
        }
        statsMap.put(gameName, stats);
    }

    /**
     * Displays a summary of play history sorted in descending order
     * along with corresponding scores, if any.
     */
    public void displayHistory() {
        System.out.println("\n=== Game Play History ===");
        if (statsMap.isEmpty()) {
            System.out.println("No games played yet.");
            return;
        }
        statsMap.entrySet().stream()
                .sorted((a, b) -> {
                    int cmp = Integer.compare(
                            b.getValue().getTimesPlayed(),
                            a.getValue().getTimesPlayed()
                    );
                    return (cmp != 0)
                            ? cmp
                            : a.getKey().compareToIgnoreCase(b.getKey());
                })
                .forEach(entry -> {
                    String game = entry.getKey();
                    GameStats stats = entry.getValue();
                    System.out.printf("%s - Played: %d",
                            game, stats.getTimesPlayed());
                    if (!stats.scores.isEmpty()) {
                        double avg =
                                stats.totalScore / (double) stats.scores.size();
                        System.out.printf(", Avg Score: %.2f", avg);
                    }
                    System.out.println();
                });
    }

    /**
     * Saves the game history to a file.
     * @param filename the name of the file to save to
     * @throws IOException if an I/O error occurs
     */
    public void saveHistory(final String filename) throws IOException {
        try (ObjectOutputStream out =
                new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
        }
    }

    /**
     * Clears the history file.
     * @param filename takes name of file to clear history from.
     */
    public void clearHistory(final String filename) {
        statsMap.clear();
        try {
            saveHistory(filename);
        } catch (IOException e) {
            System.out.println("Game history save failed: " + e.getMessage());
        }
    }

    /**
     * Loads the game history from a file.
     * @param filename the name of the file to load from
     * @return useful game history tracker
     */
    public static GameHistoryTracker loadHistory(final String filename) {
        try (ObjectInputStream in
                = new ObjectInputStream(new FileInputStream(filename)))
            {
            return (GameHistoryTracker) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(
                "No previous history found or failed to load. Starting fresh.");
            return new GameHistoryTracker();
        }
    }

    /**
     * Inner class to track stats for a single game.
     */
    private static class GameStats implements Serializable {
        private static final long serialVersionUID = 2L;
        /** The number of times game has been played. */
        private int timesPlayed = 0;
        /** The current total score. */
        private int totalScore = 0;
        /** All recorded scores. */
        private ArrayList<Integer> scores = new ArrayList<>();
        /**
         * Access the number of times the game has been played.
         * @return times played
         */
        int getTimesPlayed() {
            return this.timesPlayed;
        }
        /**
         * Increment the number of times the game has been played.
         */
        void incrementTimesPlayed() {
            this.timesPlayed++;
        }
    }
}
