<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><title>Identity Gate</title></head>
<body style="font-family: Arial; background: #f4f6f7; text-align: center; padding-top: 100px;">
    <div style="width: 300px; background: white; padding: 30px; margin: 0 auto; border-radius: 6px; box-shadow: 0 4px 8px rgba(0,0,0,0.05);">
        <h3>FinGuard Platform</h3>
        <p style="color:red; font-size: 13px;">${param.error}</p>
        <form action="login" method="post">
            <input type="text" name="username" placeholder="Username" required style="width:90%; padding:8px; margin-bottom:15px;"><br>
            <button type="submit" style="width:96%; padding:8px; background:#2c3e50; color:white; border:none; border-radius:4px; cursor:pointer;">Login</button>
        </form>
    </div>
</body>
</html>