package com.finance.service;

import com.finance.model.AdminUser;
import com.finance.model.User;
import com.finance.model.AnalystUser;
import com.finance.model.ViewerUser;
import com.finance.model.Role;
import com.finance.factory.ConnectionFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String usernameInput = request.getParameter("username").trim();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?")) {
            
            pstmt.setString(1, usernameInput);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                if (!rs.getBoolean("is_active")) {
                    response.sendRedirect("login.jsp?error=Account is deactivated by Admin.");
                    return;
                }
                
                // Determine User subclass instantiation dynamically
                String roleStr = rs.getString("role");
                User user;
                if (roleStr.equals("ADMIN")) user = new AdminUser(usernameInput);
                else if (roleStr.equals("ANALYST")) user = new AnalystUser(usernameInput);
                else user = new ViewerUser(usernameInput);
                
                // Establish Session
                HttpSession session = request.getSession(true);
                session.setAttribute("authenticatedUser", user);
                
                response.sendRedirect("dashboard/home");
            } else {
                response.sendRedirect("login.jsp?error=Invalid username profile.");
            }
        } catch (SQLException e) {
            throw new ServletException("Database authentication fault.", e);
        }
    }
}