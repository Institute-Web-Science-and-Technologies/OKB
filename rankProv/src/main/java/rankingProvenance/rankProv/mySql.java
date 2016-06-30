package rankingProvenance.rankProv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import com.mysql.jdbc.Driver;
//import org.json.JSONArray;  
//import org.json.JSONObject;  

/**
 * @desc A singleton database access class for MySQL
 * @author Ramindu
 */
public final class mySql {
    public Connection conn;
    private Statement statement;
    public static mySql db;
    public mySql() {
        String url= "jdbc:mysql://localhost:3306/";
        String dbName = "okbr";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "";
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
    public static synchronized mySql getDbCon() {
        if ( db == null ) {
            db = new mySql();
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
 
    /**
     * 
     * @param resultSet
     * @return Json String
     * @throws Exception
     */
//    public static String convertResultSetIntoJSON(ResultSet resultSet) throws Exception {
//        JSONArray jsonArray = new JSONArray();
//        while (resultSet.next()) {
//            int total_rows = resultSet.getMetaData().getColumnCount();
//            JSONObject obj = new JSONObject();
//            for (int i = 0; i < total_rows; i++) {
//                String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
//                Object columnValue = resultSet.getObject(i + 1);
//                // if value in DB is null, then we set it to default value
//                if (columnValue == null){
//                    columnValue = "null";
//                }
//                /*
//                Next if block is a hack. In case when in db we have values like price and price1 there's a bug in jdbc - 
//                both this names are getting stored as price in ResulSet. Therefore when we store second column value,
//                we overwrite original value of price. To avoid that, i simply add 1 to be consistent with DB.
//                 */
//                if (obj.has(columnName)){
//                    columnName += "1";
//                }
//                obj.put(columnName, columnValue);
//            }
//            jsonArray.put(obj);
//        }
//        return jsonArray.toString();
//    }
    
}