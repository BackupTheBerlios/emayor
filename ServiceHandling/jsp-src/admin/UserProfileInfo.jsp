<%@page contentType="text/html" %>
<%@ page import="org.emayor.servicehandling.kernel.UserProfile" %>
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
    <td colspan="3"> <div align="center"><strong><font size="4">User profile details:</font></strong> 
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
				UserProfile info = (UserProfile)session.getAttribute("USER_PROFILE_INFO");
			%>	
			<tr>
				<td>Id</td>
				<td><%= info.getUserId() %></td>
			</tr>
			<tr bgcolor="#FFCCCC">
				<td>Name</td>
				<td><%= info.getPEUserProfile().getUserName() %></td>
			</tr>
			<tr>
				<td>Role</td>
				<td><%= info.getPEUserProfile().getUserRole() %></td>
			</tr>
			<tr bgcolor="#FFCCCC">
				<td>email</td>
				<td><%= info.getPEUserProfile().getUserEmail() %></td>
			</tr>
			<tr>
			<tr>
				<td>Organisation unit</td>
				<td><%= info.getPEUserProfile().getOrganisationUnit() %></td>
			</tr>
			<tr bgcolor="#FFCCCC">
				<td>Country</td>
				<td><%= info.getPEUserProfile().getUserCountry() %></td>
			</tr>
			<tr>
				<td>ST</td>
				<td><%= info.getPEUserProfile().getUserST() %></td>
			</tr>
          <td colspan="2"> <div align="center">
		  	<form action="../adm" method="post">
				<input name="UID" value="<%= info.getUserId() %>" type="hidden"/>
				<input name="action" value="REMOVE_USER_PROFILE" type="hidden"/>
              	<input name="remove" type="submit" value="REMOVE THIS USER PROFILE"/>
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
    <td width="787" bgcolor="#99CCFF"> <div align="center"><a href="../adm?action=logout"><font size="2">logout</font></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="../adm?action=mainmenu"><font size="2">menu</font></a></div></td>
    <td width="208" bgcolor="#99CCFF"><div align="right"><a href="mailto:webmaster@emayor.org"><font size="2">webmaster</font></a></div></td>
  </tr>
</table>
</body>

</html>
