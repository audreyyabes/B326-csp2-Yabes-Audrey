package com.joysis.recordingapp.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String URL = "JDBC:mysql://localhost:3306/recording_app";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL,USERNAME, PASSWORD);
    }
}
