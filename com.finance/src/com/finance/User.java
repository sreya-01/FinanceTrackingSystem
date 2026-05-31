package com.finance;

public abstract class User {
	private String username;
	private Role role;
	private boolean active;
	
	public User(String username, Role role) {
		this.username = username;
		this.role = role;
		this.active = true;
	}
	
	public String getUsername() {
		return username;
	}
	public Role getRole() {
		return role;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public abstract void displayDashboardPermissions();
}

class AdminUser extends User{
	public AdminUser(String username) {
		super(username, Role.ADMIN);
	}
	
	@Override
	public void displayDashboardPermissions() {
		System.out.println("Access Level: FULL CONTROL(Read, Write, Delete, User Management)");
	}
}

class AnalystUser extends User{
	public AnalystUser(String username) {
		super(username, Role.ANALYST);
	}
	
	@Override
	public void displayDashboardPermissions() {
		System.out.println("Access Level: ANALYTICS & INSIGHTS (Read Records, Access Aggregations)");
	}
}

class ViewerUser extends User{
	public ViewerUser(String username) {
		super(username, Role.VIEWER);
	}
	
	@Override
	public void displayDashboardPermissions() {
		System.out.println("Access Level: READ ONLY (Dashboard Summary View Only)");
	}
}