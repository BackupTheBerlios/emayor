<%@page contentType="text/html" %>
<%@page import="org.emayor.servicehandling.kernel.Task" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>Residence Certification Service - Sign Data</title>
</head>
<%
	Task task = (Task)session.getAttribute("CURR_TASK");
	boolean status = task.getStatus().equals("YES");
	String forename = (String)session.getAttribute("REQ_FORENAME");
	String surname = (String)session.getAttribute("REQ_SURNAME");
	String email = (String)session.getAttribute("REQ_EMAIL");
%>
<body bgcolor="#FFFFFF">

<h2>Signing Site for the Residence Certification Service</h2>

<form method="post" action="ServiceHandlingTest">
	<table width="800" border="2" cellspacing="5" cellpadding="5">
		  <tr bgcolor="#999999">
			<th width="175" scope="col"><div align="center"><strong>Name</strong></div></th>
			<th width="582" scope="col"><div align="center"><strong>Value</strong></div></th>
		  </tr>
		  <tr>
		  	<td>Task Id</td>
			<td><%= task.getTaskId() %></td>
		  </tr>
		  <tr>
		  	<td>Task Status</td>
			<td><%= task.getStatus() %></td>
		  </tr>
		  <tr>
		  	<td>Request docuemnt</td>
			<td>
				<%= task.getXMLDocument() %>
			</td>
		  </tr>
		  <tr>
		  	<td>Requester forename: </td>
			<td>
		    	<%= forename %>
			</td>
		  </tr>
		  <tr>
		  	<td>Requester surname: </td>
			<td>
				<%= surname %>
			</td>
		  </tr>
		  <tr>
		  	<td>Requester email: </td>
			<td>
				<%= email %>
			</td>
		  </tr>
		  <tr>
			<td colspan="2">
				<div align="center">
				  <input type="submit" name="submit" value="Sign and post request"/>
				  <input type="hidden" name="taskid" value="<%= task.getTaskId() %>"/>
				  <input type="hidden" name="action" value="ServiceHandlingPostSignRequest"/>
			    </div>
			</td>
		  </tr>
  		</table>
</form>

</body>

</html>
