/*
 * Created on Jun 8, 2005
 */
package org.emayor.servicehandling.gui.admin;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * @author tku
 */
public class AdminTestPlatformExecProcessor extends
        AbstractRequestProcessor {
    private static final Logger log = Logger
            .getLogger(AdminTestPlatformExecProcessor.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.emayor.servicehandling.gui.admin.IRequestProcessor#process(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public String process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.debug("-> start processing ...");
        String ret = "admin/FatalError.jsp";
        HttpSession session = req.getSession(false);
        if (session == null) {
            log.debug("no valid session !");
        } else {
        	try {
	            Integer elem = null;
				String result = null;
	            boolean testOK = false;
	            Map tests = (Map) session.getAttribute("ETESTS");
	            HashMap results = new HashMap();
	            PlatformTest test = new PlatformTest();
	            Map params = req.getParameterMap();
	            Iterator i = tests.keySet().iterator();
	            
	            while (i.hasNext()) {
	            	elem = (Integer) i.next();
	            	if (params.containsKey(elem.toString())) {
	            		switch (elem.intValue()) {
	            			case PlatformTest.TEST_CONFIG: 	testOK = test.testConfig();
	            											break;
	            			case PlatformTest.TEST_BPEL: 	testOK = test.testBPEL();
															break;
	            			case PlatformTest.TEST_CR: 		testOK = test.testContentRouting();
															break;
	            			case PlatformTest.TEST_JNDI: 	testOK = test.testJNDI();
															break;
	            			case PlatformTest.TEST_LOGIN: 	testOK = test.testLogin();
															break;
	            			case PlatformTest.TEST_MAIL: 	testOK = test.testMail();
															break;					
	            			case PlatformTest.TEST_UT: 		testOK = test.testUTWrapper();
															break;
	            			case PlatformTest.TEST_SERV: 	testOK = test.testServices();
															break;
	            		}

	            		if (!testOK) result = test.getError();
		            	else result = "o.k.";
		            	
	            		results.put(elem,result);
		            } 
	            }	            
				/*for (Enumeration e = req.getParameterNames();e.hasMoreElements();) {
	            	elem = (String) e.nextElement();
	            	if (elem.matches("ETEST-(.*?)$")) {
	            		if (elem.equals("ETEST-MAIL")) {
	            			if (!test.testMail()) {
	            				result = test.getError();
	            			} else result = "o.k.";
	            		} else
	        			if (elem.equals("ETEST-LOGIN")) {
	            			if (!test.testLogin()) {
	            				result = test.getError();
	            			} else result = "o.k.";
	            		} else
	        			if (elem.equals("ETEST-CR")) {
	            			if (!test.testContentRouting()) {
	            				result = test.getError();
	            			} else result = "o.k.";
	            		} else
	        			if (elem.equals("ETEST-CONF")) {
	            			if (!test.testConfig()) {
	            				result = test.getError();
	            			} else result = "o.k.";
	            		} else
	        			if (elem.equals("ETEST-JNDI")) {
	            			if (!test.testJNDI()) {
	            				result = test.getError();
	            			} else result = "o.k.";
	            		} else
	        			if (elem.equals("ETEST-BPEL")) {
	            			if (!test.testBPEL()) {
	            				result = test.getError();
	            			} else result = "o.k.";
	            		}
	            		results.put(elem,result);
	            	}
	            }
	            */
	            session.setAttribute("ETEST_RESULTS",results);
	            ret = "admin/TestPlatformOutro.jsp";
        	} catch (Exception ex) {
                log.error("caught ex: " + ex.toString());
                AdminErrorPageData data = new AdminErrorPageData();
                data.setPageTitle(ex.getMessage());
                data
                        .setMessage(ex.getMessage());
                session.setAttribute(AdminErrorPageData.ATT_NAME, data);
                ret = "admin/ErrorPage.jsp";
			}
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

}