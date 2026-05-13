package monster;

import gameFrame.GameFrame;

public class Lich extends Monster {
	
    public Lich() { 
    	super("리치", 180, 180, 45, "      .---.       \n     /◢   ◣\\      \n    |  X X  | [🔮] \n     \\  ^  /   |  \n      '---'    |  \n  [ Lv.4 리치 ]"); 
    }
    
    @Override
    public int attack() {
        GameFrame.logArea.append("리치가 고대의 주문을 외우며 영혼을 갉아먹는 마법을 부립니다!\n");
        return getPower();
    }
}