/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel;

/**
 * By using this service the presentation layer is able to communicate with the
 * runing process instance. All the communication is done by using the instance
 * of the class Task, which in turn contains the routing information (which
 * instance should be addressed) and the information itself as xml document
 * defined by the process.
 * 
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public interface IService {

	/**
	 * On this way the caller of this methode can obtain a list of all tasks
	 * addressed to the particullar user (citizen/civil servant).
	 * 
	 * @param accessSessionId -
	 *            this parameter identified the current user - the session has
	 *            knowledge about the user
	 * @return an holder containing an array of the open tasks will be
	 *         delivered.
	 * @throws ServiceException
	 */
	public Tasks getMyTasks(String accessSessionId) throws ServiceException;

	/**
	 * 
	 * @param accessSessionId
	 * @param task
	 * @throws ServiceException
	 */
	public void completeTask(String accessSessionId, Task task)
			throws ServiceException;

	/**
	 * 
	 * @param taskId
	 * @return
	 * @throws ServiceException
	 */
	public Task lookupTask(String taskId) throws ServiceException;

	public Task lookupTaskByServiceSession(String asid, String ssid)
			throws ServiceException;
}