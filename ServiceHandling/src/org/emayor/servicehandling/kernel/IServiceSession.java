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

	public String getServiceId() throws ServiceSessionException;

	public void setServiceId(String serviceId) throws ServiceSessionException;

	public void startService(boolean isForwarded)
			throws ServiceSessionException;

	public void stopService() throws ServiceSessionException;

	public IeMayorService geteMayorService() throws ServiceSessionException;

	public void seteMayorService(IeMayorService emayorService)
			throws ServiceSessionException;
}