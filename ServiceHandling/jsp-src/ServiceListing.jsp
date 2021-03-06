<%@page contentType="text/html" %>
<%@page import="javax.servlet.http.HttpSession"%> 
<%@page import="org.emayor.servicehandling.kernel.ServiceInfo" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>eMayor: list all services</title>
	<meta http-equiv="PRAGMA" content="NO-CACHE" />
	<meta http-equiv="EXPIRES" content="-1" />
</head>

<body bgcolor="#FFFFFF">

Current Access Session id = <%= session.getAttribute("ASID") %>
<br/>

<h1>List of all available services!</h1>

<table width="600" border="1" cellspacing="0" cellpadding="0">
  <tr bgcolor="#CCCC66"> 
    <th width="176" scope="col"><span class="style13">Service Name </span></th>
    <th width="255" scope="col"><span class="style13">Service Description </span></th>
    <th width="88" scope="col">action</th>
  </tr>
  <%
	ServiceInfo[] infos = (ServiceInfo[])session.getAttribute("SERVICES_INFO");
	for (int i = 0; i < infos.length; i++) {
		ServiceInfo info = infos[i];
		info.displayService();
		String serviceName = info.getServiceName();
		String serviceDescription = info.getServiceDescription();
		String serviceId = info.getServiceId();
%>
  <tr> 
    <td width="176"><%= serviceName %></td>
    <td width="255"><%= serviceDescription %></td>
    <td width="88"><div align="center"><a href="ServiceHandlingTest?action=StartServiceNew&ServiceName=<%= serviceId %>">START IT</a></div></td>
  </tr>
  <%
	}
%>
</table>

<hr/>
<a href="ServiceHandlingTest?action=Logout"> LOGOUT </a>
</body>

</html>
