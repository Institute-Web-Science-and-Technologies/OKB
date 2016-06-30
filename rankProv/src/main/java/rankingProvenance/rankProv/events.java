package rankingProvenance.rankProv;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class events {
  
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
   
    
    int i =  mySql.getDbCon().insert("INSERT INTO EVENTS (`eventId`,`label`, `categories`, `location`) VALUES ("+this.eventID+",'"+this.label+"','"+this.categories+"','"+this.location+"') ");
    if(i>0)
      return true;
    else
      return false;
  }
  
  public HashMap<String, String> getEvent(int id) throws SQLException
  {
    HashMap<String, String> hm = new HashMap<String, String>();
    ResultSet rs = mySql.getDbCon().query("Select * from events WHERE eventId="+id);
    for (int i = 0; i < 1; i++) {
      String columnName = rs.getMetaData().getColumnLabel(i + 1).toLowerCase();
      Object columnValue = rs.getString(1);
      // if value in DB is null, then we set it to default value
      if (columnValue == null){
          columnValue = "null";
      }
      else
      {
        hm.put(columnName, columnValue.toString());
        //System.out.println(columnName +"="+ columnValue.toString()); 
        
      }
     }
    
    return hm;
    
  }
  
}
