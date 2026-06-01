package com.finance.model;

public class AdminUser extends User {
    public AdminUser(String username, boolean active) { 
        super(username, Role.ADMIN, active); 
    }
    
}

