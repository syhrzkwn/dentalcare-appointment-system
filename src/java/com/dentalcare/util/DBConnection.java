package com.dentalcare.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Syahir
 */
public class DBConnection {
    //connection settings from environment variables (see .env.example); credentials have no default
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String URL = env("DB_URL", "jdbc:mysql://localhost:3306/dentalcare");
    public static final String USER = env("DB_USER", "");
    public static final String PASSWORD = env("DB_PASSWORD", "");

    public static Connection createConnection() {
        try {
            //load the driver
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch(ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static String env(String name, String fallback) {
        String value = System.getenv(name);
        return (value == null || value.isEmpty()) ? fallback : value;
    }
}
