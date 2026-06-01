package com.finance.service;

import com.finance.beans.Role;
import com.finance.beans.User;
import com.finance.dao.FinancialRecordDAO;
import com.finance.dao.UserDAO;
import com.finance.dao.FinancialRecordDAOImpl;
import com.finance.dao.UserDAOImpl;
import com.finance.entities.FinancialRecordEntity;
import com.finance.entities.UserEntity;
import com.finance.service.FinanceService;
import java.util.List;
import java.util.Map;

public class FinanceServiceImpl implements FinanceService {
    private final UserDAO userDAO = new UserDAOImpl();
    private final FinancialRecordDAO recordDAO = new FinancialRecordDAOImpl();

    @Override
    public User authenticateUser(String username) {
        UserEntity entity = userDAO.findByUsername(username);
        if (entity != null && entity.isActive()) {
            return new User(entity.getUsername(), Role.valueOf(entity.getRole().toUpperCase()), true);
        }
        return null;
    }

    @Override
    public List<FinancialRecordEntity> processAllRecordsView() {
        return recordDAO.getAllRecords();
    }

    @Override
    public Map<String, Double> processDashboardSummary() {
        return recordDAO.getAggregatedSummary();
    }

    @Override
    public boolean commitNewRecord(double amount, String type, String category, String notes) {
        FinancialRecordEntity record = new FinancialRecordEntity(amount, type, category, notes);
        return recordDAO.saveRecord(record);
    }
}