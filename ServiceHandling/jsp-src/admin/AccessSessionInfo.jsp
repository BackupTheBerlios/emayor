<%@page contentType="text/html" %>
<%@ page import="org.emayor.servicehandling.kernel.AccessSessionInfo" %>
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
    <td colspan="3"> <div align="center"><strong><font size="4">Access session details:</font></strong> 
      </div></td>
  </tr>
  <tr> 
    <td colspan="3"> 
		<table width="784" cellpadding="4" cellspacing="0">
        <tr bgcolor="#CCCCCC"> 
          <td width="198"> 
          <div align="left"><strong>Attribute name </strong></div></td>
				
          <td width="568"> 
          <div align="left"><strong>Attribute value </strong></div></td>

		  </tr>
			<%
				AccessSessionInfo info = (AccessSessionInfo)session.getAttribute("ACCESS_SESSION_INFO");
			%>	
			<tr>
				<td>Session id</td>
				<td><%= info.getSessionId() %></td>
			</tr>
			<tr bgcolor="#FFCCCC">
				<td>Started on</td>
				<td><%= info.getStartDateAsString() %></td>
			</tr>
			<tr>
				<td>Started by</td>
				<td><a href="../adm?action=LOOKUP_USER_PROFILE&UID=<%= info.getUserId() %>"><%= info.getUserId() %></a></td>
			</tr>
			<tr>
				
          <td colspan="2"> <div align="center">
		  	<form action="../adm" method="post">
				<input name="ASID" value="<%= info.getSessionId() %>" type="hidden"/>
				<input name="action" value="REMOVE_ACCESS_SESSION" type="hidden"/>
	            <input name="remove" type="submit" value="REMOVE THIS SESSION"/>
			</form>
            </div></td>
			</tr>
		</table>
	</td>
  </tr>
  <tr> 
    <td colspan="3"> <hr /> </td>
  </tr>
  <tr> 
  	<td width="73" bgcolor="#99CCFF"><font size="2">(C) 2005</font></td>
    <td width="787" bgcolor="#99CCFF"> <div align="center"><a href="../adm?action=logout"><font size="2">EXIT</font></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="../adm?action=mainmenu"><font size="2">menu</font></a></div></td>
    <td width="208" bgcolor="#99CCFF"><div align="right"><a href="mailto:webmaster@emayor.org"><font size="2">webmaster</font></a></div></td>
  </tr>
</table>
</body>

</html>
