package drusy.utils;

import java.awt.*;
import java.io.*;
import java.util.Properties;

public class Config {
    // User data
    public static String CONFIG_FOLDER = ".freeboxmonitor";
    public static String CONFIG_FILE_NAME = "config.properties";

    // App informations
    public static String APP_NAME = "Freebox v6 Monitor";
    public static String APP_ID = "fr.drusy.freeboxmonitor";
    public static float CURRENT_VERSION = 1.1f;
    public static int APP_WITH = 1100;
    public static int APP_HEIGHT = 600;
    public static int GAP = 10;

    // Charts config
    public static int CHART_HEIGHT = 135;
    public static Color DOWNLOAD_CHART_COLOR = new Color(0.30980393f, 0.70980394f, 0.8117647f);
    public static Color UPLOAD_CHART_COLOR = new Color(0.8117647f, 0.17254902f, 0.21568628f);

    // Github urls
    public static String DOWNLOAD_URL = "https://github.com/Drusy/freebox-v6-monitor/releases";
    public static String VERSION_DISTANT_FILE = "https://raw.githubusercontent.com/Drusy/freebox-v6-monitor/master/content/version";

    // Freebox urls
    public static String FREEBOX_API_VERSION_URL = "http://mafreebox.freebox.fr/api_version";
    public static String FREEBOX_URL = "http://mafreebox.freebox.fr";
    public static String FREEBOX_API_CHECK_URL = "http://mafreebox.freebox.fr/api/v3/login";
    public static String FREEBOX_API_SESSION_TOKEN = "http://mafreebox.freebox.fr/api/v3/login/session";
    public static String FREEBOX_API_CONNECTION = "http://mafreebox.freebox.fr/api/v3/connection";
    public static String FREEBOX_API_AUTHORIZE = "http://mafreebox.freebox.fr/api/v3/login/authorize";
    public static String FREEBOX_API_WIFI_ID = "http://mafreebox.freebox.fr/api/v2/wifi/ap";
    public static String FREEBOX_API_WIFI_STATIONS = "http://mafreebox.freebox.fr/api/v2/wifi/ap/{id}/stations";
    public static String FREEBOX_API_SWITCH_STATUS = "http://mafreebox.freebox.fr/api/v3/switch/status/";
    public static String FREEBOX_API_SWITCH_ID = "http://mafreebox.freebox.fr/api/v3/switch/port/{id}/stats";
    public static String FREEBOX_API_XDSL = "http://mafreebox.freebox.fr/api/v3/connection/xdsl";

    // Alerts
    public static float ALERT_MAX_RATE_PCT = 0.9f;

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
