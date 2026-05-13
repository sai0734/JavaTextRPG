package item;

import user.Player;

public class GoodArmor extends Item {
	
	public GoodArmor() {
		super("전설의방어구", "armor");
		this.power = 100;
	}
	
	@Override
	public void useItem(Player player) {
		player.wearArmor(super.getName(), super.getPower());
	}

}
