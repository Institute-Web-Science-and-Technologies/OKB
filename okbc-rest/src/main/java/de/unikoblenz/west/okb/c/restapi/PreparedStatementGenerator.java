package de.unikoblenz.west.okb.c.restapi;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by wkoop on 14.07.2016.
 */
public class PreparedStatementGenerator {

    private static MySQLConnector connector = MySQLConnector.getInstance();

    public static PreparedStatement getLatestEditedEvents(int limit) throws SQLException {
        PreparedStatement stmt = connector.getConnection().prepareStatement(
                "SELECT eventid, label, lastedited FROM Events ORDER BY lastedited DESC LIMIT ?;"
        );
        stmt.setInt(1, limit);
        return stmt;
    }

    public static PreparedStatement getEventById(int id) throws SQLException {
        PreparedStatement stmt = connector.getConnection().prepareStatement(
                "SELECT eventid, label, lastedited FROM Events WHERE eventid = ?;"
        );
        stmt.setInt(1,id);
        return stmt;
    }

    public static PreparedStatement getEventsByLabel(String label) throws SQLException {
        PreparedStatement stmt = connector.getConnection().prepareStatement(
            "SELECT eventid, label, lastedited FROM Events WHERE LOWER(label) LIKE LOWER(CONCAT('%', ?, '%'));"
        );
        stmt.setString(1,label);
        return stmt;
    }

    public static PreparedStatement getEventsByCategory(String category) throws SQLException {
        PreparedStatement stmt = connector.getConnection().prepareStatement(
            "SELECT eventid, label, lastedited FROM Events\n"
                + "WHERE eventid IN (SELECT DISTINCT eventid FROM Categories WHERE category = ?);"
        );
        stmt.setString(1,category);
        return stmt;
    }

    public static PreparedStatement getCategoriesByEventId(int id) throws SQLException {
        PreparedStatement stmt = connector.getConnection().prepareStatement(
                "SELECT DISTINCT category FROM Categories WHERE eventid = ?;"
        );
        stmt.setInt(1,id);
        return stmt;
    }

    public static PreparedStatement getStatementsByEventId(int id) throws SQLException {
        PreparedStatement stmt = connector.getConnection().prepareStatement(
                "SELECT statementid, propertyid, label, datatype\n"
                        + "FROM Statements WHERE eventid=?;"
        );
        stmt.setInt(1,id);
        return stmt;
    }

    public static PreparedStatement getClaimsByStatementId(int id) throws SQLException {
        PreparedStatement stmt = connector.getConnection().prepareStatement(
                "SELECT claimid, snaktype, cvalue, userid, ranking\n"
                        + "FROM Claims WHERE statementid=?;"
        );
        stmt.setInt(1,id);
        return stmt;
    }

    public static PreparedStatement getClaimsByEventId(int id) throws SQLException {
        PreparedStatement stmt = connector.getConnection().prepareStatement(
                "SELECT C.claimid, C.snaktype, C.cvalue, C.ranking, C.statementid, S.eventid, S.propertyid, C.userid\n"
                        + "FROM Claims as C, Statements as S\n"
                        + "WHERE C.statementid = S.statementid AND S.eventid = ?;"
        );
        stmt.setInt(1,id);
        return stmt;
    }

    public static PreparedStatement getQualifiersByClaimId(int id) throws SQLException {
        PreparedStatement stmt = connector.getConnection().prepareStatement(
                "SELECT qualifierid, datatype, qvalue FROM Qualifiers WHERE claimid = ?;"
        );
        stmt.setInt(1,id);
        return stmt;
    }

    public static PreparedStatement getReferencesByClaimId(int id) throws SQLException {
        PreparedStatement stmt = connector.getConnection().prepareStatement(
                "SELECT refid, url, title, publicationdate, retrievaldate, trustrating, neutralityrating\n"
                        + "FROM Refs WHERE claimid = ?;"
        );
        stmt.setInt(1,id);
        return stmt;
    }

    public static PreparedStatement getUserById(int id) throws SQLException {
        PreparedStatement stmt = connector.getConnection().prepareStatement(
                "SELECT userid, username, reputation FROM Users WHERE userid = ?;"
        );
        stmt.setInt(1,id);
        return stmt;
    }

    public static PreparedStatement getUserByName(String name) throws SQLException {
        PreparedStatement stmt = connector.getConnection().prepareStatement(
                "SELECT userid, username, reputation FROM Users WHERE username = ?;"
        );
        stmt.setString(1,name);
        return stmt;
    }
}
