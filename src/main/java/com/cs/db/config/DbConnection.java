package com.cs.db.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbConnection.class);

    static String dburl;
    static String username;
    static String pwd;
    static String filedburl;

    private static volatile Connection dbConn  = null;

    private static volatile Connection fileDbConn  = null;

    static {
        LoadDbProperties();
    }
    private DbConnection(){}

    public static Connection getHSqlDbConnection() {
        try {
            // Create database connection
            if (dbConn == null) {
                synchronized (Connection.class) {
                    if (dbConn == null) {
                        dbConn = DriverManager.getConnection(dburl, username, pwd);
                    }
                }
            }
        }
        catch (Exception e) {
            LOGGER.error("Exception creating connection to db : " + e.getMessage());
        }
        return dbConn;
    }

    public static Connection getFileHSqlDbConnection() {
        try {
            // Create database connection
            if (fileDbConn == null) {
                synchronized (Connection.class) {
                    if (fileDbConn == null) {
                        fileDbConn = DriverManager.getConnection(filedburl, username, pwd);
                    }
                }
            }

        }
        catch (Exception e) {
            LOGGER.error("Exception creating connection to db : " + e.getMessage());
        }
        return fileDbConn;
    }

    private static void LoadDbProperties() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src\\main\\resources\\application.properties"));
            dburl = properties.getProperty("cs.db.url");
            username = properties.getProperty("cs.db.username");
            pwd = properties.getProperty("cs.db.password");
            filedburl = properties.getProperty("cs.db.filedb.url");
        } catch (Exception ex) {
            LOGGER.error("Exception loading DB properties" + ex.getMessage());
        }
    }
}
