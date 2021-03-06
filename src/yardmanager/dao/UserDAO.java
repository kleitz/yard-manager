/**
 * 
 */
package yardmanager.dao;

import yardmanager.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * @author maxetron
 *
 */
public class UserDAO {
	private Connection conn;
	
	public UserDAO(Connection conn) {
		this.conn = conn;
	}
	
	public User authenticate(String username, String password) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE Username='" + username + "' and Password='" + password +"'");
			
			if (rs.next()) {
				User user = new User(
					rs.getString("Username"),
					rs.getString("Password"),
					rs.getString("Clearance"),
					rs.getString("FirstName"),
					rs.getString("LastName"));
				
				rs.close();
				stmt.close();
				
				return user;
			}
		} catch (SQLException e) { System.out.println("Failed to authenticate credentials: " + e); }
		
		return null;
	}
	
	public List<User> list() {
		List<User> users = new ArrayList<User>();
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Users");
			
			while (rs.next()) {
				User user = new User(
					rs.getString("Username"),
					rs.getString("Password"),
					rs.getString("Clearance"),
					rs.getString("FirstName"),
					rs.getString("LastName"));
				
				users.add(user);
			}
			
			rs.close();
			stmt.close();
		} catch (SQLException e) { System.out.println("Failed to retrieve users: " + e); }

		return users;
	}
	
	public User find(String username) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE Username='" + username + "'");
			
			if(rs.next()) {
				User user = new User(
						rs.getString("Username"),
						rs.getString("Password"),
						rs.getString("Clearance"),
						rs.getString("FirstName"),
						rs.getString("LastName"));
				
				rs.close();
				stmt.close();
				
				return user;
			}
		} catch (SQLException e) { System.out.println("Failed to retrieve user: " + e); }
		
		return null;
	}
	
	public void create(User user) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO Users (Username, Password, Clearance, FirstName, LastName) VALUES ('" + 
				user.getUsername() + "', '" + 
				user.getPassword() + "', '" + 
				user.getClearance() + "', '" + 
				user.getFirstName() + "', '" + 
				user.getLastName() + "')");
			
			stmt.close();
		} catch (SQLException e) { System.out.println("Failed to create user: " + e); }
	}
	
	public void update(User user, User oldUser) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE Users SET Username='" + user.getUsername() + 
					"', Password='" + user.getPassword() + 
					"', Clearance='" + user.getClearance() + 
					"', FirstName='" + user.getFirstName() + 
					"', LastName='" + user.getLastName() + 
					"' WHERE Username='" + oldUser.getUsername() + "'");
			
			stmt.close();
		} catch (SQLException e) { System.out.println("Failed to update user: " + e); }
	}
	
	public void delete(String username) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM Users WHERE Username='" + username + "'");
			
			stmt.close();
		} catch(SQLException e) { System.out.println("Failed to delete user: " + e); }
	}
}
