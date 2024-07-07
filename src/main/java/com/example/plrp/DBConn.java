package com.example.plrp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {

    private static Connection conn = null;

    static {
        String location = "jdbc:sqlite:C:/Users/jdtuu/IdeaProjects/PLRP/src/main/sql/storage.db";
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(location);
            System.out.println("Connection established successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("Connection failed!");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return conn;
    }
}
