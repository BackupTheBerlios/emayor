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

import javax.ejb.CreateException;

/**
 * @ejb.bean name="UserTaskManager" display-name="Name for UserTaskManager"
 *           description="Description for UserTaskManager"
 *           jndi-name="ejb/UserTaskManager" type="Stateless" view-type="local"
 *
 */
public class UserTaskManagerEJB implements SessionBean, IService {
	private static Logger log = Logger.getLogger(UserTaskManagerEJB.class);

	private UserTaskManager userTaskManager;

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
	 *  
	 */
	public Tasks getMyTasks(String accessSessionId) throws ServiceException {
		log.debug("-> start processing ...");
		return this.userTaskManager.getMyTasks(accessSessionId);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public void completeTask(String accessSessionId, Task task)
			throws ServiceException {
		log.debug("-> start processing ...");
		this.userTaskManager.completeTask(accessSessionId, task);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public Task lookupTask(String accessSessionId, String serviceSessionId)
			throws ServiceException {
		log.debug("-> start processing ...");
		return this.userTaskManager.lookupTask(accessSessionId, serviceSessionId);
	}

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
	 */
	public void ejbCreate() throws CreateException {
		log.debug("-> start processing ...");
		try {
			this.userTaskManager = UserTaskManager.getInstance();
		} catch (UserTaskException ex) {
			log.error("caught ex: " + ex.toString());
			throw new CreateException(
					"Couldn't get the reference to the instance of the UserTaskManager!");
		}
		log.debug("-> ... processing DONE!");
	}
}