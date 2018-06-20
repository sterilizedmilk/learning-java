package com.sterilized.staffManagement;

import java.util.ArrayList;

public class Chief extends Staff {

    protected Chief(String id, String password, String name) {
        super(id, password, name, Rank.CEO);
    }

    static void addChief(String id, String password, String name) {
        staffList.put(id, new Chief(id, password, name));
    }

    @Override
    public ArrayList<Staff> getStaffList() {
        return new ArrayList<Staff>(staffList.values());
    }

}
