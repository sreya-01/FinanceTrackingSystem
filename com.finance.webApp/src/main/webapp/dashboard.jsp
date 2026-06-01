<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ tagutil prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head><title>Financial Ledger Workspace</title></head>
<body style="font-family: Arial; margin: 30px; background: #fafafa;">
    <div style="display:flex; justify-content: space-between; background: #2c3e50; padding: 20px; color: white; border-radius: 6px;">
        <h2>System Terminal Ledger Space</h2>
        <h3>Welcome, ${sessionScope.authenticatedUser.username.toUpperCase()} (${sessionScope.authenticatedUser.role})</h3>
    </div>

    <p style="color:red;">${param.error}</p>
    <p style="color:green;">${param.success}</p>

    <div style="margin: 20px 0;">
        <a href="home" style="padding:10px; background:#34495e; color:white; text-decoration:none; margin-right:10px; border-radius:4px;">Refresh Ledger Logs</a>
        <c:if test="${sessionScope.authenticatedUser.role != 'VIEWER'}">
            <a href="analytics" style="padding:10px; background:#2980b9; color:white; text-decoration:none; border-radius:4px;">Go to Analytics Suite</a>
        </c:if>
    </div>

    <c:if test="${sessionScope.authenticatedUser.role == 'ADMIN'}">
        <div style="background: white; padding: 20px; border: 1px solid #ddd; border-radius: 6px; margin-bottom: 30px;">
            <h3>Execute Certified Ledger Write (Admin Authorization Override)</h3>
            <form action="record-create" method="post" style="display:flex; gap: 15px; align-items: center;">
                <input type="number" name="amount" step="0.01" placeholder="Amount (₹)" required style="padding:8px;">
                <select name="type" style="padding:8px;">
                    <option value="INCOME">INCOME</option>
                    <option value="EXPENSE">EXPENSE</option>
                </select>
                <input type="text" name="category" placeholder="Category" required style="padding:8px;">
                <input type="text" name="notes" placeholder="Descriptive Notes" style="padding:8px;">
                <button type="submit" style="padding:8px 15px; background:#27ae60; color:white; border:none; border-radius:4px; cursor:pointer;">Commit to Database</button>
            </form>
        </div>
    </c:if>

    <h3>Live Financial Record Partition Rows</h3>
    <table border="1" cellpadding="10" cellspacing="0" style="width:100%; border-collapse:collapse; background:white;">
        <tr style="background:#ecf0f1;">
            <th>Record Sequence ID</th><th>Transaction Amount</th><th>Entry Classification</th><th>Category Space</th><th>Creation Date</th><th>Descriptive Ledger Notes</th>
        </tr>
        <c:forEach var="row" items="${ledgerRecords}">
            <tr>
                <td>${row[0]}</td>
                <td style="font-weight:bold; color: ${row[2] == 'INCOME' ? 'green' : 'red'}">₹${row[1]}</td>
                <td>${row[2]}</td><td>${row[3]}</td><td>${row[4]}</td><td>${row[5]}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>