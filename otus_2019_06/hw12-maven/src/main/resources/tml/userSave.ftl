<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Otus homework!</title>
</head>

<body>
<th>
    Create user
</th>
<div>
    <form action="/user/create_profile" method="put" target="_blank">
        User name: <input type="text" name="userName"><br>
        Password: <input type="text" name="userPassword"><br>
        <label hidden="hidden">
            Id: <input type="hidden" name="userId"><br>
        </label>
        <button type="submit" formmethod="post">Submit user save</button>
    </form>
</div>
</body>
</html>
