/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class BPELDomainCredentials implements java.io.Serializable {
	private String domainName;
	private String domainPassword;
	
	public BPELDomainCredentials() {
		
	}
	
	public String getDomainName() {
		return this.domainName;
	}
	
	public void setDomainName(String domainName) {
		this.domainName = domainName;	
	}	
	
	public String getDomainPassword() {
		return this.domainPassword;
	}
	
	public void setDomainPassword(String domainPassword) {
		this.domainPassword = domainPassword;
	}
}