<%@page contentType="text/html" %>
<%@page import="org.emayor.servicehandling.kernel.Task" %>
<%@page import="java.util.Calendar" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>JSP Page</title>
	<meta http-equiv="PRAGMA" content="NO-CACHE" />
	<meta http-equiv="EXPIRES" content="-1" />
</head>

<body bgcolor="#FFFFFF">
<p><font color="red">Current Access Session id = <%= session.getAttribute("ASID") %> </font>
  <br/>
    <font color="blue">Your role is: <%= session.getAttribute("ROLE") %> </font>
  <br/>
</p>
<table width="600" border="1" cellspacing="1" cellpadding="1">
  <tr bgcolor="#CCCC99">
    <th width="30" scope="col"><div align="center">#</div></th>
    <th width="232" scope="col">Document type </th>
    <th width="112" scope="col"><div align="center">Incoming date </div></th>
    <th width="106" scope="col"><div align="center">Deadline</div></th>
    <th width="92" scope="col"><div align="center">Action</div></th>
  </tr>
<%
	Task[] tasks = (Task[])session.getAttribute("MY_TASKS");
	for (int i = 0; i < tasks.length; i++) {
		Task task = tasks[i];
		String taskId = task.getTaskId();
		String docType = task.getExtraInfo();
		Calendar cal = task.getIncoming();
		String incoming = cal.toString();
		cal = task.getDeadline();
		String deadline = cal.toString();
		if ((i % 2) == 1) {
		%>
  <tr bgcolor="#CCCCCC">
		<%
		} else {
		%>
  <tr>
  		<%
		}
		%>
    <td><div align="center"><%= i %></div></td>
    <td><div align="right"><%= docType %></div></td>
    <td><div align="center"><%= incoming %></div></td>
    <td><div align="center"><%= deadline %></div></td>
    <td>
	<form name="" method="post" action="">
		<div align="center">
			<input name="taskid" type="hidden" value="<%= taskId %>"/>
		  <input type="submit" name="Submit" value="Submit" />
	      </div>
	</form>
	</td>
  </tr>
<%  
  }
%>
</table>
<p>&nbsp;
</p>
</body>

</html>
