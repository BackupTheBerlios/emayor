/*
 * $ Created on May 9, 2005 by tku $
 */
package org.emayor.servicehandling.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
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

	private String JBOSS_HOME_DIR = "";
	
	private String TMP_DIR;

	/**
	 *  
	 */
	private Config() throws ConfigException {
		log.debug("-> start processing ...");
		this.loadConfiguration();
		//this.listAllProperties();
		log.debug("-> ... processing DONE!");
	}

	public static synchronized Config getInstance() throws ConfigException {
		log.debug("-> start processing ...");
		if (_self == null)
			_self = new Config();
		log.debug("-> ... processing DONE!");
		return _self;
	}

	public synchronized void reloadConfiguration() throws ConfigException {
		log.debug("-> start processing ...");
		this.loadConfiguration();
		log.debug("-> ... processing DONE!");
	}

	public synchronized String getProperty(String propName, String defValue)
			throws ConfigException {
		log.debug("-> start processing ...");
		String ret = null;
		if (!this.props.containsKey(propName)) {
			if (log.isDebugEnabled())
				log.debug("unknown property name: " + propName);
			if (defValue == null || defValue.length() == 0)
				throw new ConfigException(
						"read property error: unknown property name: "
								+ propName);
			else {
				if (log.isDebugEnabled())
					log.debug("using a default value: " + defValue);
				ret = defValue.trim();
			}
		} else {
			ret = this.props.getProperty(propName).trim();
			if (log.isDebugEnabled()) {
				log.debug("got property value: [" + propName + "]=" + ret);
			}
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	public synchronized String getProperty(String propertyName)
			throws ConfigException {
		return this.getProperty(propertyName, null);
	}

	public synchronized void listPropertyNames() {
		System.out.println("-------------- all property names --------------");
		for (Enumeration e = this.props.keys(); e.hasMoreElements();) {
			System.out.println("next property name: "
					+ (String) e.nextElement());
		}
		System.out.println("------------------------------------------------");
	}
	
	public synchronized Properties getAllProperties() {
	    log.debug("-> start processing ...");
	    return this.props;
	}

	public synchronized void listAllProperties() {
		System.out.println("---------------- all properties ----------------");
		for (Enumeration e = this.props.keys(); e.hasMoreElements();) {
			String key = (String) e.nextElement();
			String value = this.props.getProperty(key);
			StringBuffer b = new StringBuffer();
			b.append("[").append(key).append("]=\"").append(value).append("\"");
			System.out.println(b.toString());
		}
		System.out.println("------------------------------------------------");
	}

	public synchronized String getQuilifiedDirectoryName(String dirName) {
		StringBuffer b = new StringBuffer(JBOSS_HOME_DIR);
		b.append(File.separator).append(dirName);
		return b.toString();
	}
	
	public synchronized String getTmpDir() {
	    return this.TMP_DIR;
	}

	private final void loadConfiguration() throws ConfigException {
		log.debug("-> start processing ...");
		this.props = new Properties();
		try {
			JBOSS_HOME_DIR = System.getProperty("jboss.server.home.dir");
			StringBuffer b = new StringBuffer(JBOSS_HOME_DIR);
			b.append(File.separator).append("conf");
			b.append(File.separator).append("emayor.properties");
			if (log.isDebugEnabled())
				log
						.debug("working with following config file: "
								+ b.toString());
			File configFile = new File(b.toString());
			if (configFile.exists()) {
				this.props.load(new FileInputStream(configFile));
				this.props.put("jboss.server.home.dir", JBOSS_HOME_DIR);
				b = new StringBuffer(this.JBOSS_HOME_DIR);
				b.append(File.separator).append(this.props.getProperty("emayor.tmp.dir"));
				this.TMP_DIR = b.toString();
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
}