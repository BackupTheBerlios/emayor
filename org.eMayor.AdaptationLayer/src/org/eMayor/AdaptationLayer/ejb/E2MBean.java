/*
 * Created on Aug 1, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.AdaptationLayer.ejb;

import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import java.io.*;
import java.rmi.*;
import java.rmi.Naming;

import javax.ejb.CreateException;

import org.apache.log4j.Logger;

/**
 * @ejb.bean name="E2M" display-name="Name for E2M" description="Description for
 *           E2M" jndi-name="ejb/E2M" type="Stateless" view-type="both"
 */
public class E2MBean implements SessionBean {
	private final static Logger log = Logger.getLogger(E2MBean.class);

	/**
	 *  
	 */
	public E2MBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext ctx) throws EJBException,
			RemoteException {
		// TODO Auto-generated method stub

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
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
	 */
	public void ejbCreate() throws CreateException {
		// TODO Auto-generated method stub
	}

	/**
	 * Business method
	 * 
	 * @ejb.interface-method view-type = "both"
	 */
	public String ServiceRequestPropagator(
			String ResidenceCertificationRequest, int ServiceSelection) {
		if (log.isDebugEnabled()) {
			log.debug("-> start processing ...");
			log.debug("document: " + ResidenceCertificationRequest);
			log.debug("type    : " + ServiceSelection);
		}
		log.debug("-> start processing ...");
		// Store the ip of the RMI server
		String RMIServerIP = new String();
		// Store the certificate document
		String CertReply;
		M2Einterface obj = null;
		// Retrieve the RMI Server IP
		try {
			FileReader fr = new FileReader("RMIServerIP.txt");
			BufferedReader br = new BufferedReader(fr);
			RMIServerIP = br.readLine();
			if (RMIServerIP == null) {
				System.out
						.println("Error: You must specify the RMI server IP in RMIServerIP.txt");
				System.exit(1);
			}
		} catch (IOException e) {
			// catch possible io errors from readLine()
			System.out.println("IOException error!");
			e.printStackTrace();
		}
		// Install the security manager: A security manager is required in every
		// JVM that needs to download code
		// like RMI stubs. In the case of an applet the security manager is
		// already installed from the browser
		if (System.getSecurityManager() == null) {
			log.debug("THE SECURITY MANAGER WAS NULL _> CREATE A NEW ONE");
			System.setSecurityManager(new RMISecurityManager());
		}
		try {
			//M2E server is just a string that combined with ip refers
			// to the real remote object. ServiceSelection contains the service
			// number.
			//Currently there is only one service
			// represented by the service number: 1
			switch (ServiceSelection) {
			case 1: {
				log.debug("looking for M2E server");
				obj = (M2Einterface) Naming.lookup("//"
						+ RMIServerIP.toString() + "/M2EServer");
				CertReply = obj.ResidenceRequest(ResidenceCertificationRequest);
				// Display the return string
				System.out.println(CertReply.toString());
				System.out.println("Client Executed");
				break;
			}
			default:
				System.out
						.println("Service Error: You have to specify the correct service number");
				System.exit(1);
			}

		} catch (Exception e) {
			System.out.println("E2M exception: " + e.getMessage());
			e.printStackTrace();
		}

		// TODO Auto-generated method stub
		return null;
	}
}