/*
 * Created on Mar 14, 2005
 */
package org.emayor.servicehandling.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tku
 */
public interface IProcessor {
	public abstract String process(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException;
}