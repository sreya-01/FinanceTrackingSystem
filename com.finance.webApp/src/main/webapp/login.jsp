<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>System Authentication</title></head>
<body style="font-family: Arial; background: #f4f6f9; padding: 50px; text-align: center;">
    <div style="background: white; width: 350px; margin: 0 auto; padding: 30px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);">
        <h2>Institutional Terminal Login</h2>
        <p style="color:red;">${param.error}</p>
        <form action="login" method="post">
            <input type="text" name="username" placeholder="Type: admin, analyst, or viewer" style="width:90%; padding:10px; margin-bottom:15px;" required><br>
            <button type="submit" style="width:96%; padding:10px; background:#2c3e50; color:white; border:none; border-radius:4px; cursor:pointer;">Authenticate Node</button>
        </form>
    </div>
</body>
</html>