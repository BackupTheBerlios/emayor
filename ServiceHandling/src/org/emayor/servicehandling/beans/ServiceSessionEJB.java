/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.SimpleIdGeneratorLocal;
import org.emayor.servicehandling.kernel.IServiceSession;
import org.emayor.servicehandling.kernel.IeMayorService;
import org.emayor.servicehandling.kernel.ServiceSessionException;
import org.emayor.servicehandling.kernel.SessionException;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @ejb.bean name="ServiceSession" display-name="ServiceSessionEJB"
 *           description="Description for ServiceSession"
 *           jndi-name="ejb/ServiceSession" type="Stateful" view-type="local"
 */
public class ServiceSessionEJB implements SessionBean, IServiceSession {
	private static Logger log = Logger.getLogger(ServiceSessionEJB.class);

	private String asid;

	private String ssid;

	private String serviceName;

	private SessionContext ctx;
	
	private IeMayorService eMayorService;

	/**
	 *  
	 */
	public ServiceSessionEJB() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext ctx) throws EJBException,
			RemoteException {
		this.ctx = ctx;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public String getAccessSessionId() throws ServiceSessionException {
		log.debug("getting asid ...");
		return this.asid;
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public void setAccessSessionId(String asid) throws ServiceSessionException {
		if (log.isDebugEnabled())
			log.debug("setting asid to " + asid);
		this.asid = asid;
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public String getServiceName() throws ServiceSessionException {
		log.debug("getting service name");
		return this.serviceName;
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public String getAccessURLString() throws ServiceSessionException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public void stopService() throws ServiceSessionException {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");
		throw new ServiceSessionException("NOT IMPLEMENTED !!!");
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public String getSessionId() throws SessionException {
		log.debug("-> start processing ...");
		return this.ssid;
	}

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
	 */
	public void ejbCreate(String asid) throws CreateException {
		log.debug("-> start processing ...");
		if (asid == null || asid.length() == 0)
			throw new CreateException("the given asid has to be a valid value!");
		this.asid = asid;
		try {
			ServiceLocator serviceLocator = ServiceLocator.getInstance();
			SimpleIdGeneratorLocal simpleIdGeneratorLocal = serviceLocator
					.getSimpleIdGeneratorLocal();
			this.ssid = simpleIdGeneratorLocal.generateId();
			if (log.isDebugEnabled())
				log.debug("generated following ssid : " + ssid);
		} catch (ServiceLocatorException slex) {
			throw new CreateException(slex.toString());
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public IeMayorService geteMayorService() throws ServiceSessionException {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");

		log.debug("-> ... processing DONE!");
		return null;
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public void seteMayorService(IeMayorService emayorService) throws ServiceSessionException {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");
		
		log.debug("-> ... processing DONE!");
	}

}