package com;
import model.Outage; 

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 

@Path("/Outages")
public class OutageService {
	Outage outObj = new Outage(); 
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readOutages() 
	 { 
		return outObj.readOutages();
	 } 
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertOutage(@FormParam("cusID") int cusID, 
	 @FormParam("cusName") String cusName, 
	 @FormParam("outArea") String outArea, 
	 @FormParam("outDate") String outDate,
	 @FormParam("outTime") String outTime,
	 @FormParam("outDesc") String outDesc) 
	{ 
	 String output = outObj.insertOutage(cusID, cusName,outArea,outDate,outTime,outDesc); 
	return output; 
	}

	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateOutage(String outageData) 
	{ 
	//Convert the input string to a JSON object 
	 JsonObject outageObject = new JsonParser().parse(outageData).getAsJsonObject(); 
	//Read the values from the JSON object
	 String outageID = outageObject.get("outageID").getAsString(); 
	 int cusID = outageObject.get("cusID").getAsInt(); 
	 String cusName = outageObject.get("cusName").getAsString(); 
	 String outArea = outageObject.get("outArea").getAsString(); 
	 String outDate = outageObject.get("outDate").getAsString(); 
	 String outTime = outageObject.get("outTime").getAsString(); 
	 String outDesc= outageObject.get("outDesc").getAsString(); 
	 String output = outObj.updateOutage(outageID,cusID,cusName,outArea,outDate,outTime,outDesc); 
	return output; 
	}

//	to Delete
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteOutage(String outageData) 
	{ 
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(outageData, "", Parser.xmlParser()); 
	 
	//Read the value from the element <itemID>
	 String outageID = doc.select("outageID").text(); 
	 String output =outObj.deleteOutage(outageID); 
	return output; 
	}


}
