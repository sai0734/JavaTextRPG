package job;

import common.Skill; 
import gameFrame.GameFrame;
import user.Player;

public class Warrior extends Player implements Skill {

    public Warrior(Player player) {
        super(player.getName());
        this.setJob("Warrior");
        
        this.setHp(player.getHp() + 200);
        this.setMaxHp(player.getMaxHp() + 200);
        this.setMp(player.getMp());
        this.setMaxMp(player.getMaxMp());
        this.setStr(player.getStr() + 10);
        this.setDex(player.getDex() + 5);
        this.setWis(player.getWis() + 3);
        
        this.setFirstSkillName("강격");
        this.setSecondSkillName("광폭화");
        
        this.setWeapon(player.getWeapon());
        this.setRemoveWeaponPower(player.getRemoveWeaponPower());
        this.setArmor(player.getArmor());
        this.setRemoveArmorPower(player.getRemoveArmorPower());
    }
    
    // [공격]
    @Override
    public int attack() {
        GameFrame.logArea.append(this.getName() + "이 도끼로 묵직하게 공격합니다.\n");
        return (int)(this.getStr() * 1.5);
    }

    @Override
    public int firstSkill() {
        if(this.getMp() >= 30) {
            this.setMp(getMp() - 30);
            GameFrame.logArea.append("⚔️ [강격] " + this.getName() + "이 도끼를 크게 휘둘러 내리찍습니다!\n");
            return (this.getStr() * 3);
        } else {
            GameFrame.logArea.append("MP가 부족합니다. (필요 MP: 30)\n");
            return 0;            
        }
    }

    @Override
    public int secondSkill() {
        if(this.getMp() >= 50) {
            this.setMp(getMp() - 50);
            GameFrame.logArea.append("🔥 [광폭화] " + this.getName() + "이 분노하여 모든 힘을 쏟아붓습니다!\n");
            return (int)(this.getStr() * 5);
        } else {
            GameFrame.logArea.append("MP가 부족합니다. (필요 MP: 50)\n");
            return 0;            
        }
    }
}