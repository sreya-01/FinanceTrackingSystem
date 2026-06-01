package com.finance.service;

import com.finance.dao.FinancialRecordDAO;
import com.finance.dao.FinancialRecordDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/dashboard/analytics")
public class AnalyticsServlet extends HttpServlet {
    private final FinancialRecordDAO recordDAO = new FinancialRecordDAOImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Pull aggregation dataset map calculated at SQL level
        Map<String, Object> analyticalMap = recordDAO.getAggregatedSummary();
        
        request.setAttribute("metrics", analyticalMap);
        request.getRequestDispatcher("/analytics.jsp").forward(request, response);
    }
}
