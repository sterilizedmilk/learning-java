package com.sterilized.puyopuyo.puyo;

import java.util.ArrayList;
import java.util.LinkedList;

import com.sterilized.puyopuyo.Main;

public class PuyoGroup {
    private LinkedList<Puyo> group;

    /**
     * group of PuyoGroup
     */
    private static LinkedList<PuyoGroup> groups = new LinkedList<PuyoGroup>();
    private static int combo = 0;
    
    public PuyoGroup(Puyo first) {
        group = new LinkedList<Puyo>();
        group.add(first);
        groups.add(this);
    }

    public void add(Puyo puyo) {
        if (puyo.getBelong() == this)
            return;

        puyo.setBelong(this);
        group.add(puyo);
    }

    public void remove(Puyo puyo) {
        group.remove(puyo);
        puyo.setBelong(null);

        if (group.size() == 0)
            groups.remove(this);
    }

    /**
     * merge two group.
     */
    public void merge(PuyoGroup other) {
        for (Puyo p : other.group) {
            add(p);
        }
        groups.remove(other);
    }

    public boolean popable() {
        if (group.size() < 4)
            return false;
        return true;
    }

    public void pop() {
        Main.addScore(combo, group.size());
        for (Puyo p : group) {
            p.pop();
        }
        groups.remove(this);
    }

    public static void popAll() {
        try {
            Main.popping = true; // prevent moving puyo while popping
            Thread.sleep(Main.interval);
            Main.popping = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<PuyoGroup> arr = new ArrayList<PuyoGroup>();
        for (PuyoGroup g : groups) {
            if (g.popable()) {
                arr.add(g);
            }
        }

        if (arr.size() == 0) {
            combo = 0;
            return;
        }
        
        for (PuyoGroup g : arr) {
            g.pop();
        }
        ++combo;
        Puyo.collapse();
        popAll();
    }

    public int getSize() {
        return group.size();
    }

}
