package algos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class algorithms {

  
  public static void main( String[] args ) throws SQLException{
  maxAlgo mA = new maxAlgo();
  ArrayList<Map<Integer, Integer>>  res= mA.rankMax(2);
  System.out.println(res);
  }
}
