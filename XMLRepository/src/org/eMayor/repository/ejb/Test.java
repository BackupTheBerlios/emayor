/*
 * Created on 23 Ιουν 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.repository.ejb;

import java.io.File;
import java.io.FileInputStream;

import org.w3c.dom.Document;
import java.util.*;

/**
 * @author AlexK
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Test {

	public static void main(String[] args) {
		
        try {
        	//String test = XMLRepository.retrieveDocumentAsXmlString("test/", "ResidenceCertificationRequestDocument.xml");
        	//System.out.println(test);
        	XMLRepository xmlrep = new XMLRepository();
        	 
        	Vector answers = xmlrep.query("test/", "//aapd:Email[aapd:EmailAddress='A']", "default", "");
        	 Enumeration enum = answers.elements();
        	 while (enum.hasMoreElements()) {
        	 System.out.println((String) enum.nextElement());
        	 }
        	 answers =  xmlrep.query("test/", "/.", "default", "");
        	 enum = answers.elements();
        	 while (enum.hasMoreElements()) {
        	 System.out.println((String) enum.nextElement());
        	 }
        	
        	 
        	Document doc = InputOutputHandler.parseDOMInput(new FileInputStream(new File("C:/1.xml")));
        	xmlrep.storeDomDocument(doc, null, "test/", "default", "");
        	System.out.println("done");
        	
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
}
