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
public class DataValidatorClient extends UserTaskAbstractClient {
	private static Logger log = Logger.getLogger(DataValidatorClient.class);

	public static final long DEF_SLEEP_PERIOD = 3000;

	public static final byte DEF_REPEAT_NUMBER = 3;

	private ServiceClient serviceClient = null;

	private long sleepPeriod;

	private byte repeatNumber;

	public DataValidatorClient() {
		this(DEF_SLEEP_PERIOD, DEF_REPEAT_NUMBER);
	}

	public DataValidatorClient(long sleepPeriod) {
		this(sleepPeriod, DEF_REPEAT_NUMBER);
	}

	public DataValidatorClient(long sleepPeriod, byte repeatNumber) {
		super();
		log.debug("-> start processing ...");
		this.sleepPeriod = sleepPeriod;
		this.repeatNumber = repeatNumber;
		this.serviceClient = new ServiceClient();
		log.debug("-> ... processing DONE!");
	}

	public Task validateInputData(Task task, String asid, String ssid)
			throws UserTaskException {
		log.debug("-> start processing ...");
		Task ret = null;
		try {
			byte index = 0;
			while (index < this.repeatNumber) {
				log.debug("continue in the loop");
				this.serviceClient.completeTask(task);
				Thread.sleep(this.sleepPeriod);
				ret = this.serviceClient.lookupTask(asid, ssid);
				if (ret != null)
					index = this.repeatNumber;
				else
					++index;
				log.debug("current index after loop is = " + index);
			}
		} catch (Exception ex) {
			throw new UserTaskException(ex);
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	public boolean postInputData(Task task, String asid, String ssid)
			throws UserTaskException {
		log.debug("-> start processing ...");
		boolean ret = false;

		log.debug("-> ... processing DONE!");
		return ret;
	}

}