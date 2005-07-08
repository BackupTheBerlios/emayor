/*
 * $ Created on May 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel.forward;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class CertificatesRepository {
	private static final Logger log = Logger
			.getLogger(CertificatesRepository.class);

	private static CertificatesRepository _self;

	private HashMap name2UserProfile;

	/**
	 *  
	 */
	private CertificatesRepository() {
		this.name2UserProfile = new HashMap();
		this.initData();
	}

	public static CertificatesRepository getInstance() {
		if (_self == null)
			_self = new CertificatesRepository();
		return _self;
	}

	public synchronized String getUserProfileString(String name) {
		log.debug("-> starting processing ...");
		String ret = null;
		if (this.name2UserProfile.containsKey(name))
			ret = (String) this.name2UserProfile.get(name);
		else {
			log
					.debug("the profile for given name hasn'r been found in the repository: "
							+ name);
		}
		return ret;
	}

	private void initData() {
		log.debug("-> starting processing ...");
		try {
			String profile = this
					.readResource("UserProfile-Server1-Citizen.xml");
			this.name2UserProfile.put("server1", profile);
			profile = this.readResource("UserProfile-Server2-Citizen.xml");
			this.name2UserProfile.put("server2", profile);
		} catch (Exception ex) {
			log.error("caught ex: " + ex.toString());
		}
		log.debug("-> ... processing DONE!");
	}

	private String readResource(String resourceName) throws IOException {
		log.debug("-> starting processing ...");
		StringBuffer b = new StringBuffer();
		InputStream is = this.getClass().getResourceAsStream(resourceName);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		while ((line = br.readLine()) != null)
			b.append(line);
		log.debug("-> ... processing DONE!");
		return b.toString();
	}

}