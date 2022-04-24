package com.electrogrid.dbaccess;

import com.electrogrid.entity.Consumer;
import com.electrogrid.util.DBConnect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsumerDBAccess {

    public static final ObjectMapper mapper = new ObjectMapper();

    public Response addQuestion(Consumer consumer) {

        Response response = null;

        try {
            Connection conn = DBConnect.connect();


            if (conn != null) {
                String query = "INSERT INTO questions (subject, date, cus_id) VALUES(?, ?, ?)";

                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, consumer.getSubject());

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currDate = sdf.format(date);

                stmt.setString(2, currDate);
                stmt.setInt(3, consumer.getCus_id());

                stmt.execute();

                ObjectNode json = mapper.createObjectNode();
                json.put("Status", "Record inserted");
                response = Response.status(Response.Status.OK).entity(json).build();

            }

        } catch (SQLException | ClassNotFoundException e) {
            ObjectNode json = mapper.createObjectNode();
            json.put("Error", e.getMessage());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json).build();
        }

        return response;
    }

}
