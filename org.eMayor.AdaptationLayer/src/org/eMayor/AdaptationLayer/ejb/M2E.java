// Here is where each municipal should place its code
// This file implements the M2E RMI server

package org.eMayor.AdaptationLayer.ejb;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;

// UnicastRemoteObject is used instead of activation.Activatable
// This mean that the server runs all the time and uses RMI's 
// default socket transport for communication
public class M2E extends UnicastRemoteObject implements M2Einterface 
	{
	// Constructor of the remote object
    	public M2E() throws RemoteException 
    		{
        	super();
    		}

    	public String ResidenceRequest(String ResidenceCertificationRequestDocument) 
    		{
		// MunicipalResponseDocument can be 
		// a)ResidenceCertificationDocument (String) in case of success
		// b)NegativeResponseResidenceCertificationDocument (String) in case of business failure
		// c)Exception represented as a string in case of technical failure
		String MunicipalResponseDocument;
		//
		//
		//
		//All municipal code should be placed here!!!!
		//
		//
		//

		MunicipalResponseDocument= ResidenceCertificationRequestDocument + " - RMI server OK";
        	return  MunicipalResponseDocument;
    		}

    	public static void main(String args[]) 
		{ 
		// Create and install a security manager.
        	if (System.getSecurityManager() == null) 
			{ 
	    		System.setSecurityManager(new RMISecurityManager()); 
        		} 
       		try 
			{ 
	    		M2E obj = new M2E(); 
			// Bind this object instance to the name "M2EServer" in the RMI server host 
	    		Naming.rebind("M2EServer", obj); 
	    		System.out.println("M2EServer bound in registry"); 
        		} 
		catch (Exception e) 
			{ 
	    		System.out.println("M2E err: " + e.getMessage()); 
	    		e.printStackTrace(); 
        		} 
    		} 
	}