/*
 * Generated by XDoclet - Do not edit!
 */
package org.eMayor.PolicyEnforcement.interfaces;

/**
 * Local interface for PolicyEnforcement.
 */
public interface PolicyEnforcementLocal
   extends javax.ejb.EJBLocalObject
{
   /**
    * Business method
    */
   public org.eMayor.PolicyEnforcement.C_UserProfile F_getUserProfile( java.security.cert.X509Certificate[] x509_CertChain ) ;

   /**
    * Business method
    */
   public boolean F_AuthenticateUser( org.eMayor.PolicyEnforcement.C_UserProfile UserProfile ) ;

   /**
    * Business method
    */
   public boolean F_VerifyXMLSignature( java.lang.String xmlDocument ) ;

   /**
    * Business method
    */
   public java.lang.String F_TimeStampXMLDocument( java.lang.String xmlDocumentDoc ) ;

   /**
    * Business method
    */
   public boolean F_VerifyXMLTimeStampedDocument( java.lang.String xmlDocument ) ;

   /**
    * Business method
    */
   public boolean F_AuthorizeService( org.eMayor.PolicyEnforcement.C_UserProfile UserProfile,org.eMayor.PolicyEnforcement.C_ServiceProfile ServiceProfile ) ;

   /**
    * Business method
    */
   public boolean F_AuthorizeServiceStep( org.eMayor.PolicyEnforcement.C_UserProfile UserProfile,org.eMayor.PolicyEnforcement.C_ServiceProfile ServiceProfile,org.eMayor.PolicyEnforcement.C_ServiceStep ServiceStep ) ;

}
