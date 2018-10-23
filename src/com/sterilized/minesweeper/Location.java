package com.sterilized.minesweeper;

public class Location {
    int x, y;

    // don't touch elements of array! I wish I could use const. :(
    private static final Location[] around = new Location[8];

/*-
 * 567
 * 4 0
 * 321
 */
    static {
        around[0] = new Location( 1,  0);
        around[1] = new Location( 1,  1);
        around[2] = new Location( 0,  1);
        around[3] = new Location(-1,  1);
        around[4] = new Location(-1,  0);
        around[5] = new Location(-1, -1);
        around[6] = new Location( 0, -1);
        around[7] = new Location( 1, -1);
    }

    Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location add(Location other) {
        return new Location(x + other.x, y + other.y);
    }

    public Location[] adjacent() {
        Location[] arr = new Location[around.length];

        int adj = 0;
        for (int i = 0; i < 8; ++i) {
            if (x == 0 && around[i].x == -1)
                continue;
            if (y == 0 && around[i].y == -1)
                continue;
            if (x == Ground.field[0].length - 1 && around[i].x == 1)
                continue;
            if (y == Ground.field.length - 1 && around[i].y == 1)
                continue;

            arr[adj++] = around[i];
        }

        Location[] adjacent = new Location[adj];
        for (int i = 0; i < adj; ++i) {
            adjacent[i] = arr[i].add(this);
        }
        return adjacent;
    }
}
