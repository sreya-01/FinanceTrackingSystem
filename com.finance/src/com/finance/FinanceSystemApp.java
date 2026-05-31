package com.finance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FinanceSystemApp {
    // In-memory Collections acting as our database for Milestone 1
    private static Map<String, User> userDatabase = new HashMap<>();
    private static List<FinancialRecord> recordDatabase = new ArrayList<>();
    private static User currentUser = null;

    public static void main(String[] args) {
        seedMockData();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Welcome to Institutional Finance Backend (Milestone 1) ===");
        
        // 1. Handle Login Simulation
        while (currentUser == null) {
            System.out.print("\nEnter Username to log in (admin / analyst / viewer): ");
            String inputUser = scanner.nextLine().trim().toLowerCase();
            
            if (userDatabase.containsKey(inputUser)) {
                User user = userDatabase.get(inputUser);
                if (!user.isActive()) {
                    System.out.println("Access Denied: This account is currently INACTIVE.");
                } else {
                    currentUser = user;
                    System.out.println("\nSuccessfully Logged In! Welcome, " + currentUser.getUsername().toUpperCase());
                    currentUser.displayDashboardPermissions();
                }
            } else {
                System.out.println("Invalid username. Try typing 'admin', 'analyst', or 'viewer'.");
            }
        }

        // 2. Main Menu Loop
        int choice = 0;
        do {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. View All Financial Records");
            System.out.println("2. View Dashboard Summary Metrics (Analytics)");
            System.out.println("3. Create New Financial Entry");
            System.out.println("4. Toggle User Status (Active/Inactive)");
            System.out.println("5. Exit Application");
            System.out.print("Select an operation: ");
            
            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
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
            } catch (NumberFormatException e) {
                System.out.println("Validation Error: Please enter a valid menu sequence number.");
            } catch (UnauthorizedAccessException e) {
                // Catching custom exception and displaying useful error messages to console
                System.err.println("SECURITY ALERT: " + e.getMessage());
            }
        } while (choice != 5);

        scanner.close();
    }

    // Pre-seeding data into collections so the app isn't blank on startup
    private static void seedMockData() {
        userDatabase.put("admin", new AdminUser("admin"));
        userDatabase.put("analyst", new AnalystUser("analyst"));
        userDatabase.put("viewer", new ViewerUser("viewer"));

        recordDatabase.add(new FinancialRecord(500000.00, "INCOME", "Clearing Fees", "Quarterly clearing house intake"));
        recordDatabase.add(new FinancialRecord(120000.00, "EXPENSE", "Infrastructure", "Server rack maintenance logs"));
        recordDatabase.add(new FinancialRecord(350000.00, "INCOME", "Penalties", "Regulatory violation recovery fine"));
        recordDatabase.add(new FinancialRecord(45000.00, "EXPENSE", "Operational", "Software license renewals"));
    }

    // Requirement 2: Viewing Records (Accessible by all logged-in roles)
    private static void viewRecords() {
        System.out.println("\n--- Fetching Financial Ledger Entries ---");
        if (recordDatabase.isEmpty()) {
            System.out.println("No transactional records found in the ledger.");
            return;
        }
        for (FinancialRecord rec : recordDatabase) {
            System.out.println(rec);
        }
    }

    // Requirement 3: Dashboard Summaries (Blocked for VIEWERS)
    private static void viewDashboardAnalytics() throws UnauthorizedAccessException {
        if (currentUser.getRole() == Role.VIEWER) {
            throw new UnauthorizedAccessException("Role VIEWER is restricted from viewing aggregated analytics reports.");
        }

        double totalIncome = 0;
        double totalExpenses = 0;
        Map<String, Double> categoryTotals = new HashMap<>();

        // Using collection streaming logic for data aggregation math
        for (FinancialRecord rec : recordDatabase) {
            if (rec.getType().equals("INCOME")) {
                totalIncome += rec.getAmount();
            } else if (rec.getType().equals("EXPENSE")) {
                totalExpenses += rec.getAmount();
            }
            
            categoryTotals.put(rec.getCategory(), 
                categoryTotals.getOrDefault(rec.getCategory(), 0.0) + rec.getAmount());
        }

        System.out.println("\n=== FINANCIAL DASHBOARD INSIGHTS ===");
        System.out.printf("Total Income : ₹%.2f\n", totalIncome);
        System.out.printf("Total Expenses: ₹%.2f\n", totalExpenses);
        System.out.printf("Net Balance   : ₹%.2f\n", (totalIncome - totalExpenses));
        System.out.println("\n--- Category Breakdown ---");
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            System.out.printf(" * %s: ₹%.2f\n", entry.getKey(), entry.getValue());
        }
    }

    // Requirement 2 & 4: Create Records (ADMIN only)
    private static void createNewRecord(Scanner scanner) throws UnauthorizedAccessException {
        if (currentUser.getRole() != Role.ADMIN) {
            throw new UnauthorizedAccessException("Role " + currentUser.getRole() + " cannot execute write mutations to ledger.");
        }

        System.out.println("\n--- Record Management Engine ---");
        
        System.out.print("Enter Amount: ");
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine());
            if (amount <= 0) throw new IllegalArgumentException();
        } catch (Exception e) {
            System.out.println("Input Validation Failed: Amount must be a positive number.");
            return;
        }

        System.out.print("Enter Type (INCOME / EXPENSE): ");
        String type = scanner.nextLine().trim().toUpperCase();
        if (!type.equals("INCOME") && !type.equals("EXPENSE")) {
            System.out.println("Input Validation Failed: Type must strictly be 'INCOME' or 'EXPENSE'.");
            return;
        }

        System.out.print("Enter Category: ");
        String category = scanner.nextLine().trim();
        
        System.out.print("Enter Description Notes: ");
        String notes = scanner.nextLine().trim();

        // Commit transaction to list store
        recordDatabase.add(new FinancialRecord(amount, type, category, notes));
        System.out.println("SUCCESS: Financial record successfully processed and committed to memory.");
    }

    // Requirement 1 & 4: User Administration Access Constraints (ADMIN only)
    private static void toggleUserManagement(Scanner scanner) throws UnauthorizedAccessException {
        if (currentUser.getRole() != Role.ADMIN) {
            throw new UnauthorizedAccessException("User Access Configuration utility requires ADMIN privileges.");
        }

        System.out.print("\nEnter target profile username to toggle (analyst/viewer): ");
        String targetUser = scanner.nextLine().trim().toLowerCase();

        if (userDatabase.containsKey(targetUser) && !targetUser.equals("admin")) {
            User user = userDatabase.get(targetUser);
            // Toggle boolean assignment status
            user.setActive(!user.isActive());
            System.out.println("SUCCESS: Profile status for '" + targetUser + "' changed to: " + (user.isActive() ? "ACTIVE" : "INACTIVE"));
        } else {
            System.out.println("Error: Target profile not found or action prohibited on master Admin profile.");
        }
    }
}