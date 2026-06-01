<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>Metrics Engine Suite</title></head>
<body style="font-family: Arial; padding: 40px; text-align: center; background: #f0f3f4;">
    <h2>System Aggregated Analytical Insights</h2>
    <hr>
    <div style="display:flex; justify-content: center; gap: 30px; margin-top: 30px;">
        <div style="background:white; padding:30px; border-radius:8px; box-shadow:0 4px 6px rgba(0,0,0,0.05); width:200px;">
            <h4>Aggregated Total Inflow</h4>
            <h2 style="color:green;">₹${metrics.totalIncome}</h2>
        </div>
        <div style="background:white; padding:30px; border-radius:8px; box-shadow:0 4px 6px rgba(0,0,0,0.05); width:200px;">
            <h4>Aggregated Total Outflow</h4>
            <h2 style="color:red;">₹${metrics.totalExpenses}</h2>
        </div>
        <div style="background:white; padding:30px; border-radius:8px; box-shadow:0 4px 6px rgba(0,0,0,0.05); width:200px;">
            <h4>Net Operational Balance</h4>
            <h2 style="color:blue;">₹${metrics.netBalance}</h2>
        </div>
    </div>
    <br><br>
    <a href="home" style="padding:10px 20px; background:#2c3e50; color:white; text-decoration:none; border-radius:4px;">Return to Workspace Dashboard</a>
</body>
</html>