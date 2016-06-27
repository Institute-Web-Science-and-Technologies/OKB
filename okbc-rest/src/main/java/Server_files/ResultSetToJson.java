package Server_files;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;

/**
 * Created by wkoop on 27.06.2016.
 */
public class ResultSetToJson {

    public static String ResultSetoutput(ResultSet rs) {
        String ret="";
        int idx=0;
        try{
            while(rs.next()) {
                ret+=++idx + ": {";
                for(int i = 1; i<rs.getMetaData().getColumnCount(); i++){
                    String name = rs.getMetaData().getColumnLabel(i).toLowerCase();
                    Object value = rs.getObject(i);
                    if(value == null || value.toString()=="") value = "null";
                    ret += name+value.toString();
                    if (i+1<rs.getMetaData().getColumnCount())
                        ret+=", ";
                }
                ret += "}\n";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }


    public static String convertResultSetIntoJSON(ResultSet resultSet) throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i = 0; i < total_rows; i++) {
                String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
                Object columnValue = resultSet.getObject(i + 1);
                // if value in DB is null, then we set it to default value
                if (columnValue == null){
                    columnValue = "null";
                }
                /*
                Next if block is a hack. In case when in db we have values like price and price1 there's a bug in jdbc -
                both this names are getting stored as price in ResulSet. Therefore when we store second column value,
                we overwrite original value of price. To avoid that, i simply add 1 to be consistent with DB.
                 */
                if (obj.has(columnName)){
                    columnName += "1";
                }
                obj.put(columnName, columnValue);
            }
            jsonArray.put(obj);
        }
        return jsonArray.toString();
    }
}
