package de.unikoblenz.west.okb.c.restapi;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

/**
 * Created by wkoop on 02.06.2016.
 */
public class MySQLConnector {
    private static MySQLConnector db;
    private Connection conn;

    private MySQLConnector() throws FileNotFoundException, IOException, SQLException{
        String url;
        String dbName;
        String driver;
        String userName;
        String password;


        //String content = new Scanner(new File(configFilePath)).useDelimiter("\\Z").next();
        FileInputStream stream = new FileInputStream(OKBRClaimProvider.DEFAULT_CONFIG_FILE_PATH);
        StringBuilder builder = new StringBuilder();
        int ch;
        while ((ch = stream.read()) != -1) {
            builder.append((char)ch);
        }
        String content = builder.toString();

        JSONObject config = new JSONObject(content);

        url = config.getString("dburl");
        dbName = config.getString("dbname");
        driver = config.getString("dbdriver");
        userName = config.getString("dbusername");
        password = config.getString("dbpassword");

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
