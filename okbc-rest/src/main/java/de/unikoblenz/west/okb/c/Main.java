package de.unikoblenz.west.okb.c;

import de.unikoblenz.west.okb.c.Item_Handling.MySQLconnector;
import de.unikoblenz.west.okb.c.Item_Handling.RequestRouter;

import java.sql.SQLException;

import static de.unikoblenz.west.okb.c.Item_Handling.RequestRouter.enableCORS;


public class Main {

    public static void main(String[] args) throws SQLException {
        String id2 = "Q104567";
        String output = WikidataItemReader.itemReader(id2);

        //System.out.println(output);
        //System.out.println(e.getClaims().toString());

        MySQLconnector.getDbCon();
        new RequestRouter();
        enableCORS("*","*","*");



    }
}