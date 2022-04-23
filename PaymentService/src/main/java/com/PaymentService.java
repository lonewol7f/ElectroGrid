package com;

import model.Payment;

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 

//For JSON
import com.google.gson.*; 

//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 

@Path("/payments")
public class PaymentService {
	
	Payment paymentObj = new Payment();
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPayment(@FormParam("connectionCode") String accountNo,
								@FormParam("name") String name,
								@FormParam("amount") String amount,
								@FormParam("paymentType") String paymentType,
								@FormParam("date") String date)
		
	{
		String output = paymentObj.insertPayment(accountNo, name, amount, paymentType, date);
		return output;
	}
	
	
	@GET
	@Path("/get") 
	@Produces(MediaType.APPLICATION_JSON) 
	public String readItems() 
	 { 
	 return paymentObj.getPayments();
	}
	
	@PUT
	@Path("/update") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updatePayment(String paymentData) {
		
		JsonObject paymentObject = new JsonParser().parse(paymentData).getAsJsonObject();
		
		String paymentID   = paymentObject.get("paymentID").getAsString();
		String accountNo   = paymentObject.get("connectionCode").getAsString();
		String name        = paymentObject.get("name").getAsString();
		String amount      = paymentObject.get("amount").getAsString(); 
		String paymentType = paymentObject.get("paymentType").getAsString();
		String date        = paymentObject.get("date").getAsString();
		
		String output  = paymentObj.updatePayment(paymentID, accountNo, name, amount, paymentType, date);
		
		return output;
		
	}
	
	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deletePayment(String paymentData) {
		
		Document doc = Jsoup.parse(paymentData , "" , Parser.xmlParser());
		
		String paymentID = doc.select("paymentID").text();
		
		String output = paymentObj.deletePayment(paymentID);
		
		return output;
	}

}
