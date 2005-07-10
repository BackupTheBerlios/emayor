/*
 * Created on Jun 9, 2005
 */
package org.emayor.servicehandling.kernel;

import javax.ejb.FinderException;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.ServiceInfoEntityLocal;
import org.emayor.servicehandling.interfaces.ServiceInfoEntityLocalHome;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author tku
 */
public class ServiceClassLoader extends ClassLoader {
	private final static Logger log = Logger
			.getLogger(ServiceClassLoader.class);

	/**
	 *  
	 */
	public ServiceClassLoader() throws ServiceClassloaderException {
		log.debug("-> start processing ...");
	}

	/**
	 * @param arg0
	 */
	public ServiceClassLoader(ClassLoader arg0)
			throws ServiceClassloaderException {
		super(arg0);
		log.debug("-> start processing ...");
	}

	public Class loadClass(byte[] bytes) {
		log.debug("-> start processing ...");
		return super.defineClass(null, bytes, 0, bytes.length);
	}

	public Class loadServiceClass(String serviceId)
			throws ServiceClassloaderException {
		log.debug("-> start processing ...");
		Class ret = null;
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			ServiceInfoEntityLocalHome home = locator
					.getServiceInfoEntityLocalHome();
			ServiceInfoEntityLocal local = home.findByPrimaryKey(serviceId);
			byte[] bytes = local.getServiceClass();
			if (log.isDebugEnabled())
				log
						.debug("got from the database the class content of the length: "
								+ bytes.length);
			ret = this.loadClass(bytes);

		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new ServiceClassloaderException(
					"Couldn't load the service class with name: " + serviceId);
		} catch (FinderException ex) {
			log.error("caught ex: " + ex.toString());
			throw new ServiceClassloaderException(
					"Couldn't load the service class with name: " + serviceId);
		}
		return ret;
	}

	public Class loadServiceFactoryClass(String serviceId)
			throws ServiceClassloaderException {
		log.debug("-> start processing ...");
		Class ret = null;
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			ServiceInfoEntityLocalHome home = locator
					.getServiceInfoEntityLocalHome();
			ServiceInfoEntityLocal local = home.findByPrimaryKey(serviceId);
			byte[] bytes = local.getServiceFactoryClass();
			if (log.isDebugEnabled())
				log
						.debug("got from the database the class content of the length: "
								+ bytes.length);
			ret = this.loadClass(bytes);
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new ServiceClassloaderException(
					"Couldn't load the factory class with name: " + serviceId);
		} catch (FinderException ex) {
			log.error("caught ex: " + ex.toString());
			throw new ServiceClassloaderException(
					"Couldn't load the factory class with name: " + serviceId);
		}
		return ret;
	}
}