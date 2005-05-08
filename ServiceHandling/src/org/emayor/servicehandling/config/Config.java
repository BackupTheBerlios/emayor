/*
 * $ Created on May 9, 2005 by tku $
 */
package org.emayor.servicehandling.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class Config {
	private static final Logger log = Logger.getLogger(Config.class);

	private static Config _self;

	private Properties props;

	/**
	 *  
	 */
	private Config() throws ConfigException {
		log.debug("-> start processing ...");
		this.props = new Properties();
		try {
			String configuration = System.getProperty("jboss.server.home.dir");
			StringBuffer b = new StringBuffer(configuration);
			b.append(File.separator).append("conf");
			b.append(File.separator).append("emayor.properties");
			if (log.isDebugEnabled())
				log
						.debug("working with following config file: "
								+ b.toString());
			File configFile = new File(b.toString());
			if (configFile.exists()) {
				this.props.load(new FileInputStream(configFile));
			} else {
				throw new ConfigException(
						"reading configuration failed: the config file doesn't exist");
			}
		} catch (FileNotFoundException fnfex) {
			log.error("caught ex: " + fnfex.toString());
			throw new ConfigException(
					"reading configuration failed: the config file doesn't exist");
		} catch (IOException ioex) {
			log.error("caught ex: " + ioex.toString());
			throw new ConfigException("couldn't read the config file");
		}
		log.debug("-> ... processing DONE!");
	}

	public static synchronized Config getinstance() throws ConfigException {
		log.debug("-> start processing ...");
		if (_self == null)
			_self = new Config();
		log.debug("-> ... processing DONE!");
		return _self;
	}

	public synchronized String getProperty(String propertyName)
			throws ConfigException {
		log.debug("-> start processing ...");
		String ret = null;
		if (!this.props.containsKey(propertyName)) {
			if (log.isDebugEnabled())
				log.debug("unknown property name: " + propertyName);
			throw new ConfigException(
					"read property error: unknown property name: "
							+ propertyName);
		} else {
			ret = this.props.getProperty(propertyName);
			if (log.isDebugEnabled()) {
				log.debug("got property value: [" + propertyName + "]=" + ret);
			}
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}
}