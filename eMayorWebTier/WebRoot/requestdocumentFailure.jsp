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

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>

<p>
<emayorbean:writeLanguage name="serviceForm" resourceElementKey="<%=TextResourceKeys.ServiceFailureInformation1%>"/>
<br>
<emayorbean:writeLanguage name="serviceForm" resourceElementKey="<%=TextResourceKeys.ServiceFailureInformation2%>"/>

    <p>
      <html:link action="municipality.do?do=logout" 
                 paramName="serviceForm" 
                 paramProperty="municipalityNameKey" 
                 paramId="municipalityNameKey"><emayorbean:writeLanguage name="serviceForm" resourceElementKey="<%=TextResourceKeys.Logout%>" /></html:link>
    </p>
      <html:link action="municipality.do?do=selectMunicipality" 
                 paramName="serviceForm" 
                 paramProperty="municipalityNameKey" 
                 paramId="municipalityNameKey"><bean:write name="serviceForm" property="nameOfMunicipality" /></html:link>
    </p>

</body>
</html>
