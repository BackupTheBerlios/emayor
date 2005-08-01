/*
 * Created on 18 Απρ 2005
 *
 */
package org.eMayor.PolicyEnforcement.CertificateValidation;

import iaik.x509.X509Certificate;

import java.io.FileInputStream;

/**
 * @author Alexandros Kaliontzoglou
 *
 */
public class Test {

    /** A demo class to show the functionality of the certificate validator.
     */
	public static void main(String[] args) {
		try {
			//Put a DER encoded X509 certificate here....
			//FileInputStream fis = new FileInputStream("C:\\civilservant1.cer");
			FileInputStream fis = new FileInputStream("C:\\citizen2.cer");
			X509Certificate cert = new X509Certificate(fis);
			CertificateValidator cv = new CertificateValidator("http://141.99.152.70/EMayor-operational.crl", true);
			System.out.println("Result:" + cv.isCertTrusted(cert));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
