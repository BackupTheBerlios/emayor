/*
 * Created on 15.08.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.emayor.servicehandling.gui.admin.pe;
import java.io.IOException;
import java.util.ArrayList;


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
public class ShowPolicySetProcessor extends AbstractRequestProcessor{


	private static final Logger log = Logger.getLogger(ShowPolicySetProcessor.class);
	public String process(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		log.debug("-> start processing ...");
		String Return = "adminpe/WelcomePage.jsp";
		// Write the PoliciesList in a session attribute
		try {
        	ServiceLocator locator = ServiceLocator.getInstance();
        	
        	PolicyEnforcementLocal pe = locator.getPolicyEnforcementLocal();
        	HttpSession mySession = req.getSession();
        	List PolicyListSet =  (List)mySession.getAttribute("PolicySet");
        	String myPolicy = req.getParameter("PolicyID");
        	mySession.setAttribute("PolicyID", myPolicy); // This is needed by the RuleChangeProcessor
        	log.debug("Get PolicyID Attribute = " + myPolicy);
        	if (myPolicy==null) {
        		log.debug("PolicyID is mising");
        		log.debug("-> End processing ...");
        		return "adminpe/FatalError.jsp";
        	}
        	
        	if (pe.FPM_isPolicySet(myPolicy, PolicyListSet))
        	{ // We have a Policy Set, so retrive The list of policies
        		mySession.setAttribute("PoliciesList",pe.FPM_GetPoliciesList(myPolicy,PolicyListSet ));
        		if (PolicyListSet==null) PolicyListSet = new ArrayList();
        		PolicyListSet.add(myPolicy);
        		mySession.setAttribute("PolicySet", PolicyListSet);
        		
        		String PolicyName = "";
        		int POlicyCount = PolicyListSet.size();
        		for (int i=0; i < POlicyCount; i++) {
        			PolicyName = PolicyName + (String)PolicyListSet.get(i)+"::";
        		}
        		
        		mySession.setAttribute("PolicyName", PolicyName);
        		
        		
        	} else
        	{ // We have a policy so let's show the rules
        		//Add the current Policy to the List
        		
        		List MyRules = pe.FPM_GetRuleList(myPolicy, PolicyListSet);
        		mySession.setAttribute("Rules", MyRules);
        		
        		
        		log.debug("Display the rules of the Policy");
        		log.debug("-> End processing ...");
        		
        		Return = "adminpe/ShowRules.jsp";
        		String PolicyName = "";
        		if (PolicyListSet!=null){
        			int POlicyCount = PolicyListSet.size();
            		for (int i=0; i < POlicyCount; i++) {
            			PolicyName = PolicyName + (String)PolicyListSet.get(i)+"::";
            		}	
        		}
        		
        		PolicyName = PolicyName + myPolicy +":";
        		mySession.setAttribute("PolicyName", PolicyName);
        	}
        	
        	
        	
		} catch (Exception e)
		{
			log.debug("Unable to get the List of Policies" + e.toString());
		}
		
		log.debug("-> End processing ...");
		return Return;
	}

}
