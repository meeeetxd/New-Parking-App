package Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import DAO.BookingDAO;
import DAO.SlotDAO;
import DAO.UserDAO;
import Model.Slot;
import Model.User;

@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		UserDAO userDAO = new UserDAO();
		HttpSession session = request.getSession();
		
		if("register".equals(action)) {
			User user = new User();
			user.setName(request.getParameter("name"));
			user.setEmail(request.getParameter("email"));
			user.setPassword(request.getParameter("password"));
			
			if(userDAO.registration(user)) {
				session.setAttribute("user", user);
				response.sendRedirect("customerHome.jsp");
			}else {
				response.sendRedirect("error.jsp");
			}
			
		}else if("selecttime".equals(action)) {
			String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");

            // Store startTime and endTime in session for later use
            session.setAttribute("startTime", startTime);
            session.setAttribute("endTime", endTime);

            // Fetch available slots
            SlotDAO slotDAO = new SlotDAO();
            List<Slot> availableSlots = slotDAO.getAvailableSlots();

            // Pass available slots to the slotsUI.jsp
            request.setAttribute("availableSlots", availableSlots);
            request.getRequestDispatcher("slotUI.jsp").forward(request, response);
            
		}else if("bookSlot".equals(action)) {
			 HttpSession session1 = request.getSession();
		        User user = (User) session1.getAttribute("currentUser");

		        if (user == null) {
		            // If the user is not logged in, redirect to the login page
		            response.sendRedirect("login.jsp");
		            return;
		        }

		        // Retrieving parameters from the form
		        int slotID = Integer.parseInt(request.getParameter("slotID"));
		        String startTime = request.getParameter("startTime");
		        String endTime = request.getParameter("endTime");

		        // Initialize BookingDAO to interact with the database
		        BookingDAO bookingDAO = new BookingDAO();

		        // Book the slot by calling bookSlot method from BookingDAO
		        boolean isBooked = bookingDAO.bookSlot(user.getUser_id(), slotID, startTime, endTime);

		        // Handling the booking result
		        if (isBooked) {
		            // If the slot is successfully booked, redirect to a success page
		            session1.setAttribute("bookingSuccessMessage", "Slot booked successfully!");
		            response.sendRedirect("bookingSuccess.jsp");
		        } else {
		            // If the booking fails, redirect to an error page or show an error message
		            session1.setAttribute("bookingErrorMessage", "Failed to book the slot. Please try again.");
		            response.sendRedirect("error.jsp");
		        }
		    }
		
		}
		//doGet(request, response);
}

