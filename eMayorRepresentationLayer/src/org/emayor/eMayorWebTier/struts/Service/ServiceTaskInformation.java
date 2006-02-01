package org.emayor.eMayorWebTier.struts.Service;



 /**
  *  Holds a task received from the ServiceHandling and
  *  related data.
  *  It is used by the ServiceForm for providing this information
  *  to JSP's.
  */

import org.emayor.servicehandling.kernel.Task;
import java.io.Serializable;


public class ServiceTaskInformation implements Serializable 
{

	
  private Task task;
	
  
  private boolean isRunningSuccessfully = true;
  private String errorInformation = "";
  
	
  public ServiceTaskInformation( Task _task )
  {
  	this.task = _task;
  } // constructor
  
  
  public void setIsRunningSuccessfully( boolean state )
  {
    this.isRunningSuccessfully = state;
  }
  
  public boolean getIsRunningSuccessfully()
  {
    return this.isRunningSuccessfully;
  }
  
  public void setErrorInformation( String errorInfo )
  {
    this.errorInformation = errorInfo;
  }
  
  public String getErrorInformation()
  {
    return this.errorInformation;
  }
  
  
  public String getXMLDocument()
  {
  	return this.task.getXMLDocument();  	
  }
  
  
  public String getDocumentResponse()
  {
  	return this.task.getDocumentResponse();
  }
	
  
  public Task getTask()
  {
  	return this.task;
  }
  
  
  public void setTask( Task _task )
  {
    this.task = _task;
  }

  
  public String getTaskId()
  {
  	return this.task.getTaskId();
  }
  
  
  public String getTaskStatus()
  {
  	return this.task.getStatus();
  }
  
    
  
} // ServiceTaskInformation


