package com.finance.jdbc.service;


import com.finance.jdbc.beans.User;
import com.finance.jdbc.exception.UnauthorizedAccessException;
import java.sql.SQLException;
import java.util.Map;

public interface FinanceService {
	User authenticateUser(String username) throws SQLException;
    void processAllRecordsView() throws SQLException;
    Map<String, Double> processDashboardSummary(User actor) throws UnauthorizedAccessException, SQLException;
    boolean commitNewRecord(User actor, double amount, String type, String category, String notes) 
            throws UnauthorizedAccessException, SQLException;
    boolean modifyUserStatus(User actor, String targetedUser, boolean status) 
            throws UnauthorizedAccessException, SQLException;
}
