package com.finance.jdbc.controller;

import com.finance.jdbc.beans.User;
import com.finance.jdbc.exception.UnauthorizedAccessException;
import com.finance.jdbc.service.FinanceService;
import com.finance.jdbc.service.FinanceServiceImpl;
import java.util.Map;
import java.util.Scanner;

public class ConsoleController {
    private static final FinanceServiceImpl service = new FinanceServiceImpl();
    private static User sessionUser = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== FinGuard System Backend Architecture Engine ===");

        // Authentication Loop Handling
        while (sessionUser == null) {
            System.out.print("\nEnter user terminal credential link: ");
            String loginInput = scanner.nextLine().trim();
            try {
                User user = service.authenticateUser(loginInput);
                if (user != null) {
                    sessionUser = user;
                    System.out.println("\nAuthentication Success! Session Active: " + sessionUser.getUsername().toUpperCase());
                    sessionUser.displayDashboardPermissions();
                } else {
                    System.out.println("Authentication Failed: Account does not exist or has been disabled by security rules.");
                }
            } catch (Exception e) {
                System.err.println("Critical connection tracking fault: " + e.getMessage());
            }
        }

        // Operational Core Loop
        int index = 0;
        do {
            System.out.println("\n--- MAIN GOVERNED DIRECTORY ---");
            System.out.println("1. Query System Log Records");
            System.out.println("2. Display Dashboard Aggregation Metrics");
            System.out.println("3. Process System Mutation Payload (Admin)");
            System.out.println("4. Modify Target Node Lifecycle Flag (Admin)");
            System.out.println("5. Close Sockets & Terminate Application");
            System.out.print("Execute Command Index: ");

            try {
                index = Integer.parseInt(scanner.nextLine());
                switch (index) {
                    case 1:
                        service.processAllRecordsView();
                        break;
                    case 2:
                        Map<String, Double> result = service.processDashboardSummary(sessionUser);
                        System.out.println("\n=== SUMMARY METRICS METADATA ===");
                        System.out.printf("Processed Total Inflow : ₹%.2f\n", result.get("income"));
                        System.out.printf("Processed Total Outflow: ₹%.2f\n", result.get("expenses"));
                        System.out.printf("Net Liquidity Index    : ₹%.2f\n", result.get("balance"));
                        break;
                    case 3:
                        handleCreateRecord(scanner);
                        break;
                    case 4:
                        handleToggleStatus(scanner);
                        break;
                    case 5:
                        System.out.println("Shutting down underlying runtime environments. Connections closed cleanly.");
                        break;
                    default:
                        System.out.println("Routing Exception: Command target index out of system boundaries.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Validation Error: Input value must align inside base integer sequences.");
            } catch (UnauthorizedAccessException e) {
                System.err.println("ACCESS INTERCEPTION PROTOCOL CRASH: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Operational exception captured during state processing: " + e.getMessage());
            }
        } while (index != 5);

        scanner.close();
    }

    private static void handleCreateRecord(Scanner scanner) throws Exception {
        System.out.print("Enter Target Allocation Value: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Matrix Orientation (INCOME/EXPENSE): ");
        String type = scanner.nextLine().trim();
        System.out.print("Enter Target Allocation Category: ");
        String category = scanner.nextLine().trim();
        System.out.print("Enter Descriptive Log Reference Notes: ");
        String notes = scanner.nextLine().trim();

        boolean outcome = service.commitNewRecord(sessionUser, amount, type, category, notes);
        if (outcome) System.out.println("SUCCESS: Change metrics persisted directly into data arrays.");
    }

    private static void handleToggleStatus(Scanner scanner) throws Exception {
        System.out.print("Enter system tracking username account node: ");
        String userNode = scanner.nextLine().trim();
        System.out.print("Assign targeted runtime map allocation status (true=ACTIVE / false=INACTIVE): ");
        boolean dynamicFlag = Boolean.parseBoolean(scanner.nextLine());

        boolean outcome = service.modifyUserStatus(sessionUser, userNode, dynamicFlag);
        if (outcome) System.out.println("SUCCESS: Database status configuration maps successfully updated.");
    }
}