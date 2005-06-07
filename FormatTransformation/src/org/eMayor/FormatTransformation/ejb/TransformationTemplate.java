/*
 * TransformationTemplate.java
 *
 * Created on 28 Δεκέμβριος 2004, 12:17 μμ
 */

package org.eMayor.FormatTransformation.ejb;

/**
 * This class represents an abstract transformation template. Specific TransformationTemplates (e.g. * an XSLT template) shall extend this one, as for example XMLTransformationTemplate. * @author  AlexK
 */

public abstract class TransformationTemplate {

	/**
	 * Gets a string representing the type of the TransformationTamplate e.g. EUFtoSienaELFtemplate. 
	 *  @return The type of this TransformationTemplate.
	 * 
	 * @uml.property name="templateType" 
	 */
	public abstract String getTemplateType();

	/**
	 * Sets the string representing the type of the TransformationTemplate. 
	 *  @param type The type of the template to be set.
	 * 
	 * @uml.property name="templateType"
	 */
	public abstract void setTemplateType(String type);

    
}
