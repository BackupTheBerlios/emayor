<%@ page language="Java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<sslext:pageScheme secure="true" />

<%@ taglib uri="/WEB-INF/emayor-bean.tld" prefix="emayorbean" %>
<%--  The class TextResourceKeys only holds [static] class attributes, no instance attributes.
      so automatic instantiation here doesn't hurt.  --%>
<jsp:useBean id="TextResourceKeys" class="org.emayor.webtier.shared.TextResourceKeys" />	


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
	<title>Test eMayorForm Interaction</title>
</head>


<%--  Note: This JSP fills in 5 different partitions (0..4) which
            are created from the template.htm file.
            See the instructions in this template.htm file.
            The processing logic in the sources is located in
            org.emayor.webtier.shared.HTMLTemplate and
            org.emayor.webtier.shared.ExtendedActionForm  äöü --%>	


<bean:write name="formTestForm" property="partition0" filter="false"/>


<%--  ..................... JSP LOGIC START .....................  --%>	
<%--  The following are table rows associated with the available languages --%>
<%--  Placeholder row, so next row is at the bottom: --%>
<%--  colspan = number of available languages:       --%>
                <tr>
                  <td colspan="<bean:write name="formTestForm" property="numberOfLanguages" />" rowspan="1">&nbsp;</td>
                </tr>
                <tr> 

<logic:equal name="formTestForm" property="language" value="ger">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="formTestForm" resourceElementKey="<%=TextResourceKeys.ShortCutGerman%>"/></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="formTestForm" property="language" value="ger">
<td style="text-align: center;">&nbsp;<html:link action="formtest.do?language=ger&do=executeService" 
    name="formTestForm" 
    property="serviceParametersWithoutLanguage" ><emayorbean:writeLanguage name="formTestForm" resourceElementKey="<%=TextResourceKeys.ShortCutGerman%>"/></html:link>&nbsp;</td>
</logic:notEqual>

<logic:equal name="formTestForm" property="language" value="en">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="formTestForm" resourceElementKey="<%=TextResourceKeys.ShortCutEnglish%>"/></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="formTestForm" property="language" value="en">
<td style="text-align: center;">&nbsp;<html:link action="formtest.do?language=en&do=executeService" 
    name="formTestForm" 
    property="serviceParametersWithoutLanguage" ><emayorbean:writeLanguage name="formTestForm" resourceElementKey="<%=TextResourceKeys.ShortCutEnglish%>"/></html:link>&nbsp;</td>
</logic:notEqual>

<logic:equal name="formTestForm" property="language" value="it">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="formTestForm" resourceElementKey="<%=TextResourceKeys.ShortCutItalian%>"/></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="formTestForm" property="language" value="it">
<td style="text-align: center;">&nbsp;<html:link action="formtest.do?language=it&do=executeService" 
    name="formTestForm" 
    property="serviceParametersWithoutLanguage" ><emayorbean:writeLanguage name="formTestForm" resourceElementKey="<%=TextResourceKeys.ShortCutItalian%>"/></html:link>&nbsp;</td>
</logic:notEqual>

<logic:equal name="formTestForm" property="language" value="sp">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="formTestForm" resourceElementKey="<%=TextResourceKeys.ShortCutSpanish%>"/></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="formTestForm" property="language" value="sp">
<td style="text-align: center;">&nbsp;<html:link action="formtest.do?language=sp&do=executeService" 
    name="formTestForm" 
    property="serviceParametersWithoutLanguage" ><emayorbean:writeLanguage name="formTestForm" resourceElementKey="<%=TextResourceKeys.ShortCutSpanish%>"/></html:link>&nbsp;</td>
</logic:notEqual>
</tr>
<%--  ..................... JSP LOGIC END .....................  --%>	


<bean:write name="formTestForm" property="partition1" filter="false"/>


<%--  ..................... JSP LOGIC START .....................  --%>	
<%-- Index page link: Use the global action forward: --%>
<tr><td>&nbsp;<html:link action="formtest.do?do=executeService">
    Index
</html:link>&nbsp;</td>
</tr>

<bean:write name="formTestForm" property="partition2" filter="false"/>


<tr>
<td>
<%--  ..................... JSP LOGIC START .....................  --%>	
<applet name="org.emayor.client.EMayorFormsClientApplet"
        code="org.emayor.client.EMayorFormsClientApplet"
        codebase=""
        archive="eMayorApplet.jar"
        width="636"
        height="<bean:write name="formTestForm" property="expectedAppletHeight" />"
        mayscript="true"
        ALT="This applet shows the eMayor input forms">
  <param name="eMayorFormName" value="/eMayor/formtest.do?do=getEMayorForm"/>
  <param name="postURL" value="/eMayor/formtest.do?do=processPostedDocument"/>
  <param name="redirectionAddressAfterPost" value="/eMayor/formtest.do?do=executeService"/>
  <param name="appletPropertiesURL" value="formtest.do?do=getAppletProperties"/>
  <param name="enumerationPropertiesURL" value="formtest.do?do=getEnumerationProperties"/>
  <param name="Language" value="<bean:write name="formTestForm" property="language" />" />
  <param name="cache_option" value="Plugin"/> 
Your browser must be configured for displaying Java V.1.5+ applets.
</applet>
<%--  ..................... JSP LOGIC END .....................  --%>	
</td>
</tr>
<bean:write name="formTestForm" property="partition3" filter="false"/>


<emayorbean:writeLanguage name="formTestForm" resourceElementKey="<%=TextResourceKeys.AbouteMayor%>"/>


<bean:write name="formTestForm" property="partition4" filter="false"/>


</html>
