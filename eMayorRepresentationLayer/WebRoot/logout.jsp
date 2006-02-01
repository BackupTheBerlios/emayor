<%@ page language="Java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<sslext:pageScheme secure="false" />


<%--  Note: This is the only JSP in eMayor, which does not
      follow the Model 2 approach. Reason is , that it
      invalidated the session, which also terminates
      Struts itself.  äöü --%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
	<title>eMayor Logged out</title>
</head>

<%--  Note: This JSP fills in 5 different partitions (0..4) which
            are created from the template.htm file.
            See the instructions in this template.htm file.
            The processing logic in the sources is located in
            org.emayor.webtier.shared.HTMLTemplate and
            org.emayor.webtier.shared.ExtendedActionForm  Ã¤Ã¶Ã¼ --%>	



<bean:write name="municipalityForm" property="partition0" filter="false"/>
<bean:write name="municipalityForm" property="partition1" filter="false"/>
<bean:write name="municipalityForm" property="partition2" filter="false"/>
<tr>
<td>

<p>
<bean:write name="logoutForm" property="logout_Information_Message"/>
<p>


<applet name="org.emayor.RepresentationLayer.ClientTier.Logout.LogoutApplet"
        code="org.emayor.RepresentationLayer.ClientTier.Logout.LogoutApplet"
        codebase="."
        archive="LogoutApplet.jar"
        width="8"
        height="8"
        ALT="This applet closes the browser frame for security reasons.">
  <param name="BackgroundColor" value="234,228,212"/>
  <param name="LogoutMessage" value="<bean:write name="logoutForm" property="logout_Dialog_Message"/>"/>
  <param name="ProceedText" value="<bean:write name="softwareCheckForm" property="proceed_Message"/>"/>
  <param name="cache_option" value="Plugin"/> 
Your browser must be configured for displaying Java V.1.5 applets.
</applet>

</td>
</tr>


<bean:write name="municipalityForm" property="partition3" filter="false"/>
About
<bean:write name="municipalityForm" property="partition4" filter="false"/>


<%
   /*
    * The Logout functionality is processed here on the server
    * when this JSP runs.
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
