package com.electrogrid.service;

import com.electrogrid.entity.Credentials;
import com.electrogrid.entity.User;
import com.electrogrid.util.AuthUser;
import com.electrogrid.util.DBUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.mindrot.jbcrypt.BCrypt;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
        String role = "Customer";


        try {
            Connection conn = DBUtil.connect();

            if (isUserExist(email)) {
                ObjectNode json = mapper.createObjectNode();
                json.put("error", "User already exist");
                return Response.status(Response.Status.EXPECTATION_FAILED)
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
    public Response login(Credentials credentials) {

        Response response = null;

        if (!isUserExist(credentials.getUsername())) {
            ObjectNode json = mapper.createObjectNode();
            json.put("error", "User not found !!!");

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(json).build();
        }

        try {
            Connection conn = DBUtil.connect();

            if (conn != null) {
                String query = "SELECT * FROM users WHERE email= ?";

                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, credentials.getUsername());

                ResultSet rs = stmt.executeQuery();

                String password = "";
                if (rs.next()) {
                    password = rs.getString("password");
                }

                if (!BCrypt.checkpw(credentials.getPassword(), password)) {
                    ObjectNode json = mapper.createObjectNode();
                    json.put("error", "Credentials does not match");

                    return Response.status(Response.Status.CREATED)
                            .entity(json).build();
                }

                String token = credentials.getUsername() + ":" + credentials.getPassword();
                String encodedToken = "Basic " + Base64.getEncoder().encodeToString(token.getBytes(StandardCharsets.UTF_8));

                System.out.println(encodedToken);

                ObjectNode json = mapper.createObjectNode();
                json.put("success", "User logged in");

                return Response.status(200)
                        .entity(json)
                        .header("Authorization", encodedToken).build();
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
    public Response updateUserRole(User user) {
        Response response = null;

        if (!isUserExistById(user.getId())) {
            ObjectNode json = mapper.createObjectNode();
            json.put("error", "User not found !!!");

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(json).build();
        }

        try {
            Connection conn = DBUtil.connect();

            if (conn != null) {
                String query = "UPDATE users SET role = ? WHERE id = ?";

                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, user.getRole());
                stmt.setInt(2, user.getId());

                stmt.executeUpdate();

                ObjectNode json = mapper.createObjectNode();
                json.put("success", "Role updated");

                return Response.status(Response.Status.OK)
                        .entity(json).build();
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
    public Response deleteUser(int id) {
        Response response = null;

        if (!isUserExistById(id)) {
            ObjectNode json = mapper.createObjectNode();
            json.put("error", "User not found !!!");

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(json).build();
        }

        try {
            Connection conn = DBUtil.connect();

            if (conn != null) {
                String query = "DELETE FROM users WHERE id= ?";

                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setInt(1, id);

                stmt.executeUpdate();

                ObjectNode json = mapper.createObjectNode();
                json.put("success", "User deleted");

                return Response.status(Response.Status.OK)
                        .entity(json).build();
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
    public Object getUserById(int id) {

        Response response = null;

        if (!isUserExistById(id)) {
            ObjectNode json = mapper.createObjectNode();
            json.put("error", "User not found !!!");

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(json).build();
        }

        try {

            Connection conn = DBUtil.connect();

            if (conn != null) {
                String query = "SELECT * FROM users WHERE id = ?";

                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setInt(1, id);

                ResultSet rs = stmt.executeQuery();
                User user = null;
                if (rs.next()){
                    user = new User();

                    user.setId(rs.getInt("id"));
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));
                    user.setPassword(rs.getString("password"));
                }

                return user;
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
    public List<User> listUsers() {

        List<User> userList = new ArrayList<>();

        try {

            Connection conn = DBUtil.connect();

            if (conn != null) {
                String query = "SELECT * FROM users";

                PreparedStatement stmt = conn.prepareStatement(query);

                ResultSet rs = stmt.executeQuery();
                User user = null;
                if (rs.next()){
                    user = new User();

                    user.setId(rs.getInt("id"));
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));
                    user.setPassword(rs.getString("password"));

                    userList.add(user);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return userList;
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


    @Override
    public boolean isUserExistById(int id) {

        boolean flag = true;

        try {
            Connection conn = DBUtil.connect();

            if (conn != null) {
                String query = "SELECT * FROM users WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setInt(1, id);

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


    @Override
    public Response validate(ContainerRequestContext containerRequestContext) {

        System.out.println("here");

        final MultivaluedMap<String, String> headers = containerRequestContext.getHeaders();
        final List<String> authorization = headers.get("Authorization");
        final List<String> roles = headers.get("Roles");

        System.out.println(authorization);

        if (roles == null || roles.isEmpty()) {
            return Response.status(Response.Status.OK)
                    .header("Allowed", true)
                    .build();
        }

        if (authorization == null || authorization.isEmpty()) {
            return Response.status(Response.Status.OK)
                    .header("Allowed", false)
                    .build();
        }

        final String authToken = authorization.get(0).replaceFirst("Basic ", "");
        final String decodedAuthToken = new String(Base64.getDecoder().decode(authToken.getBytes(StandardCharsets.UTF_8)));

        final StringTokenizer tokenizer = new StringTokenizer(decodedAuthToken, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();

        String rolesListStr = roles.get(0);
        String cleanRoleList = rolesListStr.replaceAll("\\[", "");
        cleanRoleList = cleanRoleList.replaceAll("\\]", "");
        cleanRoleList = cleanRoleList.replaceAll(" ", "");
        
        Set<String> roleList = new HashSet<>(Arrays.asList(cleanRoleList.split(",")));



        if (!AuthUser.isUserAllowed(username, password, roleList)) {
            return Response.status(Response.Status.OK)
                    .header("Allowed", false)
                    .build();
        }

        return Response.status(Response.Status.OK)
                .header("Allowed", true)
                .build();
    }
}
