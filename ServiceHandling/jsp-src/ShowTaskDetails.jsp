<%@page contentType="text/html" %>
<%@page import="org.emayor.servicehandling.kernel.Task" %>
<%@page import="com.oracle.services.bpel.task._task" %>
<%@page import="com.oracle.services.bpel.task._task_status" %>
<%@page import="java.util.Calendar" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="org.apache.axis.types.Duration" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<title>Showing Task Details</title>
</head>

<body bgcolor="#FFFFFF">
	Current Access Session id = <%= session.getAttribute("ASID") %>
	<br/>
	<h2>GOT DETAILS OF SPECIFIED TASK FROM BPEL SERVER:</h2>
	<%
		Task __task = (Task)session.getAttribute("CURRENT_TASK");
		_task task = __task.getOriginalTask();
		Calendar cal = task.getCreationDate();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
		String creationDate = formatter.format(cal.getTime());
		cal = task.getModifyDate();
		String modifyDate = cal==null?"-":formatter.format(cal.getTime());
		cal = task.getExpirationDate();
		String expirationDate = cal==null?"-":formatter.format(cal.getTime());
		_task_status taskStatus= task.getStatus();
		String status = taskStatus==null?"-":taskStatus.toString();
		Boolean b = task.getExpired();
		String expired = b==null?"-":b.toString();
		Duration dur = task.getDuration();
		String duration = dur==null?"-":dur.toString();
		Integer i = task.getPriority();
		String priority = i==null?"-":i.toString();
		Object obj = task.getAttachment();
		String att = obj==null?"-":obj.toString();
	%>
	<form method="post" action="ServiceHandlingTest">
	<table width="500" border="2" cellspacing="5" cellpadding="5">
  <tr bgcolor="#999999">
    <th scope="col"><div align="center"><strong>Name</strong></div></th>
    <th scope="col"><div align="center"><strong>Value</strong></div></th>
  </tr>
  <tr>
    <td>taskId</td>
    <td><%= task.getTaskId() %></td>
  </tr>
  <tr>
    <td>title</td>
    <td><%= task.getTitle() %></td>
  </tr>
  <tr>
    <td>creationDate</td>
    <td><%= creationDate %></td>
  </tr>
  <tr>
    <td>creator</td>
    <td><%= task.getCreator() %></td>
  </tr>
  <tr>
    <td>modifyDate</td>
    <td><%= modifyDate %></td>
  </tr>
  <tr>
    <td>modifier</td>
    <td><%= task.getModifier() %></td>
  </tr>
  <tr>
    <td>assignee</td>
    <td><%= task.getAssignee() %></td>
  </tr>
  <tr>
    <td>status</td>
    <td><%= status %></td>
  </tr>
  <tr>
    <td>expired</td>
    <td><%= expired %></td>
  </tr>
  <tr>
    <td>expirationDate</td>
    <td><%= expirationDate %></td>
  </tr>
  <tr>
    <td>duration</td>
    <td><%= duration %></td>
  </tr>
  <tr>
    <td>priority</td>
    <td><%= priority %></td>
  </tr>
  <tr>
    <td>template</td>
    <td><%= task.getTemplate() %></td>
  </tr>
  <tr>
    <td>customKey</td>
    <td><%= task.getCustomKey() %></td>
  </tr>
  <tr>
    <td>conclusion</td>
    <td><%= task.getConclusion() %></td>
  </tr>
  <tr>
    <td>attachement</td>
    <td><%= att %></td>
  </tr>
  <tr>
    <td colspan="2">
    	<input type="submit" name="submit" value="complete task"/>
		<input type="hidden" name="taskid" value="<%= task.getTaskId() %>"/>
		<input type="hidden" name="action" value="completeTask"/>
    </td>
  </tr>
</table>
</form>
</body>

</html>
