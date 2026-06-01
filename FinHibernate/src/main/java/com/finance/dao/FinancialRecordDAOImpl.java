package com.finance.dao;

import com.finance.config.HibernateUtil;
import com.finance.dao.FinancialRecordDAO;
import com.finance.entities.FinancialRecordEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinancialRecordDAOImpl implements FinancialRecordDAO {
    @Override
    public List<FinancialRecordEntity> getAllRecords() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM FinancialRecordEntity ORDER BY id DESC", FinancialRecordEntity.class).list();
        }
    }

    @Override
    public boolean saveRecord(FinancialRecordEntity record) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(record);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        }
    }

    @Override
    public Map<String, Double> getAggregatedSummary() {
        Map<String, Double> metrics = new HashMap<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Double income = session.createQuery("SELECT SUM(amount) FROM FinancialRecordEntity WHERE type = 'INCOME'", Double.class).uniqueResult();
            Double expenses = session.createQuery("SELECT SUM(amount) FROM FinancialRecordEntity WHERE type = 'EXPENSE'", Double.class).uniqueResult();
            
            metrics.put("income", (income != null) ? income : 0.0);
            metrics.put("expenses", (expenses != null) ? expenses : 0.0);
            metrics.put("balance", metrics.get("income") - metrics.get("expenses"));
        }
        return metrics;
    }
}