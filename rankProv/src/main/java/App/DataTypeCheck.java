package App;

import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 * Check data type for numeric, date or string values
 * @author Mujji
 *
 */
public class DataTypeCheck {
/**
 * Returns date, integer, double or string. 
 * @param s
 * @return
 */
  public static String checkDataType (String s){
    
      try{
        double d= Double.valueOf(s);
        if (d==(int)d){
            return "int" ;
        }else{
            return "double";
        }
      }catch(Exception e){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
          dateFormat.parse(s.trim());
        } catch (ParseException pe) {
          return "String";
        }
        return "date";
      }
  }
}
