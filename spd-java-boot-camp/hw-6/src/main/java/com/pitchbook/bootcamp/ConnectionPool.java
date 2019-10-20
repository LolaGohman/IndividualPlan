package com.pitchbook.bootcamp;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class ConnectionPool {

    private static final String PROPERTY_FILE_NAME = "application.properties";
    private static final String DATABASE_URL =
            PropertyFileReader.readPropertyValueByKey(PROPERTY_FILE_NAME,
                    "default.database.url");
    private static final String DATABASE_PASS =
            PropertyFileReader.readPropertyValueByKey(PROPERTY_FILE_NAME, "default.database.password");
    private static final String DATABASE_USER =
            PropertyFileReader.readPropertyValueByKey(PROPERTY_FILE_NAME, "default.database.user");
    private static final String DRIVER_NAME =
            PropertyFileReader.readPropertyValueByKey(PROPERTY_FILE_NAME, "driver");



    private ConnectionPool() {

    }

    private static DataSource instance = null;

    public static DataSource getInstance() {
        if (instance == null) {
            HikariConfig config = new HikariConfig();
            config.setDriverClassName(DRIVER_NAME);
            config.setJdbcUrl(DATABASE_URL);
            config.setUsername(DATABASE_USER);
            config.setPassword(DATABASE_PASS);
            config.setMaximumPoolSize(50);
            instance = new HikariDataSource(config);
        }
        return instance;
    }

}
