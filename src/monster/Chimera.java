package monster;

import gameFrame.GameFrame;

public class Chimera extends Monster {
	
    public Chimera() { 
    	super("키메라", 150, 150, 25, "     ^__^   /\\_/\\  \n    (oo )  ( o.o ) \n     \\/_\\--/_\\/  \n       ||    ||    \n  [ Lv.4 키메라 ]"); 
    }
    
    @Override
    public int attack() {
        GameFrame.logArea.append("키메라의 사자 머리와 뱀 머리가 동시에 달려듭니다!\n");
        return getPower();
    }
}