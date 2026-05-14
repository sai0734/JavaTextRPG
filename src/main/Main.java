package main;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import common.Story;
import gameFrame.GameFrame;
import job.Mage;
import job.Novice;
import job.Rogue;
import job.Warrior;
import common.BattleSystem;
import monster.*;
import user.Player;

public class Main {
    public static Player user;
    public static ArrayList<Monster> stageList = new ArrayList<>();
    public static int currentStageIndex = 0;

    public static void main(String[] args) {
        new GameFrame();
        Story.gameStart();
    }

    public static void setDifficulty(int difficulty) {
        if (difficulty == 1) {
            stageList.add(new Slime());
            stageList.add(new Skeleton());
            stageList.add(new Orc());
            stageList.add(new Chimera());
            stageList.add(new Dragon());
            GameFrame.logArea.append("[시스템] 노멀 모드로 모험을 시작합니다.\n");
        } else {
            stageList.add(new Goblin());
            stageList.add(new Ghoul());
            stageList.add(new Gargoyle());
            stageList.add(new Lich());
            stageList.add(new God());
            GameFrame.logArea.append("\n[시스템] 하드 모드로 모험을 시작합니다. 행운을 빕니다.\n");
        }

        // 입력창을 활성화
        GameFrame.nameInput.setEnabled(true);
        GameFrame.btnConfirmName.setEnabled(true);
        
        GameFrame.logArea.append("용사의 이름을 입력하고 '이름 확인'을 눌러주세요.\n");
    }

    public static void initPlayer(String name) {
        user = new Novice(name);
        GameFrame.logArea.append("\n용사 " + name + "님의 모험이 시작됩니다!\n");
        playStageStory();
    }

    public static void playStageStory() {
        if (currentStageIndex >= stageList.size()) return;

        int stage = currentStageIndex + 1;

        if(stage == 1) Story.chapter1();
        else if(stage == 2) Story.chapter2();
        else if(stage == 3) Story.chapter3();
        else if(stage == 4) Story.chapter4();
        else if(stage == 5) Story.chapter5();

        GameFrame.logArea.append("\n[상단의 '전투 시작' 버튼을 누르시면 전투가 시작됩니다]\n");
        GameFrame.btnStartBattle.setEnabled(true);
    }

    public static void startBattle() {
        GameFrame.btnStartBattle.setEnabled(false);
        int stage = currentStageIndex + 1;
        Monster currentMonster = stageList.get(currentStageIndex);
        
        GameFrame.logArea.append("\n⚔️ STAGE " + stage + " : " + currentMonster.getName() + "와의 결전\n");
        BattleSystem.battleStart(user, currentMonster);
        GameFrame.enableBattleButtons(true);
    }

    public static void onBattleEnd(boolean playerWon) {
        GameFrame.enableBattleButtons(false); 

        if (!playerWon) {
            Story.gameOver(user);
            return;
        }

        int stage = currentStageIndex + 1;

        if (stage < 5) {
            GameFrame.logArea.append("\n✨ 스테이지 클리어 다음 여정을 위해 휴식을 취합니다.\n");
            GameFrame.logArea.append("(HP와 MP가 조금 회복되었습니다)\n");
            
            user.setHp(user.getHp() + 30);
            if(user.getHp() > user.getMaxHp()) user.setHp(user.getMaxHp());
            user.setMp(user.getMp() + 30);
            if(user.getMp() > user.getMaxMp()) user.setMp(user.getMaxMp());
            
            if(stage == 2) {
                GameFrame.logArea.append("\n🏠 [이벤트] 숲 너머 작은 마을 '이젠 리'에 도착했습니다.\n");
                GameFrame.logArea.append("마을 원로가 당신의 잠재력을 알아보고 전직을 제안합니다.\n");
                
                String[] jobs = {"1. 전사", "2. 도적", "3. 마법사", "4. 유지하기"};
                int choice = JOptionPane.showOptionDialog(null, "직업을 선택하세요", "전직 이벤트",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, jobs, jobs[0]);

                int selectJob = choice + 1;

                if(selectJob == 1) {
                    user = new Warrior(user);
                    GameFrame.logArea.append("\n🛡️ [전사] 묵직한 검기가 당신의 몸을 감쌉니다!\n");
                } else if(selectJob == 2) {
                    user = new Rogue(user);
                    GameFrame.logArea.append("\n🗡️ [도적] 그림자 속에 숨어드는 민첩함을 얻었습니다!\n");
                } else if(selectJob == 3) {
                    user = new Mage(user);
                    GameFrame.logArea.append("\n🔮 [마법사] 고대의 지혜가 뇌리를 스칩니다!\n");
                } else {
                    GameFrame.logArea.append("\n[시스템] 전직을 거절했습니다. 현재의 길을 계속 걷습니다.\n");
                }
                
                GameFrame.setPlayerImage(user.getJob());
            }
            
            GameFrame.logArea.append("\n[상단의 '다음 챕터' 버튼을 누르시면 다음 챕터로 진행합니다]\n");
            GameFrame.btnNextChapter.setEnabled(true);
        } else {
            Story.winEnding(user);
        }
    }

    public static void nextChapter() {
        GameFrame.btnNextChapter.setEnabled(false);
        currentStageIndex++;
        playStageStory();
    }
}