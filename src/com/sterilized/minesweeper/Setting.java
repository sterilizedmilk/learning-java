package com.sterilized.minesweeper;

public class Setting {
	private String name;
	private int x;
	private int y;
	private int mineNum;

	static final Setting BEGINNER;
	static final Setting INTERMEDIATE;
	static final Setting ADVANCED;

	static {
		BEGINNER =		new Setting("Beginner", 	 9,  9, 10);
		INTERMEDIATE =	new Setting("Intermediate", 16, 16, 40);
		ADVANCED =		new Setting("Advanced", 	30, 16, 99);
	}

	Setting(String name, int maxX, int maxY, int mineNum) {
		this.name = name;
		this.x = maxX;
		this.y = maxY;
		this.mineNum = mineNum;
	}
	
	public String getName() {
		return name;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getMineNum() {
		return mineNum;
	}
}
