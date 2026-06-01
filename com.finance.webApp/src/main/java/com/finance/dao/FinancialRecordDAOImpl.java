package com.finance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.finance.factory.ConnectionFactory;
import com.finance.dao.FinancialRecordDAO;

public class FinancialRecordDAOImpl implements FinancialRecordDAO {

    @Override
    public ResultSet getAllRecords() throws SQLException {
        String query = "SELECT * FROM financial_records ORDER BY id DESC";
        Connection conn = ConnectionFactory.getConnection();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

    @Override
    public boolean saveRecord(double amount, String type, String category, String notes) throws SQLException {
        String query = "INSERT INTO financial_records (amount, type, category, entry_date, notes) VALUES (?, ?, ?, CURDATE(), ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDouble(1, amount);
            pstmt.setString(2, type);
            pstmt.setString(3, category);
            pstmt.setString(4, notes);
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public ResultSet getAggregatedSummary() throws SQLException {
        String query = "SELECT " +
                       "SUM(CASE WHEN type = 'INCOME' THEN amount ELSE 0 END) as total_income, " +
                       "SUM(CASE WHEN type = 'EXPENSE' THEN amount ELSE 0 END) as total_expenses " +
                       "FROM financial_records";
        Connection conn = ConnectionFactory.getConnection();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }
}