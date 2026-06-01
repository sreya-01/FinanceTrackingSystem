package com.finance.model;


public class ViewerUser extends User {
    public ViewerUser(String username, boolean active) { 
        super(username, Role.VIEWER, active); 
    }
 
}
