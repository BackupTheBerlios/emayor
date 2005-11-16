<%@ page language="Java" contentType="text/html; charset=UTF-8"%>
<%@page import="test.ssl.*" %>
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!-- äöü -->

<html>
<head>
</head>

<body>
<font size="+4"><center><%=request.getRequestURI()%></center></font>
<br>
We are on the form page.  View the page source to see the difference in the action attribute values between the two forms.
<br>
<br>
Go to the <sslext:link action="/false" module="">main module's false action</sslext:link>
<br>
Go to the <sslext:link action="/true" module="">main module's true action</sslext:link>
<br>
Go to the <sslext:link action="/any" module="">main module's any action</sslext:link>
<br>
<sslext:form action="/secureSubmit" >
This posts to a secure action.
<br>
   <html:text property="propA" value="" size="8" maxlength="8" />
<br>
   <html:text property="propB" value="" size="8" maxlength="8" />
<br>
<html:submit/>
</sslext:form>
<sslext:form action="/nonsecureSubmit" >
This posts to a non-secure action.
<br>
   <html:text property="propA" value="" size="8" maxlength="8" />
<br>
   <html:text property="propB" value="" size="8" maxlength="8" />
<br>
<html:submit/>
</sslext:form>
</body>
</html>
