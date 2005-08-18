/*
 * $ Created on 19.08.2005 by tku $
 */
package org.emayor.servicehandling.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.E2MWrapper;
import org.emayor.servicehandling.interfaces.E2MWrapperHome;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class E2MClient {
	private static final Logger log = Logger.getLogger(E2MClient.class);

	public static void main(String[] args) {
		BasicConfigurator.configure();
		log.setLevel(Level.ALL);
		String request = readDocument("/SampleResidenceCertificationRequestDocument.xml");
		if (log.isDebugEnabled())
			log.debug("got request:\n" + request);
		try {
			Context ctx = getInitialContext();
			Object ref = ctx.lookup(E2MWrapperHome.JNDI_NAME);
			E2MWrapperHome home = (E2MWrapperHome) PortableRemoteObject.narrow(
					ref, E2MWrapperHome.class);
			E2MWrapper e2m = home.create();
			log.debug("calling the method");
			String ret = e2m.serviceRequestPropagator(request, "1");
			log.debug("got response: \n" + ret);
		} catch (Exception ex) {
			log.error("caught ex: " + ex.toString());
			ex.printStackTrace();
		}
	}

	public static Context getInitialContext()
			throws javax.naming.NamingException {
		//return new InitialContext();

		//context initialized by jndi.properties file
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jnp.interfaces.NamingContextFactory");
		p.put(Context.URL_PKG_PREFIXES, "jboss.naming:org.jnp.interfaces");
		p.put(Context.PROVIDER_URL, "localhost:1099");
		return new javax.naming.InitialContext(p);
	}

	private static final String readDocument(String name) {
		String ret = "";
		try {
			InputStream is = E2MClient.class.getResourceAsStream(name);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			StringBuffer b = new StringBuffer();
			while ((line = br.readLine()) != null)
				b.append(line.trim());
		} catch (Exception ex) {
			log.error("caught ex: " + ex.toString());
			ex.printStackTrace();
		}
		return ret;
	}
}