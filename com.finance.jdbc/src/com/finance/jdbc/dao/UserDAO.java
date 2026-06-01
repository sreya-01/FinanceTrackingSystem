package com.finance.jdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserDAO {
	ResultSet findUserByUsername(String username) throws SQLException;
    boolean updateUserStatus(String username, boolean newStatus) throws SQLException;
}

