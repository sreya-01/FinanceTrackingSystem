package com.finance.jdbc.beans;

public class AdminUser extends User {
    public AdminUser(String username, boolean active) { 
        super(username, Role.ADMIN, active); 
    }
    @Override 
    public void displayDashboardPermissions() { 
        System.out.println("Access Level: FULL ADMINISTRATIVE CONTROL"); 
    }
}
