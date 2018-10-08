package com.sterilized.puyopuyo.puyo;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public enum PuyoColor {
    RED (Color.RED),
    GREEN (Color.GREEN),
    BLUE (Color.BLUE),
    PURPLE (new Color(0x800080)),
    YELLOW (Color.YELLOW);
    
    Color color; // XXX: currently field has no meaning
    
    private static PuyoColor[] usedColor;
    
    public static void randomize() {
        usedColor = new PuyoColor[4];
        
        int notUsed = (int) (Math.random() * 5);
        int index = 0;
        for (PuyoColor c : PuyoColor.values()) {
            if (c.ordinal() == notUsed)
                continue;
            usedColor[index++] = c;
        }
    }
    
    PuyoColor(Color color) {
        this.color = color;
    }
    
    public static PuyoColor random() {
        return usedColor[(int) (Math.random() * 4)];
    }
    
    public Icon icon() {
        return new ImageIcon("image\\puyopuyo\\" + this.name() + ".png");
    }
}
