/*
 * Created on 15 Ìבס 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.FormatTransformation.ejb;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * @author AlexK
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TransformerConfiguration {
	
	private Document _domConfig;
	private String _EUFtoELF;
	private String _ELFtoEUF;
	private String _ELFtoEPF;
	private String _EUFtoEPF;
	
    /** A wrapper for a transformation configuration file. The TransformationConfiguration contains
     * 	the xml structure of the transformerconfiguration.xml file, that contains the paths where 
     *  the appropriate xslt sheets can be found. Each instance of the TransformerConfiguration
     * 	object contains transformation sheet information only for one type of document (as dictated
     * 	by the typeOfDocument parameter).
     *  @param configurationDoc An org.w3c.dom.Document representation of the xml structure of the configuration file.
     *  @param typeOfDocument The type of the document we need a transformation for.
     */
	public TransformerConfiguration(Document configurationDoc, String typeOfDocument) {
		_domConfig = configurationDoc;
		NodeList list = _domConfig.getElementsByTagName(typeOfDocument);
		Element mainNode = (Element) list.item(0);
		
		list = mainNode.getElementsByTagName("EUFtoELF");
		_EUFtoELF = ((Text)((Element) list.item(0)).getFirstChild()).getNodeValue();
		list = mainNode.getElementsByTagName("ELFtoEUF");
		_ELFtoEUF = ((Text)((Element) list.item(0)).getFirstChild()).getNodeValue();
		list = mainNode.getElementsByTagName("ELFtoEPF");
		_ELFtoEPF = ((Text)((Element) list.item(0)).getFirstChild()).getNodeValue();
		list = mainNode.getElementsByTagName("EUFtoEPF");
		_EUFtoEPF = ((Text)((Element) list.item(0)).getFirstChild()).getNodeValue();
	}
	
    /** Returns the path to the sheet that performs eUF to eLF transformation
     * 	for the document type handled by this TransformerConfiguration object. 
     *  @return The path to the transformation sheet.
     */
	public String getEUFtoELF() {
		return _EUFtoELF;
	}
    
	/** Returns the path to the sheet that performs eLF to eUF transformation
     * 	for the document type handled by this TransformerConfiguration object. 
     *  @return The path to the transformation sheet.
     */
	public String getELFtoEUF() {
		return _ELFtoEUF;
	}
    
	/** Returns the path to the sheet that performs eLF to ePF transformation
     * 	for the document type handled by this TransformerConfiguration object. 
     *  @return The path to the transformation sheet.
     */
	public String getELFtoEPF() {
		return _ELFtoEPF;
	}
	
    /** Returns the path to the sheet that performs eUF to ePF transformation
     * 	for the document type handled by this TransformerConfiguration object. 
     *  @return The path to the transformation sheet.
     */
	public String getEUFtoEPF() {
		return _EUFtoEPF;
	}
}
