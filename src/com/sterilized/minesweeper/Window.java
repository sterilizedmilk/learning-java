package com.sterilized.minesweeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Window extends JFrame implements ActionListener, MouseListener {

    static final Window window = new Window();

    private Window() {
        super("MineSweeper");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
    }

    public void setSize() {
        int width = 16;
        int height = 60;

        height += Ground.fieldHeight;

        width += Main.currentSetting.getX() * Ground.SIZE;
        height += Main.currentSetting.getY() * Ground.SIZE;

        setSize(width, height);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source instanceof JButton) { // Face
            windowReset();
        } else if (source instanceof JMenuItem) { // Menu
            Menu.handleMenu((JMenuItem) source);
        } else if (source instanceof Timer) { // Timer (every seconds)
            if (Main.time >= 999) {
               ((Timer) source).stop();
            } else
                Main.timePassed.setText("00" + ++Main.time);
        }
    }

    public void windowReset() {
        repaint();
        Ground.firstMine = true;
        Ground.resetField();
        Main.setPlaying(true);
        Main.setFace(Main.SMILELY);
        Main.setMineLeft();
        Main.time = 0;
        Main.timer.stop();
        Main.timePassed.setText("000");

        setSize();
        Main.setPosition();

    }

    @Override
    public void mouseClicked(MouseEvent e) { // field
        if (e.getSource() instanceof Ground) {
            if (!Main.isPlaying())
                return;

            Ground ground = (Ground) e.getSource();

            switch (e.getButton()) {
            case MouseEvent.BUTTON1: // Left click
                if (Ground.firstAttempt) {
                    Ground.buildField(Main.currentSetting, ground);
                    Ground.firstAttempt = false;
                    Main.timer.start();
                }

                if (ground.isFlaged()) {
                    return;
                }

                ground.reveal();
                break;

            case MouseEvent.BUTTON3: // Right click
                ground.flag();
                break;
            default:
                break;
            }
            if (Ground.isCleared()) {
                Main.clear();
                return;
            }
            Main.mineLeft.setText("00" + Math.max(Ground.mineCount(), 0));
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (Main.isPlaying())
            Main.setFace(Main.OoO);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (Main.isPlaying())
            Main.setFace(Main.SMILELY);
    }

}
