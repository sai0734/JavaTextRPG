package item;

import gameFrame.GameFrame; 
import user.Player;

public class BluePotion extends Item {
	
	private int mpHealAmount;

	public BluePotion() {
		super("MP포션", "user");
		this.mpHealAmount = 50;		
	}
	
	@Override
	public void useItem(Player user) {
		if(user.getMp() >= user.getMaxMp()) {
			GameFrame.logArea.append("이미 마력이 가득 차서 배만 부릅니다\n");
		} else {
			int newMp = user.getMp() + mpHealAmount;
			if(newMp > user.getMaxMp()) {
				newMp = user.getMaxMp();
			}
			user.setMp(newMp);
			GameFrame.logArea.append(mpHealAmount + "만큼 회복되었습니다. (현재 MP : " + user.getMp() + ")\n");	
		} 
	}
	
    // [Getter && Setter]
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}