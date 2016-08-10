package provenance.data;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Events {
  
  Integer eventID;
  String label;
  String categories;
  String location;
  
  public Integer getEventID() {
    return eventID;
  }
  public void setEventID(Integer eventID) {
    this.eventID = eventID;
  }
  public String getLabel() {
    return label;
  }
  public void setLabel(String label) {
    this.label = label;
  }
  public String getCategories() {
    return categories;
  }
  public void setCategories(String categories) {
    this.categories = categories;
  }
  public String getLocation() {
    return location;
  }
  public void setLocation(String location) {
    this.location = location;
  }

  
  public boolean save() throws SQLException
  {
   
    
    int i =  MySql.getDbCon().insert("INSERT INTO EVENTS (`eventId`,`label`, `categories`, `location`) VALUES ("+this.eventID+",'"+this.label+"','"+this.categories+"','"+this.location+"') ");
    if(i>0)
      return true;
    else
      return false;
  }
  
  public List<Map<String,String>> getEvent(int id) throws SQLException
  {
    HashMap<String, String> hm = new HashMap<String, String>();
    ResultSet rs = MySql.getDbCon().query("Select * from events WHERE eventId="+id);
   
    ResultSetMetaData rsmd = rs.getMetaData();
    List<String> columns = new ArrayList<String>(rsmd.getColumnCount());
    for(int i = 1; i <= rsmd.getColumnCount(); i++){
        columns.add(rsmd.getColumnName(i));
    }
    List<Map<String,String>> data = new ArrayList<Map<String,String>>();
    while(rs.next()){                
        Map<String,String> row = new HashMap<String, String>(columns.size());
        for(String col : columns) {
            row.put(col, rs.getString(col));
        }
        data.add(row);
    }
    return data;
    
  }
  
}
