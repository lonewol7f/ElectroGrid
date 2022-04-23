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



}