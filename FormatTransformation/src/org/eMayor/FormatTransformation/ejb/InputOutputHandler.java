/*
 * InputOutputHandler.java
 *
 * Created on 28 Δεκέμβριος 2004, 12:35 μμ
 */

package org.eMayor.FormatTransformation.ejb;

import org.apache.xml.serializer.Serializer;
import org.apache.xml.serializer.SerializerFactory;
import org.apache.xml.serializer.OutputPropertiesFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import org.xml.sax.InputSource;
import java.io.StringReader;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException; 
import java.io.StringWriter;
import java.io.InputStream;

/**
 *
 * @author  AlexK
 */
public class InputOutputHandler {
    	
    	/** Serializes an org.w3c.dom.Document into a file. 
    	 *  @param domResult The document to be serialized.
    	 *  @param target The file name where the output should be written to.
    	 */
        public static void serializeDOMOutput(Document domResult, String target) {
        
            //Instantiate an Xalan XML serializer and use it to serialize the output DOM to System.out
            // using a default output format.
            Serializer serializer = SerializerFactory.getSerializer
                             (OutputPropertiesFactory.getDefaultMethodProperties("xml"));
       
            try {
                serializer.setOutputStream(new FileOutputStream(target));
                serializer.asDOMSerializer().serialize(domResult);
            } catch (IOException IOe) {
            	IOe.printStackTrace();
            } 
     
        }
        
    	/** Parses an InputStream to receive an org.w3c.dom.Document representation of
    	 *  the xml content of the stream. 
    	 *  @param inputName The name of the InputStream to be read.
    	 *  @return The xml content of the stream as an org.w3c.dom.Document.
    	 */
        
        public static Document parseDOMInput(InputStream inputName) {
            Document sourceDoc = null;
            DocumentBuilderFactory dFactory = null;
            if (dFactory == null) {
                //Instantiate a DocumentBuilderFactory if not already instantiated
                dFactory = DocumentBuilderFactory.newInstance();
                // And setNamespaceAware (required when parsing xsl files)
                dFactory.setNamespaceAware(true);
            }

            try {
                //Use the DocumentBuilderFactory to create a DocumentBuilder.
                DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
                //Use the DocumentBuilder to parse the source
                sourceDoc = dBuilder.parse(inputName);
                
                
            } catch (ParserConfigurationException pce) {
            	pce.printStackTrace();
            } catch (SAXException SAXe) {
            	SAXe.printStackTrace();
            } catch (IOException IOe) {
            	IOe.printStackTrace();
            }
              return sourceDoc;
        }
        
    	/** Parses the file represented by a String to receive an org.w3c.dom.Document 
    	 *  representation of the xml content of the file. 
    	 *  @param inputName The name of the file to be read.
    	 *  @return The xml content of the file as an org.w3c.dom.Document.
    	 */
        public static Document parseDOMInput(String inputName) {
            Document sourceDoc = null;
            DocumentBuilderFactory dFactory = null;
            if (dFactory == null) {
                //Instantiate a DocumentBuilderFactory if not already instantiated
                dFactory = DocumentBuilderFactory.newInstance();
                // And setNamespaceAware (required when parsing xsl files)
                dFactory.setNamespaceAware(true);
            }

            try {
                //Use the DocumentBuilderFactory to create a DocumentBuilder.
                DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
                //Use the DocumentBuilder to parse the source
                sourceDoc = dBuilder.parse(inputName);
                
                
            } catch (ParserConfigurationException pce) {
            	pce.printStackTrace();
            } catch (SAXException SAXe) {
            	SAXe.printStackTrace();
            } catch (IOException IOe) {
            	IOe.printStackTrace();
            }
              return sourceDoc;
        }
    	
        /** Parses the String which contains an xml document to receive 
         *  an org.w3c.dom.Document representation of it.
    	 *  @param inputName The String to be parsed.
    	 *  @return The xml content of the String as an org.w3c.dom.Document.
    	 */

        public static Document parseXMLStringAsDOM(String xmlString) {
            Document sourceDoc = null;
            DocumentBuilderFactory dFactory = null;
            if (dFactory == null) {
                //Instantiate a DocumentBuilderFactory if not already instantiated
                dFactory = DocumentBuilderFactory.newInstance();
                // And setNamespaceAware (required when parsing xsl files)
                dFactory.setNamespaceAware(true);
            }

            try {
                //Use the DocumentBuilderFactory to create a DocumentBuilder.
                DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
                //Use the DocumentBuilder to parse the source
                sourceDoc = dBuilder.parse(new InputSource(new StringReader(xmlString)));
                
            } catch (ParserConfigurationException pce) {
            	pce.printStackTrace();
            } catch (SAXException SAXe) {
            	SAXe.printStackTrace();
            } catch (IOException IOe) {
            	IOe.printStackTrace();
            }
        
            return sourceDoc;
        }

        /** Parses an org.w3c.dom.Document to receive its xml content as a String.
    	 *  @param inputDOM The Document to be parsed.
    	 *  @return The String representation of the org.w3c.dom.Document.
    	 */
        public static String parseDOMAsXMLString(Document inputDOM) {
   
        	StringWriter stringOut = new StringWriter();
        	
            Serializer serializer = SerializerFactory.getSerializer
            (OutputPropertiesFactory.getDefaultMethodProperties("xml"));

            try {
            	serializer.setWriter(stringOut);
            	serializer.asDOMSerializer().serialize(inputDOM);
            } catch (IOException IOe) {
            	IOe.printStackTrace();
            } 
        	
        	return stringOut.toString(); 
        }
        
}
