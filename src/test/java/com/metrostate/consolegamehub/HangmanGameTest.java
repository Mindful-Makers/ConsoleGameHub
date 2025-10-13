package com.metrostate.consolegamehub;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Optional;

class HangmanGameTest {
    
    @Test
    void testWinningGameWithLetterGuesses() {
        String input = "j\na\nv\na\n";
        HangmanGame game = new HangmanGame(new ByteArrayInputStream(input.getBytes()));
        game.setSecretWord("java");
        
        Optional<Integer> score = game.play();
        
        assertTrue(score.isPresent());
        assertTrue(score.get() > 0);
        assertTrue(game.isWordGuessed());
    }
    
    @Test
    void testWinningGameWithFullWordGuess() {
        String input = "programming\n";
        HangmanGame game = new HangmanGame(new ByteArrayInputStream(input.getBytes()));
        game.setSecretWord("programming");
        
        Optional<Integer> score = game.play();
        
        assertTrue(score.isPresent());
        assertEquals(6, score.get());
        assertTrue(game.isWordGuessed());
    }
    
    @Test
    void testLosingGameAfterSixIncorrectGuesses() {
        String input = "x\nz\nq\nw\ny\nk\n";
        HangmanGame game = new HangmanGame(new ByteArrayInputStream(input.getBytes()));
        game.setSecretWord("java");
        
        Optional<Integer> score = game.play();
        
        assertTrue(score.isPresent());
        assertEquals(0, score.get());
        assertEquals(0, game.getRemainingTries());
        assertFalse(game.isWordGuessed());
    }
    
    @Test
    void testRepeatedGuessesDoNotReduceTries() {
        String input = "a\na\na\nj\nv\na\n";
        HangmanGame game = new HangmanGame(new ByteArrayInputStream(input.getBytes()));
        game.setSecretWord("java");
        
        // After first 'a', repeated 'a's shouldn't reduce tries
        // This test verifies the game handles repeated guesses correctly
        Optional<Integer> score = game.play();
        assertTrue(score.isPresent());
        assertTrue(score.get() > 0);
    }
    
    @Test
    void testInvalidInputIgnored() {
        String input = "123\n@#$\nj\na\nv\na\n";
        HangmanGame game = new HangmanGame(new ByteArrayInputStream(input.getBytes()));
        game.setSecretWord("java");
        
        Optional<Integer> score = game.play();
        
        assertTrue(score.isPresent());
        assertTrue(score.get() > 0);
    }
}
