/*
 * Created on 8 Ìבס 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.ServiceHandling.PrintingUtility.ejb;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import javax.ejb.CreateException;

import java.io.InputStream;
/**
 * @ejb.bean name="Printer"
 *           display-name="Name for Printer"
 *           description="Description for Printer"
 *           jndi-name="ejb/Printer"
 *           type="Stateless"
 *           view-type="remote"
 */
public class PrinterBean implements SessionBean {

	/**
	 * 
	 */
	public PrinterBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext ctx)
		throws EJBException,
		RemoteException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
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
	 * @ejb.interface-method  view-type = "remote"
	 */
	public void print(String url, String filetypeName) {
		// TODO Auto-generated method stub
		
		
	    InputStream propsIS = this.getClass().getResourceAsStream("printing.properties");
		
		
		try {
			PrintingUtility pu = new PrintingUtility(
				url,
				propsIS,
				filetypeName);
			pu.print();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}