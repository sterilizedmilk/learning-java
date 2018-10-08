package com.sterilized.puyopuyo;

/*-
 * y
 * |
 * 12
 * 11
 * 10
 * 9
 * 8
 * 7
 * 6
 * 5
 * 4
 * 3
 * 2
 * 1
 * 0
 *   012345 -x
 */
public class Location {
    public int x;
    public int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location(Location loc) {
        this.x = loc.x;
        this.y = loc.y;
    }

    public boolean isAdjacent(Location other) {
        if (Math.abs(x - other.x) == 1 && Math.abs(y - other.y) == 1)
            return true;
        return false;
    }
    
    /**
     * this += other
     */
    public void add(Location other) {
        x += other.x;
        y += other.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
