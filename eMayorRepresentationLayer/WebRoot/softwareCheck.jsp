<%@ page language="Java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<sslext:pageScheme secure="true" />

<%--  This page actually only has the job of   --%>
<%--  performing the client software check     --%>
<%--  and install if required.                 --%>
<%--  If the client software is ok, it fowards --%>
<%--  to the municipality page                 --%>

<%@ taglib uri="/WEB-INF/emayor-bean.tld" prefix="emayorbean" %>
<%--  The class TextResourceKeys only holds [static] class attributes, no instance attributes.
      so automatic instantiation here doesn't hurt.  --%>
<jsp:useBean id="TextResourceKeys" class="org.emayor.eMayorWebTier.Utilities.TextResourceKeys" />	


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <META http-equiv="Content-Type" content="text/html;charset=utf-8">
	<title>eMayor Client Software Check and Update</title>
</head>



<%--  Note: This JSP fills in 5 different partitions (0..4) which
            are created from the template.htm file.
            See the instructions in this template.htm file.
            The processing logic in the sources is located in
            org.emayor.webtier.shared.HTMLTemplate and
            org.emayor.webtier.shared.ExtendedActionForm  äöü --%>	



<bean:write name="softwareCheckForm" property="partition0" filter="false"/>



<%--  ..................... JSP LOGIC START .....................  --%>	
<%--  The following are table rows associated with the available languages --%>
<%--  Placeholder row, so next row is at the bottom: --%>
<%--  colspan = number of available languages:       --%>
                <tr>
                  <td colspan="<bean:write name="softwareCheckForm" property="numberOfLanguages" />" rowspan="1">&nbsp;</td>
                </tr>
                <tr>


<logic:equal name="softwareCheckForm" property="language" value="ger">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="softwareCheckForm" resourceElementKey="<%=TextResourceKeys.ShortCutGerman%>" /></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="softwareCheckForm" property="language" value="ger">
                  <td style="text-align: center;">&nbsp;
                      <html:link action="login?language=ger" 
					             paramName="softwareCheckForm" 
					             paramProperty="municipalityNameKey"
					             paramId="municipalityNameKey" ><emayorbean:writeLanguage name="softwareCheckForm" resourceElementKey="<%=TextResourceKeys.ShortCutGerman%>"/></html:link>&nbsp;</td>
</logic:notEqual>

<logic:equal name="softwareCheckForm" property="language" value="en">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="softwareCheckForm" resourceElementKey="<%=TextResourceKeys.ShortCutEnglish%>" /></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="softwareCheckForm" property="language" value="en">
                  <td style="text-align: center;">&nbsp;
                      <html:link action="login?language=en" 
					             paramName="softwareCheckForm" 
					             paramProperty="municipalityNameKey"
					             paramId="municipalityNameKey" ><emayorbean:writeLanguage name="softwareCheckForm" resourceElementKey="<%=TextResourceKeys.ShortCutEnglish%>"/></html:link>&nbsp;</td>
</logic:notEqual>

<logic:equal name="softwareCheckForm" property="language" value="it">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="softwareCheckForm" resourceElementKey="<%=TextResourceKeys.ShortCutItalian%>" /></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="softwareCheckForm" property="language" value="it">
                  <td style="text-align: center;">&nbsp;
                      <html:link action="login?language=it" 
					             paramName="softwareCheckForm" 
					             paramProperty="municipalityNameKey"
					             paramId="municipalityNameKey" ><emayorbean:writeLanguage name="softwareCheckForm" resourceElementKey="<%=TextResourceKeys.ShortCutItalian%>"/></html:link>&nbsp;</td>
</logic:notEqual>

<logic:equal name="softwareCheckForm" property="language" value="sp">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="softwareCheckForm" resourceElementKey="<%=TextResourceKeys.ShortCutSpanish%>" /></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="softwareCheckForm" property="language" value="sp">
                  <td style="text-align: center;">&nbsp;
                      <html:link action="login?language=sp" 
					             paramName="softwareCheckForm" 
					             paramProperty="municipalityNameKey"
					             paramId="municipalityNameKey" ><emayorbean:writeLanguage name="softwareCheckForm" resourceElementKey="<%=TextResourceKeys.ShortCutSpanish%>"/></html:link>&nbsp;</td>
</logic:notEqual>
</tr>
<%--  ..................... JSP LOGIC END .....................  --%>	



<bean:write name="softwareCheckForm" property="partition1" filter="false"/>



<%--  ..................... JSP LOGIC START .....................  --%>	
<%-- Index page link: Use the global action forward: --%>
<tr><td>&nbsp;<html:link forward="municipalityList">
    Index
</html:link>&nbsp;</td>
<%-- name of municipality as link: --%>
<td><bean:write name="softwareCheckForm" property="nameOfMunicipality" /></td>
<td>&nbsp;login&nbsp;</td>                
<td style="width: 50%;"></td>                
<%--  ..................... JSP LOGIC END .....................  --%>	
                  <td style="width: 40%;"></td>
                  <td style="text-align: center; background-color: rgb(240, 240, 220);">
 &nbsp;<html:link action="help.do?"
                  paramName="softwareCheckForm" 
                  paramProperty="helpTopicKey"
                  paramId="helpTopicKey">
       <emayorbean:writeLanguage name="softwareCheckForm" resourceElementKey="<%=TextResourceKeys.Help%>"/></html:link>&nbsp;</td>
</tr>
                
                
<bean:write name="softwareCheckForm" property="partition2" filter="false"/>


<tr>
<td>
<%--  ..................... JSP LOGIC START .....................  --%>	
<p>
<emayorbean:writeLanguage name="softwareCheckForm" resourceElementKey="<%=TextResourceKeys.WaitForServiceRequestResponseKey%>" />
<p>
<applet name="org.emayor.RepresentationLayer.ClientTier.SoftwareCheck.SoftwareCheckApplet"
        code="org.emayor.RepresentationLayer.ClientTier.SoftwareCheck.SoftwareCheckApplet"
        codebase="."
        archive="SoftwareCheckApplet.jar"
        width="500"
        height="90"
        ALT="This applet tests your client software and installs or updates it if required.">
  <param name="BackgroundColor" value="234,228,212"/>
  <param name="ClientSoftwareChecking" value="<bean:write name="softwareCheckForm" property="clientSoftwareChecking_Message"/>"/>
  <param name="ClientSoftwareIsUpToDate" value="<bean:write name="softwareCheckForm" property="clientSoftwareIsUpToDate_Message"/>"/>
  <param name="ClientSoftwareMustBeInstalled" value="<bean:write name="softwareCheckForm" property="clientSoftwareMustBeInstalled_Message"/>"/>
  <param name="ClientSoftwareMustBeUpdated" value="<bean:write name="softwareCheckForm" property="clientSoftwareMustBeUpdated_Message"/>"/>
  <param name="ClientSoftwareHasBeenInstalled" value="<bean:write name="softwareCheckForm" property="clientSoftwareHasBeenInstalled_Message"/>"/>
  <param name="ClientSoftwareHasBeenUpdated" value="<bean:write name="softwareCheckForm" property="clientSoftwareHasBeenUpdated_Message"/>"/>
  <param name="ClientBrowserRestart" value="<bean:write name="softwareCheckForm" property="clientBrowserRestart_Message"/>"/>
  <param name="Proceed" value="<bean:write name="softwareCheckForm" property="proceed_Message"/>"/>
  <param name="Cancel" value="<bean:write name="softwareCheckForm" property="cancel_Message"/>"/>
  <param name="NextPageURL" value="<bean:write name="softwareCheckForm" property="municipalityURL"/>"/>
  <param name="LoginPageURL" value="<bean:write name="softwareCheckForm" property="loginPageURL"/>"/>
  <param name="MD5_RequestURL" value="/eMayor2.0/softwareCheck.do?do=getMD5CheckSumList"/>
  <param name="ClientFileRequestURL" value="/eMayor2.0/softwareCheck.do?do=getClientFile"/>
  <param name="Language" value="<bean:write name="softwareCheckForm" property="language" />" />
  <param name="cache_option" value="Plugin"/> 
Your browser must be configured for displaying Java V.1.5 applets.
</applet>
<%--  ..................... JSP LOGIC END .....................  --%>	
</td>
</tr>


<tr>
  <td>
    <applet name="org.emayor.RepresentationLayer.ClientTier.ProcessStep.ProcessStep_Applet"
        code="org.emayor.RepresentationLayer.ClientTier.ProcessStep.ProcessStep_Applet"
        codebase="."
        archive="ProcessStepApplet.jar"
        width="600"
        height="32"
        ALT="This applet shows a process step bar for your situational awareness.">
        <param name="BackgroundColor" value="234,228,212"/>
        <param name="ProcessStepTextList" value="<bean:write name="softwareCheckForm" property="processStepTextList"/>"/>
        <param name="ProcessStepDescriptionList" value="<bean:write name="softwareCheckForm" property="processStepDescriptionList"/>"/>
        <param name="ProcessStepIndex" value="<bean:write name="softwareCheckForm" property="processStepIndex"/>"/>
        Your browser must be configured for displaying Java V.1.5 applets.
    </applet>
  </td>
</tr>


<bean:write name="softwareCheckForm" property="partition3" filter="false"/>


<emayorbean:writeLanguage name="softwareCheckForm" resourceElementKey="<%=TextResourceKeys.AbouteMayor%>"/>


<bean:write name="softwareCheckForm" property="partition4" filter="false"/>


</html>
