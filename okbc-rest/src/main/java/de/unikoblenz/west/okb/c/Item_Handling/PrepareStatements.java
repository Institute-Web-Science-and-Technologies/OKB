package de.unikoblenz.west.okb.c.Item_Handling;

import java.sql.PreparedStatement;
import java.util.Set;

/**
 * Created by wkoop on 14.07.2016.
 */
public class PrepareStatements {

    public static PreparedStatement preparedStatementgetEvents () throws java.sql.SQLException{

        PreparedStatement stmt = MySQLconnector.db.conn.prepareStatement(
                MySQL.getSqlgetrequest() +
                        "GROUP BY events.eventid \n" +
                        "ORDER BY events.eventid ASC;"
        );
        return stmt;
    }

    public static PreparedStatement preparedStatementgetEventById (String id) throws java.sql.SQLException{

        PreparedStatement stmt = MySQLconnector.db.conn.prepareStatement(
                MySQL.getSqlgetrequest() +
                        "WHERE events.eventid = ? \n" +
                        "GROUP BY events.eventid \n" +
                        "ORDER BY events.eventid ASC;"
        );
            stmt.setString(1,id);
            return stmt;
    }



    public static PreparedStatement preparedStatementgetEventsByLabel (String label) throws java.sql.SQLException{

        PreparedStatement stmt = MySQLconnector.db.conn.prepareStatement(
                MySQL.getSqlgetrequest() +
                        "WHERE events.label = ?" +
                        "GROUP BY events.eventid \n" +
                        "ORDER BY events.eventid ASC;"
        );
        stmt.setString(1,label);
        return stmt;
    }

    public static PreparedStatement preparedStatementgetEventsByCategory (String category) throws java.sql.SQLException{

        PreparedStatement stmt = MySQLconnector.db.conn.prepareStatement(
                MySQL.getSqlgetrequest() +
                        "WHERE categories.category = ?" +
                        "GROUP BY events.eventid \n" +
                        "ORDER BY events.eventid ASC;"
        );
        stmt.setString(1,category);
        return stmt;
    }

    public static PreparedStatement preparedStatementgetLatestEditedEvents () throws java.sql.SQLException{

        PreparedStatement stmt = MySQLconnector.db.conn.prepareStatement(
                MySQL.getSqlgetrequest() +
                        "GROUP BY events.eventid \n" +
                        "ORDER BY events.eventid DESC\n" +
                        "LIMIT 10;"
        );
        return stmt;
    }
/*
    public static PreparedStatement prepareStatementaddReference(Set<String> requests){
        String refid="", url = "",title = "", publicationdate = "",
                retrievaldate = "", authors = "", articletype = "",
                trustrating = "", neutralityrating = "", claimid = "";
        for(String i : requests){
            if(i==refid)
                refid=requests(i);
        }



    }
    */

}
