/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.utils;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.AccessSessionLocal;
import org.emayor.servicehandling.interfaces.AccessSessionLocalHome;
import org.emayor.servicehandling.interfaces.ServiceSessionLocal;
import org.emayor.servicehandling.interfaces.ServiceSessionLocalHome;
import org.emayor.servicehandling.interfaces.SimpleIdGeneratorLocal;
import org.emayor.servicehandling.interfaces.SimpleIdGeneratorLocalHome;

/**
 * @author tku
 */
public class ServiceLocator {
	private static final Logger log = Logger.getLogger(ServiceLocator.class);

	private static ServiceLocator _self = null;

	private Context initialContext = null;

	private ServiceLocator() throws ServiceLocatorException {
		this.initInitialContext();
	}

	/**
	 * 
	 * @return
	 * @throws ServiceLocatorException
	 */
	public static synchronized final ServiceLocator getInstance()
			throws ServiceLocatorException {
		if (_self == null)
			_self = new ServiceLocator();
		return _self;
	}

	/**
	 * 
	 * @return
	 * @throws ServiceLocatorException
	 */
	public synchronized AccessSessionLocal getAccessSessionLocal()
			throws ServiceLocatorException {
		AccessSessionLocal ret = null;
		try {
			Object ref = this.initialContext.lookup("AccessSessionLocal");
			AccessSessionLocalHome home = (AccessSessionLocalHome) PortableRemoteObject
					.narrow(ref, AccessSessionLocalHome.class);
			ret = home.create();
			log.debug("getAccessSessionLocal -> got the reference!");
		} catch (NamingException nex) {
			log.error("getAccessSessionLocal - ex: " + nex.toString());
			throw new ServiceLocatorException(nex);
		} catch (CreateException cex) {
			log.error("getAccessSessionLocal - ex: " + cex.toString());
			throw new ServiceLocatorException(cex);
		}
		return ret;
	}

	public synchronized SimpleIdGeneratorLocal getSimpleIdGeneratorLocal()
			throws ServiceLocatorException {
		SimpleIdGeneratorLocal ret = null;
		try {
			Object ref = this.initialContext.lookup("SimpleIdGeneratorLocal");
			SimpleIdGeneratorLocalHome home = (SimpleIdGeneratorLocalHome) PortableRemoteObject
					.narrow(ref, SimpleIdGeneratorLocalHome.class);
			ret = home.create();
			log.debug("getSimpleIdGeneratorLocal -> got the reference!");
		} catch (NamingException nex) {
			log.error("getSimpleIdGeneratorLocal - ex: " + nex.toString());
			throw new ServiceLocatorException(nex);
		} catch (CreateException cex) {
			log.error("getSimpleIdGeneratorLocal - ex: " + cex.toString());
			throw new ServiceLocatorException(cex);
		}

		return ret;
	}

	public synchronized ServiceSessionLocal getServiceSessionLocal(
			String accessSessionId) throws ServiceLocatorException {
		log.info("getServiceSessionLocal -> starting processing ...");
		ServiceSessionLocal ret = null;
		try {
			Object ref = this.initialContext.lookup("ServiceSessionLocal");
			ServiceSessionLocalHome home = (ServiceSessionLocalHome) PortableRemoteObject
					.narrow(ref, ServiceSessionLocalHome.class);
			ret = home.create(accessSessionId);
			log.debug("getServiceSessionLocal -> got the reference!");
		} catch (NamingException nex) {
			log.error("getServiceSessionLocal - ex: " + nex.toString());
			throw new ServiceLocatorException(nex);
		} catch (CreateException cex) {
			log.error("getServiceSessionLocal - ex: " + cex.toString());
			throw new ServiceLocatorException(cex);
		}
		log.info("getServiceSessionLocal -> processing ended");
		return ret;
	}

	private void initInitialContext() throws ServiceLocatorException {
		try {
			this.initialContext = new InitialContext();
		} catch (NamingException ex) {
			log.error("initInitialContext - ex: " + ex.toString());
			throw new ServiceLocatorException(ex);
		}
	}
}