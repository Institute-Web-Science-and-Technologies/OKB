package App;

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

        input = new FileInputStream("config.properties");

        // load a properties file
        prop.load(input);

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