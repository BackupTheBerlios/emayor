<%@ page language="Java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%@ taglib uri="/WEB-INF/emayor-bean.tld" prefix="emayorbean" %>
<%--  The class TextResourceKeys only holds [static] class attributes, no instance attributes.
      so automatic instantiation here doesn't hurt.  --%>
<jsp:useBean id="TextResourceKeys" class="org.emayor.webtier.shared.TextResourceKeys" />	


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>eMayor Help Page</title>
</head>


<%--  Note: This JSP fills in 5 different partitions (0..4) which
            are created from the template.htm file.
            See the instructions in this template.htm file.
            The processing logic in the sources is located in
            org.emayor.webtier.shared.HTMLTemplate and
            org.emayor.webtier.shared.ExtendedActionForm  äöü --%>	


<bean:write name="helpForm" property="partition0" filter="false"/>



<%--  ..................... JSP LOGIC START .....................  --%>	
<%--  The following are table rows associated with the available languages --%>
<%--  Placeholder row, so next row is at the bottom: --%>
<%--  colspan = number of available languages:       --%>
                <tr>
                  <td colspan="<bean:write name="helpForm" property="numberOfLanguages" />" rowspan="1">&nbsp;</td>
                </tr>
                <tr>

<logic:equal name="helpForm" property="language" value="ger">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="helpForm" resourceElementKey="<%=TextResourceKeys.ShortCutGerman%>" /></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="helpForm" property="language" value="ger">
                  <td style="text-align: center;">&nbsp;
                      <html:link action="help.do?language=ger" 
					               paramName="helpForm" 
					               paramProperty="helpTopicKey"
					               paramId="helpTopicKey" ><emayorbean:writeLanguage name="helpForm" resourceElementKey="<%=TextResourceKeys.ShortCutGerman%>"/></html:link>&nbsp;</td>
</logic:notEqual>

<logic:equal name="helpForm" property="language" value="en">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="helpForm" resourceElementKey="<%=TextResourceKeys.ShortCutEnglish%>" /></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="helpForm" property="language" value="en">
                  <td style="text-align: center;">&nbsp;
                      <html:link action="help.do?language=en" 
					               paramName="helpForm"
					               paramProperty="helpTopicKey"
					               paramId="helpTopicKey" ><emayorbean:writeLanguage name="helpForm" resourceElementKey="<%=TextResourceKeys.ShortCutEnglish%>"/></html:link>&nbsp;</td>
</logic:notEqual>

<logic:equal name="helpForm" property="language" value="it">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="helpForm" resourceElementKey="<%=TextResourceKeys.ShortCutItalian%>" /></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="helpForm" property="language" value="it">
                  <td style="text-align: center;">&nbsp;
                      <html:link action="help.do?language=it" 
					               paramName="helpForm" 
					               paramProperty="helpTopicKey"
					               paramId="helpTopicKey" ><emayorbean:writeLanguage name="helpForm" resourceElementKey="<%=TextResourceKeys.ShortCutItalian%>"/></html:link>&nbsp;</td>
</logic:notEqual>

<logic:equal name="helpForm" property="language" value="sp">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="helpForm" resourceElementKey="<%=TextResourceKeys.ShortCutSpanish%>" /></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="helpForm" property="language" value="sp">
                  <td style="text-align: center;">&nbsp;
                      <html:link action="help.do?language=sp" 
					               paramName="helpForm" 
					               paramProperty="helpTopicKey"
					               paramId="helpTopicKey" ><emayorbean:writeLanguage name="helpForm" resourceElementKey="<%=TextResourceKeys.ShortCutSpanish%>"/></html:link>&nbsp;</td>
</logic:notEqual>
</tr>
<%--  ..................... JSP LOGIC END .....................  --%>	



<bean:write name="helpForm" property="partition1" filter="false"/>



<%--  ..................... JSP LOGIC START .....................  --%>	
<%-- Index page link: Use the global action forward: --%>
<tr><td>&nbsp;<html:link forward="municipalityList">
    <strong>Index</strong>
</html:link>&nbsp;</td>
<td><strong>&nbsp;<emayorbean:writeLanguage name="helpForm" resourceElementKey="<%=TextResourceKeys.Help%>"/>&nbsp;</strong></td>
<td style="width: 50%;"><strong>&nbsp;<bean:write name="helpForm" property="helpTopic" />&nbsp;</strong></td>
<%--  ..................... JSP LOGIC END .....................  --%>	
<td style="width: 40%;"></td>
</tr>


<bean:write name="helpForm" property="partition2" filter="false"/>


<tr>
<td>
<%--  ..................... JSP LOGIC START .....................  --%>	
<%-- start with an iterate over the services --%>
<br>&nbsp;<br>
under construction...
<br>
<%--  ..................... JSP LOGIC END .....................  --%>	            
</td>
</tr>


<bean:write name="helpForm" property="partition3" filter="false"/>


<emayorbean:writeLanguage name="helpForm" resourceElementKey="<%=TextResourceKeys.AbouteMayor%>"/>


<bean:write name="helpForm" property="partition4" filter="false"/>


</html>
