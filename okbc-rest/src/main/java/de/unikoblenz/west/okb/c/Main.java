package de.unikoblenz.west.okb.c;

import Server_files.ClaimController;
import Server_files.mySQL;
import java.sql.SQLException;

import static Server_files.ClaimController.enableCORS;


public class Main {

    public static void main(String[] args) throws SQLException {
        String id2 = "Q104567";
        String output = WikidataItemReader.itemReader(id2);

        //System.out.println(output);
        //System.out.println(e.getClaims().toString());

        mySQL.getDbCon();
        new ClaimController();
        enableCORS("*","*","*");



    }
}