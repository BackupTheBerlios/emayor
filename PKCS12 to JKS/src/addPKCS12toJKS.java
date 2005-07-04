/*
 * Created on 01.07.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author tcaciuc
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.security.KeyStore;
import java.security.Key;
import java.security.cert.Certificate;
import java.io.*;
import java.util.*;

public class addPKCS12toJKS {
	

	public static void main(String[] args) {
		
		if (args.length != 4 ) {
			System.out.println("Usage: java addPKCS12toJKS <JKS keystore name> <JKS keystore password> <PKCS12 file name> <PKCS12 file password>");
			return;
		}
		// test
		
	try {	
		java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		// try to get the output keystorte
		KeyStore outputKeyStore = KeyStore.getInstance("JKS");
 		
		char[] nOPassword = args[1].toCharArray();
		try {
			FileInputStream ofis = new FileInputStream(args[0]);
			outputKeyStore.load(ofis, nOPassword);
			
		} catch (FileNotFoundException myException){
			// the jks file do not exist, so we create a new one
			outputKeyStore.load(null, null);
			System.out.println("The JKS keystore does not exists, so create a new one ...");
			
		}
		
			
		
		
		
			
			KeyStore inputKeyStore = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(args[2]);
			char[] nPassword = args[3].toCharArray();
			inputKeyStore.load(fis, nPassword);
			fis.close();
			
			
			
			Enumeration enum = inputKeyStore.aliases();
			
			while (enum.hasMoreElements()){ // we are readin just one certificate.
				String keyAlias = (String)enum.nextElement();
				System.out.println("alias=[" + keyAlias + "]");
				if (inputKeyStore.isKeyEntry(keyAlias)){
					Key key = inputKeyStore.getKey(keyAlias, nPassword);
					Certificate[] certChain = inputKeyStore.getCertificateChain(keyAlias);
					
					System.out.println("Please Input an alias name (max 80 chars)for the cert with the alias or press enter to use the same");
					byte[] buffer = new byte [80];
					System.in.read(buffer, 0, 80);
					String newAlias = (new String(buffer)).trim();
					
					if (newAlias.length()==0){
						  outputKeyStore.setKeyEntry(keyAlias, key, nOPassword, certChain);	
					}
						  
					else {
						outputKeyStore.setKeyEntry(newAlias, key, nOPassword, certChain);
					}
					  
				}
			}
			FileOutputStream out = new FileOutputStream(args[0]);
			outputKeyStore.store(out, nOPassword);
			out.close();

		}
		catch (Exception e){
		e.printStackTrace();
		}
		
					
		
		
		
		
		
		
		
		
	}
}

