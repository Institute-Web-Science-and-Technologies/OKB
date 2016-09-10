package App;

import java.io.File;
/**
 * Load config file
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Config {
  /**
   * Properies are loaded and return
   * @return property object
   */
  public static Properties config() {

    Properties prop = new Properties();
    InputStream input = null;

    try {
    	File configDir = new File(System.getProperty("catalina.base"), "conf");
    	File configFile = new File(configDir, "config.properties");
    	input = new FileInputStream(configFile);
    	//input = Config.class.getClass().getResourceAsStream("/config.properties");
        input.available();
    	// load a properties file
        prop.load(input);
        return prop;
    } catch (Exception ex) {
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