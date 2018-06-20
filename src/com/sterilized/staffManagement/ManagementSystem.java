package com.sterilized.staffManagement;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class ManagementSystem extends JFrame implements ActionListener {
    static final JButton enterLogin =       new JButton("Login");
    static final JButton enterRegister =    new JButton("Register");
    static final JButton userInfo =         new JButton("User: None");
    static final JButton enterStaffList =   new JButton("Staff List");
    static final JButton enterCommute =     new JButton("Commute");

    static final Font BIG_FONT =    new Font("Courier", Font.PLAIN, 15);
    static final Font SMALL_FONT =  new Font("Courier", Font.PLAIN, 12);

    private static int currentPage = 0;

    static ManagementSystem frame = new ManagementSystem("Staff Management System");
    static Staff user = null;
    
    static Login login = new Login();
    static Register register = new Register();
    static Commute commute = new Commute();
    static StaffBoard staffBoard = new StaffBoard();

    ManagementSystem(String title) {
        super(title);
    }

    public static void main(String[] args) {
        Staff.addStaff("boss", "123456", "ChulSu Kim", Rank.CEO);
        Staff.addStaff("michael", "aweyt9578", "Michael Smith", Rank.MANAGER);
        Staff.addStaff("woosong", "swefw5321", "WooSong Park", Rank.RANK_AND_FILE);

        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        enterLogin.setFont(BIG_FONT);
        enterLogin.setBounds(10, 60, 70, 30);
        enterLogin.setMargin(new Insets(0, 0, 0, 0));

        enterRegister.setFont(BIG_FONT);
        enterRegister.setBounds(100, 60, 70, 30);
        enterRegister.setMargin(new Insets(0, 0, 0, 0));
        enterRegister.setMargin(new Insets(0, 0, 0, 0));

        userInfo.setFont(SMALL_FONT);
        userInfo.setBounds(160, 300, 150, 20);
        userInfo.addActionListener(frame);
        frame.add(userInfo);

        enterStaffList.setFont(BIG_FONT);
        enterStaffList.setBounds(140, 160, 70, 30);
        enterStaffList.setMargin(new Insets(0, 0, 0, 0));

        enterCommute.setFont(BIG_FONT);
        enterCommute.setBounds(10, 160, 100, 30);
        enterCommute.setMargin(new Insets(0, 0, 0, 0));

        enterPage();
        return;
    }

    public static void enterPage() {
        currentPage = 0;
        frame.add(enterLogin);
        enterLogin.addActionListener(frame);

        frame.setSize(600, 600);
        frame.repaint();

        if (user != null) {
            userInfo.setText("User: " + user.getName());
            userInfo.setSize(userInfo.getText().length() * 20, 20);

            frame.add(enterCommute);
            enterCommute.addActionListener(frame);

            frame.add(enterStaffList);
            enterStaffList.addActionListener(frame);
        } else {
            userInfo.setText("User: None");

            frame.add(enterRegister);
            enterRegister.addActionListener(frame);
        }
    }

    public static void exitPage() {
        frame.remove(enterLogin);
        enterLogin.removeActionListener(frame);

        frame.remove(enterRegister);
        enterRegister.removeActionListener(frame);

        if (user != null) {
            frame.remove(enterCommute);
            enterCommute.removeActionListener(frame);

            frame.remove(enterStaffList);
            enterStaffList.removeActionListener(frame);
        } else {
            frame.remove(enterRegister);
            enterRegister.removeActionListener(frame);
        }
    }

    public static void exitAllPage() {
        switch (currentPage) {
        case 0:
            exitPage();
            break;
        case 1:
            register.exitPage();
            break;
        case 2:
            login.exitPage();
            break;
        case 3:
            staffBoard.exitPage();
            break;
        case 4:
            commute.exitPage();
            break;
        default:
        }

    }

    public static void setUser(Staff staff) {
        user = staff;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == enterRegister) {
            exitPage();
            register.enterPage(frame);
            currentPage = 1;
        } else if (source == enterLogin) {
            exitPage();
            login.enterPage(frame);
            currentPage = 2;
        } else if (source == enterStaffList) {
            exitPage();
            staffBoard.enterPage(frame);
            currentPage = 3;
        } else if (source == enterCommute) {
            exitPage();
            commute.enterPage(frame);
            currentPage = 4;
        } else if (source == userInfo && user != null) {
            int answer = JOptionPane.showConfirmDialog(frame,
                    "Do you really want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.OK_OPTION) {
                exitAllPage();
                exitPage();
                user = null;
                enterPage();
            }
        }
    }

}
