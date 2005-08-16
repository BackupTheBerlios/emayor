/*
 * $ Created on Aug 12, 2005 by tku $
 */
package org.emayor.servicehandling.gui.admin.pe;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.gui.admin.AbstractRequestProcessor;
import javax.servlet.http.HttpSession;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class StartServiceProcessor extends AbstractRequestProcessor {
	private final static Logger log = Logger
			.getLogger(StartServiceProcessor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.gui.admin.IRequestProcessor#process(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public String process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("-> start processing ...");
		String ret = "admpe?action=ListPolicies";
		log.debug("-> processing DONE");
		
		HttpSession MySession = req.getSession();
		MySession.setAttribute("PolicyName", null);
		MySession.setAttribute("PoliciesList", null);
		MySession.setAttribute("PolicySet", null);
		MySession.setAttribute("Rules", null);
		
		return ret;
	}

}