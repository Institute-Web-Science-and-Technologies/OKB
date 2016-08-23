package rankingProvenance.rankProv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.*;
import com.mysql.jdbc.Driver;

import App.Config;
 

/**
 * @desc A singleton database access class for MySQL
 * @author OKB-R
 */
public final class MySql {
    public Connection conn;
    private Statement statement;    
    public static MySql db;
    
    Properties prop = Config.config();
    
    public MySql() {
        String url= prop.getProperty("dburl");
        String dbName = prop.getProperty("database");
        String driver = "com.mysql.jdbc.Driver";
        String userName = prop.getProperty("dbuser");
        String password = prop.getProperty("dbpassword");
        try {
            Class.forName(driver).newInstance();
            this.conn = (Connection)DriverManager.getConnection(url+dbName,userName,password);
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }
    /**
     *
     * @return MysqlConnect Database connection object
     */
    public static synchronized MySql getDbCon() {
        if ( db == null ) {
            db = new MySql();
        }
        return db;
 
    }
    /**
     *
     * @param query String The query to be executed
     * @return a ResultSet object containing the results or null if not available
     * @throws SQLException
     */
    public ResultSet query(String query) throws SQLException{
        statement = db.conn.createStatement();
        ResultSet res = statement.executeQuery(query);
        return res;
    }
    /**
     * @desc Method to insert data to a table
     * @param insertQuery String The Insert query
     * @return boolean
     * @throws SQLException
     */
    public int insert(String insertQuery) throws SQLException {
        statement = db.conn.createStatement();
        int result = statement.executeUpdate(insertQuery);
        return result;
 
    }
    
}