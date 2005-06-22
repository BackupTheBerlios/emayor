/*
 * Created on 22 Ιουν 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.repository.ejb;


import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;
import org.xmldb.api.*;

import javax.xml.transform.OutputKeys;
import org.w3c.dom.Document;
import java.util.Vector;

/**
 * @author AlexK
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XMLRepository {
	
	    private String _URI = null;
	    private String _driver = null;
	    
    	/** The default constructor for the class. It sets the default settings for the URI of the database location
    	 *  (which is xmldb:exist://localhost:8080/exist/xmlrpc/db/) and the driver implemenation
    	 *  (which is org.exist.xmldb.DatabaseImpl).
    	 */
	    public XMLRepository () {
	    	// default setttings
		    _URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/";
		    _driver = "org.exist.xmldb.DatabaseImpl";
	    }
	    
    	/** A costructor for the class setting the URI of the database location
    	 *  and the driver implementation based on input provided as parameters.
    	 *  @param serverURI The URI for access to the eXist database server in an XML:DB compliant format.
    	 *  @param driver The string identifying the driver for the database implemenation.
    	 */
	    public XMLRepository (String serverURI, String driver) {
	    	_URI = serverURI;
	    	_driver = driver;
	    }
	    
    	/** This method retrieves an xml document from the database based on its resource identifier.
    	 *  The document is returned as an org.w3c.dom.Document.
    	 *  @param collection The name of the collection to look for the document.
    	 *  @param resourceId The resource identifier of the document to be retrieved.
    	 *  @param username The identifier of the user calling the method used for authentication.
    	 *  @param password The password of the user calling the method used for authentication.
    	 *  @return The xml document retrieved as an org.w3c.dom.Document.
    	 */
	    public Document retrieveDocument(String collection, String resourceId, String username, String password) {
	    	Document doc = null;
	    	
	        try {
	        	// initialize database driver
	        	Class cl = Class.forName(_driver);
	        	Database database = (Database) cl.newInstance();
	        	DatabaseManager.registerDatabase(database);

	        	// get the collection
	        	Collection col = DatabaseManager.getCollection(_URI + collection, username, password);
	        	col.setProperty(OutputKeys.INDENT, "no");
	        	XMLResource res = (XMLResource)col.getResource(resourceId);
	        	if(res == null)
	        		System.out.println("document not found!");
	        	else {
	        		doc = (Document) (res.getContentAsDOM()).getOwnerDocument();
	        	}
	        		
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	    	
	    	return doc;
	    }
	    
    	/** This method retrieves an xml document from the database based on its resource identifier.
    	 *  The document is returned as a string represnetation of its xml content.
    	 *  @param collection The name of the collection to look for the document.
    	 *  @param resourceId The resource identifier of the document to be retrieved.
    	 *  @param username The identifier of the user calling the method used for authentication.
    	 *  @param password The password of the user calling the method used for authentication.
    	 *  @return The xml document retrieved as a String.
    	 */
	    public String retrieveDocumentAsXmlString(String collection, String resourceId, String username, String password) {
	    	String res = null;
	    	
	    	res = InputOutputHandler.parseDOMAsXMLString(this.retrieveDocument(collection, resourceId, username, password));
	    	
	    	return res;
	    }
	    
    	/** This method performs an XPath query to the database and returns the results
    	 *  as a Vector of Strings.
    	 *  @param collection The name of the collection to look for the document.
    	 *  @param query A string containing an XPath expression which shall act as a query against the database.
    	 *  @param username The identifier of the user calling the method used for authentication.
    	 *  @param password The password of the user calling the method used for authentication.
    	 *  @return A Vector containing the answers to the query as Strings.
    	 */
	    public Vector query(String collection, String query, String username, String password) {
	        Vector response = new Vector();
	    	try {
	    		Class cl = Class.forName(_driver);			
	    		Database database = (Database)cl.newInstance();
	    		DatabaseManager.registerDatabase(database);
	        
	    		Collection col = DatabaseManager.getCollection(_URI + collection, username, password);
	    		XPathQueryService service =
	    			(XPathQueryService) col.getService("XPathQueryService", "1.0");
	    		service.setProperty("indent", "yes");
	                
	    		ResourceSet result = service.query(query);
	    		ResourceIterator i = result.getIterator();
	    		while(i.hasMoreResources()) {
	    			Resource r = i.nextResource();
	    			response.add((String)r.getContent());
	    		}
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    	return response;
	    }
	    
    	/** This method stores an xml document given as an org.w3c.dom.Document to the database giving it
    	 *  an indentifier. If the identifier provided is null, then the database generates a unique
    	 *  identifier on its own.
    	 *  @param doc The dom represntation of the document to be stored as an org.w3c.dom.Document.
    	 *  @param resourceId The resourceId to give to the document as a unique identifier within the database. If null a unique identifier is automatically generated.
    	 *  @param collection The name of the collection to store the document under. If it does not exist, it is created.
    	 *  @param username The identifier of the user calling the method used for authentication.
    	 *  @param password The password of the user calling the method used for authentication.
    	 */
	    public void storeDomDocument(Document doc, String resourceId, String collection, String username, String password) {
	        
	    	try {
	    		// initialize driver
	    		Class cl = Class.forName(_driver);
	    		Database database = (Database)cl.newInstance();
	    		DatabaseManager.registerDatabase(database);

	    		// try to get collection
	    		Collection col = DatabaseManager.getCollection(_URI + collection, username, password);
	    		if(col == null) {
	    			// collection does not exist: get root collection and create
	    			// for simplicity, we assume that the new collection is a
	    			// direct child of the root collection, e.g. /db/test.
	    			// the example will fail otherwise.
	    			Collection root = DatabaseManager.getCollection(_URI + "/db");
	    			CollectionManagementService mgtService = (CollectionManagementService)
	                	root.getService("CollectionManagementService", "1.0");
	    			col = mgtService.createCollection(collection.substring("/db".length()));
	    		}
	    		// create new XMLResource; an id will be assigned to the new resource
	    		XMLResource document = (XMLResource)col.createResource(resourceId, "XMLResource");
	    		document.setContentAsDOM(doc.getDocumentElement());
	    		col.storeResource(document);
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	    
    	/** This method stores an xml document given as a String to the database giving it
    	 *  an indentifier. If the identifier provided is null, then the database generates a unique
    	 *  identifier on its own.
    	 *  @param doc The String representation of the document to be stored.
    	 *  @param resourceId The resourceId to give to the document as a unique identifier within the database. If null a unique identifier is automatically generated.
    	 *  @param collection The name of the collection to store the document under. If it does not exist, it is created.
    	 *  @param username The identifier of the user calling the method used for authentication.
    	 *  @param password The password of the user calling the method used for authentication.
    	 */
	    public void storeXmlStringDocument(String doc, String resourceId, String collection, String username, String password) {
	    	
	    	Document dom = InputOutputHandler.parseXMLStringAsDOM(doc);
	    	this.storeDomDocument(dom, resourceId, collection, username, password);
	    	
	    }
}
