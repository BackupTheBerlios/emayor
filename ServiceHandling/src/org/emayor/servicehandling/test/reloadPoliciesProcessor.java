/*
 * Created on Mar 22, 2005
 */
package org.emayor.servicehandling.test;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.eMayor.PolicyEnforcement.interfaces.PolicyEnforcementLocal;
import org.emayor.servicehandling.utils.ServiceLocator;





/**
 * @author tku
 */
public class reloadPoliciesProcessor extends AbstractProcessor {
    private static final Logger log = Logger
            .getLogger(reloadPoliciesProcessor.class);
    private PolicyEnforcementLocal pe;
    /*
     * (non-Javadoc)
     * 
     * @see org.emayor.servicehandling.test.IProcessor#process(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public String process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String ret = "CVMainMenu.jsp";
        try {
        	ServiceLocator locator = ServiceLocator.getInstance();
        
		this.pe = locator.getPolicyEnforcementLocal();
		if (pe == null)
			log.warn("the reference to the policy enforcer is NULL!!!!");
		
		pe.F_UpdatePolicies();
        }
        catch (Exception e)
		{
        	return "Error.jsp";
		}
        log.debug("-> ... processing DONE!");
        return ret;
    }

}