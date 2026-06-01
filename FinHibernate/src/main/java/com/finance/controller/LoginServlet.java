package com.finance.controller;

import com.finance.beans.User;
import com.finance.service.FinanceService;
import com.finance.service.FinanceServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final FinanceService service = new FinanceServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username").trim();
        User user = service.authenticateUser(username);
        
        if (user != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("authenticatedUser", user);
            response.sendRedirect("dashboard/home");
        } else {
            response.sendRedirect("login.jsp?error=Invalid profile or deactivated.");
        }
    }
}