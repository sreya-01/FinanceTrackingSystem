package com.finance.dao;

import com.finance.config.HibernateUtil;
import com.finance.dao.UserDAO;
import com.finance.entities.UserEntity;
import org.hibernate.Session;

public class UserDAOImpl implements UserDAO {
    @Override
    public UserEntity findByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM UserEntity WHERE username = :user", UserEntity.class)
                          .setParameter("user", username)
                          .uniqueResult();
        }
    }
}