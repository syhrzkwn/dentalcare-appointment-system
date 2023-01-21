/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dentalcare.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Syahir
 */
public class DBConnection {
    public static Connection createConnection() {
        try {
            //declare driver and connection string
            String driver = "org.apache.derby.jdbc.ClientDriver";
            String connectionString = "jdbc:derby://localhost:1527/DentalcareDB;create=true;user=app;password=app";

            //load the driver
            Class.forName(driver);
            return DriverManager.getConnection(connectionString);
        }
        catch(ClassNotFoundException | SQLException ex) {
        }
        return null;
    }
}
