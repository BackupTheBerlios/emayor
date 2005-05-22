/*
 * Created on 23.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.PolicyEnforcement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Properties;

import org.emayor.servicehandling.config.Config;
import org.emayor.servicehandling.config.ConfigException;
import org.emayor.servicehandling.kernel.KernelException;
import org.emayor.servicehandling.kernel.ServiceInfo;
import org.emayor.servicehandling.kernel.Kernel.ServiceInfoFilenameFilter;

/**
 * @author tcaciuc
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PDP {
	public PDP() {
		// Create the Policy Decizion Point based on the configurartion file from emayor
		try {
			Config config = Config.getInstance();
			String deployDir = config.getQuilifiedDirectoryName(config
					.getProperty("emayor.pe.info.dir"));
			
			File file = new File(deployDir);
			File[] files = file.listFiles(new PolicyFilenameFilter());
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					String filename = files[i].getAbsolutePath();
					/*
					props.load(new FileInputStream(files[i]));
					ServiceInfo serviceInfo = new ServiceInfo();
					if (!serviceInfo.unmarshall(props)) {
						log
								.error("couldn't unmarshall the service info properly");
						throw new KernelException(
								"couldn't unmarshall the service info properly, filename="
										+ filename);
					} else {
						this.repository.addServiceInfo(serviceInfo);
					}
					*/
				}
			} else {
				// Generate exception No policyes
			}
		} catch (ConfigException confex) {
			// throuw exeption on configurarion error
			//throw new KernelException(
			//		"couldn't read the configuration file properly");
		} catch (IOException ioex) {
			// throuw exeption error in policy files
			
		} 
	}
	private class PolicyFilenameFilter implements FilenameFilter {
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
		 */
		public boolean accept(File dir, String filename) {
			
			boolean ret = false;
			if (filename != null && filename.endsWith(".policy.xml")) {
				ret = true;
			}
			return ret;
		}
	}
}
