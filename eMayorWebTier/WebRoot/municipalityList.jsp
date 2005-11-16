<%@ page language="Java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<sslext:pageScheme secure="false" />

<%@ taglib uri="/WEB-INF/emayor-bean.tld" prefix="emayorbean" %>
<%--  The class TextResourceKeys only holds [static] class attributes, no instance attributes.
      so automatic instantiation here doesn't hurt.  --%>
<jsp:useBean id="TextResourceKeys" class="org.emayor.webtier.shared.TextResourceKeys" />	

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html> 
	<head>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8"></meta>
		<title><emayorbean:writeLanguage name="municipalityListForm" resourceElementKey="<%=TextResourceKeys.MunicipalitiesPageTitle%>"/></title>
	</head>


<%--  Note: This JSP fills in 5 different partitions (0..4) which
            are created from the template.htm file.
            See the instructions in this template.htm file.
            The processing logic in the sources is located in
            org.emayor.webtier.shared.HTMLTemplate and
            org.emayor.webtier.shared.ExtendedActionForm  äöü --%>	


<bean:write name="municipalityListForm" property="partition0" filter="false"/>


<%--  JSP logic for the Language Selection  --%>	
<%--  The following are table rows associated with the available languages --%>
<%--  Placeholder row, so next row is at the bottom: --%>
<%--  colspan = number of available languages:       --%>
<tr>
   <td colspan="<bean:write name="municipalityListForm" property="numberOfLanguages" />" rowspan="1">&nbsp;</td>
</tr>
<tr>
<logic:equal name="municipalityListForm" property="language" value="ger">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="municipalityListForm" resourceElementKey="<%=TextResourceKeys.ShortCutGerman%>"/></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="municipalityListForm" property="language" value="ger">
<td style="text-align: center;">&nbsp;<a
 href="default.do?language=ger"><emayorbean:writeLanguage name="municipalityListForm" resourceElementKey="<%=TextResourceKeys.ShortCutGerman%>"/></a>&nbsp;</td>
</logic:notEqual>

<logic:equal name="municipalityListForm" property="language" value="en">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="municipalityListForm" resourceElementKey="<%=TextResourceKeys.ShortCutEnglish%>"/></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="municipalityListForm" property="language" value="en">
<td style="text-align: center;">&nbsp;<a
 href="default.do?language=en"><emayorbean:writeLanguage name="municipalityListForm" resourceElementKey="<%=TextResourceKeys.ShortCutEnglish%>"/></a>&nbsp;</td>
</logic:notEqual>

<logic:equal name="municipalityListForm" property="language" value="it">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="municipalityListForm" resourceElementKey="<%=TextResourceKeys.ShortCutItalian%>"/></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="municipalityListForm" property="language" value="it">
<td style="text-align: center;">&nbsp;<a
 href="default.do?language=it"><emayorbean:writeLanguage name="municipalityListForm" resourceElementKey="<%=TextResourceKeys.ShortCutItalian%>"/></a>&nbsp;</td>
</logic:notEqual>

<logic:equal name="municipalityListForm" property="language" value="sp">
<td style="text-align: center; background-color: rgb(201, 60, 56);">&nbsp;<span
 style="color: rgb(255, 255, 255);"><emayorbean:writeLanguage name="municipalityListForm" resourceElementKey="<%=TextResourceKeys.ShortCutSpanish%>"/></span>&nbsp;</td>
</logic:equal>
<logic:notEqual name="municipalityListForm" property="language" value="sp">
<td style="text-align: center;">&nbsp;<a
 href="default.do?language=sp"><emayorbean:writeLanguage name="municipalityListForm" resourceElementKey="<%=TextResourceKeys.ShortCutSpanish%>"/></a>&nbsp;</td>
</logic:notEqual>
</tr>
<%--  JSP logic for the Language Selection  END --%>	


<bean:write name="municipalityListForm" property="partition1" filter="false"/>


<%--  JSP logic for the navigation bar and the help link  START --%>	                  
<tr><td>&nbsp;<strong><font color="#000080"><emayorbean:writeLanguage name="municipalityListForm" resourceElementKey="<%=TextResourceKeys.Municipalities%>"/></font></strong>&nbsp;</td>
                  <td style="width: 99%;"></td>
                  <td
 style="text-align: center; background-color: rgb(240, 240, 220);">
 &nbsp;<html:link action="help.do?"
                  paramName="municipalityListForm" 
                  paramProperty="helpTopicKey"
                  paramId="helpTopicKey">
       <emayorbean:writeLanguage name="municipalityListForm" resourceElementKey="<%=TextResourceKeys.Help%>"/></html:link>&nbsp;</td>
</tr>
<%--  JSP logic for the navigation bar and the help link  END --%>	                  

                  

<bean:write name="municipalityListForm" property="partition2" filter="false"/>
       


<%--  JSP logic for the spefic content  START --%>	                  
<%-- start with an iterate over the municipalities --%>
<tr><td><emayorbean:writeLanguage name="municipalityListForm" resourceElementKey="<%=TextResourceKeys.AvailableMunicipalities%>"/>:
	<logic:iterate name="municipalityListForm" property="linkParameters" id="municipalityParameter">
    <p>
<%-- name as link: --%>
<%-- Note: This link carries multiple parameters, which are
                 contained in the serviceParameters Map attribute. --%>
      <html:link action="login" 
					     name="municipalityParameter" 
					     property="parameterMap" >
         <bean:write name="municipalityParameter" property="nameOfMunicipality" />
      </html:link>
    </p>
    </logic:iterate>
<p>&nbsp;<p><%-- Display the about text below that list: --%>
<emayorbean:writeLanguage name="municipalityListForm" resourceElementKey="<%=TextResourceKeys.MessageAboutEMayorText%>"/>
                  </td>
</tr>
<%--  JSP logic for the spefic content  END --%>	                  


<bean:write name="municipalityListForm" property="partition3" filter="false"/>



<%--  JSP logic for the text used for the link to the About page  START --%>	                  
<emayorbean:writeLanguage name="municipalityListForm" resourceElementKey="<%=TextResourceKeys.AbouteMayor%>"/>
<%--  JSP logic for the text used for the link to the About page  END--%>	                  


<bean:write name="municipalityListForm" property="partition4" filter="false"/>


</html>
