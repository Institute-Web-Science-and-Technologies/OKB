package de.unikoblenz.west.okb.c;

import Server_files.ClaimController;
import Server_files.mySQL;
import WikiDataItem.Entities;
import com.mysql.jdbc.Driver;

import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws SQLException {
        String id2 = "Q104567";
        String output = WikidataItemReader.itemReader(id2);
        //System.out.println(output);
        /*
        Entities e = new Entities();
        try {
            e = WikidataJsonParser.parser(output);
        } catch (Exception exc){
            exc.printStackTrace();
        }
        */
        //System.out.println(e.getClaims().toString());
        mySQL.getDbCon();
        new ClaimController();



    }
}