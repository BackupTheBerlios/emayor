/*
 * Created on Feb 25, 2005
 */
package org.emayor.servicehandling.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * @author tku
 */
public class RCSDisplayDataFormProcessor {
	private static Logger log = Logger
			.getLogger(RCSDisplayDataFormProcessor.class);

	/**
	 *  
	 */
	public RCSDisplayDataFormProcessor() {
		super();
	}

	public void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("-> start processing ...");
		HttpSession session = req.getSession(false);
		
		log.debug("-> ... processing DONE!");
	}

}