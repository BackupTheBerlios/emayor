/*
 * Generated by XDoclet - Do not edit!
 */
package de.fraunhofer.dms.xinco.wrapper;

/**
 * Home interface for XincoWrapper.
 * @xdoclet-generated at ${TODAY}
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface XincoWrapperHome
   extends javax.ejb.EJBHome
{
   public static final String COMP_NAME="java:comp/env/ejb/XincoWrapper";
   public static final String JNDI_NAME="ejb/XincoWrapper";

   public de.fraunhofer.dms.xinco.wrapper.XincoWrapper create()
      throws javax.ejb.CreateException,java.rmi.RemoteException;

}