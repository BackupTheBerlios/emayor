/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.utclient;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.Task;
import org.emayor.servicehandling.kernel.UserTaskException;

/**
 * @author tku
 */
public class ServiceClient extends UserTaskAbstractClient {
	private static Logger log = Logger.getLogger(ServiceClient.class);

	public ServiceClient() {
		super();
	}

	public Task[] getMyTasks(String asid) throws UserTaskException {
		log.debug("-> start processing ...");
		Task[] ret = new Task[0];
		log.debug("-> ... processing DONE!");
		return ret;
	}

	public void completeTask(Task task) throws UserTaskException {
		log.debug("-> start processing ...");
		
		log.debug("-> ... processing DONE!");
	}

	public Task lookupTask(String asid, String ssid) throws UserTaskException {
		log.debug("-> start processing ...");
		Task ret = new Task();
		log.debug("-> ... processing DONE!");
		return ret;
	}

}