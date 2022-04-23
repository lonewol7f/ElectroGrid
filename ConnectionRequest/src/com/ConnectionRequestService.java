package com;

//For REST Service
import model.ConnectionRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
//For XML

@Path("/connectionRequests")
public class ConnectionRequestService {

  ConnectionRequest connectionRequestObj = new ConnectionRequest();

  //Insert a connection request
  @POST
  @Path("/add")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.TEXT_PLAIN)
  public String insertConnRequest(@FormParam("nicNo") String nicNo,
                              @FormParam("firstName") String firstName,
                              @FormParam("lastName") String lastName,
                              @FormParam("address") String address,
                              @FormParam("tpNo") String tpNo)

  {
      String output = connectionRequestObj.insertConnRequest(nicNo , firstName , lastName , address , tpNo);
      return output;
  }

  
  
  //View connection request
  @GET
  @Path("/get")
  @Produces(MediaType.APPLICATION_JSON)
  public String getConnRequest()
  {
      return connectionRequestObj.getConnRequest();
  }

  
  //Update a connection request
  @PUT
  @Path("/update")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.TEXT_PLAIN)
  public String updateConnRequest(String connRequestData) {

      JsonObject connectionRequestObject = new JsonParser().parse(connRequestData).getAsJsonObject();

      String connReqId = connectionRequestObject.get("connReqId").getAsString();
      String nicNo = connectionRequestObject.get("nicNo").getAsString();
      String firstName = connectionRequestObject.get("firstName").getAsString();
      String lastName = connectionRequestObject.get("lastName").getAsString();
      String address = connectionRequestObject.get("address").getAsString();
      String tpNo = connectionRequestObject.get("tpNo").getAsString();

      String output  = connectionRequestObj.updateConnRequest(connReqId, nicNo, firstName, lastName, address, tpNo);

      return output;

  }

  
  //Delete a connection request
  @DELETE
  @Path("/delete")
  @Consumes(MediaType.APPLICATION_XML)
  @Produces(MediaType.TEXT_PLAIN)
  public String deleteConnRequest(String connRequestData) {

      Document doc = Jsoup.parse(connRequestData , "" , Parser.xmlParser());

      String connReqId = doc.select("connReqId").text();

      String output = connectionRequestObj.deleteConnRequest(connReqId);

      return output;
  }

}