package com;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.Part;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;

public class Unit {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unitdb","root", "root");
			System.out.print("Successfully connected!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
 //inserting data
	public String insertUnit(String UserID, String UserName, String Units, String Month, String Email) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into userpay_table(`UserID`, `UserName`, `Units`,`Month`, `Email`)"
					+ " values ( ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
		
			preparedStmt.setString(1, UserID);
			preparedStmt.setString(2, UserName);
			preparedStmt.setDouble(3, Double.parseDouble(Units));
			preparedStmt.setString(4, Month);
			preparedStmt.setString(5, Email);
			
			// execute the statement
			preparedStmt.execute();
			con.close();

			String newUnits = readUnit();
			output = "{\"status\":\"success\", \"data\": \"" + newUnits + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the Units.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String readUnit() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1' class='table table-bordered'>"
			
			+"<tr>"

			+"<th>UserID</th>"
			+"<th>UserName</th>"+"<th>Units</th>"+"<th>Month</th>"+"<th>Email</th>"+"<th>Update</th>"+"<th>Delete</th>"+"</tr>";
			String query = "select * from userunit_table";

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String AID = Integer.toString(rs.getInt("AID"));
				String UserID = rs.getString("UserID");
				String UserName = rs.getString("UserName");
				String Units = Double.toString(rs.getDouble("Units"));
				String Month = rs.getString("Month");
				String Email = rs.getString("Email");

				// Add into the html table
//				output += "<tr><td><input id=\'hidUnitIdUpdate\' name=\'hidUnitIdUpdate\' type=\'hidden\' value=\'"
//						+ PID + "'>" + UserID + "</td>";
				output += "<tr>";
				output += "<td>" + UserID + "</td>";
				output += "<td>" + UserName + "</td>";
				output += "<td>" + Units + "</td>";
				output += "<td>" + Month + "</td>";
				output += "<td>" + Email + "</td>";

				
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-unitid='" + UserID +"'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-unitid=></td></tr>";
			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the units.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String updateUnit(int uID, String UserID, String UserName, String Units, String Month, String Email) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE unit_table SET UserID=?,UserName=?,Units=? Month=?,Email=?WHERE UID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, UserID);
			preparedStmt.setString(2, UserName);
			preparedStmt.setString(3, Units);
			preparedStmt.setString(4, Month);
			preparedStmt.setString(5, Email);
			
			
			// execute the statement
			preparedStmt.execute();
			con.close();

			String newUnits = readUnit();
			output = "{\"status\":\"success\", \"data\": \"" + newUnits + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the Units.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteUnit(int UID) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from unit_table where PID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1,UID);

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newUnits = readUnit();
			output = "{\"status\":\"success\", \"data\": \"" + newUnits + "\"}";
		} catch (Exception e) {
			output = "Error while deleting the project.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
