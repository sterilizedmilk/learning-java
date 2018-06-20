package com.sterilized.staffManagement;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Commute implements Page {
    private static JFrame window;

    private static ArrayList<JComponent> commuteComponents;

    private static RecordType[] recordArr = RecordType.values();
    private static JTable table;
    private static JScrollPane pane;
    private static JButton[] recordButton;
    private static JButton exit;

    private static final String[] attribute = { "Date", "Id", "type" };

    Commute() {
    }

    @Override
    public void enterPage(JFrame frame) {
        window = frame;
        commuteComponents = new ArrayList<JComponent>();

        recordButton = new JButton[recordArr.length];
        for (int i = 0; i < recordButton.length; i++) {
            recordButton[i] = new JButton(recordArr[i].name());
            recordButton[i].setBounds(0, 30 * i, 100, 30);
            recordButton[i].addActionListener(this);
            commuteComponents.add(recordButton[i]);
        }

        ArrayList<WorkingRecord> RecordList = WorkingRecord.browse();
        String[][] recordTable = new String[RecordList.size()][3];

        for (int i = 0; i < RecordList.size(); i++) {
            recordTable[i][0] = RecordList.get(i).getTime().toString();
            recordTable[i][1] = RecordList.get(i).getId();
            recordTable[i][2] = RecordList.get(i).getRecordType().name();
        }

        table = new JTable(recordTable, attribute);
        table.setEnabled(false);
        pane = new JScrollPane(table);
        pane.setBounds(100, 0, 400, 300);
        window.add(pane);

        exit = new JButton("exit");
        exit.setBounds(400, 500, 70, 25);
        exit.addActionListener(this);
        commuteComponents.add(exit);

        for (JComponent c : commuteComponents) {
            window.add(c);
        }
        window.repaint();
    }

    @Override
    public void exitPage() {
        for (JComponent c : commuteComponents) {
            window.remove(c);
        }
        window.remove(pane);
        ManagementSystem.enterPage();
    }

    private static void refresh() {
        window.remove(pane);
        ArrayList<WorkingRecord> RecordList = WorkingRecord.browse();
        String[][] recordTable = new String[RecordList.size()][3];

        for (int i = 0; i < RecordList.size(); i++) {
            recordTable[i][0] = RecordList.get(i).getTime().toString();
            recordTable[i][1] = RecordList.get(i).getId();
            recordTable[i][2] = RecordList.get(i).getRecordType().name();
        }

        table = new JTable(recordTable, attribute);
        pane = new JScrollPane(table);
        pane.setBounds(100, 0, 400, 300);
        window.add(pane);
        window.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == exit) {
            exitPage();
        } else {
            for (int i = 0; i < recordArr.length; ++i) {
                if (source == recordButton[i]) {
                    WorkingRecord.addRecord(ManagementSystem.user, recordArr[i]);
                    refresh();
                }
            }
        }
    }
}
