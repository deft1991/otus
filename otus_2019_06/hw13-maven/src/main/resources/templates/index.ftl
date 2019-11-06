<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" content="text/html;">
<head>
    <title>Head title</title>
</head>
<body>
<#--<#assign url = request.attributes['CURRENT_URL']>-->
<div class="top">
    <img src="static/15104892.jpeg" alt="..."/>
    <#--    <#assign url = window.location.href.toString().split(window.location.host)[1] />-->
    <br/>
    <nav role="navigation">
        <ul>
            <li><a href=${springMacroRequestContext.getRequestUri()}login>Log In</a></li>
        </ul>
    </nav>

</div>
</body>
</html>
