/*
 * Created on Mar 14, 2005
 */
package org.emayor.servicehandling.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.eMayor.ServiceHandling.PrintingUtility.interfaces.Printer;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author tku
 */
public class TestPrintServiceProcessor extends AbstractProcessor {
	private static final Logger log = Logger
			.getLogger(TestPrintServiceProcessor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.test.IProcessor#process(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public String process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("-> start processing ...");
		String ret = "Error.jsp";
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			Printer printer = locator.getPrinter();
			printer.print("http://www.fokus.fraunhofer.de", "text/html");
			ret = "MainMenu.jsp";
		} catch (ServiceLocatorException slex) {
			log.error("caught ex: " + slex.toString());
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

}