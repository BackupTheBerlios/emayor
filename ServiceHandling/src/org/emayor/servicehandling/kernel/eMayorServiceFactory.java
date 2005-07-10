/*
 * Created on Feb 24, 2005
 */
package org.emayor.servicehandling.kernel;

import org.apache.log4j.Logger;

/**
 * @author tku
 */
public class eMayorServiceFactory implements IeMayorServiceFactory {
	private static Logger log = Logger.getLogger(eMayorServiceFactory.class);

	public eMayorServiceFactory() throws eMayorServiceException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IeMayorServiceFactory#setup()
	 */
	public void setup() throws eMayorServiceException {
		log.debug("-> start processing ...");

		log.debug("-> ... processing DONE!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IeMayorServiceFactory#cleanup()
	 */
	public void cleanup() throws eMayorServiceException {
		log.debug("-> start processing ...");

		log.debug("-> ... processing DONE!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IeMayorServiceFactory#startService(java.lang.String,
	 *      java.lang.String)
	 */
	public synchronized IeMayorService createService(String serviceId,
			String ssid) throws eMayorServiceException {
		log.debug("-> start processing ...");
		IeMayorService ret = null;
		try {
			log.debug("get the kernel reference");
			Kernel kernel = Kernel.getInstance();
			log.debug("get the service class name");
			String className = kernel
					.getServiceClassNameByServiceName(serviceId);
			if (log.isDebugEnabled())
				log.debug("got the class name: " + className);
			ClassLoader _loader = this.getClass().getClassLoader();
			ServiceClassLoader loader = new ServiceClassLoader(_loader);
			Class _class = loader.loadServiceClass(serviceId);
			if (_class != null)
				log.debug("forName called successful - class NOT null");
			ret = (IeMayorService) _class.newInstance();
			if (ret != null)
				log.debug("newInstance called successful - ret NOT null");
		} catch (KernelException kex) {
			log.error("caught ex: " + kex.toString());
			throw new eMayorServiceException("problem with kernel");
		} catch (IllegalAccessException iaex) {
			log.error("caught ex: " + iaex.toString());
			throw new eMayorServiceException(
					"illegal access to the class instance");
		} catch (InstantiationException iex) {
			log.error("caught ex: " + iex.toString());
			throw new eMayorServiceException("cannot get an instance");
		} catch (ServiceClassloaderException ex) {
			log.error("caught ex: " + ex.toString());
			throw new eMayorServiceException(
					"The service class could not be loaded!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

}