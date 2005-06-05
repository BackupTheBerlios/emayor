/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.kernel;

import java.util.Date;

/**
 * @author tku
 */
public interface ISession {
	public String getSessionId() throws SessionException;
	
	public Date getStartDate() throws SessionException;

}
