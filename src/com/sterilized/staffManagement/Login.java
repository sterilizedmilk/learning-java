package com.sterilized.staffManagement;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Login implements Page {
    private static JFrame window;

    private static JButton loginOk;
    private static JButton loginNo;

    private static ArrayList<JComponent> loginComponents;

    private static final String[] attribute = { "Id:", "Password:" };

    Login() {
    }

    @Override
    public void enterPage(JFrame frame) {
        loginComponents = new ArrayList<JComponent>();

        window = frame;

        JTextField temp;
        for (int i = 0; i < 2; i++) {
            temp = new JTextField(attribute[i]);
            loginComponents.add(temp);

            temp.setBounds(30, 30 + 30 * i, 80, 20);
            temp.setEditable(false);

            temp = new JTextField();
            loginComponents.add(temp);
            temp.setBounds(120, 30 + 30 * i, 80, 20);
        }

        loginOk = new JButton("Ok");
        loginNo = new JButton("No");

        loginOk.setBounds(40, 200, 50, 40);
        loginNo.setBounds(100, 200, 50, 40);

        loginOk.addActionListener(this);
        loginNo.addActionListener(this);

        loginComponents.add(loginOk);
        loginComponents.add(loginNo);

        for (JComponent c : loginComponents) {
            window.add(c);
        }

        window.repaint();
    }

    @Override
    public void exitPage() {
        for (JComponent c : loginComponents) {
            window.remove(c);
        }
        loginOk.removeActionListener(this);
        loginNo.removeActionListener(this);

        ManagementSystem.enterPage();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == loginOk) {
            String id = ((JTextField) loginComponents.get(1)).getText();
            String password = ((JTextField) loginComponents.get(3)).getText();

            Staff result = Staff.getStaff(id, password);
            if (result == null) {
                JOptionPane.showMessageDialog(window, "Invalid id or password.", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                ManagementSystem.setUser(result);
            }

        } else if (source == loginNo) {

        }

        exitPage();
    }

}
