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
import org.emayor.servicehandling.interfaces.AccessManagerLocal;
import org.emayor.servicehandling.interfaces.AccessManagerLocalHome;
import org.emayor.servicehandling.interfaces.AccessSessionLocal;
import org.emayor.servicehandling.interfaces.AccessSessionLocalHome;
import org.emayor.servicehandling.interfaces.KernelLocal;
import org.emayor.servicehandling.interfaces.KernelLocalHome;
import org.emayor.servicehandling.interfaces.ServiceSessionLocal;
import org.emayor.servicehandling.interfaces.ServiceSessionLocalHome;
import org.emayor.servicehandling.interfaces.SimpleIdGeneratorLocal;
import org.emayor.servicehandling.interfaces.SimpleIdGeneratorLocalHome;
import org.emayor.servicehandling.interfaces.UserTaskManagerLocal;
import org.emayor.servicehandling.interfaces.UserTaskManagerLocalHome;

/**
 * @author tku
 */
public class ServiceLocator {
	private static final Logger log = Logger.getLogger(ServiceLocator.class);

	private static ServiceLocator _self = null;

	private Context initialContext = null;

	private ServiceLocator() throws ServiceLocatorException {
		log.info("-> starting processing ...");
		this.initInitialContext();
		log.info("-> ... processing DONE!");
	}

	/**
	 * 
	 * @return
	 * @throws ServiceLocatorException
	 */
	public static synchronized final ServiceLocator getInstance()
			throws ServiceLocatorException {
		log.info("-> starting processing ...");
		if (_self == null)
			_self = new ServiceLocator();
		return _self;
	}

	public synchronized AccessManagerLocal getAccessManager()
			throws ServiceLocatorException {
		log.info("-> starting processing ...");
		AccessManagerLocal ret = null;
		try {
			Object ref = this.initialContext.lookup("AccessManager");
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
		log.info("-> ... processing DONE!");
		return ret;
	}

	/**
	 * 
	 * @return
	 * @throws ServiceLocatorException
	 */
	public synchronized AccessSessionLocal getAccessSessionLocal()
			throws ServiceLocatorException {
		log.info("-> starting processing ...");
		AccessSessionLocal ret = null;
		try {
			Object ref = this.initialContext.lookup("AccessSessionLocal");
			AccessSessionLocalHome home = (AccessSessionLocalHome) PortableRemoteObject
					.narrow(ref, AccessSessionLocalHome.class);
			ret = home.create();
			log.debug("got the reference!");
		} catch (NamingException nex) {
			log.error("caught ex: " + nex.toString());
			throw new ServiceLocatorException(nex);
		} catch (CreateException cex) {
			log.error("caught ex: " + cex.toString());
			throw new ServiceLocatorException(cex);
		}
		log.info("-> ... processing DONE!");
		return ret;
	}

	public synchronized SimpleIdGeneratorLocal getSimpleIdGeneratorLocal()
			throws ServiceLocatorException {
		log.info("-> starting processing ...");
		SimpleIdGeneratorLocal ret = null;
		try {
			Object ref = this.initialContext.lookup("SimpleIdGeneratorLocal");
			SimpleIdGeneratorLocalHome home = (SimpleIdGeneratorLocalHome) PortableRemoteObject
					.narrow(ref, SimpleIdGeneratorLocalHome.class);
			ret = home.create();
			log.debug("got the reference!");
		} catch (NamingException nex) {
			log.error("caught ex: " + nex.toString());
			throw new ServiceLocatorException(nex);
		} catch (CreateException cex) {
			log.error("caught ex: " + cex.toString());
			throw new ServiceLocatorException(cex);
		}
		log.info("-> ... processing DONE!");
		return ret;
	}

	public synchronized ServiceSessionLocal getServiceSessionLocal(
			String accessSessionId) throws ServiceLocatorException {
		log.info("-> starting processing ...");
		ServiceSessionLocal ret = null;
		try {
			Object ref = this.initialContext.lookup("ServiceSessionLocal");
			ServiceSessionLocalHome home = (ServiceSessionLocalHome) PortableRemoteObject
					.narrow(ref, ServiceSessionLocalHome.class);
			ret = home.create(accessSessionId);
			log.debug("got the reference!");
		} catch (NamingException nex) {
			log.error("caught ex: " + nex.toString());
			throw new ServiceLocatorException(nex);
		} catch (CreateException cex) {
			log.error("caught ex: " + cex.toString());
			throw new ServiceLocatorException(cex);
		}
		log.info("-> ... processing DONE!");
		return ret;
	}

	public synchronized KernelLocal getKernelLocal()
			throws ServiceLocatorException {
		log.info("-> starting processing ...");
		KernelLocal ret = null;
		try {
			Object ref = this.initialContext.lookup("KernelLocal");
			KernelLocalHome home = (KernelLocalHome) PortableRemoteObject
					.narrow(ref, KernelLocalHome.class);
			ret = home.create();
			log.debug("got the reference!");
		} catch (NamingException nex) {
			log.error("caught ex: " + nex.toString());
			throw new ServiceLocatorException(nex);
		} catch (CreateException cex) {
			log.error("caught ex: " + cex.toString());
			throw new ServiceLocatorException(cex);
		}
		log.info("-> ... processing DONE!");
		return ret;
	}

	public synchronized UserTaskManagerLocal getUserTaskManagerLocal()
			throws ServiceLocatorException {
		log.info("-> starting processing ...");
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
		log.info("-> ... processing DONE!");
		return ret;
	}

	public Context getInitialContext() {
		log.info("-> starting processing ...");
		return this.initialContext;
	}

	private void initInitialContext() throws ServiceLocatorException {
		log.info("-> starting processing ...");
		try {
			this.initialContext = new InitialContext();
		} catch (NamingException ex) {
			log.error("initInitialContext - ex: " + ex.toString());
			throw new ServiceLocatorException(ex);
		}
		log.info("-> ... processing DONE!");
	}
}