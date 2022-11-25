<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Login page</title>
</head>
<body>
<form method="post">
    <div>
        <label>
            Email<br>
            <input type="text" name="email" placeholder="Login"><br>
        </label>
    </div>
    <div>
        <label>
            Password<br>
            <input type="password" name="password" placeholder="Password"><br>
        </label>
    </div>
    <div>
        <input type="submit" value="Sign in">
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
</body>
</html>
