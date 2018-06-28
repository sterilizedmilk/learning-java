package com.sterilized.puyopuyo;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sterilized.puyopuyo.puyo.Puyo;
import com.sterilized.puyopuyo.puyo.PuyoPair;

public class Main implements KeyListener {
    static JFrame frame = new JFrame("PuyoPuyo");
    static PuyoPair puyo;
    public static JPanel panel = new JPanel();
    
    public static final int interval = 500;
    public static boolean popping = false;

    public static void main(String[] args) {
        setWindow();
        createNewPuyo();
        
        while (true) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            if (!puyo.canMoveDown()) {
                puyo.stack();
                createNewPuyo();
                continue;
            } else {
                puyo.moveDown();
            }
        }
    }

    static void setWindow() {
        frame.setSize(315, 687);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setBounds(0, 0, 300, 650);
        panel.setBackground(Color.GREEN);
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        panel.setLayout(null);
        
        frame.add(panel);
        
        Main main = new Main();
        frame.addKeyListener(main);

        frame.repaint();
    }
    
    static void createNewPuyo() {
        if (Puyo.findPuyo(2, 11) != null) {
            JOptionPane.showMessageDialog(frame, "Game Over", "Game Over", JOptionPane.OK_OPTION);
            System.exit(0);
        }

        puyo = new PuyoPair();
        puyo.addInto(panel);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
        if (popping)
            return;
        char key = e.getKeyChar();

        switch (key) {
        case 'a':
            puyo.moveLeft();
            break;
        case 's':
            puyo.moveDown();
            break;
        case 'd':
            puyo.moveRight();
            break;
        case 'w':
            puyo.rotate(false);
            break;
        case 'v':
            puyo.fall();
            break;
        case 0x1B:
            System.exit(0);
        default:
            break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
