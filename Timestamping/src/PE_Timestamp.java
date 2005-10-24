/*
 * Created on 24 Ïêô 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import com.baltimore.timestamp.*;
import com.baltimore.jcrypto.*;
import com.baltimore.jpkiplus.*;

import java.util.*;
import java.security.Security;

import org.apache.commons.codec.binary.Base64;

/**
 * @author AlexK
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PE_Timestamp {

	String F_PE_TimeStampDocument (String text) {
		String stamp = "";
		
	    String timeinfo = new String("");
	    //First start timestamping service - the TSA server must be on!
	      Properties prop = new Properties();
	    /*
	      Vector v = MainFrame.readParam("params.txt");
	      prop.setProperty("tsa.url", (String) v.elementAt(0));
	      prop.setProperty("hash.algorithm", "SHA1");
	      prop.setProperty("tsa.policy", (String) v.elementAt(1));
	      prop.setProperty("tsa.name", (String) v.elementAt(2));
	      prop.setProperty("tsa.certificate", (String) v.elementAt(3));
	      */
	  
	      prop.setProperty("host.url", "212.113.70.188:318");
	      prop.setProperty("hash.algorithm", "SHA1");
	      prop.setProperty("tsa.policy", "1.2.3.4");
	      prop.setProperty("tsa.name", "TSS");
	      prop.setProperty("tsa.certificate", "file:///c:/eclipse/workspace/timestamping/TSSCertificate.cer");
	  
	      Security.addProvider(new com.baltimore.jcrypto.provider.JCRYPTO());
	      try {
	        TimeStampService tss = new TimeStampService(prop);
	        System.out.println("TimeStamp Service started!");

	            TimeStamp ts = tss.requestTimeStamp(text.getBytes());
	            System.out.println("TimeStamp created OK!");
	            byte[] timestamp = ts.toDER();

	            stamp = new String(Base64.encodeBase64(timestamp));
	            System.out.println(stamp);
	            
	            String year = ts.getTime().substring(0, 4);
	            String month = ts.getTime().substring(4, 6);
	            String day = ts.getTime().substring(6, 8);
	            String hours = ts.getTime().substring(8, 12) + "h";	            
	            System.out.println("year/month/day/hours" + year + "/" + month + "/" + day + "/" + hours);
	            String tsa = ts.getTSAName().getName();
	            System.out.println("TSA: " + tsa);
	        } catch (TimeStampException tsex) {

	            tsex.printStackTrace();
	        }
	    
		Security.removeProvider("JCRYPTO");
		return stamp;
	}
	
	
	boolean F_PE_VerifyTimeStampedDocument (String stamp, String originalData) {
		boolean valid = false;
		
	    //First start timestamping service - the TSA server must be on!

	    Properties prop = new Properties();
	    Security.addProvider(new com.baltimore.jcrypto.provider.JCRYPTO());
    
	    try {
	        // Verify timestamp and read data
	        byte[] timestampd = Base64.decodeBase64(stamp.getBytes());
	        TimeStamp ts1 = new TimeStamp(timestampd);

	          boolean verifyOK = ts1.verify();
	          System.out.println("Signature in timestamp OK!");
	          if (verifyOK == true) {

	              boolean validateOK = ts1.validate(originalData.getBytes());
	              if (validateOK == true) {
	              	System.out.println("Timestamp data OK!");
	              	String time = ts1.getTime();
	              	String tsa = ts1.getTSAName().getName();
	              	System.out.println("Data timestampted at: " + time);
    	
	              	valid = true;
	              } else {
	              		valid = false;
	              }
	          }
	    } catch (TimeStampException tsex) {
	    	tsex.printStackTrace();
	    }
	    Security.removeProvider("JCRYPTO");

		return valid;
	}

	public static void main(String[] args) {
		// Create
		PE_Timestamp ts = new PE_Timestamp();
		String stamp = ts.F_PE_TimeStampDocument("hello");
		System.out.println(stamp);
		
		// Verify
		boolean validity = ts.F_PE_VerifyTimeStampedDocument(stamp, "hello");
		System.out.println(validity);
	}
}
