package com.sterilized.minesweeper;

import java.util.Random;

/**
 * Substitution of Random class Singlton?
 */
public final class Util {
    private static Random rand;
    private static int seed;

    static {
        Random r = new Random();
        seed = r.nextInt();
        rand = new Random(seed);
    }

    private Util() {
    };

    public static void reset() {
        rand = new Random(seed);
    }

    public static int getSeed() {
        return seed;
    }

    // [0, bound)
    public static int randInt(int bound) {
        return rand.nextInt(bound);
    }

    // [start, end)
    public static int randInt(int start, int end) {
        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }

        return rand.nextInt(end - start) + start;
    }

    // [0, 1)
    public static double randDouble() {
        return rand.nextDouble();
    }

    // [start, end)
    public static double randDouble(double start, double end) {
        if (start > end) {
            double temp = start;
            start = end;
            end = temp;
        }

        return rand.nextDouble() * (end - start) + start;
    }

    public static boolean randBool() {
        return rand.nextBoolean();
    }
}
