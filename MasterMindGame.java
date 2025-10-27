

import java.util.Optional;

class MasterMindGame implements Game {
    private stringGen  generator; 
    
    public MasterMindGame() { 
        this.generator = new stringGen();
    }
    
    @Override
    public String getName() {
        return "MasterMind";
    }
    
    @Override
    public Optional<Integer> play() {
        System.out.println("[Playing MasterMind - Placeholder]");
        generator.generateHiddenCode(4);
        return Optional.empty();
    }
}
