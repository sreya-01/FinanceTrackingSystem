package com.finance.jdbc.dao;

import com.finance.jdbc.connection.*;
import com.finance.jdbc.dao.*;
import java.sql.*;

public class UserDAOImpl implements UserDAO {

    @Override
    public ResultSet findUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, username);
        return pstmt.executeQuery();
    }

    @Override
    public boolean updateUserStatus(String username, boolean newStatus) throws SQLException {
        String query = "UPDATE users SET is_active = ? WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setBoolean(1, newStatus);
            pstmt.setString(2, username);
            return pstmt.executeUpdate() > 0;
        }
    }
}
