/*
 * Created on Jun 9, 2005
 */
package org.emayor.servicehandling.kernel;

import java.io.File;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.config.Config;
import org.emayor.servicehandling.config.ConfigException;
import org.emayor.servicehandling.utils.IOManager;
import org.emayor.servicehandling.utils.IOManagerException;

/**
 * @author tku
 */
public class ServiceClassLoader extends ClassLoader {
	private final static Logger log = Logger
			.getLogger(ServiceClassLoader.class);

	private String JBOSS_HOME_DIR;

	private String classesDir;

	/**
	 *  
	 */
	public ServiceClassLoader() throws ServiceClassloaderException {
		log.debug("-> start processing ...");
		try {
			Config config = Config.getInstance();
			this.JBOSS_HOME_DIR = config.getProperty("jboss.server.home.dir");
			this.classesDir = config.getProperty("emayor.service.classes.dir");
		} catch (ConfigException ex) {
			log.error("caught ex: " + ex.toString());
			throw new ServiceClassloaderException();
		}
	}

	/**
	 * @param arg0
	 */
	public ServiceClassLoader(ClassLoader arg0)
			throws ServiceClassloaderException {
		super(arg0);
		log.debug("-> start processing ...");
		try {
			Config config = Config.getInstance();
			this.JBOSS_HOME_DIR = config.getProperty("jboss.server.home.dir");
			this.classesDir = config.getProperty("emayor.service.classes.dir");
		} catch (ConfigException ex) {
			log.error("caught ex: " + ex.toString());
			throw new ServiceClassloaderException("");
		}
	}

	public Class loadClass(byte[] bytes) {
		log.debug("-> start processing ...");
		return super.defineClass(null, bytes, 0, bytes.length);
	}

	public Class loadServiceClass(String name)
			throws ServiceClassloaderException {
		log.debug("-> start processing ...");
		Class ret = null;
		try {
			String className = name.replace('.', File.separatorChar);
			StringBuffer b = new StringBuffer(className);
			b.append(".class");
			IOManager manager = IOManager.getInstance();
			byte[] bytes = manager
					.readBinaryFile(this.classesDir, b.toString());
			ret = this.loadClass(bytes);
		} catch (IOManagerException ex) {
			log.error("caught ex: " + ex.toString());
			throw new ServiceClassloaderException("");
		}
		return ret;
	}

}