package com.electrogrid.dbaccess;

import com.electrogrid.entity.Question;
import com.electrogrid.util.DBConnect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuestionDBAccess {

    public static final ObjectMapper mapper = new ObjectMapper();

    public Response addQuestion(Question question) {

        Response response = null;

        try {
            Connection conn = DBConnect.connect();


            if (conn != null) {
                String query = "INSERT INTO questions (subject, date, cus_id) VALUES(?, ?, ?)";

                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, question.getSubject());

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currDate = sdf.format(date);

                stmt.setString(2, currDate);
                stmt.setInt(3, question.getCus_id());

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

    public Question getQuestionById(int id) {

        Question question = null;

        try {
            Connection conn = DBConnect.connect();

            if (conn != null) {
                String query = "SELECT * FROM questions WHERE qid = ?";

                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setInt(1, id);

                ResultSet rs = stmt.executeQuery();


                if (rs.next()) {
                    question = new Question();

                    question.setQid(rs.getInt("qid"));
                    question.setSubject(rs.getString("subject"));
                    question.setDate(rs.getDate("date"));
                    question.setCus_id(rs.getInt("cus_id"));
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return question;
    }

    public Response updateQuestion(Question question) {
        Response response = null;

        if (!isQuestionExistById(question.getQid())) {
            ObjectNode json = mapper.createObjectNode();
            json.put("error", "Question not found !!!");

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(json).build();
        }

        try {
            Connection conn = DBConnect.connect();

            if (conn != null) {
                String query = "UPDATE questions SET subject = ? WHERE qid = ?";

                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, question.getSubject());
                stmt.setInt(2, question.getQid());

                stmt.executeUpdate();

                ObjectNode json = mapper.createObjectNode();
                json.put("success", "Subject updated");

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

    public Response deleteQuestion(int id) {
        Response response = null;

        if (!isQuestionExistById(id)) {
            ObjectNode json = mapper.createObjectNode();
            json.put("error", "Question not found !!!");

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(json).build();
        }

        try {
            Connection conn = DBConnect.connect();

            if (conn != null) {
                String query = "DELETE FROM questions WHERE qid= ?";

                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setInt(1, id);

                stmt.executeUpdate();

                ObjectNode json = mapper.createObjectNode();
                json.put("success", "Question deleted");

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

    public List<Question> listQuestions() {

        List<Question> questions = new ArrayList<>();

        try {
            Connection conn = DBConnect.connect();

            if (conn != null) {

                String query = "SELECT * FROM questions";

                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                Question question = null;

                while (rs.next()) {
                    question = new Question();

                    question.setQid(rs.getInt("qid"));
                    question.setSubject(rs.getString("subject"));
                    question.setDate(rs.getDate("date"));
                    question.setCus_id(rs.getInt("cus_id"));

                    questions.add(question);
                }

            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }

    public boolean isQuestionExistById(int id) {

        boolean flag = true;

        try {
            Connection conn = DBConnect.connect();

            if (conn != null) {
                String query = "SELECT * FROM questions WHERE qid = ?";
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

}
