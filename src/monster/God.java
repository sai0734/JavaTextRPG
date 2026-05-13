package monster;

import gameFrame.GameFrame;

public class God extends Monster {
	
    public God() { 
    	super("신", 500, 500, 100, "        .  .        \n      \\  |  /      \n     -- (☼) --     \n      /  |  \\      \n        '  '        \n     [ 최종보스 신 ] "); 
    }
    
    @Override
    public int attack() {
        GameFrame.logArea.append("천지창조!\n");
        return getPower();
    }
}