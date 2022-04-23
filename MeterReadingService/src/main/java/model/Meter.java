package model;

import java.sql.*; 

public class Meter 
{ //A common method to connect to the DB
private Connection connect() 
 { 
 Connection con = null; 
 try
 { 
 Class.forName("com.mysql.jdbc.Driver"); 
 
 //Provide the correct details: DBServer/DBName, username, password 
 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/meterreadings", "root", ""); 
 } 
 catch (Exception e) 
 {e.printStackTrace();} 
 return con; 
 } 


//INSERT
public String insertMeter(String code, String name, String suprice, String unitsamount) 
{ 
Double totalprice = Double.parseDouble(suprice)*Double.parseDouble(unitsamount);	
String output = ""; 
try
{ 
Connection con = connect(); 
if (con == null) 
{return "Error while connecting to the database for inserting."; } 
// create a prepared statement
String query = " insert into meterreadings (`meterID`,`meterCode`,`houseownerName`,`singleUnitPrice`, `unitsAmount`,`totalPrice`)"
+ " values (?, ?, ?, ?, ?, ?)"; 
PreparedStatement preparedStmt = con.prepareStatement(query); 
// binding values
preparedStmt.setInt(1, 0); 
preparedStmt.setString(2, code); 
preparedStmt.setString(3, name); 
preparedStmt.setDouble(4, Double.parseDouble(suprice));
preparedStmt.setInt(5, Integer.parseInt(unitsamount));
preparedStmt.setDouble(6, totalprice); 
// execute the statement

preparedStmt.execute(); 
con.close(); 
output = "Inserted successfully"; 
} 
catch (Exception e) 
{ 
output = "Error while inserting the details."; 
System.err.println(e.getMessage()); 
} 
return output; 
} 




//READ
public String readMeters() 
 { 
 String output = ""; 
 try
 { 
 Connection con = connect(); 
 if (con == null) 
 {return "Error while connecting to the database for reading."; } 
 // Prepare the html table to be displayed
 output = "<table border='1'><tr><th>Meter Code</th><th>House Owner's Name</th>" +
 "<th>Price of a unit(Rs.)</th>" + 
 "<th>Amount of Units</th>" +
 "<th>Total Price(Rs.)</th>"; //+
 //"<th>Change</th><th>Remove</th></tr>"; 
 
 String query = "select * from meterreadings"; 
 Statement stmt = con.createStatement(); 
 ResultSet rs = stmt.executeQuery(query); 
 // iterate through the rows in the result set
 while (rs.next()) 
 { 
 String meterID = Integer.toString(rs.getInt("meterID")); 
 String meterCode = rs.getString("meterCode"); 
 String houseownerName= rs.getString("houseownerName"); 
 String singleUnitPrice= Double.toString(rs.getDouble("singleUnitPrice"));
 String unitsAmount= Integer.toString(rs.getInt("unitsAmount"));
 String totalPrice= Double.toString(rs.getDouble("totalPrice")); 
 // Add into the html table
 output += "<tr><td><center>" + meterCode + "</center></td>"; 
 output += "<td><center>" + houseownerName+ "</center></td>"; 
 output += "<td><center>" + singleUnitPrice+ "</center></td>";
 output += "<td><center>" + unitsAmount+ "</center></td>"; 
 output += "<td><center>" + totalPrice+ "</center></td>"; 
  //buttons
 /*output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>" + "<td><form method='post' action='meterreadings.jsp'>" + "<br><input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
 + "<input name='meterID' type='hidden' value='" + meterID 
 + "'>" + "</form></td></tr>"; */
 } 
 con.close(); 
 // Complete the html table
 output += "</table>"; 
 } 
 catch (Exception e) 
 { 
 output = "Error while reading the meter details."; 
 System.err.println(e.getMessage()); 
 } 
 return output; 
 } 



//UPDATE
public String updateMeter(String ID, String code, String houseownername, String suprice, String unitsamount) 
 
 { 
 Double totalprice1 = Double.parseDouble(suprice)*Double.parseDouble(unitsamount);
 String output = ""; 
 try
 { 
 Connection con = connect(); 
 if (con == null) 
 {return "Error while connecting to the database for updating."; } 
 // create a prepared statement
 String query = "UPDATE meterreadings SET meterCode=?,houseownerName=?,singleUnitPrice=?,unitsAmount=?,totalPrice=? WHERE meterID=?"; 
 PreparedStatement preparedStmt = con.prepareStatement(query); 
 // binding values
 preparedStmt.setString(1, code); 
 preparedStmt.setString(2, houseownername); 
 preparedStmt.setDouble(3, Double.parseDouble(suprice));
 preparedStmt.setInt(4, Integer.parseInt(unitsamount)); 
 preparedStmt.setDouble(5, totalprice1); 
 preparedStmt.setInt(6, Integer.parseInt(ID)); 
 // execute the statement
 preparedStmt.execute(); 
 con.close(); 
 output = "Updated successfully"; 
 } 
 catch (Exception e) 
 { 
 output = "Error while updating the meter details."; 
 System.err.println(e.getMessage()); 
 } 
 return output; 
 } 



//DELETE
public String deleteMeter(String meterID) 
 { 
 String output = ""; 
 try
 { 
 Connection con = connect(); 
 if (con == null) 
 {return "Error while connecting to the database for deleting."; } 
 // create a prepared statement
 String query = "delete from meterreadings where meterID=?"; 
 PreparedStatement preparedStmt = con.prepareStatement(query); 
 // binding values
 preparedStmt.setInt(1, Integer.parseInt(meterID)); 
 // execute the statement
 preparedStmt.execute(); 
 con.close(); 
 output = "Deleted successfully"; 
 } 
 catch (Exception e) 
 { 
 output = "Error while deleting the meter details."; 
 System.err.println(e.getMessage()); 
 } 
 return output; 
 } 
} 