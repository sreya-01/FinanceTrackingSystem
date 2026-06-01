package com.finance.model;

public class AnalystUser extends User {
    public AnalystUser(String username, boolean active) { 
        super(username, Role.ANALYST, active); 
    }
  
}
