/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.emayor.servicehandling.kernel.IService;
import org.emayor.servicehandling.kernel.ServiceException;
import org.emayor.servicehandling.kernel.Task;
import org.emayor.servicehandling.kernel.Tasks;

import javax.ejb.CreateException;

/**
 * @ejb.bean name="UserTaskManager"
 *           display-name="Name for UserTaskManager"
 *           description="Description for UserTaskManager"
 *           jndi-name="ejb/UserTaskManager"
 *           type="Stateless"
 *           view-type="local"
 */
public class UserTaskManagerEJB implements SessionBean, IService {

	/**
	 * 
	 */
	public UserTaskManagerEJB() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext ctx)
		throws EJBException,
		RemoteException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.emayor.servicehandling.kernel.IService#getMyTasks(java.lang.String)
	 */
	public Tasks getMyTasks(String accessSessionId) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.emayor.servicehandling.kernel.IService#completeTask(java.lang.String, org.emayor.servicehandling.kernel.Task)
	 */
	public void completeTask(String accessSessionId, Task task)
		throws ServiceException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.emayor.servicehandling.kernel.IService#lookupTask(java.lang.String, java.lang.String)
	 */
	public Task lookupTask(String accessSessionId, String serviceSessionId)
		throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
	 */
	public void ejbCreate() throws CreateException {
		// TODO Auto-generated method stub
	}

}