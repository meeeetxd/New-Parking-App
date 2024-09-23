package DAO;

import Utility.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Slot;

public class SlotDAO {
	private Connection connection;
	
	public SlotDAO() {
		try {
			connection = DatabaseConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean addSlot(Slot slotNumber) {
		try {
			PreparedStatement stmt = connection.prepareStatement("insert into Slots (slotID,status) values (?,?)");
			stmt.setString(1, slotNumber.getSlotID());
			stmt.setString(2, slotNumber.getStatus());
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteSlot(Slot slot) {
		try {
			PreparedStatement stmt = connection.prepareStatement("delete from Slots where slotID = ?");
			stmt.setString(1, slot.getSlotID());
			
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Slot> getAvailableSlots(){
		List<Slot> slots = new ArrayList<>();
		try {
			PreparedStatement stmt = connection.prepareStatement("se;ect * from Slots where status = 'available'");
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Slot slot = new Slot();
				slot.setSlotID(rs.getString("slotID"));
				slot.setStatus(rs.getString("status"));
				
				slots.add(slot);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return slots;
	}
}
