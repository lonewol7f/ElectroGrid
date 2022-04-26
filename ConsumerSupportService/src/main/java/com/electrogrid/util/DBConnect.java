package com.electrogrid.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

    public static Connection connection;
    public static final String USERNAME = "paf_user";
    public static final String URL = "jdbc:mysql://localhost:3306/consumer-support-service";
    public static final String PASSWORD = "PAF_user";

    public DBConnect() {
    }

    public static Connection connect() throws SQLException, ClassNotFoundException {

        if (connection == null || connection.isClosed()) {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }

        return connection;

    }

}
