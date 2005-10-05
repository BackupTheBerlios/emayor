/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.IService;
import org.emayor.servicehandling.kernel.ServiceException;
import org.emayor.servicehandling.kernel.Task;
import org.emayor.servicehandling.kernel.Tasks;
import org.emayor.servicehandling.kernel.UserTaskException;
import org.emayor.servicehandling.kernel.UserTaskManager;
import org.emayor.servicehandling.kernel.UserTaskManagerOverEJB;

import javax.ejb.CreateException;

/**
 * @ejb.bean name="UserTaskManager" display-name="Name for UserTaskManager"
 *           description="Description for UserTaskManager"
 *           jndi-name="ejb/emayor/sh/UserTaskManager" type="Stateless"
 *           view-type="local"
 *  
 */
public class UserTaskManagerEJB implements SessionBean, IService {
	private static Logger log = Logger.getLogger(UserTaskManagerEJB.class);

	public static final Integer WS_WRAPPER = new Integer(1);

	public static final Integer EJB_WRAPPER = new Integer(2);

	private IService service;

	/**
	 *  
	 */
	public UserTaskManagerEJB() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext ctx) throws EJBException,
			RemoteException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IService#getMyTasks(String)
	 */
	public Tasks getMyTasks(String accessSessionId) throws ServiceException {
		log.debug("-> start processing ...");
		return this.service.getMyTasks(accessSessionId);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IService#completeTask(String, Task)
	 */
	public void completeTask(String accessSessionId, Task task)
			throws ServiceException {
		log.debug("-> start processing ...");
		this.service.completeTask(accessSessionId, task);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IService#lookupTask(String)
	 */
	public Task lookupTask(String taskId) throws ServiceException {
		log.debug("-> start processing ...");
		return this.service.lookupTask(taskId);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IService#lookupTaskByServiceSession(String, String)
	 */
	public Task lookupTaskByServiceSession(String asid, String ssid)
			throws ServiceException {
		log.debug("-> start processing ...");
		return this.service.lookupTaskByServiceSession(asid, ssid);
	}

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
	 */
	public void ejbCreate() throws CreateException {
		log.debug("-> start processing ...");
		this.ejbCreateIt(EJB_WRAPPER);
	}

	private void ejbCreateIt(Integer userTaskType) throws CreateException {
		log.debug("-> start processing ...");
		try {
			if (userTaskType.equals(WS_WRAPPER)) {
				log.debug("do it over WS interface");
				this.service = UserTaskManager.getInstance();
			} else {
				log.debug("do it over EJB interface");
				this.service = UserTaskManagerOverEJB.getInstance();
			}
		} catch (UserTaskException ex) {
			log.error("caught ex: " + ex.toString());
			throw new CreateException(
					"Couldn't get the reference to the instance of the UserTaskManager!");
		}
		log.debug("-> ... processing DONE!");
	}
}