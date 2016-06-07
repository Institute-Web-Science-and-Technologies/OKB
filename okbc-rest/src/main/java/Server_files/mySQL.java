package Server_files;

import java.sql.*;
import com.mysql.jdbc.Driver;

/**
 * Created by wkoop on 02.06.2016.
 */
public class mySQL {
    public static mySQL db;
    public Connection conn;
    private Statement statement;

    public mySQL() {
        String url = "jdbc:mysql://mysqlhost.uni-koblenz.de:3305/";
        String dbName = "OKBCDB";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "wkoop";
        String password = "cu&qG7gq8d-3jPVa";
        try {
            Class.forName(driver).newInstance();
            this.conn = (Connection) DriverManager.getConnection(url + dbName, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized mySQL getDbCon() {
        if (db == null) {
            db = new mySQL();
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
