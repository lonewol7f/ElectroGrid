package com.electrogrid.util;

import com.electrogrid.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created By lonewol7f
 * Date: 3/18/2022
 */
public class DBUtil {

    private static Connection connection;
    private static final String url = Config.properties.getProperty("db.url");
    public static final String username = Config.properties.getProperty("db.username");
    public static final String password = Config.properties.getProperty("db.password");

    private DBUtil() {

    }

    public static Connection connect() throws SQLException, ClassNotFoundException {

        if (connection == null || connection.isClosed()) {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        }

        return connection;

    }

}
