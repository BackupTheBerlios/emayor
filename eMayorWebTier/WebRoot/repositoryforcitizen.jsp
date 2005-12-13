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
	<title>eMayor <emayorbean:writeLanguage name="serviceForm" resourceElementKey="<%=TextResourceKeys.RepositoryPageTitle%>"/></title>
</head>


<%--  Note: This JSP fills in 5 different partitions (0..4) which
            are created from the template.htm file.
            See the instructions in this template.htm file.
            The processing logic in the sources is located in
            org.emayor.webtier.shared.HTMLTemplate and
            org.emayor.webtier.shared.ExtendedActionForm  äöü --%>	


<bean:write name="serviceForm" property="partition0" filter="false"/>



<%--  ..................... JSP LOGIC START .....................  --%>	
<%--  The following are table rows associated with the available languages --%>
<%--  Placeholder row, so next row is at the bottom: --%>
<%--  colspan = number of available languages:       --%>
                <tr>
                  <td colspan="<bean:write name="serviceForm" property="numberOfLanguages" />" rowspan="1">&nbsp;</td>
                </tr>
                <tr>

<logic:equal name="serviceForm" property="language" value="ger">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="serviceForm" resourceElementKey="<%=TextResourceKeys.ShortCutGerman%>" /></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="serviceForm" property="language" value="ger">
                  <td style="text-align: center;">&nbsp;
                      <sslext:link action="service.do?language=ger&do=executeService" 
					               name="serviceForm" 
					               property="serviceParametersWithoutLanguage" ><emayorbean:writeLanguage name="serviceForm" resourceElementKey="<%=TextResourceKeys.ShortCutGerman%>"/></sslext:link>&nbsp;</td>
</logic:notEqual>

<logic:equal name="serviceForm" property="language" value="en">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="serviceForm" resourceElementKey="<%=TextResourceKeys.ShortCutEnglish%>" /></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="serviceForm" property="language" value="en">
                  <td style="text-align: center;">&nbsp;
                      <sslext:link action="service.do?language=en&do=executeService" 
					               name="serviceForm" 
					               property="serviceParametersWithoutLanguage" ><emayorbean:writeLanguage name="serviceForm" resourceElementKey="<%=TextResourceKeys.ShortCutEnglish%>"/></sslext:link>&nbsp;</td>
</logic:notEqual>

<logic:equal name="serviceForm" property="language" value="it">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="serviceForm" resourceElementKey="<%=TextResourceKeys.ShortCutItalian%>" /></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="serviceForm" property="language" value="it">
                  <td style="text-align: center;">&nbsp;
                      <sslext:link action="service.do?language=it&do=executeService" 
					               name="serviceForm" 
					               property="serviceParametersWithoutLanguage" ><emayorbean:writeLanguage name="serviceForm" resourceElementKey="<%=TextResourceKeys.ShortCutItalian%>"/></sslext:link>&nbsp;</td>
</logic:notEqual>

<logic:equal name="serviceForm" property="language" value="sp">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="serviceForm" resourceElementKey="<%=TextResourceKeys.ShortCutSpanish%>" /></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="serviceForm" property="language" value="sp">
                  <td style="text-align: center;">&nbsp;
                      <sslext:link action="service.do?language=sp&do=executeService" 
					               name="serviceForm" 
					               property="serviceParametersWithoutLanguage" ><emayorbean:writeLanguage name="serviceForm" resourceElementKey="<%=TextResourceKeys.ShortCutSpanish%>"/></sslext:link>&nbsp;</td>
</logic:notEqual>
</tr>
<%--  ..................... JSP LOGIC END .....................  --%>	



<bean:write name="serviceForm" property="partition1" filter="false"/>



<%--  ..................... JSP LOGIC START .....................  --%>	
<%-- Index page link: Use the global action forward: --%>
<tr><td>&nbsp;<html:link forward="municipalityList">
    Index
</html:link>&nbsp;</td>
<%-- name of municipality as link: --%>
<td>&nbsp;<html:link action="municipality.do?do=showIndex" 
                   paramName="serviceForm" 
                   paramProperty="municipalityNameKey" 
                   paramId="<%=TextResourceKeys.MunicipalityNameKeyTag%>">
   <bean:write name="serviceForm" property="nameOfMunicipality" />
</html:link>&nbsp;</td>

<td style="width: 50%;"><emayorbean:writeLanguage name="serviceForm" resourceElementKey="<%=TextResourceKeys.RepositoryPageTitle%>"/>&nbsp;
(<emayorbean:writeLanguage name="serviceForm" resourceElementKey="<%=TextResourceKeys.TitleCitizen%>"/>)</td>
<%--  ..................... JSP LOGIC END .....................  --%>	
                  <td style="width: 40%;"></td>
                  <td style="text-align: center; background-color: rgb(240, 240, 220);">
 &nbsp;<html:link action="help.do?"
                  paramName="serviceForm" 
                  paramProperty="helpTopicKey"
                  paramId="helpTopicKey">
       <emayorbean:writeLanguage name="serviceForm" resourceElementKey="<%=TextResourceKeys.Help%>"/></html:link>&nbsp;</td>
</tr>


<bean:write name="serviceForm" property="partition2" filter="false"/>


    <tr>
    <td>&nbsp;</td>
    <td style="text-align: left;"><span style="font-weight: bold;"><emayorbean:writeLanguage name="serviceForm" resourceElementKey="<%=TextResourceKeys.RepositoryDocument%>"/></span></td>
    <td style="text-align: left;"><span style="font-weight: bold;"><emayorbean:writeLanguage name="serviceForm" resourceElementKey="<%=TextResourceKeys.RepositoryDateOfRequest%>"/></span></td>
    <td style="text-align: left;"><span style="font-weight: bold;"><emayorbean:writeLanguage name="serviceForm" resourceElementKey="<%=TextResourceKeys.RepositoryAvailableUntil%>"/></span></td>
    </tr>
<%--  ..................... JSP LOGIC START .....................  --%>	
<%-- Show the repository entries --%>
    <% int i=1; %>
	<logic:iterate name="serviceForm" property="repositoryDocuments" id="repositorydocument">    
    <tr>
    <td>
       <%= i %><% i++; %>
    </td>
    <td style="text-align: left;">
      <html:form action="service.do?do=showDocument">
         <span style="font-weight: bold;"><bean:write name="repositorydocument" property="documentTitle" /></span>&nbsp;
         <br>
         <bean:write name="repositorydocument" property="documentStatus" />&nbsp;
         <br>
         <bean:write name="repositorydocument" property="documentRemarks" />&nbsp;
         <br>
         <html:hidden name="serviceForm" property="municipalityNameKey" />
         <html:hidden name="serviceForm" property="serviceNameKey" />
         <html:hidden name="repositorydocument" property="documentTitle" />
         <html:hidden name="repositorydocument" property="documentIndex" />
         <html:hidden name="serviceForm" property="language" />
         <html:submit><emayorbean:writeLanguage name="serviceForm" resourceElementKey="<%=TextResourceKeys.RepositoryDownload%>"/></html:submit>  
      </html:form>
    </td>
    <td style="text-align: left;">
         <bean:write name="repositorydocument" property="dateOfRequest" />&nbsp;
    </td>
    <td style="text-align: left;">
         <bean:write name="repositorydocument" property="dateOfRemoval" />&nbsp;
    </td>
    </tr>
    </logic:iterate>
<%--  ..................... JSP LOGIC END .....................  --%>



<bean:write name="serviceForm" property="partition3" filter="false"/>


<emayorbean:writeLanguage name="serviceForm" resourceElementKey="<%=TextResourceKeys.AbouteMayor%>"/>


<bean:write name="serviceForm" property="partition4" filter="false"/>


</html>
