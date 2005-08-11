<%@page contentType="text/html" %>
<%@ page import="org.emayor.servicehandling.kernel.AccessSessionInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>Listing of running access sessions</title>
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
    <td colspan="3"> <div align="center"><strong><font size="4">Currently running access sessions:</font></strong> 
      </div></td>
  </tr>
  <tr> 
    <td colspan="3"> 
		<table width="784" cellpadding="4" cellspacing="0">
        <tr bgcolor="#CCCCCC"> 
          <td width="48"> 
            <div align="center"><strong># </strong></div></td>
				
          <td width="323"> 
            <div align="center"><strong>Access session id </strong></div></td>
				
          <td width="281"> 
            <div align="center"><strong>User id </strong></div></td>
		  <td width="78"> 
            <div align="center"><strong>Action</strong></div></td>
			</tr>
			<%
				AccessSessionInfo[] infos = (AccessSessionInfo[])session.getAttribute("ACCESS_SESSION_INFO_ARRAY");
				for (int i = 0; i < infos.length; i++) {
					AccessSessionInfo info = infos[i];
			%>	
			<tr>
				<td><%= (i + 1) %>.</td>
				<td><%= info.getSessionId() %></td>
				<td><a href="../adm?action=LOOKUP_USER_PROFILE&UID=<%= info.getUserId() %>"><%= info.getUserId() %></a></td>
				<td><div align="center"><a href="../adm?action=LOOKUP_ACCESS_SESSION&ASID=<%= info.getSessionId() %>"><font size="2">DETAILS</font></a></div></td>
			</tr>
			<%
				}
			%>
		</table>
	</td>
  </tr>
  <tr> 
    <td colspan="3"> <hr /> </td>
  </tr>
  <tr> 
  	<td width="73" bgcolor="#99CCFF"><font size="2">(C) 2005</font></td>
    <td width="787" bgcolor="#99CCFF"> <div align="center"><a href="../adm?action=logout"><font size="2">EXIT</font></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="../adm?action=mainmenu"><font size="2">menu</font></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="../adm?action=LIST_ACCESS_SESSIONS"><font size="2">refresh list</font></a></div></td>
    <td width="208" bgcolor="#99CCFF"><div align="right"><a href="mailto:webmaster@emayor.org"><font size="2">webmaster</font></a></div></td>
  </tr>
</table>
</body>

</html>
