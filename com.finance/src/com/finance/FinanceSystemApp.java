package com.finance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FinanceSystemApp {
	private static Map<String, User> userDatabase = new HashMap<>();
	private static List<FinancialRecord> recordDatabase = new ArrayList<>();
	private static User currentUser = null;
	
	public static void main(String[] args) {
		seedMockData();
		Scanner scanner = new Scanner(System.in);
		System.out.println("=== Welcome to Institutional Finance Backed ===");
		
		while(currentUser == null) {
			System.out.print("\nEnter Username to log in (admin / analyst / viewer): ");
			String inputUser = scanner.nextLine().trim().toLowerCase();
			
			if(userDatabase.containsKey(inputUser)) {
				User user = userDatabase.get(inputUser);
				if(!user.isActive()) {
					System.out.println("Access Denied: This account is currently INACTIVE.");
				}else {
					currentUser = user;
					System.out.println("\nSuccessfully Logged In! Welcome, " + currentUser.getUsername().toUpperCase());
					currentUser.displayDashboardPermissions();
				}
			}else {
				System.out.println("Invalid username. Try typing 'admin', 'analyst', or 'viewer'.");
			}
		}
		
		int choice = 0;
		do {
			System.out.println("\n--- MAIN MENU ---");
			System.out.println("1. View All Financial Records");
			System.out.println("2. View Dashboard Summary Metrics(Analytics)");
			System.out.println("3. Create New Financial Entry");
			System.out.println("4. Toggle User Status(Active/Inactive)");
			System.out.println("5. Exit Application");
			System.out.println("Select an operation: ");
			
			try {
				choice = Integer.parseInt(scanner.nextLine());
				switch(choice) {
					case 1:
						viewRecords();
						break;
					case 2:
						viewDashboardAnalytics();
						break;
					case 3:
						createNewRecord(scanner);
						break;
					case 4:
						toggleUserManagement(scanner);
						break;
					case 5:
						System.out.println("Shutting down backend session safely.");
						break;
					default:
						System.out.println("Invalid selection. Choose a number between 1 and 5.");
				}
			}catch(NumberFormatException e) {
				System.out.println("Validation Error: Please enter a valid menu sequence number.");
			}catch(UnauthorizedAccessException e) {
				System.err.println("SECURITY ALERT: " + e.getMessage());
			}
		}while(choice != 5);
		scanner.close();
	}
	
	private static void seedMockData() {
		userDatabase.put("admin", new AdminUser("admin"));
		userDatabase.put("analyst", new AnalystUser("analyst"));
		userDatabase.put("viewer", new ViewerUser("viewer"));
		
	}
	
	private static void viewRecords() {
		
	}
	
	private static void viewDashboardAnalytics() throws UnauthorizedAccessException{
		
	}
	
	private static void createNewRecord(Scanner scanner) throws UnauthorizedAccessException{
		
	}
	
	private static void toggleUserManagement(Scanner scanner)throws UnauthorizedAccessException{
		
	}
}
