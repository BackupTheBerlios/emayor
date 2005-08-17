/*
 * TemplateManager.java
 *
 * Created on 28 Δεκέμβριος 2004, 12:13 μμ
 */

package org.eMayor.FormatTransformation.ejb;

import java.io.InputStream;

import org.w3c.dom.Document;



/**
 * This class performs all template management tasks for the FormatTranformation package.
 * @author  AlexK
 */
public class TemplateController {
    
		
    /** Creates a new instance of TemplateController */
    public TemplateController() {
    }
    
    /** Stores a template to the underlying Templates Repository declaring also its type. 
     *  @param template The template to be stored.
     *  @param typeOfTransformation The corresponding type of the document to be stored.
     */
    public void storeTemplate(TransformationTemplate template, String typeOfTransformation) {
        
    }
    
    /** Removes a template from the underlying Templates Repository based on its type. 
     *  @param typeOfTransformation The type of the template to be removed.
     */ 
    public void removeTemplate(String typeOfTransformation) {
        
    }
    
    /** Retrieves a specific template to be used in a transformation based on its type. 
     *  @param typeOfTransformation The type of the template to be retrieved.
     *  @return The template retrieved.
     */
    public XMLTransformationTemplate retrieveXMLTemplate(String typeOfTransformation, String typeOfDocument)
    	throws Exception {
        XMLTransformationTemplate tt = null;
        String fileToRetrieve = null;
        
        InputStream configIs = this.getClass().getResourceAsStream("transformerconfiguration.xml");
        Document configDom = InputOutputHandler.parseDOMInput(configIs);
        TransformerConfiguration tc = new TransformerConfiguration(configDom, typeOfDocument);
        
        if (typeOfTransformation.equalsIgnoreCase("EUFtoELF")) {
        	fileToRetrieve = tc.getEUFtoELF();
        } else if (typeOfTransformation.equalsIgnoreCase("ELFtoEUF")) {
        	fileToRetrieve = tc.getELFtoEUF();
        } else if (typeOfTransformation.equalsIgnoreCase("ELFtoEPF")) {
        	fileToRetrieve = tc.getELFtoEPF();
        } else if (typeOfTransformation.equalsIgnoreCase("EUFtoEPF")) {
        	fileToRetrieve = tc.getEUFtoEPF();
        } else {
        	throw new Exception("Transformation " + typeOfTransformation + " not supported.");
        }

        
        InputStream fileIs = this.getClass().getResourceAsStream(fileToRetrieve);
        Document xslDom = InputOutputHandler.parseDOMInput(fileIs);
        tt = new XMLTransformationTemplate(xslDom, typeOfTransformation, typeOfDocument);
        
        return tt;
    }
    
    /** Retrieves a specific template to be used in a transformation based on its location on a disk. 
     *  @param typeOfTransformation The type of the template to be retrieved.
     *  @return The template retrieved.
     */
    public XMLTransformationTemplate retrieveXMLTemplate(String fileToRetrieve, String typeOfTransformation, String typeOfDocument)
    	throws Exception {
        XMLTransformationTemplate tt = null;
 
        Document xslDom = InputOutputHandler.parseDOMInput(fileToRetrieve);
        tt = new XMLTransformationTemplate(xslDom, typeOfTransformation, typeOfDocument);
        
        return tt;
    }
    
    
    /** Adds the specific TransformationTemplate to those already managed declaring also its type. 
     *  @param template The template to be added.
     *  @param typeOfTransformation The type of the transformation the template corresponds to.
     */
    public void addTransformationTemplate(TransformationTemplate template, String typeOfTransformation) {
    
    }
    
    /** Removes the specific TransformationTemplate from those already managed. The template to be removed
     *  is identified by its type. 
     *  @param typeOfTransformation The type of the template to be removed.
     */
    public void removeTransformationTemplate(String typeOfTransformation) {
        
    }
    
    /** Retrieves a list of the types of the TransformationTemplates that are available. 
     *  @return An array with the types of available templates in the corresponding Templates Repository.
     */
    public String[] availableTransformationTemplates() {
        String[] availableTemplates = null;
        
        return availableTemplates;
    }
}
