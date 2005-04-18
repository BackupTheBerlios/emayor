package org.eMayor.PolicyEnforcement.CertificateValidation;

import java.net.*;
import iaik.x509.X509CRL;

/**
 * <p>Title: CRLFetcher </p>
 * <p>Description: A class for downloading CRLs.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Expertnet S.A.</p>
 * @author Alexandros Kaliontzoglou
 * @version 2.0
 */


public class CRLFetcher {

    /** This method downloads the CRL hosted in the specified url.
     *  @param url The URL of the server and the file where the CRL can be found.
     *  @return An X509CRL object containing the downloaded CRL.
     */
	public static X509CRL fetchCRL(String url) {
		X509CRL crl = null;
		try {
			URL crlurl = new URL(url);
			URLConnection crlcon = crlurl.openConnection();
			crl = new X509CRL(crlcon.getInputStream());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return crl;
	}
}
