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
	<title>eMayor <bean:write name="municipalityForm" property="nameOfMunicipality"/></title>
</head>



<%--  Note: This JSP fills in 5 different partitions (0..4) which
            are created from the template.htm file.
            See the instructions in this template.htm file.
            The processing logic in the sources is located in
            org.emayor.webtier.shared.HTMLTemplate and
            org.emayor.webtier.shared.ExtendedActionForm  äöü --%>	



<bean:write name="municipalityForm" property="partition0" filter="false"/>


<%--  ..................... JSP LOGIC START .....................  --%>	
<%--  The following are table rows associated with the available languages --%>
<%--  Placeholder row, so next row is at the bottom: --%>
<%--  colspan = number of available languages:       --%>
                <tr>
                  <td colspan="<bean:write name="municipalityForm" property="numberOfLanguages" />" rowspan="1">&nbsp;</td>
                </tr>
                <tr>


<logic:equal name="municipalityForm" property="language"
             value="ger">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="municipalityForm" resourceElementKey="<%=TextResourceKeys.ShortCutGerman%>" /></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="municipalityForm" property="language"
             value="ger">
                  <td style="text-align: center;">&nbsp;
                      <html:link action="municipality.do?language=ger&do=showIndex" 
                                 paramName="municipalityForm" 
                                 paramProperty="nameKeyOfMunicipality" 
                                 paramId="municipalityNameKey"><emayorbean:writeLanguage name="municipalityForm" resourceElementKey="<%=TextResourceKeys.ShortCutGerman%>" /></html:link>&nbsp;</td>
</logic:notEqual>

<logic:equal name="municipalityForm" property="language"
             value="en">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="municipalityForm" resourceElementKey="<%=TextResourceKeys.ShortCutEnglish%>" /></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="municipalityForm" property="language"
             value="en">
                  <td style="text-align: center;">&nbsp;
                      <html:link action="municipality.do?language=en&do=showIndex" 
                                 paramName="municipalityForm" 
                                 paramProperty="nameKeyOfMunicipality" 
                                 paramId="municipalityNameKey"><emayorbean:writeLanguage name="municipalityForm" resourceElementKey="<%=TextResourceKeys.ShortCutEnglish%>" /></html:link>&nbsp;</td>
</logic:notEqual> 

<logic:equal name="municipalityForm" property="language"
             value="it">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="municipalityForm" resourceElementKey="<%=TextResourceKeys.ShortCutItalian%>" /></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="municipalityForm" property="language"
             value="it">
                  <td style="text-align: center;">&nbsp;
                      <html:link action="municipality.do?language=it&do=showIndex" 
                                 paramName="municipalityForm" 
                                 paramProperty="nameKeyOfMunicipality" 
                                 paramId="municipalityNameKey"><emayorbean:writeLanguage name="municipalityForm" resourceElementKey="<%=TextResourceKeys.ShortCutItalian%>" /></html:link>&nbsp;</td>
</logic:notEqual>

<logic:equal name="municipalityForm" property="language"
             value="sp">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="municipalityForm" resourceElementKey="<%=TextResourceKeys.ShortCutSpanish%>" /></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="municipalityForm" property="language"
             value="sp">
                  <td style="text-align: center;">&nbsp;
                      <html:link action="municipality.do?language=sp&do=showIndex" 
                                 paramName="municipalityForm" 
                                 paramProperty="nameKeyOfMunicipality" 
                                 paramId="municipalityNameKey"><emayorbean:writeLanguage name="municipalityForm" resourceElementKey="<%=TextResourceKeys.ShortCutSpanish%>" /></html:link>&nbsp;</td>
</logic:notEqual>
</tr>
<%--  ..................... JSP LOGIC END .....................  --%>	


<bean:write name="municipalityForm" property="partition1" filter="false"/>
                  

                  
<%-- Start of the navigation bar --%>                  
<%--  ..................... JSP LOGIC START .....................  --%>	
<%-- Write the name of the municipality: --%>
<%-- Index page link: Use the global action forward: --%>
<tr><td>&nbsp;<html:link forward="municipalityList">
    Index
</html:link>&nbsp;</td>
<td>&nbsp;<bean:write name="municipalityForm" property="nameOfMunicipality" />&nbsp;</td>
<%--  ..................... JSP LOGIC END .....................  --%>	
<%-- End of the navigation bar --%>                                  
                  <td style="width: 99%;"></td>
                  <td style="text-align: center; background-color: rgb(240, 240, 220);">
 &nbsp;<html:link action="help.do?"
                  paramName="municipalityForm" 
                  paramProperty="helpTopicKey"
                  paramId="helpTopicKey">
       <emayorbean:writeLanguage name="municipalityForm" resourceElementKey="<%=TextResourceKeys.Help%>"/></html:link>&nbsp;</td>
</tr>


<bean:write name="municipalityForm" property="partition2" filter="false"/>


<tr>
<td><emayorbean:writeLanguage name="municipalityForm" resourceElementKey="<%=TextResourceKeys.AvailableServices%>" />:
<%--  ..................... JSP LOGIC START .....................  --%>	
<%-- Start with an iterate over the services --%>
	<logic:iterate name="municipalityForm" property="services" id="service">
    <p>
      <%-- name as link: --%>
      <%-- Note: This link carries multiple parameters, which are
                 contained in the serviceParameters Map attribute. --%>
      <html:link action="service.do?do=executeService" 
                 name="service" 
                 property="serviceParameters" >
         <bean:write name="service" property="nameOfService" />
      </html:link>
    </p>
    </logic:iterate>

    <p>
      <html:link action="municipality.do?do=logout" 
                 paramName="municipalityForm" 
                 paramProperty="nameKeyOfMunicipality" 
                 paramId="municipalityNameKey"><emayorbean:writeLanguage name="municipalityForm" resourceElementKey="<%=TextResourceKeys.Logout%>" /></html:link>
    </p>

<%--  ..................... JSP LOGIC END .....................  --%>	
</td>
</tr>


<bean:write name="municipalityForm" property="partition3" filter="false"/>


<emayorbean:writeLanguage name="municipalityForm" resourceElementKey="<%=TextResourceKeys.AbouteMayor%>"/>


<bean:write name="municipalityForm" property="partition4" filter="false"/>


</html>
