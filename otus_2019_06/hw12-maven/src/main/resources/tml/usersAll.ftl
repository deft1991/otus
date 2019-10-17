<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Otus homework!</title>
</head>

<body>
<th>

    All users
</th>
<div>
    <table border=1>
        <tr>
            <td>Name</td>
            <td>Password</td>
            <td>Address</td>
            <td>Actions</td>
        </tr>
        <#list users as user>
            <tr>
                <td>
                    ${user.name}
                </td>
                <td>${user.password}
                </td>

                <#if user.address??>
                    <#list user.address as address>
                        {
                        "entry" : {
                        <td> "street"</td>:
                        <td> "${address.street}"</td>,
                        }
                        }<#if address_has_next>,</#if>
                    </#list>
                    <br>
                    ]

                <#else>
                    <td>null or missing</td>
                </#if>
                <td>
                    <button>
                        <nav>
                            <ul>
                                <li><a href="/user/edit_profile?userId=${user.id}">Edit</a></li>
                            </ul>
                        </nav>
                    </button>
                </td>
            </tr>
        </#list>
    </table>
</div>
<br>
<div>
    <button>
        <nav>
            <ul>
                <li><a href="/user/create_profile">Create User</a></li>
            </ul>
        </nav>
    </button>
</div>
</body>
</html>
