/*
 * Generated by XDoclet - Do not edit!
 */
package org.emayor.servicehandling.interfaces;

/**
 * Local home interface for AccessSession.
 */
public interface AccessSessionLocalHome
   extends javax.ejb.EJBLocalHome
{
   public static final String COMP_NAME="java:comp/env/ejb/AccessSessionLocal";
   public static final String JNDI_NAME="AccessSessionLocal";

   public org.emayor.servicehandling.interfaces.AccessSessionLocal create()
      throws javax.ejb.CreateException;

}