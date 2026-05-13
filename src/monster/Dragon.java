package monster;

import gameFrame.GameFrame;

public class Dragon extends Monster {
	
    public Dragon() { 
    	super("드래곤", 300, 300, 60, "             ___       \n       |\\___/| /_\\     \n       [ o o ]/  /     \n      /  \\_/  \\_/      \n    _/_/|  |\\_\\_       \n  [ Lv.5 보스 드래곤 ]"); 
    }
    
    @Override
    public int attack() {
        GameFrame.logArea.append("드래곤이 포효하며 모든 것을 태워버릴 브레스를 내뿜습니다!\n");
        return getPower();
    }
}