<%@page contentType="text/html" %>
<%@page import="org.emayor.servicehandling.kernel.Task" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>List my tasks!</title>
</head>

<body bgcolor="#FFFFFF">

	Current Access Session id = <%= session.getAttribute("ASID") %>
	<br/>
	<h1>GOT FOLLOWING TASKS FROM BPEL SERVER:</h1>
	<table border="2">
		<tr bgcolor="#999999">
		  <th><div align="center"><strong>TaskId</strong></div></th>
			<th><div align="center"><strong>Status</strong></div></th>
			<th><div align="center"><strong>xml doc</strong></div></th>
			<th><div align="center"><strong>ACTION</strong></div></th>
	  </tr>
<%
	Task[] tasks = (Task[])session.getAttribute("MY_TASKS");
	for (int i = 0; i < tasks.length; i++) {
		Task task = tasks[i];
		String taskid = task.getTaskId();
		String status = task.getStatus();
		String xmldoc = task.getXMLDocument();
%>
	<tr>
    	<td>
            <%= taskid%>
        </td>
        <td>
            <%= status %>
        </td>
        <td>
            <%= xmldoc %>
        </td>
		<td>
			<a href="ServiceHandlingTest?action=showTaskDetails&taskId=<%= taskid%>">Details</a>
		</td>
    </tr>

<%
	}
%>
</table>
</body>

</html>
