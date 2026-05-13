package monster;

import gameFrame.GameFrame;

public class Goblin extends Monster {
	
    public Goblin() {
        super("고블린", 40, 40, 8, "     ῿  ^  ^  ῿     \n     ╰  ◡  ╯     \n      --|--[⚔️]    \n       / \\        \n  [ Lv.1 고블린 ]");
    }
    
    @Override
    public int attack() {
        GameFrame.logArea.append("고블린이 비열하게 웃으며 단검으로 찌릅니다!\n");
        return getPower();
    }
}