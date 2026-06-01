package com.finance.service;

import com.finance.beans.User;
import com.finance.entities.FinancialRecordEntity;
import java.util.List;
import java.util.Map;

public interface FinanceService {
    User authenticateUser(String username);
    List<FinancialRecordEntity> processAllRecordsView();
    Map<String, Double> processDashboardSummary();
    boolean commitNewRecord(double amount, String type, String category, String notes);
}