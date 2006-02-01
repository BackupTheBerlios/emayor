package org.emayor.eMayorWebTier.struts.Logout;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class LogoutAction extends Action
{
  public ActionForward execute( ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response )
  {
    LogoutForm logoutForm = (LogoutForm)form;
    System.out.println("LogoutAction.execute().");
    String languageParameterValue = request.getParameter("language");
    if( languageParameterValue == null ) languageParameterValue="en";
    System.out.println("LogoutAction.execute() language= " + languageParameterValue);
    // Initialize the form with this:
    logoutForm.initializeAttibutes( request.getSession(),languageParameterValue );
    return mapping.findForward("logout");  
  }
  
  
}
