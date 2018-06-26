package com.sterilized.puyopuyo.puyo;

import javax.swing.JLabel;

import com.sterilized.puyopuyo.Location;
import com.sterilized.puyopuyo.Main;

@SuppressWarnings("serial")
public class Puyo extends JLabel implements Block {
    private PuyoColor color;
    private Location loc;
    private PuyoGroup belong = null;

    private static Puyo trash = new Puyo(); // null Puyo

    public static final int GRID_X = 6;
    public static final int GRID_Y = 13;

    private static final Puyo[][] puyos = new Puyo[GRID_Y][GRID_X];

    private Puyo() {
    }

    public Puyo(PuyoColor color, Location loc) {
        super(color.icon());
        this.color = color;
        this.loc = loc;

//        this.setBorder(BorderFactory.createLineBorder(color.color, 25));
        this.setBounds(loc.x * 50, 600 - loc.y * 50, 50, 50);
    }

    PuyoGroup getBelong() {
        return belong;
    }

    public void setBelong(PuyoGroup group) {
        belong = group;
    }
    
    @Override
    public void setLocation(int x, int y) { // must be checked
        loc.x = x;
        loc.y = y;
        super.setLocation(loc.x * 50, 600 - loc.y * 50);
    }

    void setLocation(Location loc) {
        setLocation(loc.x, loc.y);
    }

    /**
     * relative + loc => return
     */
    static Location relativeLocation(Puyo relative, Location loc) {
        return new Location(relative.loc.x + loc.x, relative.loc.y + loc.y);
    }

    public boolean isSameColor(Puyo other) {
        return this.color.equals(other.color);
    }
    
    /**
     * find Puyo from puyos by Location.
     * @return if out of bound return trash.
     */
    static public Puyo findPuyo(Location loc) {
        return findPuyo(loc.x, loc.y);
    }

    /**
     * find Puyo from puyos by coordinate.
     * @return if out of bound return trash.
     */
    static public Puyo findPuyo(int x, int y) {
        if (x < 0 || x >= GRID_X || y < 0 || y >= GRID_Y) {
            return trash;
        }

        return puyos[y][x];
    }

    @Override
    public boolean canMoveLeft() {
        if (loc.x == 0 || findPuyo(loc.x - 1, loc.y) != null)
            return false;
        return true;
    }

    @Override
    public boolean canMoveRight() {
        if (loc.x == 5 || findPuyo(loc.x + 1, loc.y) != null)
            return false;
        return true;
    }

    @Override
    public boolean canMoveDown() {
        if (loc.getY() == 0 || findPuyo(loc.x, loc.y - 1) != null)
            return false;
        return true;
    }

    @Override
    public void moveLeft() {
        if (!canMoveLeft())
            return;
        loc.x -= 1;
        this.setLocation(loc);
    }

    @Override
    public void moveRight() {
        if (!canMoveRight())
            return;
        loc.x += 1;
        this.setLocation(loc);
    }

    @Override
    public boolean moveDown() {
        if (!canMoveDown())
            return false;
        loc.y -= 1;
        this.setLocation(loc);
        return true;
    }

    @Override
    public void stack() {
        puyos[loc.y][loc.x] = this;

        Puyo[] adj = adjacentPuyo();
        int adjCount = 0;
        PuyoGroup[] adjGroup = new PuyoGroup[4];
OUTTER: for (Puyo p : adj) {
    
            if (isSameColor(p)) {
                for (int i = 0; i < adjCount; i++) {
                    if (adjGroup[i] == p.belong)
                        continue OUTTER;
                }
                
                if (p.belong == null) {
                    System.out.println("what?");
                }
                adjGroup[adjCount++] = p.belong;
            }
        }

        switch (adjCount) {
        case 4:
            adjGroup[0].merge(adjGroup[3]);
        case 3:
            adjGroup[0].merge(adjGroup[2]);
        case 2:
            adjGroup[0].merge(adjGroup[1]);
        case 1:
            adjGroup[0].add(this); // XXX: nullptr exception has occurred once
            break;
        case 0:
            belong = new PuyoGroup(this);
            break;
        default:
            break;
        }
    }

    private Puyo[] adjacentPuyo() {
        Puyo[] tempArr = new Puyo[4];
        int count = 0;
        Puyo temp;

        if ((temp = findPuyo(loc.x + 1, loc.y)) != null && temp != trash)
            tempArr[count++] = temp;
        if ((temp = findPuyo(loc.x - 1, loc.y)) != null && temp != trash)
            tempArr[count++] = temp;
        if ((temp = findPuyo(loc.x, loc.y + 1)) != null && temp != trash)
            tempArr[count++] = temp;
        if ((temp = findPuyo(loc.x, loc.y - 1)) != null && temp != trash)
            tempArr[count++] = temp;

        Puyo[] adj = new Puyo[count];

        for (int i = 0; i < count; i++) {
            adj[i] = tempArr[i];
        }
        return adj;
    }

    public void pop() {
        puyos[loc.y][loc.x] = null;
        Main.panel.remove(this);
    }

    public static void collapse() {
        for (Puyo[] pp : puyos) {
            for (Puyo p : pp) {
                if (p != null && p.canMoveDown()) {
                    if (p.belong != null) {
                        p.belong.remove(p);
                    }
                    puyos[p.loc.y][p.loc.x] = null;
                    p.fall();
                    p.stack();
                }
            }
        }
        Main.panel.repaint();
    }

}
