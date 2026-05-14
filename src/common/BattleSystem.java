package common;

import java.util.ArrayList; 
import java.util.List; 
import javax.swing.JOptionPane;

import gameFrame.GameFrame;
import item.Item;
import monster.Monster;
import user.Player;
import main.Main;

public class BattleSystem {
    
    // [전투 시작]
    // [리팩토링] 기존의 while(true) 반복문을 삭제함. 
    // GUI는 반복문이 돌면 화면이 멈춰버리기 때문에, 대신 초기 정보를 출력하고 버튼 입력을 대기함.
    public static void battleStart(Player player, Monster monster) {
        GameFrame.logArea.append(monster.getName() + "가 나타났습니다.\n");
        // [해석] 전투가 시작되자마자 현재 상태를 화면에 그려줍니다.
        updateDisplay(player, monster);
    }

    // [화면 갱신 전용 메서드]
    // [리팩토링] 플레이어의 직업이나 몬스터 종류에 따라 이미지를 바꾸고 스탯 로그를 찍어줌.
    // 기존 코드에서 while문 상단에 반복되던 출력 로직을 별도 메서드로 분리한 것.
    public static void updateDisplay(Player player, Monster monster) {
        GameFrame.setMonsterImage(monster.getName()); // 몬스터 이미지 변경
        GameFrame.setPlayerImage(player.getJob());    // 전직한 직업에 맞춰 플레이어 이미지 변경
        
        GameFrame.logArea.append("몬스터 HP: " + monster.getHp() + " / " + monster.getMaxHp() + "\n");
        GameFrame.logArea.append("\n--- [ " + player.getName() + "의 턴 ] ---\n");
        GameFrame.logArea.append("HP: " + player.getHp() + " / " + player.getMaxHp() + " | MP: " + player.getMp() + " / " + player.getMaxMp() + "\n");
        GameFrame.logArea.append("직업: " + player.getJob() + " | STR: " + player.getStr() + " | DEX: " + player.getDex() + " | WIS: " + player.getWis() + "\n");
        GameFrame.logArea.append("착용 무기: " + player.getWeapon() + " | 착용 방어구: " + player.getArmor() + "\n\n");
    }

    // while문 안의 if-else 로직을 메서드로 분리
    // [일반 공격 버튼 클릭]
    public static void playerAttack(Player player, Monster monster) {
        monster.takeAttack(player.attack()); // 데미지 계산 및 적용
        // [해석] 몬스터가 죽었는지 확인하고, 안 죽었으면 몬스터 턴으로 넘김
        if (!checkWinCondition(player, monster)) {
            monsterTurn(player, monster, 1); // 1번은 '공격' 액션을 뜻함
        }
    }

    // [방어 버튼 클릭]
    public static void playerDefend(Player player, Monster monster) {
        GameFrame.logArea.append("🛡️ " + player.getName() + "이(가) 방어 태세를 취하며 피해에 대비합니다.\n");
        monsterTurn(player, monster, 2); // 2번은 '방어' 액션을 뜻함
    }

    // [회피 버튼 클릭]
    public static void playerAvoid(Player player, Monster monster) {
        GameFrame.logArea.append("💨 " + player.getName() + "이(가) 몸을 가볍게 하여 회피를 시도합니다.\n");
        monsterTurn(player, monster, 3); // 3번은 '회피' 액션을 뜻함
    }

    // [스킬 버튼 클릭]
    public static void playerSkill(Player player, Monster monster, int skillSelect) {
        int damage = 0;

        if(skillSelect == 1) {
            damage = player.firstSkill();
        } else if (skillSelect == 2) {
            damage = player.secondSkill();
        }
        
        if(damage > 0) {
            monster.takeAttack(damage);
            if (!checkWinCondition(player, monster)) {
                monsterTurn(player, monster, 4);
            }
        } else {
            GameFrame.logArea.append("[시스템] 마나가 부족하여 스킬을 사용할 수 없습니다.\n");
        }
    }

    // [가방 버튼 클릭]
    public static void playerInventory(Player player, Monster monster) {
        List<Item> invItems = player.getInventory().getItems();
        
        if(invItems.size() == 0) {
            GameFrame.logArea.append("가방이 텅 비어 있습니다.\n\n");                
            return; 
        } else {
            GameFrame.logArea.append("\n========== [ 가방 목록 ] ==========\n");
            for (int i = 0; i < invItems.size(); i++) {
                GameFrame.logArea.append((i + 1) + ". " + invItems.get(i).getName() + "\n");
            }
            GameFrame.logArea.append("===================================\n");
            
            String input = JOptionPane.showInputDialog(null, "사용하실 아이템 번호를 입력하세요 (0번: 취소)");
            
            try {
                if (input != null && !input.trim().isEmpty()) {
                    int itemSelect = Integer.parseInt(input);
                    
                    if (itemSelect == 0) {
                        GameFrame.logArea.append("가방 사용을 취소했습니다.\n");
                        return; 
                    }
                    
                    if(itemSelect > 0 && itemSelect <= invItems.size()) {
                        int invIndex = itemSelect - 1;
                        Item selectedItem = invItems.get(invIndex);
                        
                        if(selectedItem.getType().equals("user")) {
                            selectedItem.useItem(player);
                            player.getInventory().removeItem(invIndex);
                            monsterTurn(player, monster, 5); 
                            
                        } else if (selectedItem.getType().equals("monster")) {
                            selectedItem.useItem(monster);
                            player.getInventory().removeItem(invIndex);
                            monsterTurn(player, monster, 5); 
                            
                        } else {
                            selectedItem.useItem(player);
                            player.getInventory().removeItem(invIndex);
                            GameFrame.logArea.append("장비를 장착/교체했습니다. (턴 소모 없음)\n");
                            updateDisplay(player, monster); 
                        }
                    } else {
                        GameFrame.logArea.append("잘못된 아이템 번호입니다.\n");
                    }
                }
            } catch (Exception e) {
                GameFrame.logArea.append("숫자만 입력할 수 있습니다.\n");
            }
        }
    }

    // [몬스터 턴]
    public static void monsterTurn(Player player, Monster monster, int playerAction) {
        if (monster.getHp() <= 0) return;

        GameFrame.logArea.append("\n--- [ " + monster.getName() + "의 턴 ] ---\n");
        int mDamage = monster.attack();

        if(playerAction == 2) {
            player.defend(mDamage);
        } else if(playerAction == 3) {
            player.avoid(mDamage);
        } else {
            player.takeAttack(mDamage);
        }

        // [플레이어 사망 체크]
        if(player.getHp() <= 0) {
            GameFrame.logArea.append("\n💀 " + player.getName() + "이(가) 쓰러졌습니다... 게임 오버.\n");
        } else {
            updateDisplay(player, monster); 
        }
    }

    // [승리 확인]
    public static boolean checkWinCondition(Player player, Monster monster) {
        if(monster.getHp() <= 0) {
            GameFrame.logArea.append("\n✨ " + monster.getName() + "을 처치했습니다. 승리!\n");
            
            Item dropItem = monster.dropItem();
            
            if (dropItem != null) {
                GameFrame.logArea.append("🎁 몬스터가 죽으면서 무언가를 떨어뜨렸습니다.\n");
                if (Main.currentStageIndex < 4) {
	                // [가방 용량 체크 및 아이템 버리기]
	                if(player.getInventory().getItems().size() >= 10) {                    
	                    GameFrame.logArea.append("가방이 꽉 찼습니다. 버릴 아이템을 선택하여 주십시오.\n");
	                    
	                    List<Item> invItems = player.getInventory().getItems();
	                    for (int i = 0; i < invItems.size(); i++) {
	                        GameFrame.logArea.append((i + 1) + ". " + invItems.get(i).getName() + "\n");
	                    }
	                    
	                    String dropInput = JOptionPane.showInputDialog(null, "버릴 아이템 번호를 입력하세요:");
	                    try {
	                        int removeItem = Integer.parseInt(dropInput);
	                        player.getInventory().removeItem(removeItem - 1);
	                        GameFrame.logArea.append(removeItem + "번 아이템을 버렸습니다.\n");
	                    } catch (Exception e) {
	                        GameFrame.logArea.append("잘못된 입력으로 아무것도 버리지 못했습니다.\n");
	                    }                   
	                }
                    
                    GameFrame.logArea.append("👉 전리품: [" + dropItem.getName() + "] 획득!\n");    
                    
                } else {
                    GameFrame.logArea.append("👉 전리품: [" + dropItem.getName() + "] 획득!\n");
                }
                
                player.getInventory().addItem(dropItem);
            } else {
            	if (Main.currentStageIndex < 4) {
            		GameFrame.logArea.append("💨 이 몬스터는 빈털터리였나 봅니다...\n");
            	}
            }
            return true;
        }
        return false; 
    }
}