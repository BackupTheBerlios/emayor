package org.eMayor.PolicyEnforcement.CertificateValidation;

import iaik.asn1.ObjectID;
import iaik.x509.V3Extension;
import iaik.x509.X509CRL;


import java.io.File;
import java.security.KeyStore;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
import java.security.cert.X509Certificate;
import java.net.ConnectException;

/**
 * <p>Title: CertificateValidator</p>
 * <p>Description: A class that checks the trustworthiness of a particular certificate.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Expertnet S.A.</p>
 * @author Alexandros Kaliontzoglou, Thanos Karantjias
 * @version 3.0
 */

public class CertificateValidator {

	/**
	 * 
	 * @uml.property name="tsc"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	//TrustedStoreConfiguration tsc = new TrustedStoreConfiguration();
	String CRLUri;

	private boolean checkRevocationStatus = true;

	/**
	 * 
	 * @uml.property name="trustedStore"
	 * @uml.associationEnd multiplicity="(0 -1)" elementType="java.lang.String"
	 */
	KeyStore trustedStore = null;

    /** CertificateValidator provides methods to check the validity of a given
     * X509 certificate with respect to two aspects:
     * - Whether the certificate is trusted (belongs to a trusted certificate chain).
     * - Whether the certificate is revoked (based on one ore more CRLs accessed).
     */
	public CertificateValidator(String MyCRLUri) {
		this.retrieveTrustedStoreFromDisk();
		CRLUri = MyCRLUri;
		/*if (new File(tsc.getStorePath()).exists()) {
			
		}else {
			System.out.println("Non-existent trusted store.");
		}*/
	}

    /** This method provides an answer to the question "can this certificate be
     * trusted?". It checks the certificate chain starting from the given
     * certificate up to a root CA certificate, based on certificates stored
     * in a trusted store file on the disk (a java keystore).
     * If the chain is trusted, then the method checks if it revoked and
     * provides a true or false result.
     *  @param iaikCert An X509 certificate wrapper based on the IAIK library.
     *  @return A boolean indicating whether the certificate can be trusted.
     */
	public boolean isCertTrusted(iaik.x509.X509Certificate iaikCert){ 
	
		//We extract the subject and issuer DNs to search for them in our trusted store.

		boolean certIsTrusted = false;
		String subjectDN = iaikCert.getSubjectDN().getName();
		String issuerDN = iaikCert.getIssuerDN().getName();

		try {    

			for (Enumeration aliases = trustedStore.aliases(); aliases.hasMoreElements(); ) {
				// We check each one of the certificates contained in the store by getting
				// each one's alias
				String item =(String) aliases.nextElement();
				iaik.x509.X509Certificate trustedCert = new iaik.x509.X509Certificate((trustedStore.getCertificate(item)).getEncoded());
				String trustedEntryDN = trustedCert.getSubjectDN().getName();
				String trustedEntryIssuerDN =trustedCert.getIssuerDN().getName();
				
				//System.out.println(trustedEntryDN);
				//System.out.println(trustedEntryIssuerDN);
				//System.out.println("Current Check: " + subjectDN);
				//System.out.println("Current Issuer: " + issuerDN);
    	      
				// If the issuer of the target certificate is the same as the subject of the 
				// trusted certificate we are currently analyzing AND the subject and issuer DNs of
				// the trusted cert are the same, we have found the issuer of the target
				// certificate and it is a root certificate
				if ( trustedEntryDN.equalsIgnoreCase(issuerDN) && trustedEntryIssuerDN.equals(issuerDN)) { 
					// we are in root
					// we check if the target certificate is revoked
    	    		//System.out.println("Root");
					
					if(checkRevocationStatus){
						certIsTrusted = !isCertRevoked(iaikCert);
					} else certIsTrusted=true;
    	    		
					break;
				} 
				// Here we have found the issuer, but and since it is not a Root CA it is an intermediate one
				else if (trustedEntryDN.equalsIgnoreCase(issuerDN)) { 
					// we are in intermediate CA
					//iaik.x509.X509Certificate issuerCert = new iaik.x509.X509Certificate(cert.getEncoded());
					//System.out.println("Intermediate");
					
					certIsTrusted = this.isCertTrusted(trustedCert);
    	    			
					if(checkRevocationStatus){
						// We check if it is revoked
						certIsTrusted = certIsTrusted && !isCertRevoked(iaikCert);
					} //else certIsTrusted=true;
    	    			
					break;
				} else {
					certIsTrusted = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return certIsTrusted;
	}

    /** This method provides an answer to the question "is this certificate revoked?". 
     * It searches in the certificate extensions to find a CRL distrubtion point.
     * If it doesn't find one, it checks the CDP list provided in the CRLDistributionPoints
     * configuration file.
     *  @param iaikCert An X509 certificate wrapper based on the IAIK library.
     *  @return A boolean indicating whether the certificate is revoked.
     */
	private boolean isCertRevoked(iaik.x509.X509Certificate cert){ 
		// check of revocation Status

		boolean certIsRevoked = false;
		String oidStr = "2.5.29.31";
		String name = "CRL Distribution Point";
		String shortName = "CDP";
		X509CRL crl = null;
    
		ObjectID newOID = new ObjectID(oidStr, name, shortName);
		try {
			// First we check if there is CRL Distribution Point inside
			// the certificate as an extension
			
			V3Extension cdpExt = cert.getExtension(newOID);
			if (cdpExt != null) {
				// If there is, we use that as a source for the crl
				String cdp = cdpExt.toString();
				String url = cdp.substring(cdp.lastIndexOf("http"));
				crl = CRLFetcher.fetchCRL(url);
				certIsRevoked = crl.isRevoked(cert);
			} else {
				// Otherwise we use the list provided in the CRLDistributionPoints configuration file
				//CDPConfiguration cdpConf = new CDPConfiguration();
				//Vector cdpUris = cdpConf.getCDPs();
    		
				//for (Iterator i=cdpUris.iterator(); i.hasNext(); ) 
				{
					//String url = (String) i.next();
					crl = CRLFetcher.fetchCRL(CRLUri);
					certIsRevoked = certIsRevoked || crl.isRevoked(cert);
				}
			}

		} catch (ConnectException ce) {
			// In case we fail to connect we use the predefined CDP and get a ConnectException
			System.out.println("no Connection to CRL from the Certificate, Use default");
			try {
			/*	CDPConfiguration cdpConf = new CDPConfiguration();
				Vector cdpUris = cdpConf.getCDPs();
		
				for (Iterator i=cdpUris.iterator(); i.hasNext(); ) {
					String url = (String) i.next();
					crl = CRLFetcher.fetchCRL(url);
					certIsRevoked = certIsRevoked || crl.isRevoked(cert);
				} */
				crl = CRLFetcher.fetchCRL(CRLUri);
				certIsRevoked = certIsRevoked || crl.isRevoked(cert);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return certIsRevoked;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return certIsRevoked;
	}

	public boolean validateChain(X509Certificate[] chain) {
		boolean result=false;
		
		// We begin checking top level certificates in the chain.
		// If we find one valid according to our trusted store
		
		try {
			for (int i=chain.length-1; i>=0; i--) {
				if (chain[i] != null) {
					iaik.x509.X509Certificate cert = new iaik.x509.X509Certificate(chain[i].getEncoded());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
			
		return result;
	}
	
    /** This method initializes the trustedStore parameter of this CertificateValidator
     *  based on the java keystore information contained in the TrustedStoreConfiguration file.
     */
	public void retrieveTrustedStoreFromDisk(){
		try {
			trustedStore = KeyStore.getInstance(KeyStore.getDefaultType());
			// get user password and file input stream for the cacerts
		
			String sJavaHome = System.getProperty("java.home");
			System.out.println("The java home is " + sJavaHome);
			
			String sCacertspath = sJavaHome + "\\lib\\security\\cacerts";
			char[] password = (new String("changeit")).toCharArray();
			java.io.FileInputStream fis = new java.io.FileInputStream(sCacertspath);
			trustedStore.load(fis, password);
			/*	char[] password = tsc.getStorePass().toCharArray();
			
			java.io.FileInputStream fis = new java.io.FileInputStream(tsc.getStorePath());
			trustedStore.load(fis, password);
			fis.close();
			
			*/
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
    /** This method sets whether a CRL server shall be contacted to download the appropriate
     *  CRL while checking a certificate.
     */
	public void setRevocationChecking(boolean checkRevocationStatus) {
		this.checkRevocationStatus = checkRevocationStatus;
	}
}
