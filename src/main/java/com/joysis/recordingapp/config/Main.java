package com.joysis.recordingapp.config;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        DbConnection db = new DbConnection();
        try {
            Connection conn = db.connect();
            System.out.println("Connected Successfully..");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // CRUD Operation

}
