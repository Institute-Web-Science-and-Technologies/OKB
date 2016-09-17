package de.unikoblenz.west.okb.c.restapi;

import java.io.IOException;
import java.sql.*;

/**
 * Created by wkoop on 02.06.2016.
 */
public class MySQLConnector {
    private static MySQLConnector db;
    private Connection conn;

    private MySQLConnector() throws IOException, SQLException{
        Configuration inst = Configuration.getInstance();

        String url = inst.getDbUrl();
        String dbName = inst.getDbName();
        String driver = inst.getDbDriver();
        String userName = inst.getDbUsername();
        String password = inst.getDbPassword();

        try {
            Class.forName(driver).newInstance();
            this.conn = (Connection) DriverManager.getConnection(url + dbName, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized MySQLConnector getInstance() {
        if (db == null)
            try {
                db = new MySQLConnector();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return db;
    }

    public Connection getConnection() {
        return this.conn;
    }

    public ResultSet query(String query) throws SQLException {
        Statement statement = db.conn.createStatement();
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
        Statement statement = db.conn.createStatement();
        int result = statement.executeUpdate(insertQuery);
        return result;
    }
    
}
