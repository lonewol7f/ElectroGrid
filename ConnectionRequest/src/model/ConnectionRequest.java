package model;

import java.sql.*;

public class ConnectionRequest {
	
	//Database connection
    private Connection connect(){

        Connection con = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/connection_request", "root", "");
            //For testing
            System.out.print("Successfully connected");

        }catch (Exception e) {
            e.printStackTrace(); }

        return con;
    }



    
    //Insert connection request
    public String insertConnRequest(String nicNo , String firstName , String lastName , String address , String tpNo) {

        String output = "";

        try {

            Connection con = connect();

            if(con == null) {
                return "error while connecting database"; }
            
            
         // create a prepared statement
            String sql = "insert into connection_req(`connReqId`, `nicNo`, `firstName`, `lastName`, `address`, `tpNo`)"
                    + "values (?,?,?,?,?,?)";

            PreparedStatement state = con.prepareStatement(sql);

         // binding values
            state.setInt(1, 0);
            state.setString(2, nicNo);
            state.setString(3, firstName);
            state.setString(4, lastName);
            state.setString(5, address);
            state.setString(6, tpNo);

          //execute the statement
            state.execute();
            con.close();

            output = "Inserted successfully";

        }catch(Exception e) {
            output = "Error while inserting the connection request.";
            System.err.println(e.getMessage()); }
        return output;
    }



    //View connection request
    public String getConnRequest(){

        String output = "";

        try {
            Connection con = connect();

            output = "<table border='1'><tr><th>NIC No</th><th>First Name</th>" +
                    "<th>Last Name</th>" +
                    "<th>Address</th>" +
                    "<th>Telephone No</th>";

            String sql = "select * from connection_req";
            Statement state = con.createStatement();
            ResultSet rs = state.executeQuery(sql);

            // Iterate through the raws in the result set
            while(rs.next()) {

                String connReqId = Integer.toString(rs.getInt("connReqId"));
                String nicNo = rs.getString("nicNo");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String address = rs.getString("address");
                String tpNo = rs.getString("tpNo");

                output += "<tr><td>" + nicNo + "</td>";
                output += "<td>" + firstName + "</td>";
                output += "<td>" + lastName + "</td>";
                output += "<td>" + address + "</td>";
                output += "<td>" + tpNo + "</td>";
            }
            con.close();
            output += "</table>";
        }catch(Exception e) {
            System.err.println(e.getMessage()); }

        return output;
    }

    
    
    //Update a connection request
    public String updateConnRequest(String connReqID ,String nicNo , String firstName , String lastName , String address , String tpNo) {

        String output = "";

        try {

            Connection con = connect();
            if(con == null) {

                return "error while connecting database";
            }

            //create a prepared statement
            String sql = "UPDATE connection_req SET nicNo = ? , firstName = ? , lastName = ? , address = ? , tpNo = ?"
                    + "WHERE connReqId = ?";

            PreparedStatement state = con.prepareStatement(sql);

            //binding values
            state.setString(1, nicNo);
            state.setString(2, firstName);
            state.setString(3, lastName);
            state.setString(4, address);
            state.setString(5, tpNo);
            state.setInt(6, Integer.parseInt(connReqID));

            state.execute();
            con.close();

            output = "Update Successfully";

        }catch(Exception e) {
            output = "Error while updating the connection request";
            System.err.println(e.getMessage()); }

        return output;
    }




    //Delete a connection request
    public String deleteConnRequest(String connReqId) {

        String output = "";

        try {

            Connection con = connect();
            if(con == null) {
                return "error while deleting connection request";
            }

            // Create a prepared statement
            String sql = "delete from connection_req where connReqId = ?";

            PreparedStatement state = con.prepareStatement(sql);

        	// binding values
            state.setInt(1 , Integer.parseInt(connReqId));

            // execute the statement
            state.execute();
            con.close();

            output = "Deleted Successfully";

        }catch(Exception e) {

            output = "Error while deleting the connection request.";
            System.err.println(e.getMessage());

        }

        return output;
    }

}