package com.finance.jdbc.beans;

public class AnalystUser extends User {
    public AnalystUser(String username, boolean active) { 
        super(username, Role.ANALYST, active); 
    }
    @Override 
    public void displayDashboardPermissions() { 
        System.out.println("Access Level: READ & ANALYTICS ACCESS"); 
    }
}
