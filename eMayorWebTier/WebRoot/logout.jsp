<%@ page language="Java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<sslext:pageScheme secure="false" />


<%--  Note: This is the only JSP in eMayor, which does not
      follow the Model 2 approach. Reason is , that it
      invalidated the session, which also terminates
      Struts itself.  הצü --%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
	<title>eMayor Logged out</title>
</head>

<body bgcolor="#FEFEFE">
<p>
You have logged out from eMayor.
<br>
Please dont forget your smartcard.
<p>

<%
    String loginPageURL = (String)session.getAttribute("LoginPageURL");
    // Fallback address:
    if( loginPageURL == null )
    {
       loginPageURL = "/eMayor/default.do"; // Just redirect to index page then. No parameters set.
    }
%>


<p>

<a href="<%=loginPageURL%>"> login again </aA

<%
   /*
    * The Logout functionality is processed here.
    * After these statements, struts tags must not be used anymore,
    * because struts itself will be down after this.
    */
	if( session != null ) 
	{
	  session.invalidate();
	}
%>

</body>


</html>
