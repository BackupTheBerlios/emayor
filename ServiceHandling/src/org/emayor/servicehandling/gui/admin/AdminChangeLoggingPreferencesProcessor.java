/*
 * Created on Jun 8, 2005
 */
package org.emayor.servicehandling.gui.admin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.xerces.parsers.SAXParser;
import org.emayor.servicehandling.config.Config;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author tku
 */
public class AdminChangeLoggingPreferencesProcessor extends
        AbstractRequestProcessor {
    private static final Logger log = Logger
            .getLogger(AdminChangeLoggingPreferencesProcessor.class);

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
            	SAXParser parser = new SAXParser();
            	AdminChangeLoggingPreferencesProcessor.MyHandler handler = new AdminChangeLoggingPreferencesProcessor.MyHandler();
    			parser.setContentHandler(handler);
    			parser.setFeature("http://xml.org/sax/features/validation",false);
    			parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd",false);
    			parser.parse(Config.getInstance().getQuilifiedDirectoryName("conf"+File.separator+"log4j.xml"));
    			HashMap map = handler.values;
    			HashSet prios = new HashSet();
    			prios.add("ALL");
    			prios.add("DEBUG");
    			prios.add("WARN");
    			prios.add("FATAL");
    			prios.add("INFO");
    			prios.add("ERROR");
    			prios.add("OFF");
                session.setAttribute("LOG4J_CONFIG", map);
    			session.setAttribute("LOG4J_PRIOS", prios);
                ret = "admin/SystemChangeLoggingPreferences.jsp";
            } catch (Exception ex) {
            	ex.printStackTrace();
                log.error("caught ex: " + ex.toString());
                AdminErrorPageData data = new AdminErrorPageData();
                data.setPageTitle("System Error!");
                data
                        .setMessage("There was a problem to get the log4j configuration.\nPlease try it again later.");
                session.setAttribute(AdminErrorPageData.ATT_NAME, data);
                ret = "admin/ErrorPage.jsp";
            }
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }
    
	private static class MyHandler extends DefaultHandler {
		
		private HashMap values = new HashMap();
		//private boolean inCategory = false;
		private String name = null;
		private String priority = null;
		
		public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {
			if (localName.equals("category")) {
				//inCategory = true;
				name = atts.getValue("name");
				priority = Logger.getLogger(name).getLevel().toString();
				values.put(name,priority);
				/*
			} else if (localName.equals("priority") && inCategory) {
				priority = atts.getValue("value");
				if (name != null) {
					priority = Logger.getLogger("name").getLevel().toString();
					values.put(name,priority);
				}
				inCategory = false;
				*/
			}
		}
	}

}