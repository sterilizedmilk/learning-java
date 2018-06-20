package com.sterilized.staffManagement;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Register implements Page {
    private static JFrame window;

    private static JButton registerOk;
    private static JButton registerNo;

    private static ArrayList<JComponent> registerComponents;

    private static final String[] attribute = { "Id:", "Password:", "Name:", "Rank:" };

    Register() {
    }

    @Override
    public void enterPage(JFrame frame) {
        window = frame;

        registerComponents = new ArrayList<JComponent>();

        JTextField temp;
        for (int i = 0; i < 4; i++) {
            temp = new JTextField(attribute[i]);
            registerComponents.add(temp);

            temp.setBounds(30, 30 + 30 * i, 80, 20);
            temp.setEditable(false);

            temp = new JTextField();
            registerComponents.add(temp);
            temp.setBounds(120, 30 + 30 * i, 80, 20);
        }

        registerOk = new JButton("ok");
        registerNo = new JButton("no");

        registerOk.setBounds(40, 200, 50, 40);
        registerNo.setBounds(100, 200, 50, 40);

        registerOk.addActionListener(this);
        registerNo.addActionListener(this);

        registerComponents.add(registerOk);
        registerComponents.add(registerNo);

        for (JComponent c : registerComponents) {
            window.add(c);
        }
        window.repaint();
    }

    @Override
    public void exitPage() {
        for (JComponent c : registerComponents) {
            window.remove(c);
        }
        ManagementSystem.enterPage();
        registerOk.removeActionListener(this);
        registerNo.removeActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == registerOk) {
            String id = ((JTextField) (registerComponents.get(1))).getText();
            String password = ((JTextField) (registerComponents.get(3))).getText();
            String name = ((JTextField) (registerComponents.get(5))).getText();
            String rank = ((JTextField) (registerComponents.get(7))).getText();

            if (!Staff.isValidId(id)) {
                JOptionPane.showMessageDialog(window, "Invalid Id.", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (!Staff.isValidPassword(password)) {
                JOptionPane.showMessageDialog(window, "Invalid Password.", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (name.isEmpty()) {
                JOptionPane.showMessageDialog(window, "There is no name.", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                Rank r = Rank.stringToRank(rank);
                if (r == null) {
                    JOptionPane.showMessageDialog(window, "Invalid Rank.", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    Staff.addStaff(id, password, name, r);
                    exitPage();
                    return;
                }
            }

        } else if (source == registerNo) {
            exitPage();
        }

    }

}
