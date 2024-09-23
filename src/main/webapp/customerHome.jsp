<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="Model.User" %>
<%@ page import="DAO.SlotDAO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer Home</title>
<link rel="stylesheet" href="css/styles.css">
</head>
<body>
	<div class="container">
        <h1>Welcome, ${sessionScope.user.name}</h1>
        <%
        	HttpSession sessionOne = request.getSession(false);
        	User user = (User) sessionOne.getAttribute("user");
        	if(user!=null){
        %>
        
        <p>Hello, <%=user.getName() %>! You are logged in as  <%= user.getRole() %></p>
        <h2>Select Your Booking Time</h2>
   
        <!-- Form for selecting the booking time -->
            <form action="CustomerServlet" method="post">
                <input type="hidden" name="action" value="selectTime">
                
                <label for="startTime">Start Time:</label>
                <input type="datetime-local" id="startTime" name="startTime" required>

                <label for="endTime">End Time:</label>
                <input type="datetime-local" id="endTime" name="endTime" required>

                <button type="submit">Proceed to Select Slot</button>
            </form>
        
         <%
            } else {
        %>
            <p>You are not logged in. Please <a href="login.jsp">login</a> to continue.</p>
        <%
            }
        %>
        
    </div>
</body>
</html>