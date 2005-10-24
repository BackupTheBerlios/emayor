package org.eMayor.PolicyEnforcement.CertificateValidation;

import iaik.x509.X509CRL;

import java.net.URL;
import java.net.URLConnection;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.InputStream;

import java.security.cert.CRLException;


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
	public static X509CRL fetchCRL(String url) throws ConnectException, MalformedURLException, IOException, CRLException {
		X509CRL crl = null;
			
			URL crlurl = new URL(url);
			URLConnection crlcon = crlurl.openConnection();
			InputStream myStream = crlcon.getInputStream();
			
			System.out.println("=============fetchCRL============");
			System.out.println("from URL:"+url);
			System.out.println("=============End ================");
			try {
				crl = new X509CRL(myStream);
				
			} catch (Exception e) {
				e.printStackTrace();
				
				//Hack, try once more if error
				myStream = crlurl.openStream();
				crl = new X509CRL(myStream);
			}
			

		return crl;
	}
}
