package drusy.utils;

import java.io.*;
import java.util.Properties;

public class Config {
    // User data
    public static String CONFIG_FOLDER = ".freeboxmonitor";
    public static String CONFIG_FILE_NAME = "config.properties";

    // App informations
    public static String APP_NAME = "Freebox v6 Monitor";
    public static String APP_ID = "fr.drusy.freeboxmonitor";
    public static float CURRENT_VERSION = 1.0f;
    public static int APP_WITH = 1100;
    public static int APP_HEIGHT = 600;

    // Github urls
    public static String DOWNLOAD_URL = "https://github.com/Drusy/freebox-v6-monitor/releases";
    public static String VERSION_DISTANT_FILE = "https://raw.githubusercontent.com/Drusy/freebox-v6-monitor/master/content/version";

    // Freebox urls
    public static String FREEBOX_API_VERSION_URL = "http://mafreebox.freebox.fr/api_version";
    public static String FREEBOX_API_CHECK_URL = "http://mafreebox.freebox.fr/api/v3/login";
    public static String FREEBOX_API_SESSION_TOKEN = "http://mafreebox.freebox.fr/api/v3/login/session";
    public static String FREEBOX_API_CONNECTION = "http://mafreebox.freebox.fr/api/v3/connection";
    public static String FREEBOX_API_AUTHORIZE = "http://mafreebox.freebox.fr/api/v3/login/authorize";
    public static String FREEBOX_URL = "http://mafreebox.freebox.fr";

    private static void CreateConfigFile() {
        String homePath = System.getProperty("user.home");
        File configFile = new File(homePath + "/" + CONFIG_FOLDER + "/" + "config.properties");

        configFile.getParentFile().mkdirs();
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void ClearConfigFile() {
        String homePath = System.getProperty("user.home");
        File configFile = new File(homePath + "/" + CONFIG_FOLDER + "/" + "config.properties");

        configFile.delete();
        CreateConfigFile();
    }

    public static void WriteProperties(Properties properties) {
        String homePath = System.getProperty("user.home");
        File configFile = new File(homePath + "/" + CONFIG_FOLDER + "/" + "config.properties");
        OutputStream output = null;

        CreateConfigFile();

        try {
            output = new FileOutputStream(configFile);
            properties.store(output, null);
        } catch (Exception e) {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static Properties ReadProperties() {
        Properties properties = new Properties();
        String homePath = System.getProperty("user.home");
        File configFile = new File(homePath + "/" + CONFIG_FOLDER + "/" + "config.properties");
        InputStream input = null;

        CreateConfigFile();

        try {
            input = new FileInputStream(configFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}
