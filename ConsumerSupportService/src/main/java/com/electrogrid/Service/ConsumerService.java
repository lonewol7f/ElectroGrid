package com.electrogrid.Service;

import com.electrogrid.dbaccess.QuestionDBAccess;
import com.electrogrid.entity.Question;
import com.electrogrid.util.DBConnect;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Path("")
public class ConsumerService {

    private final QuestionDBAccess questionDBAccess = new QuestionDBAccess();

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
    public Response addQuestion(Question question) {
        return questionDBAccess.addQuestion(question);
    }

    @GET
    @Path("questions/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Question getQuestionById(@PathParam("id") int qid) {
        return questionDBAccess.getQuestionById(qid);
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateQuestion(Question question) {
        return questionDBAccess.updateQuestion(question);
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteQuestion(@PathParam("id") int qid) {
        return questionDBAccess.deleteQuestion(qid);
    }

    @GET
    @Path("/list-all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Question> listQuestions(){
        return questionDBAccess.listQuestions();
    }


}
