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


}