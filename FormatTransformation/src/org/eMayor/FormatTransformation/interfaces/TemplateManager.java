/*
 * Generated by XDoclet - Do not edit!
 */
package org.eMayor.FormatTransformation.interfaces;

/**
 * Remote interface for TemplateManager.
 */
public interface TemplateManager
   extends javax.ejb.EJBObject
{
   /**
    * Business method
    * @param typeOfTransformation The type of transformation that we need a template for.
    * @param typeOfDocument The type of business document we need a transformation for.
    * @return The TransformationTemplate to be returned.    */
   public org.eMayor.FormatTransformation.ejb.XMLTransformationTemplate retrieveXMLTemplate( java.lang.String typeOfTransformation,java.lang.String typeOfDocument )
      throws java.rmi.RemoteException;

}