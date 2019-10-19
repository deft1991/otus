<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Log in</title>
</head>
<body>
<nav role="navigation">
    <ul>
        <li><a href="/">Home</a></li>
    </ul>
</nav>
<nav role="navigation">
    <ul>
        <li><a href="/user/all">View all users</a></li>
    </ul>
</nav>

<h1>Log in</h1>

<form role="form" action="/login" method="post">
    <table>
        <tr>
            <td>User:</td>
            <td><input type='text' name='name' value=''></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type='password' name='password' /></td>
        </tr>
        <tr>
            <td><input name="submit" type="submit" value="submit" /></td>
        </tr>
    </table>
</form>

</body>
</html>
