package com.carrieriq.DIS_Integration;

        import org.apache.log4j.Logger;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.IOException;
        import java.util.Properties;

public class PropertiesLoader {
    private static Logger LOG = Logger.getLogger(PropertiesLoader.class);

    public static final String URL;
    public static final String USER;
    public static final String PASS;
    public static final String HUB_HOST;
    public static final String IE_DRIVER_PATH;
    public static final String GECKO_DRIVER_PATH;
    public static final String CHROME_DRIVER_PATH;
    static {
        Properties properties = new Properties();
        String configFilePath = "config" + File.separator + Constants.SEDIS_CONFIG_TESTS;
        File configFile = new File(configFilePath);
        try {
            if (configFile.exists()) {
                LOG.info("Loading properties from : " + configFilePath);
                properties.load(new FileInputStream(configFile));
            }
            else {
                LOG.info("Loading properties from classpath.");
                properties.load(PropertiesLoader.class.getClassLoader().getResourceAsStream(Constants.SEDIS_CONFIG_TESTS));
               }
        } catch (IOException e) {
            LOG.info(e.getMessage(), e);
        }

        URL = properties.getProperty(Constants.URL);
        USER = properties.getProperty(Constants.USER);
        PASS = properties.getProperty(Constants.PASS);

        HUB_HOST = properties.getProperty(Constants.HUB_HOST);
        IE_DRIVER_PATH = properties.getProperty(Constants.IE_DRIVER_PATH);
        GECKO_DRIVER_PATH=properties.getProperty(Constants.GECKO_DRIVER_PATH);
        CHROME_DRIVER_PATH=properties.getProperty(Constants.CHROME_DRIVER_PATH);
    }


}

