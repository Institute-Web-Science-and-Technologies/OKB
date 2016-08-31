package de.unikoblenz.west.okb.c.restapi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alex on 09.08.2016.
 */
public class Reputation {

    public static final double DEFAULT_REPUTATION = 0;

    private static final String PREFERRED = "preferred";
    private static final String NORMAL = "normal";
    private static final String DEPRECATED = "deprecated";
    private static final String UNRANKED = "null";

    private static final double PREFERRED_WEIGHT = 1.5;
    private static final double NORMAL_WEIGHT = 1;
    private static final double DEPRECATED_WEIGHT = 0;
    private static final double UNRANKED_WEIGHT = 0.5;

    private static final double CONFLICT_MODIFIER = 1.5;

    /*
     * SLOPE_FALL_OFF has to be higher than 1. The higher the value the slower is increase/decrease of reputation.
     */
    private static final float SLOPE_FALL_OFF = 25;

    /*
 * Simple data capsule for storing relevant data about claims.
 */
    private static class ClaimRanking {
        private String ranking;
        private boolean conflicted;

        ClaimRanking(String ranking, boolean conflicted) {
            this.ranking = ranking;
            this.conflicted = conflicted;
        }

        double getWeight() {
            if (ranking.equals(PREFERRED)) {
                return PREFERRED_WEIGHT;
            } else if (ranking.equals(NORMAL)) {
                return NORMAL_WEIGHT;
            } else if (ranking.equals(DEPRECATED)) {
                return DEPRECATED_WEIGHT;
            } else {
                return UNRANKED_WEIGHT;
            }
        }

        int isConflicted() {
            return conflicted ? 1 : 0;
        }
    }

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
        List<ClaimRanking> claims = getClaimsByUser(username);

        if (claims.isEmpty()) {
            return (float)DEFAULT_REPUTATION;
        }
        double sum = 0;
        for (ClaimRanking claim : claims) {
            sum += Math.pow(CONFLICT_MODIFIER, claim.isConflicted()) * claim.getWeight();
        }
        double reputation =
                DEFAULT_REPUTATION + sum/(sum+SLOPE_FALL_OFF);
        return reputation;
    }

    private static List<ClaimRanking> getClaimsByUser(String username) throws SQLException {
        List<ClaimRanking> result = new LinkedList<>();
        ResultSet claims = PreparedStatementGenerator.getClaimRankingDataByUser(username).executeQuery();
        if (claims.isBeforeFirst()) {
            while (claims.next()) {
                String rank = claims.getNString("ranking");
                if (rank == null) rank = UNRANKED;
                boolean conflict = claims.getNString("multiclaimtype").trim().equals("");
                result.add(new ClaimRanking(rank, conflict));
            }
        }
        return result;
    }
}
