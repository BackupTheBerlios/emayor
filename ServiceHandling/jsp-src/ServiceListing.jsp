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

<table width="579" border="2" cellspacing="5" cellpadding="5">
  <tr bgcolor="#999999"> 
    <th width="150" scope="col"><span class="style13">Service Name </span></th>
    <th width="230" scope="col"><span class="style13">Service Description </span></th>
    <th width="61" scope="col">*</th>
    <th width="61" scope="col"><span class="style14">action</span></th>
  </tr>
  <%
	ServiceInfo[] infos = (ServiceInfo[])session.getAttribute("SERVICES_INFO");
	for (int i = 0; i < infos.length; i++) {
		ServiceInfo info = infos[i];
		String serviceName = info.getServiceName();
		String serviceDescription = info.getServiceDescription();
		String serviceId = info.getServiceId();
%>
  <tr> 
    <td width="150"><%= serviceName %></td>
    <td width="230"><%= serviceDescription %></td>
    <td width="61"><a href="ServiceHandlingTest?action=StartServiceNew&ServiceName=<%= serviceId %>">NEW START</a></td>
    <td width="61"><a href="ServiceHandlingTest?action=StartService&ServiceName=<%= serviceId %>">start 
      it</a></td>
  </tr>
  <%
	}
%>
</table>

<hr/>
<a href="ServiceHandlingTest?action=Logout"> LOGOUT </a>
</body>

</html>
