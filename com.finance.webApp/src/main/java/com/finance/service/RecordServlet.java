package com.finance.service;

import com.finance.dao.FinancialRecordDAO;
import com.finance.dao.FinancialRecordDAOImpl;
import com.finance.factory.ConnectionFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/dashboard/home")
public class RecordServlet extends HttpServlet {
    private final FinancialRecordDAO recordDAO = new FinancialRecordDAOImpl();

    // Handles listing records securely to the dashboard grid
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<String[]> recordsList = new ArrayList<>();
        String sql = "SELECT * FROM financial_records ORDER BY id DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                recordsList.add(new String[]{
                    rs.getString("id"),
                    rs.getString("amount"),
                    rs.getString("type"),
                    rs.getString("category"),
                    rs.getString("entry_date"),
                    rs.getString("notes")
                });
            }
            request.setAttribute("ledgerRecords", recordsList);
            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
            
        } catch (SQLException e) {
            throw new ServletException("Data tracking breakdown context error.", e);
        }
    }

    // Handles processing new entries (Only reachable if passed via the Admin filter)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            double amount = Double.parseDouble(request.getParameter("amount"));
            String type = request.getParameter("type").toUpperCase();
            String category = request.getParameter("category").trim();
            String notes = request.getParameter("notes").trim();

            // Guard validation checks
            if (amount <= 0 || category.isEmpty()) {
                response.sendRedirect("home?error=Invalid payload boundaries.");
                return;
            }

            recordDAO.saveRecord(amount, type, category, notes);
            response.sendRedirect("home?success=Transaction verified and written.");
        } catch (NumberFormatException e) {
            response.sendRedirect("home?error=Invalid numerical format mapping parameters.");
        }
    }
}