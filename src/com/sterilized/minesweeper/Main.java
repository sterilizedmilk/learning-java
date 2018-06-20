package com.sterilized.minesweeper;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Main {
    static boolean playing = true;

    static final ImageIcon SMILELY = new ImageIcon("image/mine_sweeper/smilelyFace.png"); // :)
    static final ImageIcon DEAD =    new ImageIcon("image/mine_sweeper/deadFace.png");    // X(
    static final ImageIcon COOL =    new ImageIcon("image/mine_sweeper/sunglasses.png");  // B)
    static final ImageIcon OoO =     new ImageIcon("image/mine_sweeper/OoO.png");         // :o

    static Setting currentSetting = Setting.BEGINNER;
    static JButton face = new JButton(SMILELY);
    static JTextField mineLeft = new JTextField("010");
    static JTextField timePassed = new JTextField("000");
    static Timer timer = new Timer(1000, Window.window);
    static int time = 0;

    static {
        timer.stop();
    }

    public static void main(String[] args) {
        // create empty field
        Ground.initiateField(currentSetting);

        // face
        Window.window.add(face);
        face.addActionListener(Window.window);

        // mine left
        mineLeft.setFont(new Font("Courier", Font.BOLD, 20));
        mineLeft.setForeground(Color.RED);
        mineLeft.setBackground(Color.BLACK);
        mineLeft.setEditable(false);
        Window.window.add(mineLeft);

        // time passed
        timePassed.setFont(new Font("Courier", Font.BOLD, 20));
        timePassed.setForeground(Color.RED);
        timePassed.setBackground(Color.BLACK);
        timePassed.setEditable(false);
        Window.window.add(timePassed);

        Menu.setMenu();

        Window.window.setSize(); // must placed before setPosition()
        setPosition();

        Ground.revealMine();
    }

    public static boolean isPlaying() {
        return playing;
    }

    public static void setPlaying(boolean newStatus) {
        playing = newStatus;
        if (!playing)
            timer.stop();
    }

    public static void clear() {
        face.setIcon(COOL);
        playing = false;
        Ground.revealMine();
        timer.stop();
    }

    public static void setPosition() {
        face.setBounds(Window.window.getSize().width / 2 - 17, 6, 20, 20);
        mineLeft.setBounds(10, 6, 37, 22);
        timePassed.setBounds(Window.window.getSize().width - 63, 6, 37, 22);
    }

    public static void setFace(ImageIcon expression) {
        face.setIcon(expression);
    }

    public static void setMineLeft() {
        Main.mineLeft.setText("00" + Main.currentSetting.getMineNum());
    }

}
