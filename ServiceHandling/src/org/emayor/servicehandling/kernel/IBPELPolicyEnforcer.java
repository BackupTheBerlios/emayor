/*
 * Created on Mar 10, 2005
 */
package org.emayor.servicehandling.kernel;

/**
 * @author tku
 */
public interface IBPELPolicyEnforcer {

	public String FPM_GetSignerRole(String xmlDocument) 
			throws BPELPolicyEnforcerException;
	
	public boolean F_VerifyXMLSignature(String xmlDocument, String ssid)
			throws BPELPolicyEnforcerException;

	public String F_TimeStampXMLDocument(String xmlDocumentDoc)
			throws BPELPolicyEnforcerException;

	public boolean F_VerifyXMLTimeStampedDocument(String xmlDocument)
			throws BPELPolicyEnforcerException;

	public boolean F_AuthorizeServiceStep(String userId, String ssid,
			String serviceStepID) throws BPELPolicyEnforcerException;
}