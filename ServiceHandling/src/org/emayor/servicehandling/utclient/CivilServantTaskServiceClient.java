/*
 * $ Created on Mar 14, 2005 by tku $
 */
package org.emayor.servicehandling.utclient;

import org.apache.log4j.Logger;
import org.eMayor.PolicyEnforcement.E_PolicyEnforcementException;
import org.eMayor.PolicyEnforcement.interfaces.PolicyEnforcementLocal;
import org.emayor.servicehandling.kernel.Task;
import org.emayor.servicehandling.kernel.UserTaskException;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class CivilServantTaskServiceClient extends UserTaskServiceClient {
	private static final Logger log = Logger
			.getLogger(CivilServantTaskServiceClient.class);

	/**
	 *  
	 */
	public CivilServantTaskServiceClient() {
		super();
	}

	public Task[] getCivilServantTasks(String asid) throws UserTaskException {
		log.debug("-> start processing ...");
		Task[] ret = super.getMyTasks(asid);
		if (ret != null) {
			if (log.isDebugEnabled())
				log.debug("found some civil servant tasks: " + ret.length);
			try {
				ServiceLocator locator = ServiceLocator.getInstance();
				PolicyEnforcementLocal pe = locator.getPolicyEnforcementLocal();
				for (int i = 0; i < ret.length; i++) {
					String xml = ret[i].getXMLDocument();
					ret[i].setSignatureStatus(pe.F_VerifyXMLSignature(xml));
				}
			} catch (ServiceLocatorException slex) {
				log.error("caught ex: " + slex.toString());
				throw new UserTaskException("problem with service locator");
			} catch (E_PolicyEnforcementException pex) {
				log.error("caught ex: " + pex.toString());
				throw new UserTaskException("problem with policy enforcer");
			}
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	public void completeTask(String asid, Task task) throws UserTaskException {
		log.debug("-> start processing ...");
		super.completeTask(asid, task);
	}

}