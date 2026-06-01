<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><title>Analytics Suite</title></head>
<body style="font-family: Arial; padding: 40px; text-align: center; background: #f2f4f4;">
    <h2>System Aggregated Metric Insights (HQL Derived Engine)</h2>
    <div style="display:flex; justify-content:center; gap:20px; margin-top:30px; margin-bottom:40px;">
        <div style="background:white; padding:20px; border-radius:4px; width:150px; box-shadow:0 2px 4px rgba(0,0,0,0.05);"><h5>Total Inflow</h5><h3 style="color:#27ae60;">₹${analyticsMetrics.income}</h3></div>
        <div style="background:white; padding:20px; border-radius:4px; width:150px; box-shadow:0 2px 4px rgba(0,0,0,0.05);"><h5>Total Outflow</h5><h3 style="color:#c0392b;">₹${analyticsMetrics.expenses}</h3></div>
        <div style="background:white; padding:20px; border-radius:4px; width:150px; box-shadow:0 2px 4px rgba(0,0,0,0.05);"><h5>Net Liquidity</h5><h3 style="color:#2980b9;">₹${analyticsMetrics.balance}</h3></div>
    </div>
    <a href="home" style="padding:8px 16px; background:#2c3e50; color:white; text-decoration:none; border-radius:4px;">Return to Dashboard</a>
</body>
</html>