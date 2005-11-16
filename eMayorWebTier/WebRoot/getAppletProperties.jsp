<%@ page language="Java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/sslext.tld" prefix="sslext"%>
<sslext:pageScheme secure="true" />
<%@ taglib uri="/WEB-INF/emayor-bean.tld" prefix="emayorbean" %>
<%--  The class TextResourceKeys only holds [static] class attributes, no instance attributes.
      so automatic instantiation here doesn't hurt.  --%>
<jsp:useBean id="TextResourceKeys" class="org.emayor.webtier.shared.TextResourceKeys" />	
<%--  This JSP does not contain html content  --%>	
<%--  It is called by the emayor client applet for obtaining  --%>	
<%--  the required applet property file in one string. Get this one:  äöü --%>

<bean:write name="serviceForm" property="appletPropertyFile" filter="false"/>
