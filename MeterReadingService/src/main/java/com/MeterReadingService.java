package com;

import model.Meter;

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 

@Path("/Meters") 
public class MeterReadingService {
	Meter meterObj = new Meter(); 
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readMeters() 
	{ 
		return meterObj.readMeters(); 
	} 
	
	@POST
	@Path("/add") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertMeter(@FormParam("meterCode") String meterCode, 
	 @FormParam("houseownerName") String houseownerName, 
	 @FormParam("singleUnitPrice") String singleUnitPrice,
	 @FormParam("unitsAmount") String unitsAmount) 
	{ 
	 String output = meterObj.insertMeter(meterCode, houseownerName, singleUnitPrice, unitsAmount); 
	return output; 
	}



	@PUT
	@Path("/update") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateMeter(String meterData) 
	{ 
	//Convert the input string to a JSON object 
	 JsonObject meterObject = new JsonParser().parse(meterData).getAsJsonObject(); 
	//Read the values from the JSON object
	 String meterID = meterObject.get("meterID").getAsString(); 
	 String meterCode = meterObject.get("meterCode").getAsString(); 
	 String houseownerName= meterObject.get("houseownerName").getAsString(); 
	 String singleUnitPrice= meterObject.get("singleUnitPrice").getAsString();
	 String unitsAmount= meterObject.get("unitsAmount").getAsString(); 
	 //String totalPrice= meterObject.get("totalPrice").getAsString(); 
	 String output = meterObj.updateMeter(meterID, meterCode, houseownerName, singleUnitPrice, unitsAmount); 
	return output; 
	}



	@DELETE
	@Path("/delete") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteMeter(String meterData) 
	{ 
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(meterData, "", Parser.xmlParser()); 
	 
	//Read the value from the element <meterID>
	 String meterID = doc.select("meterID").text(); 
	 String output = meterObj.deleteMeter(meterID); 
	return output; 
	}

}

	


