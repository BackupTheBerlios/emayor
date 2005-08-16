/*
 * Created on 24 ÷Â‚ 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.FormatTransformation.ejb;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import javax.ejb.CreateException;

/**
 * @ejb.bean name="TemplateManager"
 *           display-name="Name for TemplateManager"
 *           description="Description for TemplateManager"
 *           jndi-name="ejb/TemplateManager"
 *           type="Stateless"
 *           view-type="remote"
 */
public class TemplateManagerBean implements SessionBean {

	/**
	 * 
	 * @uml.property name="tManager"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	private TemplateController tManager = new TemplateController();

	/**
	 * 
	 */
	public TemplateManagerBean() {
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
	 * 
	 * Retreives the appropriate transformation template to be used, based on
	 * parameters received.
     * @param typeOfTransformation The type of transformation that we need a template for.
     * @param typeOfDocument The type of business document we need a transformation for.
     * 
     * @return The TransformationTemplate to be returned.
	 */
	public XMLTransformationTemplate retrieveXMLTemplate(String typeOfTransformation, String typeOfDocument) {
		// TODO Auto-generated method stub
		XMLTransformationTemplate tt = null;
		
		try {
			tt = tManager.retrieveXMLTemplate(typeOfTransformation, typeOfDocument);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return tt;
			
	}
}