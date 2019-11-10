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
    <form name="user" action="/user/update_profile" method="post">
        Name: <input type="text" name="name"  value=${user.name} />	<br/>
        Password: <input type="text" name="password" value= ${user.password} />		<br/>
        <label hidden="hidden">
            Id: <input type="hidden" name="userId" value= ${user.id}><br>
        </label>
        <input type="submit" value="Save" />
    </form>
</div>
</body>
</html>
