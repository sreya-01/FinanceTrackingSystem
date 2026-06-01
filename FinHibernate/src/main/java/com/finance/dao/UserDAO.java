package com.finance.dao;

import com.finance.entities.UserEntity;

public interface UserDAO {
    UserEntity findByUsername(String username);
}