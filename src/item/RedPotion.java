package item;

import gameFrame.GameFrame;
import user.Player;

public class RedPotion extends Item {
    
    private int hpHealAmount;
    
    public RedPotion() {
        super("HP포션", "user");
        this.hpHealAmount = 50;        
    }
    
    @Override
    public void useItem(Player user) {
        if(user.getHp() >= user.getMaxHp()) {
            GameFrame.logArea.append("이미 체력이 가득 차서 배만 부릅니다\n");
        } else {
            int newHp = user.getHp() + hpHealAmount;
            if(newHp > user.getMaxHp()) {
                newHp = user.getMaxHp();
            }
            user.setHp(newHp);
            GameFrame.logArea.append(hpHealAmount + "만큼 회복되었습니다. (현재 HP : " + user.getHp() + ")\n");
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