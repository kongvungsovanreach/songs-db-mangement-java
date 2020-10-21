package com.sovanreach.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    // init database constants
    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12372186";
    private static final String USERNAME = "sql12372186";
    private static final String PASSWORD = "CKq377Ju8P";
    private static final String MAX_POOL = "250";
    private static Connection connection;
    private static Properties properties;

    //Connect to database and return connection object ot reuse
    public static Connection connect(){
        if (connection == null) {
            try {
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, getProperties());
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace(System.out);
            }
        }else {
            System.out.println("Connected");
        }
        return connection;
    }

    // create properties
    private static Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
            properties.setProperty("MaxPooledStatements", MAX_POOL);
        }
        return properties;
    }
}
