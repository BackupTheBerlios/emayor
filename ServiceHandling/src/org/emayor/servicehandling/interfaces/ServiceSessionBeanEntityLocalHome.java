/*
 * Generated by XDoclet - Do not edit!
 */
package org.emayor.servicehandling.interfaces;

/**
 * Local home interface for ServiceSessionBeanEntity.
 */
public interface ServiceSessionBeanEntityLocalHome
   extends javax.ejb.EJBLocalHome
{
   public static final String COMP_NAME="java:comp/env/ejb/ServiceSessionBeanEntityLocal";
   public static final String JNDI_NAME="ServiceSessionBeanEntityLocal";

   public org.emayor.servicehandling.interfaces.ServiceSessionBeanEntityLocal create(java.lang.String ssid)
      throws javax.ejb.CreateException;

   public java.util.Collection findByASID(String asid)
      throws javax.ejb.FinderException;

   public java.util.Collection findByServiceID(String serviceId)
      throws javax.ejb.FinderException;

   public org.emayor.servicehandling.interfaces.ServiceSessionBeanEntityLocal findByPrimaryKey(java.lang.String pk)
      throws javax.ejb.FinderException;

}
