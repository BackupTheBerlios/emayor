/*
 * Created on 14 ÷Â‚ 2005
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
 * @ejb.bean name="Transformer"
 *           display-name="Name for Transformer"
 *           description="Description for Transformer"
 *           jndi-name="ejb/Transformer"
 *           type="Stateless"
 *           view-type="remote"
 */
public class TransformerBean implements SessionBean {
	private TransformationController tc = new TransformationController();
	/**
	 * 
	 */
	public TransformerBean() {
		super();
		// TODO Auto-generated constructor stub
		tc.instantiateTransformationFactory();
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
	 * Performs a transformation on the inputed document based on its type and the type of transformation requested.
     * The result of the transformation is returned as a String containing an XML document in the requested format. 
     * @param documentToTransform The document to be transformed.
     * @param typeOfTransformation The type of transformation to apply.
     * @param typeOfDocument The type of the document to be transformed.
     * @return The output of the transformation.
	 */
	public String transform(
		String documentToTransform,
		String typeOfTransformation,
		String typeOfDocument) {
		// TODO Auto-generated method stub
		
		String output = tc.transform(documentToTransform, typeOfTransformation, typeOfDocument);
		
		return output;
	}
}