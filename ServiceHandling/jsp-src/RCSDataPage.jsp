<%@page contentType="text/html" %>
<%@page import="org.emayor.servicehandling.kernel.Task" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>Residence Certification Service - Data Collection</title>
</head>
<%
	Task task = (Task)session.getAttribute("CURR_TASK");
	boolean status = task.getStatus().equals("YES");
	String forename = (String)session.getAttribute("REQ_FORENAME");
	String surname = (String)session.getAttribute("REQ_SURNAME");
	String email = (String)session.getAttribute("REQ_EMAIL");
%>
<body bgcolor="#FFFFFF">
	Current Access Session id = <%= session.getAttribute("ASID") %>
	<br/>
	<h2>GOT DETAILS FOR INPUT DATA PAGE</h2>
	<form method="post" action="ServiceHandlingTest">
		<table width="800" border="2" cellspacing="5" cellpadding="5">
		  <tr bgcolor="#999999">
			<th scope="col"><div align="center"><strong>Name</strong></div></th>
			<th scope="col"><div align="center"><strong>Value</strong></div></th>
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
				<textarea name="XML_DOCUMENT" cols="60" rows="10"><%= task.getXMLDocument() %></textarea>
			</td>
		  </tr>
		  <tr>
		  	<td>Requester forename: </td>
			<td>
		    	<input name="REQ_FORENAME" type="text" value="<%= forename %>" size="60" />
		    	<font color="#FF0000">*</font>
			</td>
		  </tr>
		  <tr>
		  	<td>Requester surname: </td>
			<td>
				<input name="REQ_SURNAME" type="text" value="<%= surname %>" size="60" />
				<font color="#FF0000">*</font>
			</td>
		  </tr>
		  <tr>
		  	<td>Requester email: </td>
			<td>
				<input name="REQ_EMAIL" type="text" value="<%= email %>" size="60" />
				<font color="#FF0000">*</font>
			</td>
		  </tr>
		  <tr>
			<td colspan="2">
				<div align="center">
				  <input type="submit" name="submit" value="validate data"/>
				  <input type="hidden" name="taskid" value="<%= task.getTaskId() %>"/>
				  <input type="hidden" name="action" value="ValidateInputData"/>
			    </div>
			</td>
		  </tr>
  		</table>
	</form>
</body>

</html>
