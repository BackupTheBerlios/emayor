/*
 * Generated by XDoclet - Do not edit!
 */
package org.eMayor.repository.interfaces;

/**
 * Home interface for RepositoryManager.
 */
public interface RepositoryManagerHome
   extends javax.ejb.EJBHome
{
   public static final String COMP_NAME="java:comp/env/ejb/RepositoryManager";
   public static final String JNDI_NAME="ejb/RepositoryManager";

   public org.eMayor.repository.interfaces.RepositoryManager create()
      throws javax.ejb.CreateException,java.rmi.RemoteException;

}