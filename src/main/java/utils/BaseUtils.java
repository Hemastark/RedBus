package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.util.Properties;

public class BaseUtils {

    private static final Logger log = LogManager.getLogger(BaseUtils.class);

    public static String readProperties(String input) {
        try {
            Properties property = new Properties();
            FileInputStream inputFile = new FileInputStream("src/test/resources/config/Redbus.properties");
            property.load(inputFile);
            return property.getProperty(input);
        } catch (Exception e) {
            log.error("Unable to fetch the value " + input);
            return null;
        }
    }

}
