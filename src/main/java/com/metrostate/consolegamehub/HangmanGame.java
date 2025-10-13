package com.metrostate.consolegamehub;

import java.io.InputStream;
import java.util.*;

public class HangmanGame {
    private static final String[] WORD_LIST = {
        "programming", "java", "computer", "algorithm", 
        "database", "network", "software", "hardware"
    };
    
    private static final int MAX_TRIES = 6;
    
    private String secretWord;
    private Set<Character> guessedLetters;
    private int remainingTries;
    private Scanner scanner;
    
    // Default constructor
    public HangmanGame() {
        this(System.in);
    }
    
    // Parameterized constructor for testing
    public HangmanGame(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
        this.guessedLetters = new HashSet<>();
        this.remainingTries = MAX_TRIES;
        this.secretWord = selectRandomWord();
    }
    
    private String selectRandomWord() {
        Random random = new Random();
        return WORD_LIST[random.nextInt(WORD_LIST.length)].toLowerCase();
    }
    
    public Optional<Integer> play() {
        System.out.println("=== HANGMAN GAME ===");
        
        while (remainingTries > 0 && !isWordGuessed()) {
            displayGameState();
            processGuess();
        }
        
        displayFinalResult();
        return Optional.of(calculateScore());
    }
    
    private void displayGameState() {
        System.out.println("\n" + getHangmanDrawing());
        System.out.println("Word: " + getCurrentWordProgress());
        System.out.println("Guessed letters: " + guessedLetters);
        System.out.println("Remaining tries: " + remainingTries);
    }
    
    private String getCurrentWordProgress() {
        StringBuilder progress = new StringBuilder();
        for (char c : secretWord.toCharArray()) {
            if (guessedLetters.contains(c)) {
                progress.append(c).append(" ");
            } else {
                progress.append("_ ");
            }
        }
        return progress.toString().trim();
    }
    
    private void processGuess() {
        System.out.print("\nEnter your guess (letter or word): ");
        String input = scanner.nextLine().toLowerCase().trim();
        
        if (input.isEmpty() || !input.matches("[a-z]+")) {
            System.out.println("Invalid input! Please enter letters only.");
            return;
        }
        
        if (input.length() == 1) {
            char letter = input.charAt(0);
            
            if (guessedLetters.contains(letter)) {
                System.out.println("You already guessed that letter!");
                return;
            }
            
            guessedLetters.add(letter);
            
            if (!secretWord.contains(String.valueOf(letter))) {
                remainingTries--;
                System.out.println("Incorrect guess!");
            } else {
                System.out.println("Good guess!");
            }
        } else {
            if (input.equals(secretWord)) {
                for (char c : secretWord.toCharArray()) {
                    guessedLetters.add(c);
                }
                System.out.println("Correct! You guessed the word!");
            } else {
                remainingTries--;
                System.out.println("Incorrect word!");
            }
        }
    }
    
    protected boolean isWordGuessed() {
        for (char c : secretWord.toCharArray()) {
            if (!guessedLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }
    
    private String getHangmanDrawing() {
        int incorrectGuesses = MAX_TRIES - remainingTries;
        String[] stages = {
            """
              +---+
              |   |
                  |
                  |
                  |
                  |
            =========
            """,
            """
              +---+
              |   |
              O   |
                  |
                  |
                  |
            =========
            """,
            """
              +---+
              |   |
              O   |
              |   |
                  |
                  |
            =========
            """,
            """
              +---+
              |   |
              O   |
             /|   |
                  |
                  |
            =========
            """,
            """
              +---+
              |   |
              O   |
             /|\\  |
                  |
                  |
            =========
            """,
            """
              +---+
              |   |
              O   |
             /|\\  |
             /    |
                  |
            =========
            """,
            """
              +---+
              |   |
              O   |
             /|\\  |
             / \\  |
                  |
            =========
            """
        };
        
        return stages[Math.min(incorrectGuesses, stages.length - 1)];
    }
    
    private void displayFinalResult() {
        System.out.println("\n" + getHangmanDrawing());
        
        if (isWordGuessed()) {
            System.out.println("\nCongratulations! You won!");
            System.out.println("The word was: " + secretWord);
            System.out.println("Your score: " + calculateScore());
        } else {
            System.out.println("\nGame Over! You lost!");
            System.out.println("The word was: " + secretWord);
            System.out.println("Your score: 0");
        }
    }
    
    private int calculateScore() {
        return isWordGuessed() ? remainingTries : 0;
    }
    
    protected Set<Character> getGuessedLetters() {
        return new HashSet<>(guessedLetters);
    }
    
    protected int getRemainingTries() {
        return remainingTries;
    }
    
    protected void setSecretWord(String word) {
        this.secretWord = word.toLowerCase();
    }
    
    protected String getSecretWord() {
        return secretWord;
    }
    
    public static void main(String[] args) {
        HangmanGame game = new HangmanGame();
        game.play();
    }
}
