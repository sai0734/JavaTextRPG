package monster;

import java.util.List;  
import java.util.ArrayList;
import gameFrame.GameFrame;
import item.*;

public abstract class Monster {
    private String name;
    private int hp;
    private int maxHp;
    private int power;
    private String monsterImage;
    private List<Item> items;
    
    public Monster(String name, int hp, int maxHp, int power, String monsterImage) {
        this.name = name;
        this.hp = hp;
        this.maxHp = maxHp;
        this.power = power;
        this.monsterImage = monsterImage;
        this.items = new ArrayList<>();
        this.items.add(new RedPotion());
        this.items.add(new BluePotion());
        this.items.add(new Dynamite());
        this.items.add(new GoodWeapon());
        this.items.add(new BadArmor());
        this.items.add(new GoodWeapon());
        this.items.add(new BadArmor());
        this.items.add(null); 
    }
    
    public void showMonster() {
        GameFrame.logArea.append(monsterImage + "\n");
    }
    
    // [공격]
    public abstract int attack();
    
    // [피격]
    public void takeAttack(int damage) {
        this.hp -= damage;
        GameFrame.logArea.append(this.name + "이(가) " + damage + "의 데미지를 입었습니다.\n");

        if(this.hp < 0) this.hp = 0;
        
        GameFrame.logArea.append("\n");
    }
    
    // [아이템 드랍]
    public Item dropItem() {
        if (items.isEmpty()) return null;       
        int randomIndex = (int)(Math.random() * items.size());
        return items.get(randomIndex);
    }
    
    // [Getter & Setter]
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

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getMonsterImage() {
        return monsterImage;
    }

    public void setMonsterImage(String monsterImage) {
        this.monsterImage = monsterImage;
    }
}