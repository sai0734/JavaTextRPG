package gameFrame;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.DefaultCaret;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import common.BattleSystem;
import main.Main;
import monster.Monster;

public class GameFrame extends JFrame {
    public static JTextArea logArea;
    public static JLabel monsterImageLabel;
    public static JLabel playerImageLabel; 
    
    // 💡 명령을 받을 수 있게 public static으로 설정
    public static JTextField nameInput;
    public static JButton btnNormal, btnHard, btnConfirmName;
    public static JButton btnStartBattle, btnNextChapter;
    private static JButton btnAttack, btnDefend, btnAvoid, btnSkill, btnBag;

    private final Color BG_COLOR = new Color(20, 20, 20);
    private final Color PANEL_COLOR = new Color(35, 35, 35);
    private final Color TEXT_COLOR = new Color(220, 220, 220);
    private final Color ACCENT_COLOR = new Color(180, 150, 80);

    public GameFrame() {
        setTitle("JAVA GUI RPG : THE EZEN");
        setSize(1050, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BG_COLOR);
        setLayout(new BorderLayout(10, 10));

        logArea = new JTextArea();

        // 1. 상단 컨트롤 패널
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(PANEL_COLOR);
        topPanel.setBorder(new CompoundBorder(new LineBorder(ACCENT_COLOR, 1), new EmptyBorder(15, 10, 15, 10)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1.0;

        gbc.gridx = 0; gbc.weightx = 0;
        topPanel.add(createLabel("난이도:", 14), gbc);
        
        btnNormal = createStyledButton("Normal", Color.DARK_GRAY, 100, 45);
        gbc.gridx = 1; topPanel.add(btnNormal, gbc);
        btnHard = createStyledButton("Hard", new Color(100, 30, 30), 100, 45);
        gbc.gridx = 2; topPanel.add(btnHard, gbc);

        gbc.gridx = 3; gbc.weightx = 0;
        topPanel.add(createLabel("  이름:", 14), gbc);
        
        nameInput = new JTextField();
        nameInput.setBackground(Color.BLACK);
        nameInput.setForeground(Color.WHITE);
        nameInput.setCaretColor(Color.WHITE);
        nameInput.setFont(new Font("돋움체", Font.PLAIN, 15));
        nameInput.setPreferredSize(new Dimension(150, 45));
        gbc.gridx = 4; gbc.weightx = 1.0; 
        topPanel.add(nameInput, gbc);

        btnConfirmName = createStyledButton("이름 확인", ACCENT_COLOR, 140, 45);
        gbc.gridx = 5; gbc.weightx = 0;
        topPanel.add(btnConfirmName, gbc);

        btnStartBattle = createStyledButton("⚔️ 전투 시작", new Color(50, 100, 50), 140, 45);
        gbc.gridx = 6; topPanel.add(btnStartBattle, gbc);

        btnNextChapter = createStyledButton("⏩ 다음 챕터", new Color(50, 70, 120), 140, 45);
        gbc.gridx = 7; topPanel.add(btnNextChapter, gbc);

        add(topPanel, BorderLayout.NORTH);

        // 2. 중앙 패널
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(new EmptyBorder(0, 10, 0, 10));

        JPanel imagePanel = new JPanel(new GridLayout(1, 2, 10, 0));
        imagePanel.setOpaque(false);
        monsterImageLabel = createDisplayLabel();
        playerImageLabel = createDisplayLabel();
        imagePanel.add(monsterImageLabel);
        imagePanel.add(playerImageLabel);
        centerPanel.add(imagePanel, BorderLayout.NORTH);

        logArea.setEditable(false);
        logArea.setBackground(Color.BLACK);
        logArea.setForeground(TEXT_COLOR);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        logArea.setMargin(new Insets(10, 10, 10, 10));

        DefaultCaret caret = (DefaultCaret)logArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        logArea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { scrollToBottom(); }
            public void removeUpdate(DocumentEvent e) { scrollToBottom(); }
            public void changedUpdate(DocumentEvent e) { scrollToBottom(); }
            private void scrollToBottom() {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if(logArea != null) logArea.setCaretPosition(logArea.getDocument().getLength());
                    }
                });
            }
        });

        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(new LineBorder(ACCENT_COLOR, 1));
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // 3. 하단 패널
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        buttonPanel.setBackground(PANEL_COLOR);
        buttonPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        btnAttack = createStyledButton("공격", new Color(120, 40, 40), 0, 55);
        btnDefend = createStyledButton("방어", new Color(40, 80, 120), 0, 55);
        btnAvoid = createStyledButton("회피", new Color(100, 100, 40), 0, 55);
        btnSkill = createStyledButton("스킬", new Color(80, 40, 120), 0, 55);
        btnBag = createStyledButton("가방", new Color(60, 60, 60), 0, 55);

        buttonPanel.add(btnAttack); buttonPanel.add(btnDefend); buttonPanel.add(btnAvoid);
        buttonPanel.add(btnSkill); buttonPanel.add(btnBag);

        add(buttonPanel, BorderLayout.SOUTH);

        setupEventListeners();

        // 💡 초기 상태 설정: 이름 입력 관련은 비활성화
        nameInput.setEnabled(false);
        btnConfirmName.setEnabled(false);
        btnStartBattle.setEnabled(false);
        btnNextChapter.setEnabled(false);
        enableBattleButtons(false);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JLabel createLabel(String text, int size) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, size));
        return label;
    }

    private JLabel createDisplayLabel() {
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(480, 350));
        label.setBackground(Color.BLACK);
        label.setOpaque(true);
        label.setBorder(new EmptyBorder(5, 5, 5, 5)); 
        return label;
    }

    private JButton createStyledButton(String text, Color baseColor, int width, int height) {
        JButton btn = new JButton(text);
        btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        btn.setBackground(baseColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        
        Dimension d = new Dimension(width, height);
        if (width > 0) {
            btn.setPreferredSize(d);
            btn.setMinimumSize(d);
            btn.setMaximumSize(d);
        } else {
            btn.setPreferredSize(new Dimension(btn.getPreferredSize().width, height));
        }
        return btn;
    }

    private void setupEventListeners() {
        btnNormal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.setDifficulty(1);
                btnNormal.setEnabled(false);
                btnHard.setEnabled(false);
            }
        });

        btnHard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.setDifficulty(2);
                btnNormal.setEnabled(false);
                btnHard.setEnabled(false);
            }
        });

        btnConfirmName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameInput.getText();
                if(!name.isEmpty()) {
                    Main.initPlayer(name);
                    btnConfirmName.setEnabled(false);
                    nameInput.setEditable(false);
                }
            }
        });

        btnStartBattle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.startBattle();
            }
        });

        btnNextChapter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.nextChapter();
            }
        });

        btnAttack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Monster m = Main.stageList.get(Main.currentStageIndex);
                BattleSystem.playerAttack(Main.user, m);
                checkBattleEnd(m);
            }
        });

        btnDefend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Monster m = Main.stageList.get(Main.currentStageIndex);
                BattleSystem.playerDefend(Main.user, m);
                checkBattleEnd(m);
            }
        });

        btnAvoid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Monster m = Main.stageList.get(Main.currentStageIndex);
                BattleSystem.playerAvoid(Main.user, m);
                checkBattleEnd(m);
            }
        });

        btnSkill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Monster m = Main.stageList.get(Main.currentStageIndex);
                String[] options = {Main.user.getFirstSkillName(), Main.user.getSecondSkillName()};
                int choice = JOptionPane.showOptionDialog(null, "스킬 선택", "Skill", 
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                if(choice != -1) {
                    BattleSystem.playerSkill(Main.user, m, choice + 1);
                    checkBattleEnd(m);
                }
            }
        });

        btnBag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Monster m = Main.stageList.get(Main.currentStageIndex);
                BattleSystem.playerInventory(Main.user, m);
                checkBattleEnd(m);
            }
        });
    }

    public static void enableBattleButtons(boolean enable) {
        btnAttack.setEnabled(enable); btnDefend.setEnabled(enable);
        btnAvoid.setEnabled(enable); btnSkill.setEnabled(enable); btnBag.setEnabled(enable);
    }

    private void checkBattleEnd(Monster m) {
        if (m.getHp() <= 0) { Main.onBattleEnd(true); setMonsterImage(null); }
        else if (Main.user.getHp() <= 0) { Main.onBattleEnd(false); }
    }

    private static void setImageToLabel(JLabel label, String name, String fallbackText) {
        if (name == null || label == null) {
            if(label != null) { label.setIcon(null); label.setText(fallbackText); }
            return;
        }
        String pngPath = "images/" + name + ".png";
        String jpgPath = "images/" + name + ".jpg";
        File file = new File(pngPath);
        String targetPath = file.exists() ? pngPath : (new File(jpgPath).exists() ? jpgPath : null);

        if (targetPath != null) {
            ImageIcon icon = new ImageIcon(targetPath);
            Image img = icon.getImage();
            int w = label.getWidth() > 0 ? label.getWidth() : label.getPreferredSize().width;
            int h = label.getHeight() > 0 ? label.getHeight() : label.getPreferredSize().height;
            label.setIcon(new ImageIcon(img.getScaledInstance(w, h, Image.SCALE_SMOOTH)));
            label.setText("");
        } else {
            label.setIcon(null);
            label.setText("[" + name + " 이미지 없음]");
        }
        label.revalidate(); label.repaint();
    }

    public static void setMonsterImage(String monsterName) { setImageToLabel(monsterImageLabel, monsterName, "전투 대기 중"); }
    public static void setPlayerImage(String jobName) { setImageToLabel(playerImageLabel, jobName, "캐릭터 대기 중"); }
}