package com.finance;

import java.time.LocalDate;

public class FinancialRecord {
    private static long idCounter = 1; // Auto-increment simulation
    
    private long id;
    private double amount;
    private String type; // INCOME or EXPENSE
    private String category;
    private LocalDate date;
    private String notes;

    public FinancialRecord(double amount, String type, String category, String notes) {
        this.id = idCounter++;
        this.amount = amount;
        this.type = type.toUpperCase();
        this.category = category;
        this.date = LocalDate.now(); // Sets to today's date
        this.notes = notes;
    }

    // Getters
    public long getId() { return id; }
    public double getAmount() { return amount; }
    public String getType() { return type; }
    public String getCategory() { return category; }
    public LocalDate getDate() { return date; }
    public String getNotes() { return notes; }

    @Override
    public String toString() {
        return String.format("ID: %d | %s | Amount: ₹%.2f | Category: %s | Date: %s | Notes: %s", 
                id, type, amount, category, date, notes);
    }
}