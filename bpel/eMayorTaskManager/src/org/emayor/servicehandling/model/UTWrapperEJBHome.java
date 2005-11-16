package org.emayor.servicehandling.model;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;
import javax.ejb.CreateException;

public interface UTWrapperEJBHome extends EJBHome 
{
  UTWrapperEJB create() throws RemoteException, CreateException;
}