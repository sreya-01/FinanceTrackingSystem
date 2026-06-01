package com.finance.dao;

import com.finance.entities.FinancialRecordEntity;
import java.util.List;
import java.util.Map;

public interface FinancialRecordDAO {
    List<FinancialRecordEntity> getAllRecords();
    boolean saveRecord(FinancialRecordEntity record);
    Map<String, Double> getAggregatedSummary();
}