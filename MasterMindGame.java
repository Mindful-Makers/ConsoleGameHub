import java.util.Optional;

/**
 * A code-breaking game where the app selects a sequence of symbols, and
 * the player tries to guess the sequence within a fixed number of attempts.
 *
 * Feedback for each guess indicates how many values are correct and in
 * the correct position and how many are correct but in the wrong position.
 *
 * Consider using arrays or strings to store and compare codes.
 * @version 1
 */
class MasterMindGame implements Game {
    @Override
    public String getName() {
        return "MasterMind";
    }

    @Override
    public Optional<Integer> play() {
        System.out.println("[Playing MasterMind - Placeholder]");
        createSecret();
        return Optional.empty();
    }
   public Optional<Integer> play() {
        System.out.println("[Playing MasterMind - Placeholder]");
        String SECRET_STRING = generateSecret();
        return Optional.empty();
    }
    //Generates a random length 4 secret code for Mastermind game session       
    public static String generateSecret() {
	//Generates int between 0 and 9999                                      
	int secretInt = (int)(Math.random()*9999);
	//converts int into string of length 4                                  
	String secretString = String.format("%04d", secretInt);
	return secretString;

}


