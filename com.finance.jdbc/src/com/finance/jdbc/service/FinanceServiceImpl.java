package com.finance.jdbc.service;

import com.finance.jdbc.beans.Role;
import com.finance.jdbc.beans.User;
import com.finance.jdbc.beans.AdminUser;
import com.finance.jdbc.beans.AnalystUser;
import com.finance.jdbc.beans.ViewerUser;
import com.finance.jdbc.dao.FinancialRecordDAO;
import com.finance.jdbc.dao.UserDAO;
import com.finance.jdbc.dao.FinancialRecordDAOImpl;
import com.finance.jdbc.dao.UserDAOImpl;
import com.finance.jdbc.exception.UnauthorizedAccessException;
import com.finance.jdbc.service.FinanceService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class FinanceServiceImpl implements FinanceService {
    // Instantiating implementations via the Interface references (Decoupling)
    private final UserDAO userDAO = new UserDAOImpl();
    private final FinancialRecordDAO recordDAO = new FinancialRecordDAOImpl();

    @Override
    public User authenticateUser(String username) throws SQLException {
        try (ResultSet rs = userDAO.findUserByUsername(username)) {
            if (rs.next()) {
                if (!rs.getBoolean("is_active")) return null;

                String roleStr = rs.getString("role").toUpperCase();
                // Factory-style class resolution mapping
                try {
                    String fullClassName = "com.finance.beans." + 
                        roleStr.substring(0, 1) + roleStr.substring(1).toLowerCase() + "User";
                    java.lang.reflect.Constructor<?> ctor = Class.forName(fullClassName)
                        .getConstructor(String.class, boolean.class);
                    return (User) ctor.newInstance(username, true);
                } catch (Exception e) {
                    // Fallback configuration if dynamic constructor resolution errors
                    if (roleStr.equals("ADMIN")) return new AdminUser(username, true);
                    if (roleStr.equals("ANALYST")) return new AnalystUser(username, true);
                }
                return new ViewerUser(username, true);
            }
        }
        return null;
    }

    @Override
    public void processAllRecordsView() throws SQLException {
        try (ResultSet rs = recordDAO.getAllRecords()) {
            boolean status = false;
            while (rs.next()) {
                status = true;
                System.out.printf("ID: %d | %s | Amount: ₹%.2f | Category: %s | Date: %s | Notes: %s\n",
                        rs.getInt("id"), rs.getString("type"), rs.getDouble("amount"),
                        rs.getString("category"), rs.getDate("entry_date"), rs.getString("notes"));
            }
            if (!status) System.out.println("Zero records registered inside data space layers.");
        }
    }

    @Override
    public Map<String, Double> processDashboardSummary(User actor) throws UnauthorizedAccessException, SQLException {
        if (actor.getRole() == Role.VIEWER) {
            throw new UnauthorizedAccessException("Role VIEWER is unauthorized to compute aggregate metrics.");
        }

        Map<String, Double> metrics = new HashMap<>();
        try (ResultSet rs = recordDAO.getAggregatedSummary()) {
            if (rs.next()) {
                double inc = rs.getDouble("total_income");
                double exp = rs.getDouble("total_expenses");
                metrics.put("income", inc);
                metrics.put("expenses", exp);
                metrics.put("balance", inc - exp);
            }
        }
        return metrics;
    }

    @Override
    public boolean commitNewRecord(User actor, double amount, String type, String category, String notes) 
            throws UnauthorizedAccessException, SQLException {
        if (actor.getRole() != Role.ADMIN) {
            throw new UnauthorizedAccessException("Ledger writes are strictly restricted to role: ADMIN.");
        }
        return recordDAO.saveRecord(amount, type, category, notes);
    }

    @Override
    public boolean modifyUserStatus(User actor, String targetedUser, boolean status) 
            throws UnauthorizedAccessException, SQLException {
        if (actor.getRole() != Role.ADMIN) {
            throw new UnauthorizedAccessException("Identity adjustments require global master ADMIN permissions.");
        }
        return userDAO.updateUserStatus(targetedUser, status);
    }
}
