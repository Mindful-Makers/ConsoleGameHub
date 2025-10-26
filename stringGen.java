import java.util.Random;

public class stringGen {
    private Random random;
    private String hiddenCode;  
    
    public stringGen() {
        this.random = new Random(); 
        this.hiddenCode = null;
    }
    
    public void generateHiddenCode(int length) {  
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder builder = new StringBuilder(length);  
        
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());  
            builder.append(characters.charAt(index));
        }
        
        this.hiddenCode = builder.toString();
    }
    
    public boolean checkCode(String guess) {
        if (hiddenCode == null) {
            return false;
        }
        return hiddenCode.equals(guess);
    }
}
