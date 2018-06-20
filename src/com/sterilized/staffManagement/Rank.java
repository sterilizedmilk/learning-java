package com.sterilized.staffManagement;

public enum Rank {
    RANK_AND_FILE,
    ASSISTANCE,
    TEAM_LEADER,
    MANAGER,
    DIRECTOR,
    CEO;

    public static boolean isValidRank(String rank) { // useless?
        for (Rank r : Rank.values()) {
            if (rank.equals(r.name()))
                return true;
        }
        return false;
    }

    public static Rank stringToRank(String rank) {
        for (Rank r : Rank.values()) {
            if (rank.equals(r.name()))
                return r;
        }
        return null;
    }
}
