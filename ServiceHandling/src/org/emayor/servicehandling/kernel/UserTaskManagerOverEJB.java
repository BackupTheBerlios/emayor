/*
 * Created on Mar 4, 2005
 */
package org.emayor.servicehandling.kernel;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.model.UTWrapperEJB;
import org.emayor.servicehandling.model.UTWrapperException;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author tku
 */
public class UserTaskManagerOverEJB implements IService {
	private static final Logger log = Logger
			.getLogger(UserTaskManagerOverEJB.class);

	private static UserTaskManagerOverEJB _self = null;

	private UTWrapperEJB wrapper = null;

	private Kernel kernel;

	private UserTaskManagerOverEJB() throws UserTaskException {
		log.debug("-> start processing ...");
		try {
			log.debug("get the locator instance");
			ServiceLocator locator = ServiceLocator.getInstance();
			log.debug("get the user task wrapper instance");
			this.wrapper = locator.getUTWrapperRemoteInterface();
			if (this.wrapper == null)
				log.info("THE WRAPPER INSTANCE IS NULL!!!");
			log.debug("get the kernel instance");
			this.kernel = Kernel.getInstance();
			if (this.kernel == null)
				log.info("THE KERNEL INSTANCE IS NULL!!!");
		} catch (ServiceLocatorException slex) {
			log.error("caught ex: " + slex.toString());
			throw new UserTaskException(
					"Couldn't get the remote reference to the UTWrapper");
		} catch (KernelException kex) {

		}
		log.debug("-> ... processing DONE!");
	}

	public static UserTaskManagerOverEJB getInstance() throws UserTaskException {
		log.debug("-> start processing ...");
		if (_self == null)
			_self = new UserTaskManagerOverEJB();
		log.debug("-> ... processing DONE!");
		return _self;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IService#getMyTasks(java.lang.String)
	 */
	public synchronized Tasks getMyTasks(String asid) throws ServiceException {
		log.debug("-> start processing ...");
		Tasks ret = new Tasks();
		if (log.isDebugEnabled())
			log.debug("working with asid = " + asid);
		try {
			log.debug("get the uid from the kernel");
			String uid = this.kernel.getUserIdByASID(asid);
			if (log.isDebugEnabled())
				log.debug("got the uid: " + uid);
			log.debug("get the role");
			String role = this.kernel.getUserProfile(uid).getPEUserProfile()
					.getUserRole();
			if (log.isDebugEnabled())
				log.debug("got following role: " + role);
			if (role.equalsIgnoreCase("citizen")) {
				log.debug("try to call the listTasks operation for citizen");
				ret = this.wrapper.listTasksByAssignee(uid);
			} else {
				log
						.debug("try to call the listTasks operation for civil servant");
				ret = this.wrapper.listTasksByAssignee("CivilServant");
			}
			if (log.isDebugEnabled())
				log.debug("got following number of tasks: "
						+ ret.getTasks().length);
		} catch (KernelException kex) {
			log.error("caught ex: " + kex.toString());
			throw new ServiceException("Couldn't obtain the user id!");
		} catch (RemoteException rex) {
			log.error("caught ex: " + rex.toString());
			throw new ServiceException("Couldn't list the tasks!");
		} catch (UTWrapperException utwex) {
			log.error("caught ex: " + utwex.toString());
			throw new ServiceException("Couldn't list the tasks");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IService#completeTask(java.lang.String,
	 *      org.emayor.servicehandling.kernel.Task)
	 */
	public synchronized void completeTask(String asid, Task task)
			throws ServiceException {
		log.debug("-> start processing ...");
		try {
			this.wrapper.completeTask(task);
		} catch (RemoteException rex) {
			log.error("caught ex: " + rex.toString());
			throw new ServiceException("Couldn't complete task !");
		} catch (UTWrapperException utwex) {
			log.error("caught ex: " + utwex.toString());
			throw new ServiceException("Couldn't complete task!");
		}
		log.debug("-> ... processing DONE!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IService#lookupTask(java.lang.String)
	 */
	public synchronized Task lookupTask(String taskId) throws ServiceException {
		log.debug("-> start processing ...");
		Task ret = new Task();
		if (log.isDebugEnabled())
			log.debug("got taskId = " + taskId);
		try {
			ret = (Task) this.wrapper.lookupTask(taskId);
		} catch (RemoteException rex) {
			log.error("caught ex: " + rex.toString());
			throw new ServiceException("Couldn't lookup task !");
		} catch (UTWrapperException utwex) {
			log.error("caught ex: " + utwex.toString());
			throw new ServiceException("Couldn't lookup task!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IService#lookupTaskByServiceSession(java.lang.String,
	 *      java.lang.String)
	 */
	public synchronized Task lookupTaskByServiceSession(String asid, String ssid)
			throws ServiceException {
		log.debug("-> start processing ...");
		Task ret = null;
		if (log.isDebugEnabled()) {
			log.debug("working with asid = " + asid);
			log.debug("working with ssid = " + ssid);
		}
		try {
			String uid = this.kernel.getUserIdByASID(asid);
			if (log.isDebugEnabled())
				log.debug("got the uid: " + uid);
			log.debug("try to call the listTasks operation");
			Tasks tasks = this.wrapper.lookupTasksByAssigneeAndTitle(uid, ssid);
			if (tasks != null) {
			    log.debug("got SOME tasks from remote bean !");
				ITask[] _tasks = tasks.getTasks();
				if (_tasks != null && _tasks.length > 0) {
					if (log.isDebugEnabled())
						log.debug("found tasks: number=" + _tasks.length);
					ret = (Task) _tasks[0];
				}
			}
			else {
			    log.debug("got no tasks from the remote bean!");
			}
		} catch (KernelException kex) {
			log.error("caught ex: " + kex.toString());
			throw new ServiceException("Couldn't lookup task!");
		} catch (RemoteException rex) {
			log.error("caught ex: " + rex.toString());
			throw new ServiceException("Couldn't lookup task!");
		} catch (UTWrapperException utwex) {
			log.error("caught ex: " + utwex.toString());
			throw new ServiceException("Couldn't lookup task!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

}