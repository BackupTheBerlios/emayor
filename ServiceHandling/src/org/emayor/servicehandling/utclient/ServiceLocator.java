/*
 * Created on Feb 15, 2005
 */
package org.emayor.servicehandling.utclient;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.AccessManagerLocal;
import org.emayor.servicehandling.interfaces.AccessManagerLocalHome;
import org.emayor.servicehandling.interfaces.UserTaskManagerLocal;
import org.emayor.servicehandling.interfaces.UserTaskManagerLocalHome;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author tku
 */
public class ServiceLocator {
	private static Logger log = Logger.getLogger(ServiceLocator.class);

	private Context initialContext = null;

	private static ServiceLocator _self = null;

	/**
	 * 
	 * @throws ServiceLocatorException
	 */
	private ServiceLocator() throws ServiceLocatorException {
		log.debug("-> starting processing ...");
		this.initInitialContext();
		log.debug("-> ... processing DONE!");
	}

	/**
	 * 
	 * @return
	 * @throws ServiceLocatorException
	 */
	public static final synchronized ServiceLocator getInstance()
			throws ServiceLocatorException {
		if (_self == null)
			_self = new ServiceLocator();
		return _self;
	}

	public synchronized AccessManagerLocal getAccessManager()
			throws ServiceLocatorException {
		log.debug("-> starting processing ...");
		AccessManagerLocal ret = null;
		try {
			Object ref = this.initialContext.lookup("AccessManagerLocal");
			AccessManagerLocalHome home = (AccessManagerLocalHome) PortableRemoteObject
					.narrow(ref, AccessManagerLocalHome.class);
			ret = home.create();
			log.debug("got the reference !");
		} catch (NamingException nex) {
			log.error("caught ex: " + nex.toString());
			throw new ServiceLocatorException(nex);
		} catch (CreateException cex) {
			log.error("caught ex: " + cex.toString());
			throw new ServiceLocatorException(cex);
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	public synchronized UserTaskManagerLocal getUserTaskManagerLocal()
			throws ServiceLocatorException {
		log.debug("-> starting processing ...");
		UserTaskManagerLocal ret = null;
		try {
			Object ref = this.initialContext.lookup("UserTaskManagerLocal");
			UserTaskManagerLocalHome home = (UserTaskManagerLocalHome) PortableRemoteObject
					.narrow(ref, UserTaskManagerLocalHome.class);
			ret = home.create();
			log.debug("got the reference!");
		} catch (NamingException nex) {
			log.error("caught ex: " + nex.toString());
			throw new ServiceLocatorException(nex);
		} catch (CreateException cex) {
			log.error("caught ex: " + cex.toString());
			throw new ServiceLocatorException(cex);
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	private void initInitialContext() throws ServiceLocatorException {
		log.debug("-> starting processing ...");
		try {
			this.initialContext = new InitialContext();
		} catch (NamingException ex) {
			log.error("initInitialContext - ex: " + ex.toString());
			throw new ServiceLocatorException(ex);
		}
		log.debug("-> ... processing DONE!");
	}
}