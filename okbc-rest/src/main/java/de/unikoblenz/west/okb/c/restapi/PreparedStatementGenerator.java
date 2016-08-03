package de.unikoblenz.west.okb.c.restapi;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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

    public static PreparedStatement getStatementByEventIdAndPropertyId(int eventId, int propertyId) throws SQLException{
        PreparedStatement stmt = connector.getConnection().prepareStatement(
                "SELECT statementid, propertyid, label, datatype\n"
                        + "FROM Statements WHERE eventid=? AND propertyid=?;"
        );
        stmt.setInt(1,eventId);
        stmt.setInt(2, propertyId);
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

    public static PreparedStatement updateRankOfClaim(int claimid, String rank) throws SQLException {
        PreparedStatement stmt = connector.getConnection().prepareStatement(
                "UPDATE Claims SET ranking=? WHERE claimid=?;"
        );
        stmt.setString(1, rank);
        stmt.setInt(2, claimid);
        return stmt;
    }

    public static PreparedStatement createEvent(int eventid, String label, Date lastedited) throws SQLException {
        PreparedStatement stmt = connector.getConnection().prepareStatement(
                "INSERT INTO Events(eventid, label, lastedited) VALUES (?,?,?);",
                Statement.RETURN_GENERATED_KEYS
        );
        stmt.setInt(1, eventid);
        stmt.setString(2, label);
        stmt.setDate(3, lastedited);
        return stmt;
    }

    public static PreparedStatement createStatement(int propertyid, String label, String datatype, int eventid) throws SQLException {
        PreparedStatement stmt = connector.getConnection().prepareStatement(
                "INSERT INTO Statements(propertyid, label, datatype, eventid) VALUES (?,?,?,?);",
                Statement.RETURN_GENERATED_KEYS
        );
        stmt.setInt(1, propertyid);
        stmt.setString(2, label);
        stmt.setString(3, datatype);
        stmt.setInt(4, eventid);
        return stmt;
    }

    public static PreparedStatement createClaim(String snaktype, String value, int statementid) throws SQLException {
        PreparedStatement stmt = connector.getConnection().prepareStatement(
                "INSERT INTO Claims(snaktype, cvalue, statementid) VALUES (?,?,?);",
                Statement.RETURN_GENERATED_KEYS
        );
        stmt.setString(1, snaktype);
        stmt.setString(2, value);
        stmt.setInt(3, statementid);
        return stmt;
    }

    public static PreparedStatement createClaim(String snaktype, String value, int userid, int statementid) throws SQLException {
        PreparedStatement stmt = connector.getConnection().prepareStatement(
                "INSERT INTO Claims(snaktype, cvalue, userid, statementid) VALUES (?,?,?,?);",
                Statement.RETURN_GENERATED_KEYS
        );
        stmt.setString(1, snaktype);
        stmt.setString(2, value);
        stmt.setInt(3, userid);
        stmt.setInt(4, statementid);
        return stmt;
    }

    public static PreparedStatement createQualifier(int propertyid, String value, int claimid) throws SQLException {
        PreparedStatement stmt = connector.getConnection().prepareStatement(
                "INSERT INTO Qualifiers(propertyid, qvalue, claimid) VALUES (?,?,?);",
                Statement.RETURN_GENERATED_KEYS
        );
        stmt.setInt(1, propertyid);
        stmt.setString(2, value);
        stmt.setInt(3, claimid);
        return stmt;
    }

    public static PreparedStatement createReference(String url, String publicationDate, String retrievalDate, double reliabilityRating, double neutralityRating, String multiClaimType, int claimId) throws SQLException {
        PreparedStatement stmt = connector.getConnection().prepareStatement(
                "INSERT INTO Refs(url, publicationdate, retrievaldate, trustrating, neutralityrating, claimid) VALUES (?,?,?,?,?,?);",
                Statement.RETURN_GENERATED_KEYS
        );
        stmt.setString(1, url);
        stmt.setDate(2, Date.valueOf(publicationDate));
        stmt.setDate(3, Date.valueOf(retrievalDate));
        stmt.setFloat(4, (float)reliabilityRating);
        stmt.setFloat(5, (float)neutralityRating);
        stmt.setInt(6, claimId);
        return stmt;
    }

    public static PreparedStatement createAuthor(String author, int referenceid) throws SQLException {
        PreparedStatement stmt = connector.getConnection().prepareStatement(
                "INSERT INTO Authors(author, refid) VALUES (?,?);",
                Statement.RETURN_GENERATED_KEYS
        );
        stmt.setString(1, author);
        stmt.setInt(2, referenceid);
        return stmt;
    }

    public static PreparedStatement createUser(String userName, double reputation) throws SQLException{
        PreparedStatement stmt = connector.getConnection().prepareStatement(
                "INSERT INTO Users(username, reputation) VALUES (?,?);",
                Statement.RETURN_GENERATED_KEYS
        );
        stmt.setString(1, userName);
        stmt.setFloat(2, (float)reputation);
        return stmt;
    }
}
