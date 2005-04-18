<%@page contentType="text/html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>Invoke BPEL Callback</title>
	<meta http-equiv="PRAGMA" content="NO-CACHE" />
	<meta http-equiv="EXPIRES" content="-1" />
</head>

<body bgcolor="#FFFFFF">
<font color="red">Current Access Session id = <%= session.getAttribute("ASID") %> </font>
<br>
<h2>Invoking the onResult callback method !</h2>

<form action="ServiceHandlingTest" method="post">
	<input type="hidden" name="action" value="InvokeBPELCallback"/>
	<table width="300" border="0">
  <tr>
    <td width="101">SSID</td>
    <td width="189"><input name="SSID" type="text" size="20" maxlength="20" /></td>
  </tr>
  <tr>
    <td colspan="2"><div align="center">
      <input name="submit" type="submit" value="invoke"/>
    </div></td>
    </tr>
</table>
</form>
<br/>
<hr/>
<a href="ServiceHandlingTest?action=Logout" class="style2"> LOGOUT </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="ServiceHandlingTest?action=MainMenu" class="style2"> Main menu </a>
</body>

</html>
