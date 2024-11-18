package org.example.fourthlab.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mariadb://localhost:3306/MailSystem"; // Update with your DB name
    private static final String USER = "root"; // Your MySQL username
    private static final String PASSWORD = "12345password"; // Your MySQL password

    public static Connection connect() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
