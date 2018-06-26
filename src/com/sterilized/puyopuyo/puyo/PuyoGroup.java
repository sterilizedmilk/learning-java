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
        for (Puyo p : group) {
            p.pop();
        }
        groups.remove(this);
    }

    public static void popAll() {        
        try {
            Thread.sleep(Main.interval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
                
        ArrayList<PuyoGroup> arr = new ArrayList<PuyoGroup>();
        for (PuyoGroup g : groups) {
            if (g.popable()) {
                arr.add(g);
            }
        }
        
        if (arr.size() == 0)
            return;

        for (PuyoGroup g : arr) {
            g.pop();
        }

        Puyo.collapse();
        popAll();
    }

    public int getSize() {
        return group.size();
    }

}
