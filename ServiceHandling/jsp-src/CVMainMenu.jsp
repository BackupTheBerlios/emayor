<%@page contentType="text/html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>eMayor: Civil servant main menu page</title>
	<meta http-equiv="PRAGMA" content="NO-CACHE" />
	<meta http-equiv="EXPIRES" content="-1" />
    <style type="text/css">
<!--
.style2 {font-size: 14px}
.style3 {font-size: 14}
-->
    </style>
</head>

<body bgcolor="#FFFFFF">
<span class="style3"><font color="red">Current Access Session id = <%= session.getAttribute("ASID") %> </font></span>
<br/>
<span class="style2"><font color="blue">Your role is: <%= session.getAttribute("ROLE") %> </font></span>
<br/>

<h2>Main Menu Page</h2>

<a href="ServiceHandlingTest?action=listMyTasks" class="style2">my tasks listing :-)</a>
		<br/>
<a href="ServiceHandlingTest?action=reloadPolicies" class="style2">Reload Policies</a>
        <br/>

<hr/>
<a href="ServiceHandlingTest?action=Logout" class="style2"> LOGOUT </a>
</body>

</body>

</html>
