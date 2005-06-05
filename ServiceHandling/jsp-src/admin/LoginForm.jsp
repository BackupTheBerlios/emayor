<%@page contentType="text/html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>eMayor - Admin login page</title>
</head>

<body bgcolor="#FFFFFF">
<table border="0" width="800">
  <tr bgcolor="#999900"> 
    <td height="60" bgcolor="#99CCFF" colspan="3"> <div align="center"><strong><font size="7">eMayor 
        Platform</font></strong></div></td>
  </tr>
  <tr> 
    <td colspan="3"> <hr /></td>
  </tr>
  <tr> 
    <td colspan="3"> <div align="center"><strong><font size="4">Admin login page</font></strong> 
      </div></td>
  </tr>
  <tr> 
    <td colspan="3"> <form action="../adm" method="post">
		<input type="hidden" name="action" value="login"/>
        <div align="center"> 
          <table border="0" cellspacing="5">
            <tr> 
              <td><font size="3"><strong>User id:</strong></font></td>
              <td><input type="text" name="uid" size="20" maxlength="20"/></td>
            </tr>
            <tr> 
              <td><strong><font size="3">Password:</font></strong></td>
              <td><input type="password" name="pswd" size="20" maxlength="20"/></td>
            </tr>
            <tr> 
              <td colspan="2" align="center"><input type="reset" value="RESET"/> 
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="submit" name="submit" value="LOGIN"/></td>
            </tr>
          </table>
        </div>
      </form></td>
  </tr>
  <tr> 
    <td colspan="3"> <hr /> </td>
  </tr>
  <tr> 
    <td width="73" bgcolor="#99CCFF"><font size="2">(C) 2005</font></td>
	<td width="603" bgcolor="#99CCFF"><div align="center"><font size="2">&nbsp;</font></div></td>
    <td width="110" bgcolor="#99CCFF"><div align="right"><a href=""><font size="2">webmaster</font></a></div></td>
  </tr>
</table>
</body>

</html>
