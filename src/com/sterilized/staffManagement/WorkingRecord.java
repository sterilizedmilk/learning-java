package com.sterilized.staffManagement;

import java.util.ArrayList;
import java.util.Date;

public class WorkingRecord {
    private Date time;
    private Staff staff;
    private RecordType type;

    private static ArrayList<WorkingRecord> record = new ArrayList<WorkingRecord>();

    private WorkingRecord(Date time, Staff staff, RecordType type) {
        this.time = time;
        this.staff = staff;
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public String getId() {
        return staff.getId();
    }

    public String getName() {
        return staff.getName();
    }

    public RecordType getRecordType() {
        return type;
    }

    public static void addRecord(Staff staff, RecordType type) {
        record.add(new WorkingRecord(new Date(), staff, type));
    }

    public static ArrayList<WorkingRecord> browse(Date earliest, Date latest, Staff staff, RecordType type) {
        ArrayList<WorkingRecord> requested = new ArrayList<WorkingRecord>();

        for (WorkingRecord r : record) {
            if (earliest != null && r.time.before(earliest))
                break;

            if (latest != null && r.time.after(earliest))
                break;

            if (staff != null && !r.staff.equals(staff))
                continue;

            if (type != null && !type.equals(r.type))
                continue;

            requested.add(r);
        }

        return requested;
    }

    public static ArrayList<WorkingRecord> browse() {
        return browse(null, null, null, null);
    }

    static void print() {
        for (WorkingRecord w : record) {
            System.out.println(w.time.toString() + "  " + w.staff.getName() + "  " + w.type.name());
        }
        System.out.println();
    }

}
