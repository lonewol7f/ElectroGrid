package com.electrogrid.util;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created By lonewol7f
 * Date: 4/5/2022
 */
public class AuthUser {

    public static boolean isUserAllowed(final String username, final String password, final Set<String> roleSet) {

        boolean isAllowed = false;
        String user = "";
        String pwd = "";
        String role = "";

        try {
            Connection conn = DBUtil.connect();

            String query = "SELECT * FROM users WHERE username = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = rs.getString("username");
                pwd = rs.getString("password");
                role = rs.getString("role");
            }

            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (username.equals(user) && BCrypt.checkpw(password, pwd)) {

            if (roleSet.contains(role)) {
                isAllowed = true;
            }

        }

        return isAllowed;

    }

}
