package org.emayor.servicehandling.model;
import com.oracle.bpel.client.ServerException;
import com.oracle.services.bpel.task.IWorklistService;
import java.util.Hashtable;
import javax.naming.Context;
import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.BPELDomainCredentials;

public class Locator 
{
  private final static Logger log = Logger.getLogger(Locator.class);
  
  private static Locator _self = null;
  
  private Locator()
  {
  }
  
  public static final synchronized Locator getInstance() 
  {
    if (_self == null)
      _self = new Locator();
    return _self;
  }
  
  public synchronized IWorklistService getWorklistService(BPELDomainCredentials credentials) throws ServerException
  {
      Hashtable env = new Hashtable();
      // Standalone OC4J connection details
      env.put(Context.INITIAL_CONTEXT_FACTORY, "com.evermind.server.rmi.RMIInitialContextFactory");
      env.put(Context.SECURITY_PRINCIPAL, "admin");
      env.put(Context.SECURITY_CREDENTIALS, "welcome");
      env.put(Context.PROVIDER_URL, "ormi://localhost/orabpel");
      log.debug("getWorklistService -> create the locator instance");
      // com.oracle.bpel.client.Locator locator = new com.oracle.bpel.client.Locator("default","bpel", env);
      com.oracle.bpel.client.Locator locator = 
      	new com.oracle.bpel.client.Locator(credentials.getDomainName(),credentials.getDomainPassword(), env);
      log.debug("getWorklistService -> lookup for the Worklist Service");
      return (IWorklistService)locator.lookupService(IWorklistService.SERVICE_NAME);
  }
}