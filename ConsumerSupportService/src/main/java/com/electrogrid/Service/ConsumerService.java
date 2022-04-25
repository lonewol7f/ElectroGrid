package com.electrogrid.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.electrogrid.dbaccess.QuestionDBAccess;
import com.electrogrid.entity.Question;
import com.electrogrid.util.DBConnect;

@Path("")
public class ConsumerService {

	private final QuestionDBAccess questionDBAccess = new QuestionDBAccess();

//	@RolesAllowed("Admin")
	@DenyAll
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

	@PermitAll
	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addQuestion(Question question) {
		return questionDBAccess.addQuestion(question);
	}

	@PermitAll
	@GET
	@Path("questions/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Question getQuestionById(@PathParam("id") int qid) {
		return questionDBAccess.getQuestionById(qid);
	}

	@PermitAll
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateQuestion(Question question) {
		return questionDBAccess.updateQuestion(question);
	}

	@PermitAll
	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteQuestion(@PathParam("id") int qid) {
		return questionDBAccess.deleteQuestion(qid);
	}

	@PermitAll
	@GET
	@Path("/list-all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Question> listQuestions() {
		return questionDBAccess.listQuestions();
	}

}
