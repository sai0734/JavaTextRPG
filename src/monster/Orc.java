package monster;

import gameFrame.GameFrame;

public class Orc extends Monster {
	
    public Orc() { 
    	super("오크", 80, 80, 15, "     (  ◣ ◢  )     \n     <  ︶  >     \n    _ /  ▅  \\ _    \n     |  [ ]  |     \n  [ Lv.3 오크 전사 ]"); 
    }
    
    @Override
    public int attack() {
        GameFrame.logArea.append("오크가 거대한 철퇴를 무겁게 휘두릅니다!\n");
        return getPower();
    }
}