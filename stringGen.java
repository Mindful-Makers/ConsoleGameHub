import java.util.Random;

public class stringGen {

    public class randGenerator {
        private String hiddenCode;  
        
        public String generateRandomString(int length) {
            String character = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            Random rands = new Random();
            StringBuilder build = new StringBuilder(length);

            for (int i = 0; i < length; i++) {
                int index = rands.nextInt(character.length());
                build.append(character.charAt(index));
            }
            return build.toString();
        }
        
        
        public void generateCode() {
            this.hiddenCode = generateRandomString(4);
        }
        
    
        public String getHiddenCode() {
            return hiddenCode;
        }
        
        public static void main(String[] args) {
            stringGen outer = new stringGen();
            randGenerator gen = outer.new randGenerator();
            
            gen.generateCode(); 
            System.out.println("Code generated and hidden!");
            
        }
    }
}
