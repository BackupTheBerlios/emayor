/*
 * XMLTransformationTemplate.java
 *
 * Created on 28 ���������� 2004, 12:33 ��
 */

package org.eMayor.FormatTransformation.ejb;

import java.io.Serializable;

import org.w3c.dom.Document;

/**
 * This class represents a TransformationTemplate that can be used to in XSL transformations (e.g. an XSL stylesheet).
 * It extends the TransformationTemplate class.
 * @author  AlexK
 */
public class XMLTransformationTemplate extends TransformationTemplate implements Serializable {

	/**
	 * 
	 * @uml.property name="_template"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	private Document _template;

	/**
	 * 
	 * @uml.property name="_templateType" 
	 */
	private String _templateType;

	/**
	 * 
	 * @uml.property name="_documentType" 
	 */
	private String _documentType;

    
    /** Creates a new instance of XMLTransformationTemplate
     * 	@param templateDOM The org.w3c.dom.document object containing the structure of this transformation template.
     * 	@param templateType A string declaring the type of this template.
     * 	@param documentType A string declaring the type of documents this template can transform.
     */
    public XMLTransformationTemplate(Document templateDOM, String templateType, String documentType) {
    	_template = templateDOM;
    	_templateType = templateType;
    	_documentType = documentType;
    }

	/**
	 * Sets the internal org.w3c.dom.Document object representing the template as a DOM tree. 
	 *  @param template The org.w3c.dom.Document object to be set.
	 * 
	 * @uml.property name="_template"
	 */
	public void setTemplate(Document template) {
		_template = template;
	}

	/**
	 * Retrieves the internal org.w3c.dom.Document object representing the template as a DOM tree. 
	 *  @return The org.w3c.dom.Document object containing the DOM tree of this template.
	 * 
	 * @uml.property name="_template"
	 */
	public Document getTemplate() {
		return _template;
	}

	/**
	 * Gets a string representing the type of the TransformationTamplate e.g. EUFtoSienaELF. 
	 *  @return The type of this TransformationTemplate.
	 * 
	 * @uml.property name="_templateType"
	 */
	public String getTemplateType() {
		return _templateType;
	}

	/**
	 * Gets a string representing the type of the document that is to be transformed with this template e.g. ResidenceCertificationDocument. 
	 *  @return The type of the document transformed by this template.
	 * 
	 * @uml.property name="_documentType"
	 */
	public String getDocumentType() {
		return _documentType;
	}

	/**
	 * Sets the string representing the type of the TransformationTemplate. 
	 *  @param type The type of the template to be set.
	 * 
	 * @uml.property name="_templateType"
	 */
	public void setTemplateType(String type) {
		_templateType = type;
	}

}
