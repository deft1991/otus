<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Otus homework!</title>
</head>

<body>
<th>
    Edit user
</th>
<div>
    <form action="/user/update_profile" method="put" target="_blank">
        User name: <input type="text" name="userName" value=${user.name}><br>
        Password: <input type="text" name="userPassword" value= ${user.password}><br>
        <label hidden="hidden">
            Id: <input type="hidden" name="userId" value= ${user.id}><br>
        </label>
        <button type="submit" formmethod="post">Submit user update</button>
    </form>
</div>
</body>
</html>
