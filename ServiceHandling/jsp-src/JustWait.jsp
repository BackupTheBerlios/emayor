<%@page contentType="text/html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%
	String sleepTime = (String)session.getAttribute("SLEEP_TIME");
	String redirectionURL = (String)session.getAttribute("REDIRECTION_URL");
	String pageTitle = (String)session.getAttribute("PAGE_TITLE");
	String redirectionText = (String)session.getAttribute("REDIRECTION_TEXT");
	String redirectionCancelAction = (String)session.getAttribute("REDIRECTION_CANCEL_ACTION");
	String redirectionAction = (String)session.getAttribute("REDIRECTION_ACTION");
%>

<head>
	<title><%= pageTitle %></title>
	<META HTTP-EQUIV="Refresh" CONTENT="<%= sleepTime %>; URL=<%= redirectionURL %>">
</head>

<body bgcolor="#FFFFFF">

<p>
<%= redirectionText %>
</p>

<form action="<%= redirectionAction %>" method="post">
<input type="hidden" name="action" value="<%= redirectionCancelAction %>"/>
<input type="submit" name="submit" value="CANCEL"/>
</form>
</body>

</html>
