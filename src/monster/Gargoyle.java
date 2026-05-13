package monster;

import gameFrame.GameFrame;

public class Gargoyle extends Monster {
	
    public Gargoyle() { 
    	super("가고일", 120, 120, 10, "    /\\      /\\    \n   /  \\____/  \\   \n  |  ( ಠ ಠ )  |   \n   \\__  ◡  __/    \n     /|  |\\       \n  [ Lv.3 가고일 ]"); 
    }
    
    @Override
    public int attack() {
        GameFrame.logArea.append("석상이었던 가고일이 굳건한 주먹을 날립니다!\n");
        return getPower();
    }
}