/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.test;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet Class
 * 
 * @web.servlet name="ServiceHandlingTest" display-name="Name for
 *              ServiceHandlingTest" description="Description for
 *              ServiceHandlingTest"
 * 
 * @web.servlet-mapping url-pattern = "/ServiceHandlingTest"
 *  
 */
public class ServiceHandlingTestServlet extends HttpServlet {
    private static Logger log = Logger
            .getLogger(ServiceHandlingTestServlet.class);

    public static String CWD_PATH = "";

    /**
     *  
     */
    public ServiceHandlingTestServlet() {
        super();
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        CWD_PATH = config.getServletContext().getRealPath(".");
        if (log.isDebugEnabled())
            log.debug("CWD_PATH = " + CWD_PATH);
        StringBuffer b = new StringBuffer();
        b.append(CWD_PATH).append(File.separator).append("temp");
        File file = new File(b.toString());
        if (!file.exists())
            file.mkdir();
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        String page = "index.jsp";
        IProcessor processor = null;
        if (log.isDebugEnabled())
            log.debug("got following action from request: " + action);

        if (action.equalsIgnoreCase("login")) {
            log.debug("processing welcome request");
            processor = new LoginProcessor();
        } else if (action.equalsIgnoreCase("welcome")) {
            log.debug("processing welcome request");
            processor = new WelcomeProcessor();
        } else if (action.equalsIgnoreCase("Logout")) {
            log.debug("processing Logout request");
            processor = new LogoutProcessor();
        } else if (action.equalsIgnoreCase("listMyTasks")) {
            log.debug("processing the listMyTasks request");
            processor = new ListMyTasksProcessor();
        } else if (action.equalsIgnoreCase("showTaskDetails")) {
            log.debug("processing the showTaskDetails request");
            processor = new ShowTaskDetailsProcessor();
        } else if (action.equalsIgnoreCase("completeTask")) {
            log.debug("processing the completeTask request");
            processor = new CompleteTaskProcessor();
        } else if (action.equalsIgnoreCase("StartService")) {
            log.debug("processing the StartService request");
            processor = new StartServiceProcessor();
        } else if (action.equalsIgnoreCase("StartServiceNew")) {
            log.debug("processing the StartServiceNew request");
            processor = new StartServiceNewProcessor();
        } else if (action.equalsIgnoreCase("GetInputDataPage")) {
            log.debug("processing the GetInputDataPage request");
            processor = new GetInputDataPageProcessor();
        } else if (action.equalsIgnoreCase("ValidateInputData")) {
            log.debug("processing the ValidateInputData request");
            processor = new PostTaskAndWaitProcessor();
        } else if (action.equalsIgnoreCase("GetTask")) {
            log.debug("processing the GetTask request");
            processor = new GetTaskProcessor();
        } else if (action.equalsIgnoreCase("ServiceHandlingPostSignRequest")) {
            log.debug("processing the StartService request");
            processor = new RCSPostSignRequestProcessor();
        } else if (action.equalsIgnoreCase("testPrintService")) {
            log.debug("processing the testPrintService request");
            processor = new TestPrintServiceProcessor();
        } else if (action.equalsIgnoreCase("CVDisplayTask")) {
            log.debug("processing the CVDisplayTask request");
            processor = new CVDisplayTaskProcessor();
        } else if (action.equalsIgnoreCase("CVHandleTask")) {
            log.debug("processing the CVHandleTask request");
            processor = new CVHandleTaskProcessor();
        } else if (action.equalsIgnoreCase("AfterDownload")) {
            log.debug("processing the AfterDownload request");
            processor = new AfterDownloadCompletedDocumentProcessor();
        } else if (action.equalsIgnoreCase("MainMenu")) {
            log.debug("processing the AfterDownload request");
            processor = new MainMenuProcessor();
        } else if (action.equalsIgnoreCase("ShowInvokeBPELCallbackPage")) {
            log.debug("processing the AfterDownload request");
            processor = new ShowInvokeBPELCallbackPageProcessor();
        } else if (action.equalsIgnoreCase("InvokeBPELCallback")) {
            log.debug("processing the AfterDownload request");
            processor = new InvokeBPELCallbackProcessor();
        } else if (action.equalsIgnoreCase("ForwardRequest")) {
            log.debug("processing the ForwardRequest request");
            processor = new ForwardServiceProcessor();
        } else if (action.equalsIgnoreCase("reloadPolicies")) {
            log.debug("processing the ReloadPolicies request");
            processor = new reloadPoliciesProcessor();
        } else if (action.equalsIgnoreCase("RCSDataNotCompletePage")) {
            log.debug("processing the RCSDataNotCompletePage request");
            processor = new RCSDataNotCompleteProcessor();
        } else if (action.equalsIgnoreCase("RCSDataCompletePage")) {
            log.debug("processing the RCSDataCompletePage request");
            processor = new RCSDataCompleteProcessor();
        } else {
            log.debug("processing unknown request");
            processor = new ErrorProcessor();
        }
        page = processor.process(req, resp);
        resp.sendRedirect(page);
    }

    public String getServletInfo() {
        return "Control servlet for test handling GUI";
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.service(req, resp);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        this.service(request, response);
    }

}