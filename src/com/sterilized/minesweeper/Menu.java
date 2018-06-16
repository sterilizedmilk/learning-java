package com.sterilized.minesweeper;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Menu {

	static JMenuBar menuBar = new JMenuBar();

	static JMenu menu = new JMenu("Game");

	static ArrayList<JMenuItem> menuList = new ArrayList<JMenuItem>(7);

	static JMenuItem newGame = 			new JMenuItem("New");

	static JMenuItem diffBeginner = 	new JMenuItem("Beginner");
	static JMenuItem diffIntermediate = new JMenuItem("Intermediate");
	static JMenuItem diffAdvanced = 	new JMenuItem("Advanced");
	static JMenuItem diffCustom = 		new JMenuItem("Custom");

	static JMenuItem Records = 			new JMenuItem("Best Times...");
	static JMenuItem Exit =				new JMenuItem("Exit");

	static Font menuFont = 	new Font("Courier", Font.PLAIN, 11);

	static void setMenu() {
		menuList.add(newGame);
		menuList.add(diffBeginner);
		menuList.add(diffIntermediate);
		menuList.add(diffAdvanced);
		menuList.add(diffCustom);
		menuList.add(Records);
		menuList.add(Exit);

		for (JMenuItem i : menuList) {
			menu.add(i);
			i.addActionListener(Window.window);
			i.setFont(menuFont);
		}

		menu.setFont(menuFont);

		menuBar.add(menu);
		Window.window.setJMenuBar(menuBar);
	}
	
	static void handleMenu(JMenuItem source) {
		if (source == newGame) {
			Window.window.windowReset();
		} else if (source == diffBeginner) {
			Ground.initiateField(Setting.BEGINNER);
			Window.window.windowReset();
		} else if (source == diffIntermediate) {
			Ground.initiateField(Setting.INTERMEDIATE);
			Window.window.windowReset();
		} else if (source == diffAdvanced) {
			Ground.initiateField(Setting.ADVANCED);
			Window.window.windowReset();
		} else if (source == diffCustom) {
			customField();
			Window.window.windowReset();
		} else if (source == Records) {
			System.out.println("not yet!");
		} else if (source == Exit) {
			System.exit(0);
		}
	}
	
	static void customField() {
		JTextField textHeight = new JTextField(String.valueOf(Main.currentSetting.getY()));
		JTextField textWidth = new JTextField(String.valueOf(Main.currentSetting.getX()));
		JTextField textMines = new JTextField(String.valueOf(Main.currentSetting.getMineNum()));
		Object[] options = {"Height:", textHeight, "WIdth:", textWidth, "Mines:", textMines};
		
		if (JOptionPane.showConfirmDialog(null, options, "Custom Field", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			try {
				Setting newSetting = new Setting("", Integer.parseInt(textWidth.getText()),
												 	 Integer.parseInt(textHeight.getText()),
												 	 Integer.parseInt(textMines.getText()));
				
				if (newSetting.getMineNum() < newSetting.getX() * newSetting.getY())
					Main.currentSetting = newSetting;
			} catch (NumberFormatException a) {}
		}
		Ground.initiateField(Main.currentSetting);
	}
	
}
