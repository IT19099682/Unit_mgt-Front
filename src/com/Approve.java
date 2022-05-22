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
public class Approve {

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
		public String insertApprove(String UserID, String UserName, String Units, String Month, String Email) {
			String output = "";
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for inserting.";
				}
				// create a prepared statement
				String query = " insert into approve(`UserID`, `UserName`, `Units`,`Month`, `Email`)"
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

				String newApprove = readApprove();
				output = "{\"status\":\"success\", \"data\": \"" + newApprove + "\"}";
			} catch (Exception e) {
				output = "{\"status\":\"error\", \"data\": \"Error while inserting the Approve.\"}";
				System.err.println(e.getMessage());
			}

			return output;
		}

		public String readApprove() {
			String output = "";
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for reading.";
				}
				// Prepare the HTML table to be displayed
				output = "<table border='1' class='table table-bordered'>"
				
				+"<tr>"

				+"<th>UserID</th>"
				+"<th>UserName</th>"+"<th>Units</th>"+"<th>Month</th>"+"<th>Email</th>"+"</tr>";
				String query = "select * from approve";

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
					
					// Add into the HTML table
//					output += "<tr><td><input id=\'hidApproveIdUpdate\' name=\'hidApproveIdUpdate\' type=\'hidden\' value=\'"
//							+ UID + "'>" + UserID + "</td>";
					output += "<tr>";
					output += "<td>" + UserID + "</td>";
					output += "<td>" + UserName + "</td>";
					output += "<td>" + Units + "</td>";
					output += "<td>" + Month + "</td>";
					output += "<td>" + Email + "</td>";
					
					
					output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-approveid='" + AID +"'></td>"
							+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-approveid='"
							+ AID + "'></td></tr>";
				}

				con.close();

				// Complete the HTML table
				output += "</table>";
			} catch (Exception e) {
				output = "Error while reading the Approve.";
				System.err.println(e.getMessage());
			}

			return output;
		}

		public String updateApprove(int AID, String UserID, String UserName, String Units,String Month, String Email) {
			String output = "";

			try {
				Connection con = connect();

				if (con == null) {
					return "Error while connecting to the database for updating.";
				}

				// create a prepared statement
				String query = "UPDATE approve SET UserID=?,UserName=?,Units=? Month=?,Email=?,WHERE AID=?";

				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values

				preparedStmt.setString(1, UserID);
				preparedStmt.setString(2, UserName);
				preparedStmt.setString(3, Units);
				preparedStmt.setString(4, Month);
				preparedStmt.setString(5, Email);
								
				preparedStmt.setInt(8,AID);

				// execute the statement
				preparedStmt.execute();
				con.close();

				String newApprove = readApprove();
				output = "{\"status\":\"success\", \"data\": \"" + newApprove + "\"}";
			} catch (Exception e) {
				output = "{\"status\":\"error\", \"data\": \"Error while updating the Approve.\"}";
				System.err.println(e.getMessage());
			}

			return output;
		}

		public String deleteApprove(int AID) {

			String output = "";

			try {
				Connection con = connect();

				if (con == null) {
					return "Error while connecting to the database for deleting.";
				}

				// create a prepared statement
				String query = "delete from approve where AID=?";

				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values
				preparedStmt.setInt(1,AID);

				// execute the statement
				preparedStmt.execute();
				con.close();

				String newApprove = readApprove();
				output = "{\"status\":\"success\", \"data\": \"" + newApprove + "\"}";
			} catch (Exception e) {
				output = "Error while deleting the project.";
				System.err.println(e.getMessage());
			}

			return output;
		}

}
