package com.pitchbook.bootcamp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileReader {

    public static String readPropertyValueByKey(String fileName, String key)  {
        Properties propertyFile = getPropertyFileByName(fileName);
        if (!key.isBlank() && propertyFile.getProperty(key) != null) {
            return propertyFile.getProperty(key);
        } else {
            throw new IllegalArgumentException("Value with key: '" + key + "' does not exist!");
        }
    }

    private static Properties getPropertyFileByName(String fileName) {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(fileName)) {
            final Properties properties = new Properties();
            if (inputStream != null) {
                properties.load(inputStream);
                return properties;
            }
            throw new IllegalArgumentException("Sorry, can not get file with name: " + fileName);
        } catch (IOException e) {
            throw new RuntimeException("Sorry, can not load resources...");
        }
    }

}
