/*
 * TransformationController.java
 *
 * Created on 28 Δεκέμβριος 2004, 10:31 πμ
 */

package org.eMayor.FormatTransformation.ejb;

//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.stream.StreamResult;
//import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.dom.DOMResult;


import org.eMayor.FormatTransformation.interfaces.TemplateManagerHome;
import org.eMayor.FormatTransformation.interfaces.TemplateManager;
import org.w3c.dom.Document;
//import java.io.StringReader;
//import java.io.FileInputStream;


/**
 * This is the main class of the FormatTransformation package. External packages may utilize the exposed
 * methods to transform the various types of documents form one format to another. The class implements
 * the TransformInterface.
 * A typical example of a transformation is the following:
 * <p>
 * Creation of a TransformationController, initialization of the Controller's factory and calling
 * of the appropriate transform method:
 * <p>
 * <code>TransformationController tc = new TransformationController();</code><p>
 * <code>tc.instantiateTransformationFactory();</code><p>
 * <code>Document result = tc.transform(xmlDoc, "ELFtoEUF", "ResidenceCertificationDocument");</code>
 * <p>
 * where xmlDoc is the document to be transformed, "ELFtoEUF" is the type of transformation to perform,
 * "ResidenceCertificationDocument" is the type of the document
 * and the transformed document finally resides in the result object.
 *
 * @author  AlexK
 */

public class TransformationController {

	/**
	 * 
	 * @uml.property name="_tFactory"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private TransformerFactory _tFactory = null;

    
    /** Creates a new instance of TransformationController */
    public TransformationController() {
    }
    
    /** Initializes a TransformerFactory object for one or multiple transformations. */
    public void instantiateTransformationFactory() {
    	TransformerFactory tFactory = TransformerFactory.newInstance();
    	_tFactory = tFactory;
    }
    
    /** Performs a transformation on the inputed document based on its type and the type of transformation requested.
     *  The result of the transformation is returned as an org.w3c.dom.Document object
     *  containing an XML document in the requested format. 
     *  @param docToTransform The document to be transformed.
     *  @param typeOfDocument The type of the document to be transformed.
     *  @return The output of the transformation.
     */
    public Document transform(Document docToTransform, String typeOfTransformation, String typeOfDocument) {
        if (_tFactory == null) {
        	instantiateTransformationFactory();
        }
            
        Document result = null;
        XMLTransformationTemplate tt = null;
        TemplateManagerHome home;
        
		try {
			Context context = new InitialContext();
			Object ref = context.lookup("ejb/TemplateManager");
			home = (TemplateManagerHome) PortableRemoteObject.narrow(ref, TemplateManagerHome.class);
			TemplateManager tmBean = home.create();
			
			tt = tmBean.retrieveXMLTemplate(typeOfTransformation, typeOfDocument);
			tmBean.remove();
		}	catch (Exception e) {
			e.printStackTrace();
		}
        
        try {
            
        	Document xslDom = tt.getTemplate();
            
            // Use the DOM Document to define a DOMSource object.
            DOMSource xslDomSource = new DOMSource(xslDom);

            // Process the stylesheet DOMSource and generate a Transformer.
            Transformer transformer = _tFactory.newTransformer(xslDomSource);

            // Use the DOM Document to define a DOMSource object.
            DOMSource xmlDomSource = new DOMSource(docToTransform);
      
            // Create an empty DOMResult for the Result.
            DOMResult domResult = new DOMResult();
  
            // Perform the transformation, placing the output in the DOMResult.
            transformer.transform(xmlDomSource, domResult);
            result = (Document) domResult.getNode();
            
        } catch (TransformerConfigurationException tce) {
            tce.printStackTrace();
        } catch (TransformerException te) {
            te.printStackTrace();
        }
        
        return result;
    }

    /** Performs a transformation on the inputed document based on its type and the type of transformation requested.
     *  The result of the transformation is returned as a String containing an XML document in the requested format. 
     *  @param docToTransform The document to be transformed.
     *  @param typeOfTransformation The type of transformation to apply.
     *  @param typeOfDocument The type of the document to be transformed.
     *  @return The output of the transformation.
     */
    
    public String transform(String docToTransform, String typeOfTransformation, String typeOfDocument) {
    	Document resultDOM;
    	Document inputDOM;
    	
    	inputDOM = InputOutputHandler.parseXMLStringAsDOM(docToTransform);
    	resultDOM = transform(inputDOM, typeOfTransformation, typeOfDocument);
    	return InputOutputHandler.parseDOMAsXMLString(resultDOM);
    	
    }
    
    /** Performs a transformation on the inputed document based on its type and the type of transformation requested,
     *  locally, without contacting a template controller bean.
     *  The result of the transformation is returned as a String containing an XML document in the requested format. 
     *  @param docToTransform The document to be transformed.
     *  @param typeOfTransformation The type of transformation to apply.
     *  @param typeOfDocument The type of the document to be transformed.
     *  @return The output of the transformation.
     */
    
    public String transformLocally(String docToTransform, String typeOfTransformation, String typeOfDocument) {
    	Document resultDOM;
    	Document inputDOM;
    	
    	inputDOM = InputOutputHandler.parseXMLStringAsDOM(docToTransform);
    	resultDOM = transform(inputDOM, typeOfTransformation, typeOfDocument);
    	return InputOutputHandler.parseDOMAsXMLString(resultDOM);
    	
    }
    
    /** Performs a transformation on the inputed document based on its type and the type of transformation requested,
     *  locally, without going through the TemplateManager bean.
     *  The result of the transformation is returned as an org.w3c.dom.Document object
     *  containing an XML document in the requested format. 
     *  @param docToTransform The document to be transformed.
     *  @param typeOfDocument The type of the document to be transformed.
     *  @return The output of the transformation.
     */
    public Document transformLocally(Document docToTransform, String typeOfTransformation, String typeOfDocument, String fileNameOfTemplate) {
        if (_tFactory == null) {
        	instantiateTransformationFactory();
        }
            
        Document result = null;
        XMLTransformationTemplate tt = null;

        
		try {
			TemplateController tm = new TemplateController();
			tt = tm.retrieveXMLTemplate(fileNameOfTemplate, typeOfTransformation, typeOfDocument);

		}	catch (Exception e) {
			e.printStackTrace();
		}
        
        try {
            
        	Document xslDom = tt.getTemplate();
            
            // Use the DOM Document to define a DOMSource object.
            DOMSource xslDomSource = new DOMSource(xslDom);

            // Process the stylesheet DOMSource and generate a Transformer.
            Transformer transformer = _tFactory.newTransformer(xslDomSource);

            // Use the DOM Document to define a DOMSource object.
            DOMSource xmlDomSource = new DOMSource(docToTransform);
      
            // Create an empty DOMResult for the Result.
            DOMResult domResult = new DOMResult();
  
            // Perform the transformation, placing the output in the DOMResult.
            transformer.transform(xmlDomSource, domResult);
            result = (Document) domResult.getNode();
            
        } catch (TransformerConfigurationException tce) {
            tce.printStackTrace();
        } catch (TransformerException te) {
            te.printStackTrace();
        }
        
        return result;
    }
    
}
