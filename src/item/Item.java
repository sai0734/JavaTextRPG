package item;

import gameFrame.GameFrame;
import monster.Monster;
import user.Player;

public class Item {
    protected String name;
    protected String type;
    protected int power;
    
    public Item(String name, String type) {
        this.name = name;
        this.type = type;
        this.power = 0;
    }
    
    // [Getter]
    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
    }
    
    public int getPower() {
        return power;
    }
    
    // [유저용 아이템 사용]
    public void useItem(Player user) {
        GameFrame.logArea.append("이건 유저용 아이템입니다.\n");
    }
    
    // [몬스터용 아이템 사용]
    public void useItem(Monster monster) {
        GameFrame.logArea.append("이건 몬스터용 아이템입니다.\n");
    }
}