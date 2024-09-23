package Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import DAO.SlotDAO;
import DAO.UserDAO;
import Model.Slot;
import Model.User;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
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
		SlotDAO slotDAO = new SlotDAO();
		UserDAO userDAO = new UserDAO();
		
		if("addSlot".equals(action)) {
			Slot slot = new Slot();
			slot.setSlotID(request.getParameter("slotID"));
			slot.setStatus("available");
			
			if(slotDAO.addSlot(slot)) {
				response.sendRedirect("manageSlots.jsp");
			}else {
				response.sendRedirect("error.jsp");
			}
			
		}else if("deleteSlot".equals(action)) {
			Slot slot = new Slot();
			int slotID = Integer.parseInt(request.getParameter("slotID"));
			slot.setSlotID(request.getParameter("slotID"));
			
			if(slotDAO.deleteSlot(slot)) {
				response.sendRedirect("manageSlots.jsp");
			}else {
				response.sendRedirect("error.jsp");
			}
		}else if("login".equals(action)) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			User user = userDAO.loginUser(email, password);
			if(user != null && "admin".equals(user.getRole())) {
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				response.sendRedirect("adminHome.jsp");
			}
		}
		doGet(request, response);
	}

}
