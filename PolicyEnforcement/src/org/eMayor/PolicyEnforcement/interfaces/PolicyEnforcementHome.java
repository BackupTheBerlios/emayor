/*
 * Generated by XDoclet - Do not edit!
 */
package org.eMayor.PolicyEnforcement.interfaces;

/**
 * Home interface for PolicyEnforcement.
 */
public interface PolicyEnforcementHome
   extends javax.ejb.EJBHome
{
   public static final String COMP_NAME="java:comp/env/ejb/PolicyEnforcement";
   public static final String JNDI_NAME="ejb/PolicyEnforcement";

   public org.eMayor.PolicyEnforcement.interfaces.PolicyEnforcement create()
      throws javax.ejb.CreateException,java.rmi.RemoteException;

}
