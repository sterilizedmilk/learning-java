package com.sterilized.puyopuyo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.sterilized.puyopuyo.puyo.Puyo;
import com.sterilized.puyopuyo.puyo.PuyoColor;
import com.sterilized.puyopuyo.puyo.PuyoPair;

public class Main implements KeyListener {
    private static JFrame frame = new JFrame("PuyoPuyo");
    private static PuyoPair puyo;
    public static JPanel panel = new JPanel();
    private static JLabel scoreLabel = new JLabel("0", SwingConstants.RIGHT);

    private static int score = 0;
    public static int interval = 500;
    public static boolean popping = false;

    public static void main(String[] args) {
        PuyoColor.randomize();
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

    private static void setWindow() {
        frame.setSize(315, 747);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setBounds(0, 0, 300, 650);
        panel.setBackground(Color.GRAY);
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        panel.setLayout(null);

        scoreLabel.setBounds(0, 650, 300, 60);
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(Color.BLACK);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Courier", Font.BOLD, 40));
        scoreLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        scoreLabel.setLayout(null);
        frame.add(scoreLabel);

        frame.add(panel);

        Main main = new Main();
        frame.addKeyListener(main);
    }

    private static void createNewPuyo() {
        if (Puyo.findPuyo(2, 11) != null) {
            JOptionPane.showMessageDialog(frame, "Game Over", "Game Over", JOptionPane.OK_OPTION);
            System.exit(0);
        }

        puyo = new PuyoPair();
        puyo.addInto(panel);
    }

    public static void addScore(int combo, int count) {
        for (int i = 0; i < combo; ++i) {
            count *= 2;
        }
        score += count;
        scoreLabel.setText("" + score);
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
        case 0x1B: // ESC
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
