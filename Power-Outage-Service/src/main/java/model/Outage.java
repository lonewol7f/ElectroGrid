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
	
	
	
}
