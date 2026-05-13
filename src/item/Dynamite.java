package item;

import gameFrame.GameFrame;
import monster.Monster;

public class Dynamite extends Item {
	
	private int boomDamage;

	public Dynamite() {
		super("폭탄", "monster");
		this.boomDamage = 30;
	}
	
	@Override
	public void useItem(Monster monster) {
		monster.setHp(monster.getHp() - boomDamage);
		GameFrame.logArea.append(monster.getName() + "에게 " + boomDamage + "의 데미지를 주었습니다.\n");
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
}