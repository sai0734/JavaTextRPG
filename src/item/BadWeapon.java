package item;

import user.Player;

public class BadWeapon extends Item {
	
	public BadWeapon() {
		super("쓰레기검", "weapon");
		this.power = 3;
	}
	
	@Override
	public void useItem(Player player) {
		player.wearWeapon(super.getName(), super.getPower());
	}

}
