package DAO;
import java.sql.*;
import java.text.SimpleDateFormat;

import Utility.DatabaseConnection;

public class BookingDAO {
	private Connection connection;
	
	public BookingDAO() {
		try {
			connection = DatabaseConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean bookSlot(int user_ID, int slotID, String startTime, String endTime ) {
		boolean isBooked = false;
		
		String insertBookingQuery = "insert into Bookings (user_id, slotID, start_time, end_time, status, payment_status) values (?,?,?,?,'active','unpaid'";
		String updateSlotStatusQuery = "update Slots SET status = 'booked' where slotID = ?";
		
		try {
			PreparedStatement bookingstmt = connection.prepareStatement(insertBookingQuery);
			PreparedStatement updateSlotstmt = connection.prepareStatement(updateSlotStatusQuery);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp startTimestamp = new Timestamp(dateFormat.parse(startTime).getTime());
            Timestamp endTimestamp = new Timestamp(dateFormat.parse(endTime).getTime());
            
            bookingstmt.setInt(1, user_ID);
            bookingstmt.setInt(2, slotID);
            bookingstmt.setTimestamp(3, startTimestamp);
            bookingstmt.setTimestamp(4, endTimestamp);
            
            int bookingRowsAffected = bookingstmt.executeUpdate();
            
            if(bookingRowsAffected > 0) {
            	updateSlotstmt.setInt(1, slotID);
            	int slotRowsAffected = updateSlotstmt.executeUpdate();
            	if(slotRowsAffected > 0) {
            		isBooked = true;
            	}
            }
		}catch(Exception e) {
			e.printStackTrace();
		}
		return isBooked;
	}
}
