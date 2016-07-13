package de.unikoblenz.west.okb.c;

import de.unikoblenz.west.okb.c.Item_Handling.Request_Router;
import de.unikoblenz.west.okb.c.Item_Handling.MySQL_connector;
import java.sql.SQLException;

import static de.unikoblenz.west.okb.c.Item_Handling.Request_Router.enableCORS;


public class Main {

    public static void main(String[] args) throws SQLException {
        String id2 = "Q104567";
        String output = WikidataItemReader.itemReader(id2);

        //System.out.println(output);
        //System.out.println(e.getClaims().toString());

        MySQL_connector.getDbCon();
        new Request_Router();
        enableCORS("*","*","*");



    }
}