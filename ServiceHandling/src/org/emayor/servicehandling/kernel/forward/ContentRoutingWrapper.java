/*
 * Created on 06.05.2005
 */
package org.emayor.servicehandling.kernel.forward;

import javax.naming.ServiceUnavailableException;

import org.apache.log4j.Logger;
import org.emayor.ContentRouting.ejb.AccessPointNotFoundException;
import org.emayor.ContentRouting.ejb.BindingTemplateNotFoundException;
import org.emayor.ContentRouting.ejb.OrganisationNotFoundException;
import org.emayor.ContentRouting.ejb.ServiceNotFoundException;
import org.emayor.ContentRouting.interfaces.ContentRouterLocal;
import org.emayor.servicehandling.config.Config;
import org.emayor.servicehandling.config.ConfigException;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author mxs
 */
public class ContentRoutingWrapper {
	private static final Logger log = Logger
			.getLogger(ContentRoutingWrapper.class);

	public static String getAccessPoint(String municipality, String service) {
		log.debug("-> start processing ...");
		String result = null;
		String mode = "";
		try {
			Config config = Config.getInstance();
			mode = config.getProperty("emayor.operating.mode.content.routing");
			if (log.isDebugEnabled()) {
				log.debug("content routing mode is: " + mode);
				log.debug("requested municipality is: " + municipality);
				log.debug("requested service is : " + service);
			}
		} catch (ConfigException confex) {
			log.error("caught ex: " + confex.toString());
		}
		log.info("content routing target municipality is: " + municipality);
		log.info("content routing target service is: " + service);
		if (mode.equals("test")) {
			try {
				Config config = Config.getInstance();
				String localName = config
						.getProperty("forward.manager.test.local.municipality.name");
				String remoteName = config
						.getProperty("forward.manager.test.remote.municipality.name");
				if (municipality.equals(localName)) {
					result = config
							.getProperty("forward.manager.test.local.municipality.address");
				}
				if (municipality.equals(remoteName)) {
					result = config
							.getProperty("forward.manager.test.remote.municipality.address");
				}
			} catch (ConfigException confex) {
				log.error("caught ex: " + confex.toString());
			}
		} else {
			log.debug("work in production mode");
			try {
				log
						.debug("try to get the ref to the ContentRouter local interface");
				ServiceLocator loc = ServiceLocator.getInstance();
				ContentRouterLocal content = loc.getContentRouterLocal();
				if (log.isDebugEnabled()) {
					log.debug("try to get the access point");
					log.debug("municipality: " + municipality);
					log.debug("service     : " + service);
				}
				result = content.getAccessPoint(municipality, service);
				if (log.isDebugEnabled())
					log.debug("got result from content routing: " + result);
				if (log.isDebugEnabled())
					log.debug("got from ContentRouting: " + result);
			} catch (ServiceLocatorException slex) {
				log.error("caught ex: " + slex.toString());
			} catch (OrganisationNotFoundException e) {
				log.error("caught ex: " + e.toString());
			} catch (ServiceNotFoundException e) {
				log.error("caught ex: " + e.toString());
			} catch (BindingTemplateNotFoundException e) {
				log.error("caught ex: " + e.toString());
			} catch (AccessPointNotFoundException e) {
				log.error("caught ex: " + e.toString());
			} catch (ConfigException ex) {
				// this is thrown by the ContentRoutingBean in case the config doesn't work
				log.error("caught ex: " + ex.toString());
			} catch (ServiceUnavailableException e) {
				log.error("caught ex: " + e.toString());
			}
		}
		log.debug("-> ... processing DONE!");
		return result;
	}
}