/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"><font size="-1">Tomasz Kusber</font></a><font size="-1"> FHI FOKUS (C)</font>
 */
public class UserTaskManager implements IService {

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

}
