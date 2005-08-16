/*
 * Created on 15.08.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.emayor.servicehandling.gui.admin.pe;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.gui.admin.AbstractRequestProcessor;
import org.eMayor.PolicyEnforcement.interfaces.PolicyEnforcementLocal;
import org.emayor.servicehandling.utils.ServiceLocator;
/**
 * @author tcaciuc
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ListPoliciesProcessor extends AbstractRequestProcessor {
	private static final Logger log = Logger.getLogger(ListPoliciesProcessor.class);
	public String process(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		log.debug("-> start processing ...");
		
		// Write the PoliciesList in a session attribute
		try {
        	ServiceLocator locator = ServiceLocator.getInstance();
        
        	PolicyEnforcementLocal pe = locator.getPolicyEnforcementLocal();
        	
        	Set PolicyListSet = pe.FPM_GetPoliciesList(null, null);
        	HttpSession mySession = req.getSession();
        	mySession.setAttribute("PoliciesList",PolicyListSet );
		} catch (Exception e)
		{
			log.debug("ListPoliciesProcessor:: Unable to get the List of Policies" + e.toString());
		}
		
		
		return "adminpe/WelcomePage.jsp";
	}
}
