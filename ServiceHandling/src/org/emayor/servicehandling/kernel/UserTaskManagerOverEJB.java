/*
 * Created on Mar 4, 2005
 */
package org.emayor.servicehandling.kernel;

import org.apache.log4j.Logger;

/**
 * @author tku
 */
public class UserTaskManagerOverEJB implements IService {
	private static final Logger log = Logger
			.getLogger(UserTaskManagerOverEJB.class);

	private static UserTaskManagerOverEJB _self = null;

	private Kernel kernel;

	private UserTaskManagerOverEJB() throws UserTaskException {
		log.debug("-> start processing ...");

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
	public Tasks getMyTasks(String accessSessionId) throws ServiceException {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");

		log.debug("-> ... processing DONE!");
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IService#completeTask(java.lang.String,
	 *      org.emayor.servicehandling.kernel.Task)
	 */
	public void completeTask(String accessSessionId, Task task)
			throws ServiceException {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");

		log.debug("-> ... processing DONE!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IService#lookupTask(java.lang.String)
	 */
	public Task lookupTask(String taskId) throws ServiceException {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");

		log.debug("-> ... processing DONE!");
		return null;
	}

}