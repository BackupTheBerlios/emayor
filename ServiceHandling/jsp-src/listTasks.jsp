<%@page contentType="text/html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>JSP Page</title>\n\
</head>

<body bgcolor="#FFFFFF">
	<h1>GOT FOLLOWING TASKS FROM SERVER:</h1>
<%
	Task[] tasks = (Task[])session.getAttribute("MY_TASKS");
	for (int i = 0; i < tasks.length; i++) {
		Task task = tasks[i];
		String taskid = task.getTaskId();
		String userid = task.getUserId();
		String xmldoc = task.getXMLDocument();
%>
	<tr>
    	<td>
            <%= taskid%>
        </td>
        <td>
            <%= userid %>
        </td>
        <td>
            <%= xmldoc %>
        </td>
    </tr>

<%
	}
%>
</body>

</html>
