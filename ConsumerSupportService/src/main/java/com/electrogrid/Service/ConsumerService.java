package com.electrogrid.Service;

import com.electrogrid.dbaccess.ConsumerDBAccess;
import com.electrogrid.entity.Consumer;
import com.electrogrid.util.DBConnect;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

@Path("")
public class ConsumerService {

    private final ConsumerDBAccess consumerDBAccess = new ConsumerDBAccess();

    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_HTML)
    public String test() {
        try {
            Connection conn = DBConnect.connect();

            if (conn != null) {
                return "data base connected";
            }

        } catch (SQLException | ClassNotFoundException e) {
            return Arrays.toString(e.getStackTrace());
        }

        return "data base not connected";
    }

    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addQuestion(Consumer consumer) {
        return consumerDBAccess.addQuestion(consumer);
    }

}
