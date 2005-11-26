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
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
	<title>Test eMayorForms Service</title>
</head>


<%--  Note: This JSP fills in 5 different partitions (0..4) which
            are created from the template.htm file.
            See the instructions in this template.htm file.
            The processing logic in the sources is located in
            org.emayor.webtier.shared.HTMLTemplate and
            org.emayor.webtier.shared.ExtendedActionForm  äöü --%>	



<bean:write name="formTestForm" property="partition0" filter="false"/>


                <tr>
                  <td colspan="4" rowspan="1">&nbsp;</td>
                </tr>


<bean:write name="formTestForm" property="partition1" filter="false"/>



<%--  ..................... JSP LOGIC START .....................  --%>	
<%-- Index page link: Use the global action forward: --%>
<tr><td>&nbsp;<html:link forward="municipalityList">
    Index
</html:link>&nbsp;</td>
<td style="width: 66%;">eMayorForm Test Service. See User Guide Chapter 5</td>
<%--  ..................... JSP LOGIC END .....................  --%>	
                  <td style="width: 40%;"></td>
                  <td
 style="text-align: center; background-color: rgb(240, 240, 220);">
 &nbsp;<a href="help/help.htm">Help</a>&nbsp;</td>
                </tr>


<bean:write name="formTestForm" property="partition2" filter="false"/>


<tr>
<td style="background-color: rgb(255,255,255);">
<%--  ..................... JSP LOGIC START .....................  --%>	
<%-- Show the content of the test index page --%>

<b>Available e-documents and associated eMayorForm templates
for complete eMayor web and client tier user role interaction tests:</b>
<p>

	<logic:iterate name="formTestForm" property="edocumentparameters" id="entry">
    <hr>
    <p>
    <b>e-document&nbsp;&nbsp;<bean:write name="entry" property="edocumentname" /></b>
    <br>
    in file&nbsp;&nbsp;<bean:write name="entry" property="edocfilename" />
    <p>
    eMayorForm templates:
    <br>
    <logic:equal name="entry" property="hasCitizenTemplate" value="true">
        <html:link action="formtest.do?do=executeService"
                   paramName="entry" 
                   paramProperty="citizenTemplateName"
                   paramId="templateName">
         <bean:write name="entry" property="citizenTemplateName" />
      </html:link>
    <br>    
    </logic:equal>
    <logic:equal name="entry" property="hasCivilServantTemplate" value="true">
        <html:link action="formtest.do?do=executeService"
                   paramName="entry" 
                   paramProperty="civilServantTemplateName"
                   paramId="templateName">
         <bean:write name="entry" property="civilServantTemplateName" />
      </html:link>
    <br>    
    </logic:equal>
    <logic:equal name="entry" property="hasReadOnlyTemplate" value="true">
        <html:link action="formtest.do?do=executeService"
                   paramName="entry" 
                   paramProperty="readOnlyTemplateName"
                   paramId="templateName">
         <bean:write name="entry" property="readOnlyTemplateName" />
      </html:link>
    <br>    
    </logic:equal>
    </p>
    </hr>
    </logic:iterate>


<%--  ..................... JSP LOGIC END .....................  --%>
</td>
</tr>


<bean:write name="formTestForm" property="partition3" filter="false"/>


<emayorbean:writeLanguage name="formTestForm" resourceElementKey="<%=TextResourceKeys.AbouteMayor%>"/>


<bean:write name="formTestForm" property="partition4" filter="false"/>


</html>


