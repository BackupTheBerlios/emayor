<%@page contentType="text/html" %>
<%@ page import="org.emayor.servicehandling.kernel.AdminServiceProfileData" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>Listing of all deployed services.</title>
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
    <td colspan="3"> <div align="center"><strong><font size="4">All deployed services:</font></strong> 
      </div></td>
  </tr>
  <tr> 
    <td colspan="3"> 
		<table width="784" cellpadding="4" cellspacing="0">
        <tr bgcolor="#CCCCCC"> 
          <td width="42"> 
            <div align="center"><strong># </strong></div></td>
				
          <td width="204"> 
            <div align="center"><strong>Service name </strong></div></td>
				
          <td width="300"> 
            <div align="center"><strong>Description </strong></div></td>
		  <td width="113"> <div align="center"><strong>Version </strong></div></td>
		  <td width="113"> <div align="center"><strong>No. of running instances </strong></div></td>
		  <td width="83"> 
            <div align="center"><strong>Action</strong></div></td>
			</tr>
			<%
				AdminServiceProfileData[] infos = (AdminServiceProfileData[])session.getAttribute("ADMIN_SERVICE_PROFILE_ARRAY");
				for (int i = 0; i < infos.length; i++) {
					AdminServiceProfileData info = infos[i];
					if ( (i%2)==1 ) {
			%>	
			<tr bgcolor="#FFCCCC">
			<%
					} else {
			%>
			<tr>
			<%
					}
			%>
				<td><%= (i+1) %>.</td>
				<td><%= info.getServiceName() %></td>
				<td><%= info.getServiceDescription() %></td>
				<td><div align="center"><%= info.getServiceVersion() %></div></td>
				<td><div align="center"><%= info.getNumberOfInstances() %></div></td>
				<td><div align="center"><a href="../adm?action=w"><font size="2">DETAILS</font></a></div></td>
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
    <td width="787" bgcolor="#99CCFF"> <div align="center"><a href="../adm?action=logout"><font size="2">logout</font></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="../adm?action=mainmenu"><font size="2">menu</font></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="../adm?action=LIST_DEPLOYED_SERVICES"><font size="2">refresh list</font></a></div></td>
    <td width="208" bgcolor="#99CCFF"><div align="right"><a href="mailto:webmaster@emayor.org"><font size="2">webmaster</font></a></div></td>
  </tr>
</table>
</body>

</html>
