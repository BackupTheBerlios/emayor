/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.kernel;

/**
 * @author tku
 */
public interface IServiceSession extends ISession {
	
	public String getAccessSessionId() throws ServiceSessionException;
	
	public void setAccessSessionId(String asid) throws ServiceSessionException;
	
	public String getServiceName() throws ServiceSessionException;
	
	public String getAccessURLString() throws ServiceSessionException;
	
	public void stopService() throws ServiceSessionException;
}
