package model;

import java.sql.*;

import DBUtil.DBConnect;

public class Outage {
	
	
	//Insert Data
	public String insertOutage(int cusid , String cusname , String outarea ,String outdate, String outtime ,String outdesc) {
		
		String output = "";
		
		
		
		try {
			
			Connection con = DBConnect.connect();
			if(con == null) {
				return "error while connecting database";
			}
			
			String query = "insert into outage(`outageID` , `cusID` ,`cusName` , `outArea` , `outDate`,`outTime` , `outDesc`)"
					        + "value (?,?,?,?,?,?,?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			 preparedStmt.setInt(1, 0); 
			 preparedStmt.setInt(2, cusid); 
			 preparedStmt.setString(3, cusname); 
			 preparedStmt.setString(4, outarea);
			 preparedStmt.setString(5, outdate);
			 preparedStmt.setString(6, outtime);
             preparedStmt.setString(7, outdesc);
			 
			//execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 output = "Inserted successfully";
			 
		}catch(Exception e) {
			
			 output = "Error while inserting"; 
			 System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	//read Data
		public String readOutages() {
			String output  = "";
			
			try {
				Connection con = DBConnect.connect();
				
				if (con == null) {
					return "error while connecting database for reading";
				}
				
				// Prepare the html table to be displayed
				 output = "<table border='1'><tr><th>Customer ID</th>" 
				 +"<th>Customer Name</th><th>Outage Area</th>"
				 + "<th>Outage Date</th>"  + "<th>Outage Time</th>" + "<th>Outage Description</th>"
				 + "<th>Update</th><th>Remove</th></tr>";
				 
				 String query = "select * from outage"; 
				 Statement stmt = con.createStatement(); 
				 ResultSet rs = stmt.executeQuery(query);
				 
				 while (rs.next()) 
				 { 
					 String outageID = Integer.toString(rs.getInt("outageID")); 
					 String cusID = Integer.toString(rs.getInt("cusID")); 
					 String cusName = rs.getString("cusName"); 
					 String outArea =  rs.getString("outArea"); 
					 String outDate = rs.getString("outDate"); 
	                 String outTime = rs.getString("outTime");
	                 String outDesc = rs.getString("outDesc");
					 // Add a row into the html table
					 
					 output += "<tr><td>" + cusID + "</td>"; 
					 output += "<td>" + cusName + "</td>"; 
					 output += "<td>" + outArea + "</td>";
					 output += "<td>" + outDate + "</td>"; 
	                 output += "<td>" + outTime + "</td>";
	                 output += "<td>" + outDesc + "</td>";
					 
					 // buttons
					 output += "<td><input name='btnUpdate' " 
							 + " type='button' value='Update'></td>"
							 + "<td><form method='post' action='outages.jsp'>"
							 + "<input name='btnRemove' " 
							 + " type='submit' value='Remove'>"
							 + "<input name='outageID' type='hidden' " 
							 + " value='" + outageID + "'>" + "</form></td></tr>"; 
				 } 
				 
				 con.close(); 
				 // Complete the html table
				 output += "</table>"; 
				 
				 
			}catch(Exception e) {
				
				output = "error while reading outages";
				System.err.println(e.getMessage());
				
			}
			
			return output;
		}
		
		
	
}
