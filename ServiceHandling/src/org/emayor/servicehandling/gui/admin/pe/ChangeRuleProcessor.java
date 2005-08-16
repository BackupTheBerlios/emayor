/*
 * Created on 15.08.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.emayor.servicehandling.gui.admin.pe;
import java.io.IOException;



import java.util.List;


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
public class ChangeRuleProcessor extends AbstractRequestProcessor{


	private static final Logger log = Logger.getLogger(ChangeRuleProcessor.class);
	public String process(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		log.debug("-> start processing ...");
		String Return = "admpe?action=ShowPolicy&PolicyID=";
		// Write the PoliciesList in a session attribute
		try {
        	ServiceLocator locator = ServiceLocator.getInstance();
        	
        	PolicyEnforcementLocal pe = locator.getPolicyEnforcementLocal();
        	HttpSession mySession = req.getSession();
        	List PolicyListSet =  (List)mySession.getAttribute("PolicySet");
        	String myPolicy = (String)mySession.getAttribute("PolicyID");
        	String myRule = req.getParameter("RuleID");
        	if (myPolicy==null) {
        		log.debug("PolicyID is mising");
        		log.debug("-> End processing ...");
        		return "adminpe/FatalError.jsp";
        	}
        	Return = Return + myPolicy;
        	if (myRule==null) {
        		log.debug("RuleID is mising");
        		log.debug("-> End processing ...");
        		return "adminpe/FatalError.jsp";
        	}
        	
        	pe.FPM_ChangeRuleEffect(myRule, myPolicy, PolicyListSet);
        	
        	
        	
		} catch (Exception e)
		{
			log.debug("Unable to get the List of Policies" + e.toString());
		}
		
		log.debug("-> End processing ...");
		return Return;
	}

}
