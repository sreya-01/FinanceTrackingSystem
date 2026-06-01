package com.finance.jdbc.beans;

public class ViewerUser extends User {
    public ViewerUser(String username, boolean active) { 
        super(username, Role.VIEWER, active); 
    }
    @Override 
    public void displayDashboardPermissions() { 
        System.out.println("Access Level: READ-ONLY BASE VIEW"); 
    }
}
