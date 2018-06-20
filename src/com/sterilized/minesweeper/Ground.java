package com.sterilized.minesweeper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/*
 * field's component.
 */
@SuppressWarnings("serial")
public class Ground extends JButton {
    private boolean mine;
    private final Location loc;
    private boolean revealed;
    private boolean flag;

    static final int SIZE = 16; // width & height of ground
    static int fieldHeight = 30;
    static final Location FIELD_START = new Location(0, fieldHeight);
    static final Font GROUND_FONT = new Font("Courier", Font.BOLD, 15);
    static boolean firstAttempt = true;
    static boolean firstMine = true;

    static final ImageIcon MINE =     new ImageIcon("image/mine_sweeper/mine.png");
    static final ImageIcon MINE_RED = new ImageIcon("image/mine_sweeper/mineRed.png");
    static final ImageIcon BLANK =    new ImageIcon("image/mine_sweeper/blank.png");
    static final ImageIcon FLAG =     new ImageIcon("image/mine_sweeper/flag.png");
    static final ImageIcon FAILED =   new ImageIcon("image/mine_sweeper/failed.png");
    static final Insets insets =      new Insets(0, 0, 0, 0);

    static Ground[][] field;

    Ground(int x, int y, boolean mine, String text) {
        loc = new Location(x, y);
        setBounds(FIELD_START.x + SIZE * x, FIELD_START.y + SIZE * y, SIZE, SIZE);
        setText(text);
        setFont(GROUND_FONT);
        setMargin(insets);
        this.mine = mine;
        revealed = false;
        flag = false;
    }

    Ground(int x, int y, boolean mine) {
        this(x, y, mine, "");
    }

    Ground(Location loc, boolean mine) {
        this(loc.x, loc.y, mine);
    }

    // Destroy past field if possible, and make new grounds, but don't make mine.
    public static void initiateField(Setting setting) {
        if (Ground.field != null) {
            for (int i = 0; i < Ground.field.length; i++) {
                for (int j = 0; j < Ground.field[i].length; j++) {
                    Window.window.remove(Ground.field[i][j]);
                }
            }
        }

        if (Main.currentSetting != setting)
            Main.currentSetting = setting;

        Ground.field = new Ground[setting.getY()][setting.getX()];

        for (int i = 0; i < Ground.field.length; i++) {
            for (int j = 0; j < Ground.field[i].length; j++) {
                Ground.field[i][j] = new Ground(j, i, false, "");
                Window.window.add(Ground.field[i][j]);
                Ground.field[i][j].addMouseListener(Window.window);
            }
        }
    }

    public static void resetField() {
        for (Ground[] grounds : Ground.field) {
            for (Ground g : grounds) {
                g.reset();
            }
        }
        firstAttempt = true;
    }

    // Lay new mines in new field.
    public static void buildField(Setting setting, Ground safe) {
        int mineNum = setting.getMineNum();

        int groundNum = Ground.field.length * Ground.field[0].length;

        for (Ground[] grounds : Ground.field) {
            for (Ground g : grounds) {
                if (g == safe) {
                    continue;
                }

                if (Util.randInt(groundNum - 1) >= mineNum)
                    g.mine = false;
                else {
                    g.mine = true;
                    --mineNum;
                }

                --groundNum;
            }
        }
    }

    public void reset() {
        mine = false;
        unreveal();
        if (flag)
            flag();
    }

    // how many mines adjacent?
    public int adjacentMine() {
        int mineNum = 0;
        for (Location l : loc.adjacent()) {
            if (locToGround(l).isMine()) {
                ++mineNum;
            }
        }
        return mineNum;
    }

    public void reveal() {
        if (revealed || flag)
            return;

        revealed = true;

        if (isMine()) {
            setIcon(MINE_RED);
            Main.setFace(Main.DEAD);
            revealMine();
            Main.setPlaying(false);
            return;
        }

        int adj = adjacentMine();
        setText(adj);
    }

    public void unreveal() {
        revealed = false;
        setIcon(null);
        setText("");
    }

    public static void revealMine() {
        firstMine = false;
        for (Ground[] grounds : field) {
            for (Ground g : grounds) {
                if (g.isMine()) {
                    if (g.flag)
                        continue;

                    if (g.revealed)
                        continue;

                    g.setIcon(MINE);
                } else if (g.flag) {
                    g.flag();
                    g.setIcon(FAILED);
                    continue;
                }
            }
        }
    }

    void setText(int adj) {
        switch (adj) {
        case 0:
            setText("");
            setIcon(BLANK);
            for (Location l : loc.adjacent()) {
                locToGround(l).reveal();
            }
            return;
        case 1:
            setForeground(Color.BLUE);
            break;
        case 2: // Green
            setForeground(new Color(0x047e03));
            break;
        case 3:
            setForeground(Color.RED);
            break;
        case 4: // Dark blue
            setForeground(new Color(0x01017e));
            break;
        case 5: // Dark red
            setForeground(new Color(0x750205));
            break;
        case 6: // Jade
            setForeground(new Color(0x027f82));
            break;
        case 7:
            setForeground(Color.BLACK);
            break;
        case 8:
            setForeground(Color.GRAY);
            break;
        default:
            break;
        }
        setText("" + adj);
    }

    public boolean isMine() {
        if (mine)
            return true;
        return false;
    }

    public boolean isFlaged() {
        return flag;
    }

    // toggle flag
    public boolean flag() {
        if (revealed)
            return false;

        flag = !flag;

        if (flag) {
            setIcon(FLAG);
        } else {
            setIcon(null);
        }

        return flag;
    }

    // find matching ground
    public static Ground locToGround(Location loc) {
        return field[loc.y][loc.x];
    }

    // are we won?
    public static boolean isCleared() {
        for (Ground[] grounds : field) {
            for (Ground g : grounds) {
                if (!g.revealed && !g.mine)
                    return false;
            }
        }
        return true;
    }

    // mineNum - flagNum
    public static int mineCount() {
        int count = Main.currentSetting.getMineNum();

        for (Ground[] grounds : field) {
            for (Ground g : grounds) {
                if (g.isFlaged())
                    --count;
            }
        }
        return Math.max(count, 0);
    }

}
