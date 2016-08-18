package de.unikoblenz.west.okb.c.restapi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 09.08.2016.
 */
public class Reputation {

    public static final double DEFAULT_REPUTATION = 0.5;

    private static final int PREFERRED_INDEX = 0;
    private static final int NORMAL_INDEX = 1;
    private static final int DEPRECATED_INDEX = 2;
    private static final int UNRANKED_INDEX = 3;

    private static final String PREFERRED = "preferred";
    private static final String NORMAL = "normal";
    private static final String DEPRECATED = "deprecated";
    private static final String UNRANKED = "null";

    private static final double PREFERRED_WEIGHT = 1;
    private static final double NORMAL_WEIGHT = 0.75;
    private static final double DEPRECATED_WEIGHT = -1.25;
    private static final double UNRANKED_WEIGHT = 0.4;

    /*
     * SLOPE_FALL_OFF has to be higher than 1. The higher the value the slower is increase/decrease of reputation.
     */
    private static final float SLOPE_FALL_OFF = 50;

    public static void updateUserReputationForAllUsers() {
        try {
            ResultSet users = PreparedStatementGenerator.getAllUsers().executeQuery();
            List<String> usernames = new ArrayList<>();
            if (users.isBeforeFirst()) {
                while (users.next()) {
                    usernames.add(users.getNString("username"));
                }
            }
            updateUserReputation((String[])usernames.toArray());
        } catch (SQLException e) {
            // TODO: some error handling
        }
    }

    public static void updateUserReputation(String[] usernames) {
        for (String username : usernames) {
            updateUserReputation(username);
        }
    }

    public static void updateUserReputation(String username) {
        try {
            double reputation = calculateCurrentUserReputation(username);
            PreparedStatementGenerator.updateUserReputation(username, reputation).executeUpdate();
        } catch (SQLException e) {
            // TODO: do error handling
        } catch (IllegalArgumentException e) {
            // username was not valid
        }
    }

    public static double calculateCurrentUserReputation(String username) throws SQLException, IllegalArgumentException {
        int[] rankCount = getNumberOfClaimsPerRankOfUser(username);
        int sumOfRanks = rankCount[PREFERRED_INDEX] + rankCount[NORMAL_INDEX] + rankCount[DEPRECATED_INDEX] + rankCount[UNRANKED_INDEX];
        if (sumOfRanks == 0) {
            return (float)DEFAULT_REPUTATION;
        }
        double x = PREFERRED_WEIGHT*rankCount[PREFERRED_INDEX]
                + NORMAL_WEIGHT*rankCount[NORMAL_INDEX]
                + DEPRECATED_WEIGHT*rankCount[DEPRECATED_INDEX]
                + UNRANKED_WEIGHT*rankCount[UNRANKED_INDEX];
        double reputation =
                DEFAULT_REPUTATION + x/(x+SLOPE_FALL_OFF);
        if (reputation < 0)
            reputation = 0;
        return reputation;
    }

    private static int[] getNumberOfClaimsPerRankOfUser(String username) throws SQLException {
        int[] rankCount = new int[4];
        ResultSet ranks = PreparedStatementGenerator.getRanksOfClaimsByUser(username).executeQuery();
        if (ranks.isBeforeFirst()) {
            while (ranks.next()) {
                String rank = ranks.getNString("ranking");
                if (rank == null)
                    rank = UNRANKED;
                if (rank.equals(PREFERRED)) {
                    rankCount[PREFERRED_INDEX]++;
                } else if (rank.equals(NORMAL)) {
                    rankCount[NORMAL_INDEX]++;
                } else if (rank.equals(DEPRECATED)) {
                    rankCount[DEPRECATED_INDEX]++;
                } else if (rank.equals(UNRANKED)) {
                    rankCount[UNRANKED_INDEX]++;
                } else {
                    // TODO: some exception behaviour?
                }
            }
        }
        return rankCount;
    }

}
