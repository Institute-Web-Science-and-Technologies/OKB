package App;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DataTypeCheck {

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
