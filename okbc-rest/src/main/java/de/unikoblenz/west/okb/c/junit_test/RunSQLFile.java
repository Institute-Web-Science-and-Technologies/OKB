package de.unikoblenz.west.okb.c.junit_test;

import de.unikoblenz.west.okb.c.restapi.MySQLConnector;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by wkoop on 10.08.16.
 */

public class RunSQLFile {

    private static String droptable = "sql_setup/00 drop_table.sql";
    private static String createtable="sql_setup/01 create_table.sql";
    private static String insertinto="sql_setup/02 Insert_into.sql";

    public static void RunScript(String sqlpath){
        Connection con = MySQLConnector.getInstance().getConnection();
        Statement stmt = null;
        try {
            ScriptRunner sr = new ScriptRunner(con);
            sr.setSendFullScript(false);
            Reader reader = new BufferedReader(new FileReader(sqlpath));
            sr.runScript(reader);
        } catch (IOException e) {e.printStackTrace();}
    }

    public static void resetTable(){

        RunScript(droptable);
        RunScript(createtable);
        RunScript(insertinto);

    }
}
