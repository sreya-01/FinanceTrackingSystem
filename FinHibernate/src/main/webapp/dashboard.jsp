<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head><title>Workspace Core</title></head>
<body style="font-family: Arial; margin: 40px; background: #fafafa;">
    <div style="background: #2c3e50; color: white; padding: 15px 20px; border-radius: 4px; display:flex; justify-content:space-between; align-items:center;">
        <h2>FinGuard Workspace</h2>
        <h4>Active Session: ${sessionScope.authenticatedUser.username.toUpperCase()} [${sessionScope.authenticatedUser.role}]</h4>
    </div>

    <p style="color:red;">${param.error}</p><p style="color:green;">${param.success}</p>

    <div style="margin: 20px 0;">
        <a href="home" style="padding:8px 12px; background:#95a5a6; color:white; text-decoration:none; border-radius:4px; margin-right:10px;">Refresh Grid</a>
        <c:if test="${sessionScope.authenticatedUser.role != 'VIEWER'}">
            <a href="analytics" style="padding:8px 12px; background:#2980b9; color:white; text-decoration:none; border-radius:4px;">View Analytics</a>
        </c:if>
    </div>

    <c:if test="${sessionScope.authenticatedUser.role == 'ADMIN'}">
        <div style="background:white; padding:20px; border:1px solid #ddd; border-radius:4px; margin-bottom:20px;">
            <h5>Certified Ledger Write Mutation</h5>
            <form action="record-create" method="post" style="display:flex; gap:10px;">
                <input type="number" name="amount" step="0.01" placeholder="Amount (₹)" required style="padding:6px;">
                <select name="type" style="padding:6px;"><option value="INCOME">INCOME</option><option value="EXPENSE">EXPENSE</option></select>
                <input type="text" name="category" placeholder="Category" required style="padding:6px;">
                <input type="text" name="notes" placeholder="Notes" style="padding:6px;">
                <button type="submit" style="background:#27ae60; color:white; border:none; padding:6px 12px; cursor:pointer; border-radius:4px;">Commit Record</button>
            </form>
        </div>
    </c:if>

    <h3>Active Database Entities Context Grid</h3>
    <table border="1" cellpadding="8" style="width:100%; border-collapse:collapse; background:white;">
        <tr style="background:#f4f6f7;"><th>ID</th><th>Amount</th><th>Type</th><th>Category</th><th>Date</th><th>Notes</th></tr>
        <c:forEach var="item" items="${ledgerRecords}">
            <tr>
                <td>${item.id}</td>
                <td style="font-weight:bold; color:${item.type == 'INCOME' ? '#27ae60' : '#c0392b'}">₹${item.amount}</td>
                <td>${item.type}</td><td>${item.category}</td><td>${item.entryDate}</td><td>${item.notes}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>