package item;

import user.Player;

public class BadArmor extends Item {
	
	public BadArmor() {
		super("쓰레기방어구", "armor");
		this.power = 10;
	}
	
	@Override
	public void useItem(Player player) {
		player.wearArmor(super.getName(), super.getPower());
	}

}
