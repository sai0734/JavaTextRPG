package item;

import user.Player;

public class GoodWeapon extends Item {
	
	public GoodWeapon() {
		super("전설의검", "weapon");
		this.power = 10;
	}
	
	@Override
	public void useItem(Player player) {
		player.wearWeapon(super.getName(), super.getPower());
	}

}
