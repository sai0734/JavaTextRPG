package monster;

import gameFrame.GameFrame;

public class Skeleton extends Monster {
	
    public Skeleton() { 
    	super("스켈레톤", 50, 50, 12, "      .----.      \n     [| o o |]     \n      \\  -  /      \n       --|--       \n        / \\        \n  [ Lv.2 스켈레톤 ]"); 
    }
    
    @Override
    public int attack() {
        GameFrame.logArea.append("스켈레톤이 뼈 부딪히는 소리를 내며 칼을 휘두릅니다!\n");
        return getPower();
    }
}