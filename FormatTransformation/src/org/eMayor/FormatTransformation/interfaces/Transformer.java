/*
 * Generated by XDoclet - Do not edit!
 */
package org.eMayor.FormatTransformation.interfaces;

/**
 * Remote interface for Transformer.
 */
public interface Transformer
   extends javax.ejb.EJBObject
{
   /**
    * Business method
    * @param documentToTransform The document to be transformed.
    * @param typeOfTransformation The type of transformation to apply.
    * @param typeOfDocument The type of the document to be transformed.
    * @return The output of the transformation.    */
   public java.lang.String transform( java.lang.String documentToTransform,java.lang.String typeOfTransformation,java.lang.String typeOfDocument )
      throws java.rmi.RemoteException;

}
