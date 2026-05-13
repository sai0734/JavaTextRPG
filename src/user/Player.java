package user;

import common.Skill; 
import gameFrame.GameFrame;

public class Player implements Skill {

    private String name;
    private String job;
    private int hp;
    private int maxHp;
    private int mp;
    private int maxMp;
    private int str;
    private int dex;
    private int wis;
    private String firstSkillName = "필사의 일격";
    private String secondSkillName = "젖 먹던 힘까지";
    private Inventory inventory;
    private String weapon;
    private int removeWeaponPower;
    private String armor;
    private int removeArmorPower;
    

    public Player (String name) {
        this.name = name;
        this.job = "novice";
        this.inventory = new Inventory();
        this.weapon = "없음";
        this.armor = "없음";
        hp = 100;
        maxHp = 100;
        mp = 100;
        maxMp = 100;
        str = 5 + (int)(Math.random()*5);
        dex = 5 + (int)(Math.random()*5);
        wis = 5 + (int)(Math.random()*5);
    }
    
    @Override
    public int firstSkill() {
        if(this.mp >= 20) {
            this.mp -= 20;
            GameFrame.logArea.append("⚔️ [필사의 일격] " + this.name + "이(가) 온 힘을 다해 공격합니다!\n");
            return this.str + this.dex + this.wis;
        } else {
            GameFrame.logArea.append("MP가 부족합니다. (필요 MP: 20)\n");
            return 0;
        }
    }

    @Override
    public int secondSkill() {
        if(this.mp >= 80) {
            this.mp -= 80;
            GameFrame.logArea.append("😫 [젖 먹던 힘까지] " + this.name + "이(가) 눈을 질끈 감고 무기를 마구 휘두릅니다!\n");
            return this.str*2 + this.dex*2 + this.wis*2;
        } else {
            GameFrame.logArea.append("MP가 부족합니다. (필요 MP: 80)\n");
            return 0;
        }
    }
    
    // [갑옷 착용]
    public void wearArmor(String name, int power) {
        if(!armor.equals("없음")) {
            this.hp -= removeArmorPower;
            this.maxHp -= removeArmorPower;
            this.mp -= removeArmorPower;
            this.maxMp -= removeArmorPower;
            GameFrame.logArea.append("기존 방어구 [" + armor + "]를 버렸습니다.\n");
            armor = "없음";
        } 
        
        if(armor.equals("없음")) {
            armor = name;
            this.removeArmorPower = power;
            this.hp += power;
            this.maxHp += power;
            this.mp += power;
            this.maxMp += power;
            GameFrame.logArea.append("🛡️ " + name + "을(를) 착용했습니다. (HP && MP +" + power + ")\n");
        }    
    }
    
    // [무기 착용]
    public void wearWeapon(String name, int power) {
        if(!weapon.equals("없음")) {
            this.str -= removeWeaponPower;
            this.dex -= removeWeaponPower;
            this.wis -= removeWeaponPower;
            
            GameFrame.logArea.append("기존 무기 [" + weapon + "]를 버렸습니다.\n");
            weapon = "없음";
        } 
        if(weapon.equals("없음")) {
            weapon = name;
            this.removeWeaponPower = power;
            this.str += power;
            this.dex += power;
            this.wis += power;
            GameFrame.logArea.append("⚔️ " + name + "을(를) 착용했습니다. (str && dex && wis +" + power + ")\n");
        }    
    }
    
    // [공격]
    public int attack() {
        GameFrame.logArea.append(name + "이(가) 주먹으로 공격했습니다\n");
        return (str/2) + (dex/2) + (wis/2);
    }
    
    // [피격]
    public void takeAttack(int damage) {
        hp -= damage;
        GameFrame.logArea.append(name + "이(가) " + damage + "의 데미지를 받았습니다\n");
    }
    
    // [방어]
    public int defend(int damage) {
        GameFrame.logArea.append(name + "이(가) " + damage/2 + "의 피해로 공격을 막았습니다.\n");
        hp -= damage/2;
        return hp;
    }
    
    // [회피]
    public void avoid(int damage) {
        if((int)(Math.random()*100) + dex > 50) {
            GameFrame.logArea.append(name + "이(가) 공격을 피했습니다.\n");
        } else {
            GameFrame.logArea.append(name + "이(가) 공격을 피하지 못하고 치명상을 입었습니다.\n");
            GameFrame.logArea.append(name + "이(가) " + (int)(damage*1.5) + "의 데미지를 받았습니다\n");
            hp -= (int)(damage*1.5);
        }
    }
    
    // [인벤토리 정보]
    public void getInventoryInfo() {
        // [해석] 이 메서드는 내부적으로 Inventory 클래스의 메서드를 호출함
        this.inventory.showInvetory();
    }
    
    // [캐릭터 정보]
    public void getUserInfo() {
        GameFrame.logArea.append("============================\n");
        GameFrame.logArea.append(" [ 용사 상태 정보 ] \n");
        GameFrame.logArea.append(" 이름 : " + name + "\n");
        GameFrame.logArea.append(" 직업 : " + job + "\n");
        GameFrame.logArea.append(" HP : " + hp + " / " + maxHp + "\n");
        GameFrame.logArea.append(" MP : " + mp + " / " + maxMp + "\n");
        GameFrame.logArea.append(" 힘(STR) : " + str + "\n");
        GameFrame.logArea.append(" 민첩(DEX) : " + dex + "\n");
        GameFrame.logArea.append(" 지능(WIS) : " + wis + "\n");
        GameFrame.logArea.append(" 무기 : " + weapon + "\n");
        GameFrame.logArea.append(" 방어구 : " + armor + "\n");
        GameFrame.logArea.append("============================\n");
    }

    // [Getter && Setter]
 	public String getName() {
 		return name;
 	}

 	public void setName(String name) {
 		this.name = name;
 	}

 	public int getHp() {
 		return hp;
 	}

 	public void setHp(int hp) {
 		this.hp = hp;
 	}

 	public int getMaxHp() {
 		return maxHp;
 	}

 	public void setMaxHp(int maxHp) {
 		this.maxHp = maxHp;
 	}

 	public int getMp() {
 		return mp;
 	}

 	public void setMp(int mp) {
 		this.mp = mp;
 	}

 	public int getMaxMp() {
 		return maxMp;
 	}

 	public void setMaxMp(int maxMp) {
 		this.maxMp = maxMp;
 	}

 	public int getStr() {
 		return str;
 	}

 	public void setStr(int str) {
 		this.str = str;
 	}

 	public int getDex() {
 		return dex;
 	}

 	public void setDex(int dex) {
 		this.dex = dex;
 	}

 	public int getWis() {
 		return wis;
 	}

 	public void setWis(int wis) {
 		this.wis = wis;
 	}

 	public String getJob() {
 		return job;
 	}

 	public void setJob(String job) {
 		this.job = job;
 	}
 	
 	public Inventory getInventory() {
 		return inventory;
 	}

 	public void setInventory(Inventory inventory) {
 		this.inventory = inventory;
 	}

 	public String getFirstSkillName() {
 		return firstSkillName;
 	}

 	public void setFirstSkillName(String firstSkillName) {
 		this.firstSkillName = firstSkillName;
 	}

 	public String getSecondSkillName() {
 		return secondSkillName;
 	}

 	public void setSecondSkillName(String secondSkillName) {
 		this.secondSkillName = secondSkillName;
 	}

 	public String getWeapon() {
 		return weapon;
 	}

 	public void setWeapon(String weapon) {
 		this.weapon = weapon;
 	}

 	public String getArmor() {
 		return armor;
 	}

 	public void setArmor(String armor) {
 		this.armor = armor;
 	}

 	public int getRemoveWeaponPower() {
 		return removeWeaponPower;
 	}

 	public void setRemoveWeaponPower(int removeWeaponPower) {
 		this.removeWeaponPower = removeWeaponPower;
 	}

 	public int getRemoveArmorPower() {
 		return removeArmorPower;
 	}

 	public void setRemoveArmorPower(int removeArmorPower) {
 		this.removeArmorPower = removeArmorPower;
 	}
}