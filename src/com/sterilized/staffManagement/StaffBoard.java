package com.sterilized.staffManagement;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class StaffBoard implements Page {
    private static JFrame window;

    private static ArrayList<JComponent> staffLIstComponents;

    private static JTable table;
    private static JButton exit;

    private static final String[] attribute = { "Id", "Name", "Rank" };

    StaffBoard() {
    }

    @Override
    public void enterPage(JFrame frame) {
        window = frame;
        staffLIstComponents = new ArrayList<JComponent>();

        ArrayList<Staff> staffList = ManagementSystem.user.getStaffList();
        String[][] staffTable = new String[staffList.size()][3];

        for (int i = 0; i < staffList.size(); i++) {
            staffTable[i][0] = staffList.get(i).getId();
            staffTable[i][1] = staffList.get(i).getName();
            staffTable[i][2] = staffList.get(i).getRank().name();
        }

        table = new JTable(staffTable, attribute);
        table.setEnabled(false);
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 300, 300);
        staffLIstComponents.add(pane);

        exit = new JButton("exit");
        exit.setBounds(300, 0, 70, 25);
        exit.addActionListener(this);
        staffLIstComponents.add(exit);
        

        for (JComponent c : staffLIstComponents) {
            window.add(c);
        }
        window.repaint();
    }

    @Override
    public void exitPage() {
        for (JComponent c : staffLIstComponents) {
            window.remove(c);
        }
        ManagementSystem.enterPage();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == exit) {
            exitPage();
        }
    }
}
