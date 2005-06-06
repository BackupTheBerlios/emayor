<%@page contentType="text/html" %>
<%@ page import="org.emayor.servicehandling.kernel.AdminServiceProfileData" %>
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
    <td colspan="3"> <div align="center"><strong><font size="4">Service profile details:</font></strong> 
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
				AdminServiceProfileData info = (AdminServiceProfileData)session.getAttribute("SERVICE_PROFILE_INFO");
			%>	
			<tr>
				<td>Id</td>
				<td><%= info.getServiceId() %></td>
			</tr>
			<tr bgcolor="#FFCCCC">
				<td>Name</td>
				<td><%= info.getServiceName() %></td>
			</tr>
			<tr>
				<td>Description</td>
				<td><%= info.getServiceDescription() %></td>
			</tr>
			<tr bgcolor="#FFCCCC">
				<td>Version</td>
				<td><%= info.getServiceVersion() %></td>
			</tr>
			<tr>
			<tr>
				<td>Endpoint</td>
				<td><%= info.getServiceEndpoint() %></td>
			</tr>
			<tr bgcolor="#FFCCCC">
				<td>Class name</td>
				<td><%= info.getServiceClassName() %></td>
			</tr>
			<tr>
				<td>Factory class name</td>
				<td><%= info.getServiceFactoryClassName() %></td>
			</tr>
			<tr bgcolor="#FFCCCC">
				<td>Number of running instances</td>
				<td><%= info.getNumberOfInstances() %></td>
			</tr>	
          <td colspan="2"> <div align="center">
              <input name="remove" type="submit" value="UNDEPLOY THIS SERVICE"/>
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
