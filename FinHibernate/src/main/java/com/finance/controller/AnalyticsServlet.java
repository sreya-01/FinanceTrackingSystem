package com.finance.controller;

import com.finance.service.FinanceService;
import com.finance.service.FinanceServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/dashboard/analytics")
public class AnalyticsServlet extends HttpServlet {
    private final FinanceService service = new FinanceServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Map<String, Double> metrics = service.processDashboardSummary();
        request.setAttribute("analyticsMetrics", metrics);
        request.getRequestDispatcher("/analytics.jsp").forward(request, response);
    }
}