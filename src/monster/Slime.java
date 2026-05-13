package monster;

import gameFrame.GameFrame;

public class Slime extends Monster {

    public Slime() {
        super("슬라임", 30, 30, 5, "      .----.      \n     / >  < \\     \n    (  \\__/  )    \n     '------'     \n  [ Lv.1 슬라임 ]");
    }
    
    @Override
    public int attack() {
        GameFrame.logArea.append("슬라임이 몸을 움츠렸다가 튀어 오릅니다!\n");
        return getPower();
    }
}