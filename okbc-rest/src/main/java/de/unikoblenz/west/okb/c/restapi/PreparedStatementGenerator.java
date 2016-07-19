package de.unikoblenz.west.okb.c.restapi;

import java.sql.PreparedStatement;

/**
 * Created by wkoop on 14.07.2016.
 */
public class PreparedStatementGenerator {

    public static PreparedStatement preparedStatementgetEvents () throws java.sql.SQLException{

        PreparedStatement stmt = MySQLConnector.db.conn.prepareStatement(

                MySQL.getSqlgetrequest() +
                        "GROUP BY events.eventid \n" +
                        "ORDER BY events.eventid ASC;"
        );
        return stmt;
    }

    public static PreparedStatement getEventById(String id) throws java.sql.SQLException{

        PreparedStatement stmt = MySQLConnector.db.conn.prepareStatement(
                MySQL.getSqlgetrequest() +
                        "WHERE events.eventid = ? \n" +
                        "GROUP BY events.eventid \n" +
                        "ORDER BY events.eventid ASC;"
        );
            stmt.setString(1,id);
            return stmt;
    }



    public static PreparedStatement getEventsByLabel(String label) throws java.sql.SQLException{

        PreparedStatement stmt = MySQLConnector.db.conn.prepareStatement(
                MySQL.getSqlgetrequest() +
                        "WHERE events.label = ?" +
                        "GROUP BY events.eventid \n" +
                        "ORDER BY events.eventid ASC;"
        );
        stmt.setString(1,label);
        return stmt;
    }

    public static PreparedStatement getEventsByCategory(String category) throws java.sql.SQLException{

        PreparedStatement stmt = MySQLConnector.db.conn.prepareStatement(
                MySQL.getSqlgetrequest() +
                        "WHERE categories.category = ?" +
                        "GROUP BY events.eventid \n" +
                        "ORDER BY events.eventid ASC;"
        );
        stmt.setString(1,category);
        return stmt;
    }

    public static PreparedStatement getLatestEditedEvents() throws java.sql.SQLException{

        PreparedStatement stmt = MySQLConnector.db.conn.prepareStatement(
                MySQL.getSqlgetrequest() +
                        "GROUP BY events.eventid \n" +
                        "ORDER BY events.eventid DESC\n" +
                        "LIMIT 10;"
        );
        return stmt;
    }

    public static PreparedStatement addReference(String refid, String url, String title, String publicationdate, String retrievaldate, String authors, String articletype
            , String trustrating, String neutralityrating, String claimid)throws java.sql.SQLException{

        PreparedStatement stmt = MySQLConnector.db.conn.prepareStatement(
                "INSERT INTO OKBCDB.reference(refid, url, title, publicationdate, retrievaldate, " +
                        "authors, articletype, trustrating, neutralityrating, claimid)\n" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
        );
        stmt.setString(1,refid );
        stmt.setString(2, url);
        stmt.setString(3, title);
        stmt.setString(4, publicationdate);
        stmt.setString(5, retrievaldate);
        stmt.setString(6, authors);
        stmt.setString(7, articletype);
        stmt.setString(8, trustrating);
        stmt.setString(9, neutralityrating);
        stmt.setString(10, claimid);

        return stmt;



    }

    public static PreparedStatement addQualifier(String propertyid, String label,
                                                 String datatype, String qvalue)throws java.sql.SQLException{

        PreparedStatement stmt = MySQLConnector.db.conn.prepareStatement(
                "INSERT INTO OKBCDB.Qualifier(propertyId, label,  datatype, qvalue\n)" +
                        "VALUES (?,?,?,?);"
        );
        stmt.setString(1, propertyid );
        stmt.setString(2, label);
        stmt.setString(3, datatype);
        stmt.setString(4, qvalue);

        return stmt;
    }

    public static PreparedStatement addClaim(String clid, String clvalue, String snaktype, String userid,
                                             String ranking, String refid, String qualifierid)throws java.sql.SQLException{

        PreparedStatement stmt = MySQLConnector.db.conn.prepareStatement(
                "INSERT INTO OKBCDB.Claim (clid, clvalue, snaktype, userid, ranking, refid, qualifierid\n)" +
                        "VALUES (?,?,?,?,?,?,?);"
        );
        stmt.setString(1, clid);
        stmt.setString(2, clvalue);
        stmt.setString(3, snaktype);
        stmt.setString(4,userid );
        stmt.setString(5, ranking);
        stmt.setString(6, refid);
        stmt.setString(7, qualifierid);

        return stmt;
    }

    public static PreparedStatement addCategory(String ctid, String category)throws java.sql.SQLException {

        PreparedStatement stmt = MySQLConnector.db.conn.prepareStatement(
                "INSERT INTO OKBCDB.Qualifier(ctid, category\n)" +
                        "VALUES (?,?);"
        );
        stmt.setString(1, ctid);
        stmt.setString(2, category);

        return stmt;

    }

    public static PreparedStatement addOkbStatement(String propertyid, String label, String datatype, String ctid,
                                                    String claimid)throws java.sql.SQLException{

        PreparedStatement stmt = MySQLConnector.db.conn.prepareStatement(
                "INSERT INTO OKBCDB.okbstatement(propertyid, label, datatype, ctid, claimid\n)" +
                        "VALUES (?,?,?,?,?);"
        );
        stmt.setString(1, propertyid);
        stmt.setString(2, label);
        stmt.setString(3, datatype);
        stmt.setString(4, ctid );
        stmt.setString(5, claimid);

        return stmt;
    }


    public static PreparedStatement addEvent(String eventid, String label, String location)throws java.sql.SQLException{

        PreparedStatement stmt = MySQLConnector.db.conn.prepareStatement(
                "INSERT INTO OKBCDB.events(eventid, label, location\n)" +
                        "VALUES (?,?,?,?,?);"
        );
        stmt.setString(1, eventid);
        stmt.setString(2, label);
        stmt.setString(3, location);

        return stmt;
    }


}
