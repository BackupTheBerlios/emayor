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

import org.emayor.servicehandling.bpel.task.MyWorklistManager_Port;
import org.emayor.servicehandling.bpel.task.MyWorklistManager_Service;
import org.emayor.servicehandling.bpel.task._assigneeAndCustomKey;
import org.emayor.servicehandling.bpel.task._task;
import org.emayor.servicehandling.bpel.task._tasklist;

//import org.emayor.servicehandling.bpel.task._whereCondition;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class UserTaskManager implements IService {
	private static Logger log = Logger.getLogger(UserTaskManager.class);

	private static UserTaskManager _self = null;

	private Kernel kernel;

	private MyWorklistManager_Port worklistManager;

	private UserTaskManager() throws UserTaskException {
		log.debug("-> start processing ...");
		try {
			InitialContext initialContext = this.getInitialContext();
			if (initialContext != null)
				log.debug("the initial context is NOT null");
			MyWorklistManager_Service service = (MyWorklistManager_Service) initialContext
					.lookup("java:comp/env/service/MyWorklistManagerJSE");
			if (service != null)
				log.debug("the service is NOT null");
			this.worklistManager = service.getMyWorklistManagerPort();
			if (this.worklistManager != null)
				log.debug("the port reference is NOT null");
			this.kernel = Kernel.getInstance();
		} catch (javax.xml.rpc.ServiceException ex) {
			log.error("caught ex: " + ex.toString());
			throw new UserTaskException(
					"Couldn't connect to the Worklist Manager WS!");
		} catch (NamingException nex) {
			log.error("caught ex: " + nex.toString());
		} catch (KernelException kex) {
			log.error("caught ex: " + kex.toString());
		}
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
		log.debug("-> start processing ...");
		Tasks ret = new Tasks();
		if (log.isDebugEnabled())
			log.debug("working with asid = " + accessSessionId);
		try {
			log.debug("getting the uid from the kernel");
			String uid = this.kernel.getUserIdByASID(accessSessionId);
			if (log.isDebugEnabled())
				log.debug("got the uid: " + uid);
			log.debug("try to call the listTasks operation");
			_tasklist _list = this.worklistManager.listTasksByAssignee(uid);
			Task[] tasks = new Task[0];
			if (_list != null) {
				_task[] _tasks = _list.getTask();
				if (_tasks == null) {
					log.debug("the arry of _task is null");
					_tasks = new _task[0];
				}
				tasks = new Task[_tasks.length];
				for (int i = 0; i < tasks.length; i++) {
					Task task = new Task();
					// default is set to "open"
					task.setStatus(_tasks[i].getConclusion());
					task.setTaskId(_tasks[i].getTaskId());
					task.setXMLDocument("def xml document");
					task.setOriginalTask(_tasks[i]);
					tasks[i] = task;
				}
			} else {
				log.debug("got null ref from WS");
				tasks = new Task[0];
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
		log.debug("-> start processing ...");
		try {
			_task __task = task.getOriginalTask();
			log.debug("attach the changed xml document");
			if (log.isDebugEnabled())
				log.debug("the doc content: " + task.getXMLDocument());
			__task.setAttachment(task.getXMLDocument());
			log.debug("try to call complete task operation");
			this.worklistManager.completeTask(__task);
		} catch (RemoteException rex) {
			log.error("caught ex: " + rex.toString());
			throw new ServiceException(
					"Couldn't get the list of open tasks from WorklistManager!");
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 *  
	 */
	public Task lookupTask(String taskId) throws ServiceException {
		log.debug("-> start processing ...");
		Task ret = new Task();
		if (log.isDebugEnabled())
			log.debug("got taskId = " + taskId);
		try {
			log.debug("try to lookup the required task");
			_task __task = this.worklistManager.lookupTask(taskId);
			if (__task != null) {
				log.debug("the returned task is NOT null!");
				log.debug("set up the return value");
				ret.setStatus(__task.getConclusion());
				ret.setTaskId(__task.getTaskId());
				String att = __task.getAttachment().toString();
				ret.setXMLDocument(att.substring(1, att.length() - 1));
				ret.setOriginalTask(__task);
				log.debug("processing OK");
			}
		} catch (RemoteException rex) {
			log.error("caught ex: " + rex.toString());
			throw new ServiceException(
					"Couldn't get the list of open tasks from WorklistManager!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	public Task lookupTaskByAssigneeAndCustomKey(String asid, String ssid)
			throws ServiceException {
		log.debug("-> start processing ...");
		Task ret = null;
		if (log.isDebugEnabled()) {
			log.debug("working with asid = " + asid);
			log.debug("working with ssid = " + ssid);
		}
		try {
			String uid = this.kernel.getUserIdByASID(asid);
			_assigneeAndCustomKey req = new _assigneeAndCustomKey();
			req.setAssignee(uid);
			req.setCustomKey(ssid);
			if (log.isDebugEnabled())
				log.debug("got the uid: " + uid);
			log.debug("try to call the listTasks operation");
			_tasklist _list = this.worklistManager
					.lookupTasksByAssigneeAndCustomKey(req);
			_task[] _tasks = _list.getTask();
			if (_tasks != null && _tasks.length > 0) {
				_task task = _tasks[0];
				ret = this.lookupTask(task.getTaskId());
			}
		} catch (KernelException kex) {
			log.error("caught ex: " + kex.toString());
		} catch (RemoteException rex) {
			log.error("caught ex: " + rex.toString());
			throw new ServiceException(
					"Couldn't get the list of open tasks from WorklistManager!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
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