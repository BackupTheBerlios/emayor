/*
 * Created on Mar 10, 2005
 */
package org.emayor.servicehandling.kernel;

import org.eMayor.PolicyEnforcement.C_ServiceStep;

/**
 * @author tku
 */
public interface IBPELPolicyEnforcer {

	public boolean F_VerifyXMLSignature(String xmlDocument)
			throws BPELPolicyEnforcerException;

	public String F_TimeStampXMLDocument(String xmlDocumentDoc)
			throws BPELPolicyEnforcerException;

	public boolean F_VerifyXMLTimeStampedDocument(String xmlDocument)
			throws BPELPolicyEnforcerException;

	public boolean F_AuthorizeServiceStep(String userId, String ssid,
			C_ServiceStep ServiceStep) throws BPELPolicyEnforcerException;
}