package com.sterilized.staffManagement;

import java.util.ArrayList;
import java.util.HashMap;

public class Staff {
    private final String id;
    private String password;
    private String name;
    private Rank rank;

    protected static HashMap<String, Staff> staffList = new HashMap<String, Staff>();

    protected Staff(String id, String password, String name, Rank rank) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.rank = rank;

        staffList.put(id, this);
    }

    public String getId() {
        return id;
    }

    public static boolean isValidId(String newId) {
        if (staffList.containsKey(newId) || newId.indexOf(' ') != -1 || newId.isEmpty())
            return false;
        return true;
    }

    public boolean isPassword(String password) {
        if (password.equals(this.password))
            return true;
        return false;
    }

    public static boolean isValidPassword(String password) {
        if (password.length() < 6)
            return false;
        return true;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public static boolean addStaff(String id, String password, String name, Rank rank) {
        if (!isValidId(id) || !isValidPassword(password))
            return false;

        if (rank == Rank.CEO) {
            Chief.addChief(id, password, name);
            return true;
        }

        staffList.put(id, new Staff(id, password, name, rank));
        return true;
    }

    public static Staff getStaff(String id, String password) {
        Staff temp = staffList.get(id);

        if (temp != null && temp.isPassword(password))
            return temp;

        return null;
    }

    public boolean removeStaff(String password) {
        if (isValidPassword(password))
            return false;

        staffList.remove(id);
        return true;
    }

    public ArrayList<Staff> getStaffList() {
        ArrayList<Staff> list = new ArrayList<Staff>();
        list.add(this);
        return list;
    }

}
