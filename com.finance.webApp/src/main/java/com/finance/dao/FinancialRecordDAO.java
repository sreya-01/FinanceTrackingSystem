package com.finance.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface FinancialRecordDAO {
	ResultSet getAllRecords() throws SQLException;
    boolean saveRecord(double amount, String type, String category, String notes) throws SQLException;
    ResultSet getAggregatedSummary() throws SQLException;
}
