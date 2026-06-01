package com.finance.controller;

import com.finance.entities.FinancialRecordEntity;
import com.finance.service.FinanceService;
import com.finance.service.FinanceServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet({"/dashboard/home", "/dashboard/record-create"})
public class RecordServlet extends HttpServlet {
    private final FinanceService service = new FinanceServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<FinancialRecordEntity> list = service.processAllRecordsView();
        request.setAttribute("ledgerRecords", list);
        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            double amount = Double.parseDouble(request.getParameter("amount"));
            String type = request.getParameter("type");
            String category = request.getParameter("category").trim();
            String notes = request.getParameter("notes").trim();

            service.commitNewRecord(amount, type, category, notes);
            response.sendRedirect("home?success=Saved via Hibernate.");
        } catch (Exception e) {
            response.sendRedirect("home?error=Persistence error.");
        }
    }
}