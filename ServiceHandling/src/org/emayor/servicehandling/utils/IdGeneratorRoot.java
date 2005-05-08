/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.utils;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.config.Config;

/**
 * @author tku
 */
public class IdGeneratorRoot {
	private static Logger log = Logger.getLogger(IdGeneratorRoot.class);

	public static final String DEF_ROOT = "123456";
	
	public String prefix = "";

	private static IdGeneratorRoot _self;

	private String root;

	private int currentId = 0;

	private IdGeneratorRoot() {
		log.debug("-> starting processing ...");
		try {
			Calendar cal = Calendar.getInstance();
			StringBuffer b = new StringBuffer();
			b.append(cal.get(Calendar.YEAR)).append("-");
			b.append(cal.get(Calendar.MONTH) + 1).append("-");
			b.append(cal.get(Calendar.DAY_OF_MONTH)).append("-");
			b.append(cal.get(Calendar.HOUR_OF_DAY)).append("-");
			b.append(cal.get(Calendar.MINUTE)).append("-");
			this.root = b.toString();
			Config config = Config.getinstance();
			this.prefix = config.getProperty("emayor.platform.instance.id");
		} catch (Exception ex) {
			log.error("caught ex: " + ex.toString());
			this.root = DEF_ROOT;
		}
		log.debug("-> ... processing DONE!");
	}
	/**
	 * 
	 * @return
	 */
	public synchronized static final IdGeneratorRoot getInstance() {
		if (_self == null)
			_self = new IdGeneratorRoot();
		return _self;
	}
	/**
	 * 
	 * @return
	 */
	public synchronized String getNextId() {
		StringBuffer b = new StringBuffer();
		b.append(this.prefix).append("-");
		b.append(root).append(this.currentId++);
		return b.toString();
	}
}