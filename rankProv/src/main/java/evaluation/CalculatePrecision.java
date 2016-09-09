package evaluation;

import java.sql.SQLException;

public class CalculatePrecision {
  public static double calcPrecsision () throws SQLException{

    
    return 0;
    
  }
  
  public static void main(String[] args) throws SQLException{
    EvaluationRank ev = new EvaluationRank();
    System.out.println(ev.getAllClaims());
    
    FactRanks fr = new FactRanks();
    System.out.println(fr.getAllClaims());
    
  }
}
