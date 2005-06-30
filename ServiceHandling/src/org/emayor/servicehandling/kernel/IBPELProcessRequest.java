/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"><font size="-1">Tomasz Kusber</font></a><font size="-1"> FHI FOKUS (C)</font>
 */
public interface IBPELProcessRequest {
	public String getSSID();
	
	public void setSSID(String ssid);
	
	public boolean getForwardFlag();
	
	public void setForwardFlag(boolean forwardFlag);
	
	//public UserID getUserID();
	
	//public void setUserID(UserID userId);
	
	public RequestDocument getRequestDocument();
	
	public void setRequestDocument(RequestDocument requestDocument);
}
