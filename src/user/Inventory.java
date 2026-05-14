package user;

import java.util.ArrayList;  
import java.util.List;  
import gameFrame.GameFrame;
import item.Item;
import item.GoodArmor;
import item.BadArmor;
import item.BadWeapon;
import item.BluePotion;
import item.Dynamite;
import item.RedPotion;
import item.GoodWeapon;

public class Inventory {
	// [가방]
    private List<Item> items;
    private int maxSize = 10;
    
    public Inventory() {
        this.items = new ArrayList<>();
        items.add(new RedPotion());
        items.add(new RedPotion());
        items.add(new BluePotion());
        items.add(new BluePotion());
        items.add(new Dynamite());
        items.add(new Dynamite());
        items.add(new GoodWeapon());
        items.add(new GoodArmor());
        items.add(new BadWeapon());
        items.add(new BadArmor());
    }
    
    // [인벤토리 보기]
    public void showInvetory() {
        GameFrame.logArea.append("=== 인벤토리 목록 ===\n");
        for(int i = 0; i < items.size(); i++) {
            GameFrame.logArea.append((i+1) + ". " + items.get(i).getName() + "\n");
        }
    }
    
    // [아이템 추가]
    public void addItem(Item item) {
        if(items.size() < maxSize) {
            items.add(item);
        } else {
            GameFrame.logArea.append("[시스템] 인벤토리가 가득 찼습니다. 아이템을 추가할 수 없습니다.\n");
        } 
    }
    
    // [아이템 제거]
    public void removeItem(int index) {
        if(index >= 0 && index < items.size()) {
            items.remove(index);    
        }   
    }

    // [Getter && Setter]
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    
    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}