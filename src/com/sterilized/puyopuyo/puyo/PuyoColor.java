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
    
    PuyoColor(Color color) {
        this.color = color;
    }
    
    public static PuyoColor random() {
        PuyoColor[] arr = values();
        return arr[(int) (Math.random() * arr.length)];
    }
    
    public Icon icon() {
        return new ImageIcon("image\\puyopuyo\\" + this.name() + ".png");
    }
}
