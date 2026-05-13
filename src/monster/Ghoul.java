package monster;

import gameFrame.GameFrame;

public class Ghoul extends Monster {
	
    public Ghoul() { 
    	super("구울", 60, 60, 18, "      .╥──╥.      \n     ( ◉  ◉ )     \n      \\  口 /      \n     /|    |\\     \n  [ Lv.2 구울 ]"); 
    }
    
    @Override
    public int attack() {
        GameFrame.logArea.append("구울이 썩은 손톱으로 살점을 파고듭니다!\n");
        return getPower();
    }
}