<%@ page language="Java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>



<%@ taglib uri="/WEB-INF/emayor-bean.tld" prefix="emayorbean" %>
<%--  The class TextResourceKeys only holds [static] class attributes, no instance attributes.
      so automatic instantiation here doesn't hurt.  --%>
<jsp:useBean id="TextResourceKeys" class="org.emayor.webtier.shared.TextResourceKeys" />	


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"></meta>
	<title>eMayor Incorrect WebTier Installation Information</title>
</head>



<%--  Note: This JSP fills in 5 different partitions (0..4) which
            are created from the template.htm file.
            See the instructions in this template.htm file.
            The processing logic in the sources is located in
            org.emayor.webtier.shared.HTMLTemplate and
            org.emayor.webtier.shared.ExtendedActionForm  äöü --%>	



<bean:write name="loginForm" property="partition0" filter="false"/>



<%--  ..................... JSP LOGIC START .....................  --%>	
<%--  The following are table rows associated with the available languages --%>
<%--  Placeholder row, so next row is at the bottom: --%>
<%--  colspan = number of available languages:       --%>
                <tr>
                  <td colspan="<bean:write name="loginForm" property="numberOfLanguages" />" rowspan="1">&nbsp;</td>
                </tr>
                <tr>


<logic:equal name="loginForm" property="language" value="ger">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="loginForm" resourceElementKey="<%=TextResourceKeys.ShortCutGerman%>" /></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="loginForm" property="language" value="ger">
                  <td style="text-align: center;">&nbsp;
                      <html:link action="login?language=ger" 
					             paramName="loginForm" 
					             paramProperty="municipalityNameKey"
					             paramId="municipalityNameKey" ><emayorbean:writeLanguage name="loginForm" resourceElementKey="<%=TextResourceKeys.ShortCutGerman%>"/></html:link>&nbsp;</td>
</logic:notEqual>

<logic:equal name="loginForm" property="language" value="en">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="loginForm" resourceElementKey="<%=TextResourceKeys.ShortCutEnglish%>" /></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="loginForm" property="language" value="en">
                  <td style="text-align: center;">&nbsp;
                      <html:link action="login?language=en" 
					             paramName="loginForm" 
					             paramProperty="municipalityNameKey"
					             paramId="municipalityNameKey" ><emayorbean:writeLanguage name="loginForm" resourceElementKey="<%=TextResourceKeys.ShortCutEnglish%>"/></html:link>&nbsp;</td>
</logic:notEqual>

<logic:equal name="loginForm" property="language" value="it">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="loginForm" resourceElementKey="<%=TextResourceKeys.ShortCutItalian%>" /></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="loginForm" property="language" value="it">
                  <td style="text-align: center;">&nbsp;
                      <html:link action="login?language=it" 
					             paramName="loginForm" 
					             paramProperty="municipalityNameKey"
					             paramId="municipalityNameKey" ><emayorbean:writeLanguage name="loginForm" resourceElementKey="<%=TextResourceKeys.ShortCutItalian%>"/></html:link>&nbsp;</td>
</logic:notEqual>

<logic:equal name="loginForm" property="language" value="sp">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="loginForm" resourceElementKey="<%=TextResourceKeys.ShortCutSpanish%>" /></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="loginForm" property="language" value="sp">
                  <td style="text-align: center;">&nbsp;
                      <html:link action="login?language=sp" 
					             paramName="loginForm" 
					             paramProperty="municipalityNameKey"
					             paramId="municipalityNameKey" ><emayorbean:writeLanguage name="loginForm" resourceElementKey="<%=TextResourceKeys.ShortCutSpanish%>"/></html:link>&nbsp;</td>
</logic:notEqual>
</tr>
<%--  ..................... JSP LOGIC END .....................  --%>	



<bean:write name="loginForm" property="partition1" filter="false"/>



<%--  ..................... JSP LOGIC START .....................  --%>	
<%-- Index page link: Use the global action forward: --%>
<tr><td>&nbsp;<html:link forward="municipalityList">
    Index
</html:link>&nbsp;</td>
<%-- name of municipality as link: --%>
<td><bean:write name="loginForm" property="nameOfMunicipality" /></td>
<td>&nbsp;login&nbsp;</td>                
<td style="width: 50%;></td>                
<%--  ..................... JSP LOGIC END .....................  --%>	
                  <td style="width: 40%;"></td>
                  <td style="text-align: center; background-color: rgb(240, 240, 220);">
 &nbsp;<html:link action="help.do?"
                  paramName="loginForm" 
                  paramProperty="helpTopicKey"
                  paramId="helpTopicKey">
       <emayorbean:writeLanguage name="loginForm" resourceElementKey="<%=TextResourceKeys.Help%>"/></html:link>&nbsp;</td>
</tr>
                
                
<bean:write name="loginForm" property="partition2" filter="false"/>


<tr>
<td>
<%--  ..................... JSP LOGIC START .....................  --%>	
<p>
The web tier is not correctly installed and contains files with incorrect versions:
<p>
<bean:write name="loginForm" property="informationAboutFilesWithIncorrectVersions" filter="false"/>
</p>
<%--  ..................... JSP LOGIC END .....................  --%>	
</td>
</tr>


<bean:write name="loginForm" property="partition3" filter="false"/>


<emayorbean:writeLanguage name="loginForm" resourceElementKey="<%=TextResourceKeys.AbouteMayor%>"/>


<bean:write name="loginForm" property="partition4" filter="false"/>


</html>
