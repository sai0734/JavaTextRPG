package job;

import user.Player;
import gameFrame.GameFrame;

public class Novice extends Player {

    public Novice(String name) {
        super(name);
        this.setJob("novice");
        this.setHp(100);
        this.setMaxHp(100);
        this.setMp(100);
        this.setMaxMp(100);
        
        this.setStr(5 + (int)(Math.random() * 5));
        this.setDex(5 + (int)(Math.random() * 5));
        this.setWis(5 + (int)(Math.random() * 5));
        
        this.setFirstSkillName("필사의 일격");
        this.setSecondSkillName("젖 먹던 힘까지");
    }

    @Override
    public int attack() {
        GameFrame.logArea.append(this.getName() + "이(가) 주먹으로 공격했습니다\n");
        return (this.getStr() / 2) + (this.getDex() / 2) + (this.getWis() / 2);
    }

    @Override
    public int firstSkill() {
        if(this.getMp() >= 20) {
            this.setMp(this.getMp() - 20);
            GameFrame.logArea.append("⚔️ [" + this.getFirstSkillName() + "] " + this.getName() + "이(가) 온 힘을 다해 공격합니다!\n");
            return this.getStr() + this.getDex() + this.getWis();
        } else {
            GameFrame.logArea.append("MP가 부족합니다. (필요 MP: 20)\n");
            return 0;
        }
    }

    @Override
    public int secondSkill() {
        if(this.getMp() >= 80) {
            this.setMp(this.getMp() - 80);
            GameFrame.logArea.append("😫 [" + this.getSecondSkillName() + "] " + this.getName() + "이(가) 눈을 질끈 감고 무기를 마구 휘두릅니다!\n");
            return this.getStr() * 2 + this.getDex() * 2 + this.getWis() * 2;
        } else {
            GameFrame.logArea.append("MP가 부족합니다. (필요 MP: 80)\n");
            return 0;
        }
    }
}