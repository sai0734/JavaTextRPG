package job;

import common.Skill; 
import gameFrame.GameFrame;
import user.Player;

public class Mage extends Player implements Skill {

    public Mage(Player player) {
        super(player.getName());
        this.setJob("Mage");
        
        this.setHp(player.getHp() + 50);
        this.setMaxHp(player.getMaxHp() + 50);
        this.setMp(player.getMp() + 200);
        this.setMaxMp(player.getMaxMp() + 200);
        this.setStr(player.getStr() + 2);
        this.setDex(player.getDex() + 5);
        this.setWis(player.getWis() + 20);
        
        this.setFirstSkillName("파이어볼");
        this.setSecondSkillName("메테오");
        
        this.setWeapon(player.getWeapon());
        this.setRemoveWeaponPower(player.getRemoveWeaponPower());
        this.setArmor(player.getArmor());
        this.setRemoveArmorPower(player.getRemoveArmorPower());
    }

    // [공격]
    @Override
    public int attack() {
        GameFrame.logArea.append(this.getName() + "이(가) 지팡이를 휘둘러 물리 타격을 가합니다.\n");
        return this.getStr() + (this.getWis() / 2);
    }


    @Override
    public int firstSkill() {
        if (this.getMp() >= 40) {
            this.setMp(this.getMp() - 40);
            GameFrame.logArea.append("🔥 [파이어볼] " + this.getName() + "이(가) 거대한 화염구를 날립니다!\n");
            return (this.getWis() * 4);
        } else {
            GameFrame.logArea.append("MP가 부족합니다. (필요 MP: 40)\n");
            return 0;
        }
    }

    @Override
    public int secondSkill() {
        if (this.getMp() >= 100) {
            this.setMp(this.getMp() - 100);
            GameFrame.logArea.append("☄️ [메테오] 하늘에서 거대한 운석이 떨어집니다!\n");
            return (this.getWis() * 8) + this.getDex();
        } else {
            GameFrame.logArea.append("MP가 부족합니다. (필요 MP: 100)\n");
            return 0;
        }
    }
}