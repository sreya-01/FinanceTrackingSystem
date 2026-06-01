package com.finance.beans;

public class User {
    private String username;
    private Role role;
    private boolean active;

    public User(String username, Role role, boolean active) {
        this.username = username;
        this.role = role;
        this.active = active;
    }

    public String getUsername() { return username; }
    public Role getRole() { return role; }
    public boolean isActive() { return active; }
}