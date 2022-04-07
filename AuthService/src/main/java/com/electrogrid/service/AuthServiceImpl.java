package com.electrogrid.service;

import com.electrogrid.entity.User;
import com.electrogrid.util.DBUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.mindrot.jbcrypt.BCrypt;

import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created By lonewol7f
 * Date: 4/6/2022
 */
public class AuthServiceImpl implements AuthServiceI {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Response insertUser(User user) {

        Response response = null;

        String email = user.getEmail();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String password = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        String role = user.getRole();


        try {
            Connection conn = DBUtil.connect();

            if (isUserExist(email)) {
                ObjectNode json = mapper.createObjectNode();
                json.put("error", "User already exist");
                return Response.status(Response.Status.CREATED)
                        .entity(json).build();
            }

            if (conn != null) {
                String query = "INSERT INTO users(email, firstName, lastName, password, role) VALUES (?, ?, ?, ?, ?)";

                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, email);
                stmt.setString(2, firstName);
                stmt.setString(3, lastName);
                stmt.setString(4, password);
                stmt.setString(5, role);

                stmt.execute();

                ObjectNode json = mapper.createObjectNode();
                json.put("status", "OK");
                response = Response.status(Response.Status.CREATED)
                        .entity(json).build();

                conn.close();
            }

        } catch (SQLException | ClassNotFoundException e) {
            ObjectNode json = mapper.createObjectNode();
            json.put("error", e.getMessage());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(json).build();
        }

        return response;
    }

    @Override
    public boolean isUserExist(String email) {

        boolean flag = true;

        try {
            Connection conn = DBUtil.connect();

            if (conn != null) {
                String query = "SELECT * FROM users WHERE email = ?";
                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, email);

                ResultSet rs = stmt.executeQuery();

                if (!rs.next()) {
                    flag = false;
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return flag;
    }
}