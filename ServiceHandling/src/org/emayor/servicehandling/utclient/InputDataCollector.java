/*
 * Created on Feb 15, 2005
 */
package org.emayor.servicehandling.utclient;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.Task;
import org.emayor.servicehandling.kernel.UserTaskException;

/**
 * @author tku
 */
public class InputDataCollector extends UserTaskAbstractClient {
	private static Logger log = Logger.getLogger(InputDataCollector.class);

	// per def set to 10 seconds
	public static final long DEF_SLEEP_PERIOD = 10000;

	public static final byte DEF_REPEAT_NUMBER = 3;

	private UserTaskServiceClient serviceClient = null;

	private long sleepPeriod;

	private byte repeatNumber;

	public InputDataCollector() {
		this(DEF_SLEEP_PERIOD, DEF_REPEAT_NUMBER);
	}

	public InputDataCollector(long sleepPeriod) {
		this(sleepPeriod, DEF_REPEAT_NUMBER);
	}

	public InputDataCollector(long sleepPeriod, byte repeatNumber) {
		super();
		log.debug("-> start processing ...");
		this.sleepPeriod = sleepPeriod;
		this.repeatNumber = repeatNumber;
		this.serviceClient = new UserTaskServiceClient();
		log.debug("-> ... processing DONE!");
	}

	public Task validateInputData(Task task, String asid, String ssid)
			throws UserTaskException {
		log.debug("-> start processing ...");
		Task ret = null;
		try {
			log.debug("send complete request");
			this.serviceClient.completeTask(asid, task);
			byte index = 0;
			while (index < this.repeatNumber) {
				log
						.debug("continue in the loop - waiting a while before get the validated data");
				Thread.sleep(this.sleepPeriod);
				ret = this.serviceClient.lookupTask(asid, ssid);
				if (ret != null)
					index = this.repeatNumber;
				else
					++index;
				log.debug("current index after loop is = " + index);
			}
		} catch (Exception ex) {
			log.error("caught ex: " + ex.toString());
			throw new UserTaskException(ex);
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	public Task getInputDataForm(String asid, String ssid)
			throws UserTaskException {
		log.debug("-> start processing ...");
		Task ret = null;
		try {
			byte index = 0;
			while (index < this.repeatNumber) {
				log
						.debug("continue in the loop - waiting a while before get the validated data");
				Thread.sleep(this.sleepPeriod);
				ret = this.serviceClient.lookupTask(asid, ssid);
				if (ret != null)
					index = this.repeatNumber;
				else
					++index;
				log.debug("current index after loop is = " + index);
			}
		} catch (Exception ex) {
			log.error("caught ex: " + ex.toString());
			throw new UserTaskException(ex);
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * 
	 * @param task
	 *            task to be posted
	 * @param asid
	 *            access session id
	 * @param ssid
	 *            service session id
	 * @return true if successful posted, otherwise false
	 * @throws UserTaskException
	 */
	public boolean postInputData(Task task, String asid, String ssid)
			throws UserTaskException {
		log.debug("-> start processing ...");
		boolean ret = false;
		try {
			this.serviceClient.completeTask(asid, task);
			ret = true;
		} catch (Exception ex) {
			log.error("caught ex: " + ex.toString());
			throw new UserTaskException(ex);
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

}