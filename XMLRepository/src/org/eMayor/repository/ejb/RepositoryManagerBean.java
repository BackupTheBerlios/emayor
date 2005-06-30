/*
 * Created on 1 Ιουλ 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.repository.ejb;

import java.rmi.RemoteException;
import java.util.Vector;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import javax.ejb.CreateException;

/**
 * @ejb.bean name="RepositoryManager"
 *           display-name="Name for RepositoryManager"
 *           description="Description for RepositoryManager"
 *           jndi-name="ejb/RepositoryManager"
 *           type="Stateless"
 *           view-type="remote"
 */
public class RepositoryManagerBean implements SessionBean {
	private XMLRepository _rep = null;

	/**
	 * 
	 */

	public RepositoryManagerBean() {
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
	public void initDatabase() {
		_rep = new XMLRepository();
	}

	/**
	 * Business method
	 * @ejb.interface-method  view-type = "remote"
	 */
	public void initDatabase(String serverURI, String driver) {
		_rep = new XMLRepository(serverURI, driver);
	}

	/**
	 * Business method
	 * @throws Exception
	 * @ejb.interface-method  view-type = "remote"
	 */
	public Vector query(
		String collection,
		String xPathQuery,
		String username,
		String password) throws Exception {
		if (_rep == null) {
			throw new Exception("XML Repository not initialised!");
		}
		return _rep.query(collection, xPathQuery, username, password);
	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "remote"
	 */
	public String retrieveDocumentAsXmlString(
		String collection,
		String resourceId,
		String username,
		String password) throws Exception {
		if (_rep == null) {
			throw new Exception("XML Repository not initialised!");
		}
		return _rep.retrieveDocumentAsXmlString(
			collection,
			resourceId,
			username,
			password);
	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "remote"
	 */
	public void storeXmlStringDocument(
		String doc,
		String resourceId,
		String collection,
		String username,
		String password) throws Exception{
		if (_rep == null) {
			throw new Exception("XML Repository not initialised!");
		}
		_rep.storeXmlStringDocument(doc,resourceId,collection,username,password);
	}
}