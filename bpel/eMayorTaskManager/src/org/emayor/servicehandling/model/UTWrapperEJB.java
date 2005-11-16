package org.emayor.servicehandling.model;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import org.emayor.servicehandling.kernel.ITask;
import com.oracle.bpel.client.ServerException;
import com.oracle.bpel.client.util.WhereCondition;
import org.emayor.servicehandling.kernel.Tasks;
import org.emayor.servicehandling.kernel.BPELDomainCredentials;

public interface UTWrapperEJB extends EJBObject 
{
  ITask lookupTask(String taskId, BPELDomainCredentials credentials) throws RemoteException, UTWrapperException;


  Tasks listTasksByAssignee(String assignee, BPELDomainCredentials credentials) throws RemoteException, UTWrapperException;

  Tasks lookupTasksByAssigneeAndTitle(String assignee, String title, BPELDomainCredentials credentials) throws RemoteException, UTWrapperException;

  void completeTask(ITask task, BPELDomainCredentials credentials, String userID) throws RemoteException, UTWrapperException;
}