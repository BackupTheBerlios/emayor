/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel;

import java.rmi.RemoteException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.oracle.services.bpel.task.WorklistManager_Port;
import com.oracle.services.bpel.task.WorklistManager_Service;
import com.oracle.services.bpel.task._task;
import com.oracle.services.bpel.task._tasklist;
import com.oracle.services.bpel.task._whereCondition;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class UserTaskManager implements IService {
	private static Logger log = Logger.getLogger(UserTaskManager.class);

	private static UserTaskManager _self = null;

	private Kernel kernel;

	private WorklistManager_Port worklistManager;

	private UserTaskManager() throws UserTaskException {
		log.debug("-> start processing ...");
		try {
			InitialContext initialContext = this.getInitialContext();
			WorklistManager_Service service = (WorklistManager_Service)initialContext.lookup("java:comp/env/service/WorklistManagerJSE");
			this.worklistManager = service.getWorklistManagerPort();
		} catch (javax.xml.rpc.ServiceException ex) {
			log.error("caught ex: " + ex.toString());
			throw new UserTaskException(
					"Couldn't connect to the Worklist Manager WS!");
		} catch (NamingException nex) {
			log.debug("caught ex: " + nex.toString());
		}
		this.kernel = Kernel.getInstance();
		log.debug("-> ... processing DONE!");
	}

	/**
	 * 
	 * @return
	 */
	public static final synchronized UserTaskManager getInstance()
			throws UserTaskException {
		log.debug("-> start processing ...");
		if (_self == null)
			_self = new UserTaskManager();
		log.debug("-> ... processing DONE!");
		return _self;
	}

	/**
	 *  
	 */
	public Tasks getMyTasks(String accessSessionId) throws ServiceException {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");
		Tasks ret = new Tasks();
		try {
			String uid = this.kernel.getUserIdByASID(accessSessionId);
			_whereCondition condition = new _whereCondition();
			condition.setAssignee(uid);
			_tasklist _list = this.worklistManager.listTasks(condition);
			_task[] _tasks = _list.getTask();
			Task[] tasks = new Task[_tasks.length];
			for (int i = 0; i < tasks.length; i++) {
				Task task = new Task();
				// default is set to "open"
				task.setStatus(_tasks[i].getConclusion());
				task.setTaskId(_tasks[i].getTaskId());
				task.setXMLDocument("xml document");
				task.setOriginalTask(_tasks[i]);
				tasks[i] = task;
			}
			ret.setTasks(tasks);
		} catch (KernelException kex) {
			log.error("caught ex: " + kex.toString());
			throw new ServiceException("Couldn't obtain the user id!");
		} catch (RemoteException rex) {
			log.error("caught ex: " + rex.toString());
			throw new ServiceException(
					"Couldn't get the list of open tasks from WorklistManager!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 *  
	 */
	public void completeTask(String accessSessionId, Task task)
			throws ServiceException {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");
	}

	/**
	 *  
	 */
	public Task lookupTask(String accessSessionId, String serviceSessionId)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	private InitialContext getInitialContext() throws NamingException {
		Properties env = new Properties();
		env.setProperty(Context.INITIAL_CONTEXT_FACTORY,
				"org.jnp.interfaces.NamingContextFactory");
		env.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.naming.client");
		env.setProperty(Context.PROVIDER_URL, "jnp://localhost:1099");
		env.setProperty("j2ee.clientName", "ws4ee-client");
		return new InitialContext(env);
	}

}