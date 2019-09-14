package com.pitchbook.pbinstensive.task1;

import com.pitchbook.pbinstensive.task1.exception.PropertyDoesNotExistException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class PropertyReader {

    public static String readPropertyValueByKey(String fileName, String key) throws PropertyDoesNotExistException {
        Properties propertyFile = getPropertyFileByName(fileName);
        if (!key.isBlank() && propertyFile.getProperty(key) != null) {
            return propertyFile.getProperty(key);
        } else {
            throw new PropertyDoesNotExistException("Value with key: '" + key + "' does not exist!");
        }
    }

    public static String readPropertyKeyByValue(String fileName, String value) throws PropertyDoesNotExistException {
        Properties propertyFile = getPropertyFileByName(fileName);
        for (Map.Entry<Object, Object> entry : propertyFile.entrySet()) {
            if (entry.getValue().equals(value)) {
                return String.valueOf(entry.getKey());
            }
        }
        throw new PropertyDoesNotExistException("Value: '" + value + "' does not exist!");
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
