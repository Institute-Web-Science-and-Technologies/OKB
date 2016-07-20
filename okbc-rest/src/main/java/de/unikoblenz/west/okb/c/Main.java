package de.unikoblenz.west.okb.c;

import de.unikoblenz.west.okb.c.restapi.RequestRouter;

import java.sql.SQLException;

import static de.unikoblenz.west.okb.c.restapi.RequestRouter.enableCORS;


public class Main {

    public static void main(String[] args) throws SQLException {
        //MySQLConnector.getInstance();
        new RequestRouter();
        enableCORS("*","*","*");
    }
}