/*
 * Created on Feb 22, 2005
 */
package org.emayor.servicehandling.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.Task;

/**
 * @author tku
 */
public class CompleteTaskProcessor extends AbstractProcessor {
    private static Logger log = Logger.getLogger(CompleteTaskProcessor.class);

    /**
     *  
     */
    public CompleteTaskProcessor() {
        super();
    }

    public String process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.debug("-> start processing ...");
        String ret = "Error.jsp";
        try {
            HttpSession session = req.getSession(false);
            Task task = (Task) session.getAttribute("CURR_TASK");
            String asid = (String) session.getAttribute("ASID");
            String ssid = (String) session.getAttribute("SSID");
            String role = (String) session.getAttribute("ROLE");
            if (log.isDebugEnabled()) {
                log.debug("got asid: " + asid);
                log.debug("got ssid: " + ssid);
                log.debug("got role: " + role);
            }

            String xmlDoc = task.getXMLDocument();
            StringBuffer b = new StringBuffer();
            b.append(ServiceHandlingTestServlet.CWD_PATH);
            b.append(File.separator).append("temp");
            b.append(File.separator).append(ssid).append(".xml");
            if (log.isDebugEnabled())
                log.debug("build the file name: " + b.toString());
            log.debug("creating the file instance");
            File file = new File(b.toString());
            log.debug("got path: " + file.getAbsolutePath().toString());
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(xmlDoc);
            bw.flush();
            bw.close();
            log.debug("the xml document data has been written into file");

            log.debug("building the response redirection url");
            b = new StringBuffer();
            b.append("temp/").append(ssid).append(".xml");
            if (log.isDebugEnabled())
                log.debug("built the download link: " + b.toString());
            session.setAttribute("DOWNLOAD_LINK", b.toString());
            log.debug("DONE");
            ret = "DownloadDocument.jsp";
        } catch (Exception ex1) {
            log.error("caught ex: " + ex1.toString());
            // TODO hadle exception
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }
}