package job;

import common.Skill; 
import gameFrame.GameFrame;
import user.Player;

public class Rogue extends Player implements Skill {

    public Rogue(Player player) {
        super(player.getName());
        this.setJob("Rogue");
        
        this.setHp(player.getHp() + 100);
        this.setMaxHp(player.getMaxHp() + 100);
        this.setMp(player.getMp() + 100);
        this.setMaxMp(player.getMaxMp() + 100);
        this.setStr(player.getStr() + 5);
        this.setDex(player.getDex() + 10);
        this.setWis(player.getWis() + 3);
     
        this.setFirstSkillName("기습");
        this.setSecondSkillName("암살");
        
        this.setWeapon(player.getWeapon());
        this.setRemoveWeaponPower(player.getRemoveWeaponPower());
        this.setArmor(player.getArmor());
        this.setRemoveArmorPower(player.getRemoveArmorPower());
    }
    
    // [공격]
    @Override
    public int attack() {
        GameFrame.logArea.append(this.getName() + "이(가) 단검으로 빠르게 벱니다.\n");
        return (int)(this.getDex() * 1.5);
    }
    
    @Override
    public int firstSkill() {
        if(this.getMp() > 20) {
            this.setMp(getMp() - 20);
            GameFrame.logArea.append("🗡️ [기습] " + this.getName() + "이(가) 그림자처럼 나타나 급소를 찌릅니다!\n");
            return (int)(this.getDex() * 2);
        } else {
            GameFrame.logArea.append("MP가 부족합니다. (필요 MP: 20)\n");
            return 0;
        }        
    }
    
    @Override
    public int secondSkill() {
        if (this.getMp() >= 50) {
            this.setMp(this.getMp() - 50);
            GameFrame.logArea.append("🌑 [암살] " + this.getName() + "이(가) 보이지 않는 속도로 적을 섬멸합니다!\n");
            return (this.getDex() * 4) + this.getStr();
        } else {
            GameFrame.logArea.append("MP가 부족합니다. (필요 MP: 50)\n");
            return 0;
        }
    }
}