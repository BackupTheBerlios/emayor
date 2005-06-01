/*
 * Created on Jun 2, 2005
 */
package org.emayor.servicehandling.gui.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tku
 */
public interface IRequestProcessor {
    public abstract String process(HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException;
}