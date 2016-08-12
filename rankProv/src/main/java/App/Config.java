package App;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
  public static Properties config() {

    Properties prop = new Properties();
    InputStream input = null;

    try {

        input = new FileInputStream("config.properties");

        // load a properties file
        prop.load(input);

        // get the property value and print it out
       /// System.out.println(prop.getProperty("database"));
       // System.out.println(prop.getProperty("dbuser"));
       // System.out.println(prop.getProperty("dbpassword"));
        return prop;
    } catch (IOException ex) {
        ex.printStackTrace();
    } finally {
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    return prop;

  }
}