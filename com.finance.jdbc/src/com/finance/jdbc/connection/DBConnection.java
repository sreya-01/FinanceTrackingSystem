package com.finance.jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/finance";
    private static final String USER = "root";
    private static final String PASSWORD = "password01"; // Change to your password
    private static Connection connection = null;

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                connection.setAutoCommit(false);
            } catch (ClassNotFoundException e) {
                System.err.println("Driver not registered: " + e.getMessage());
            }catch(SQLException e) {
            	e.printStackTrace();
            }
        }
        return connection;
    }
}
