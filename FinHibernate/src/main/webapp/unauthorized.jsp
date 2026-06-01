<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>403 - Forbidden Scope</title>
    <base href="${pageContext.request.contextPath}/">
</head>
<body style="font-family: Arial; text-align: center; padding-top: 120px; background: #fdf2e9;">

    <h1 style="color:#e67e22; font-size:55px; margin:0 0 10px 0;">403 - FORBIDDEN</h1>
    <h3 style="color:#2c3e50;">Access Token Scope Mismatch Detected</h3>
    <p style="color:#7f8c8d; max-width:500px; margin:15px auto;">
        Your active role-based profile security map details do not match the necessary authorization criteria.
    </p>
    <br>
    
    <a href="dashboard/home" style="color:#2980b9; text-decoration:none; font-weight:bold;">
        Return to Main System Grid Terminal
    </a>

</body>
</html>