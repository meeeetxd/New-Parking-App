package DAO;
import java.sql.*;
import Utility.DatabaseConnection;
import Model.User;

public class UserDAO {
	private Connection connection;
	
	public UserDAO() {
		try {
			connection = DatabaseConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean registration(User user) {
		try {
			PreparedStatement stmt = connection.prepareStatement("insert into Users(name, email, password, role) values (?,?,?,?)");
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getEmail());
			stmt.setString(3, user.getPassword());
			stmt.setString(4, user.getRole());
			
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public User loginUser(String email, String password) {
		try {
			PreparedStatement stmt = connection.prepareStatement("select * from Users where email=? and password = ?");
			stmt.setString(1, email);
			stmt.setString(2, password);
			
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				User user = new User();
				user.setUser_id(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
				return user;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
