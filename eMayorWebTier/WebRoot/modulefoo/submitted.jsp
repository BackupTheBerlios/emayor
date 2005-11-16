<%@ page language="Java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>

<!-- äöü -->

<html>
<head>
</head>

<body>
<font size="+4"><center><%=request.getRequestURI()%></center></font>
<br>
We are on the submitted page.  These are the values that were posted:
<br>
<bean:write name="testForm" property="propA"/>
<br>
<bean:write name="testForm" property="propB"/>
<br>
<br>
Return to <sslext:link page="/formAction.do" >form</sslext:link> test page.
<br>
<br>
Go to the <sslext:link action="/false" module="">main module's false action</sslext:link>
<br>
Go to the <sslext:link action="/true" module="">main module's true action</sslext:link>
<br>
Go to the <sslext:link action="/any" module="">main module's any action</sslext:link>
<br>
<br>
Go to <sslext:link page="/true.do" >true</sslext:link> page.
<br>
Go to <sslext:link page="/false.do" >false</sslext:link> page.
<br>
Go to <sslext:link page="/any.do" >any</sslext:link> page.
<br>
Go to <sslext:link page="/truetag.do" >truetag</sslext:link> page.
<br>
Go to <sslext:link page="/falsetag.do" >falsetag</sslext:link> page.
<br>
Go to <sslext:link page="/anytag.do" >anytag</sslext:link> page.
</body>
</html>
