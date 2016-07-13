package de.unikoblenz.west.okb.c.Item_Handling;

import java.sql.*;

/**
 * Created by wkoop on 02.06.2016.
 */
public class MySQL_connector {
    public static MySQL_connector db;
    public Connection conn;
    private Statement statement;

    public MySQL_connector() {
        String url = "jdbc:mysql://mysqlhost.uni-koblenz.de:3306/";
        String dbName = "OKBCDB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "cstein1";
        String password = "95_curati_R/O_78";

        try {
            Class.forName(driver).newInstance();
            this.conn = (Connection) DriverManager.getConnection(url + dbName, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized MySQL_connector getDbCon() {
        if (db == null) {
            db = new MySQL_connector();
        }
        return db;
    }

    public ResultSet query(String query) throws SQLException {
        statement = db.conn.createStatement();
        ResultSet res = statement.executeQuery(query);
        return res;
    }

    /**
     * @param insertQuery String The Insert query
     * @return boolean
     * @throws SQLException
     * @desc Method to insert data to a table
     */
    public int insert(String insertQuery) throws SQLException {
        statement = db.conn.createStatement();
        int result = statement.executeUpdate(insertQuery);
        return result;

    }
    
}
