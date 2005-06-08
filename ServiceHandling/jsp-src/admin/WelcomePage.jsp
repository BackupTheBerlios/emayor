<%@page contentType="text/html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>eMayor Admin Interface (C) - v.1.0.0</title>
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
    <td colspan="3"> <div align="left"><strong><font size="4">Please, choose an action:</font></strong> 
      </div></td>
  </tr>
  <tr> 
    <td colspan="3"> 
		<table width="388" cellpadding="4" cellspacing="4">
        <tr>	
          		<td>
					- <a href="../adm?action=RELOAD_SERVICES"><font size="2">Reload the services ...</font></a>
				</td>
			</tr>
			<tr>
				
          <td>- <a href="../adm?action=RELOAD_CONFIGURATION"><font size="2">Reload the platform configuration ...</font></a></td>
			</tr>
			<tr>
				
          <td>- <a href="../adm?action=SHOW_SYSTEM_CONFIGURATION"><font size="2">View the platform configuration ...</font></a></td>
			</tr>
			<tr>	
          		<td>
					- <a href="../adm?action=LIST_LOGGED_USERS"><font size="2">List all logged in users ...</font></a>
				</td>
			</tr>
			<tr>	
          		<td>
					- <a href="../adm?action=LOOKUP_USER_PROFILE_INPUT"><font size="2">Lookup user's profile ...</font></a>
				</td>
			</tr>
			<tr>	
          		<td>
					- <a href="../adm?action=LIST_KNOWN_USERS"><font size="2">List all known users ...</font></a>
				</td>
			</tr>
			<tr>	
          		<td>
					- <a href="../adm?action=LIST_ACCESS_SESSIONS"><font size="2">List all running access sessions</font></a>
				</td>
			</tr>
			<tr>	
          		<td>
					- <a href="../adm?action=LOOKUP_ACCESS_SESSION_INPUT"><font size="2">Lookup an access session ...</font></a>
				</td>
			</tr>
			<tr>	
          		<td>
					- <a href="../adm?action=LIST_SERVICE_SESSIONS"><font size="2">List all running service sessions ...</font></a>
				</td>
			</tr>
			<tr>	
          		<td>
					- <a href="../adm?action=LOOKUP_SERVICE_SESSION_INPUT"><font size="2">Lookup a service session ...</font></a>
				</td>
			</tr>
			<tr>	
          		<td>
					- <a href="../adm?action=LIST_DEPLOYED_SERVICES"><font size="2">List deployed services ...</font></a>
				</td>
			</tr>
			<tr>	
          		<td>
					- <a href="../adm?action=LOOKUP_SERVICE_PROFILE_INPUT"><font size="2">Lookup a service profile ...</font></a>
				</td>
			</tr>
			<tr>	
          		<td>
					- <a href="../adm?action=DEPLOY_NEW_SERVICE_INPUT"><font size="2">Deploy new service ...</font></a>
				</td>
			</tr>
			<tr>	
          		<td>
					- <a href="../adm?action=UNDEPLOY_SERVICE_INPUT"><font size="2">Undeploy service ...</font></a>
				</td>
			</tr>
		</table>
	</td>
  </tr>
  <tr> 
    <td colspan="3"> <hr /> </td>
  </tr>
  <tr> 
  	<td width="73" bgcolor="#99CCFF"><font size="2">(C) 2005</font></td>
    <td width="787" bgcolor="#99CCFF"> <div align="center"><a href="../adm?action=LOGOUT"><font size="2">logout</font></a></div></td>
    <td width="208" bgcolor="#99CCFF"><div align="right"><a href="mailto:webmaster@emayor.org"><font size="2">webmaster</font></a></div></td>
  </tr>
</table>
</body>

</html>
