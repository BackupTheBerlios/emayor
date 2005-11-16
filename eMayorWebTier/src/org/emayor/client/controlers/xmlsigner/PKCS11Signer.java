//==============================================================================
// Copyright (C) 2005 Advanced Encryption Technology Europe B.V.
// Copyright (C) 2005 eMayor Consortium (http://www.emayor.org)
// All rights are reserved. Reproduction in whole or in part is prohibited
// without the written consent of the copyright owner.
//
// Package: org.emayor.xmlsigner
// Class:   PKCS11Signer
// Version: $Id: PKCS11Signer.java,v 1.1 2005/11/16 10:51:00 emayor Exp $
// Product: Java XML signing applet
//
// Description:
//		PKCS #11 based Signer implementation
//==============================================================================

package org.emayor.client.controlers.xmlsigner;

import iaik.pkcs.pkcs11.*;
import iaik.pkcs.pkcs11.objects.*;

public class PKCS11Signer extends SignerFactory implements Signer
{
    //==========================================================================
    // Member variables
    //==========================================================================
    
    // PKCS #11 module instance
    private Module				p11Module					= null;
    
    private Token				p11Token					= null;
    
    private Session				p11Session					= null;
    
    private RSAPrivateKey		p11PrivateSigningKey		= null;
    
    private byte[]				signingCertificate			= null;
    
    //==========================================================================
    // Constructors
    //==========================================================================
    
    private PKCS11Signer()
    {
        // Initialise the PKCS #11 library
        initialisePKCS11();
        
        try
        {
            // Find tokens
	        Slot[] slotsWithToken = p11Module.getSlotList(
	            Module.SlotRequirement.TOKEN_PRESENT);
	        
	        for (int i = 0; i < slotsWithToken.length; i++)
	        {
	            try
	            {
	                p11Token = slotsWithToken[i].getToken();
	                
	                p11Session = p11Token.openSession(
	                    Token.SessionType.SERIAL_SESSION,
	                    Token.SessionReadWriteBehavior.RW_SESSION,
	                    null,
	                    null);
	                
	                // Log in, if necessary
	                login();
	                
	                // Search for signing keys
	                RSAPrivateKey searchTemplate = new RSAPrivateKey();
	                searchTemplate.getSign().setBooleanValue(Boolean.TRUE);
	                
	                p11Session.findObjectsInit(searchTemplate);
	                
	                iaik.pkcs.pkcs11.objects.Object[] foundObjects = 
	                    p11Session.findObjects(1);
	                
	                p11Session.findObjectsFinal();
	                
	                if ((foundObjects == null) || (foundObjects.length == 0))
	                {
	                    continue;
	                }
	                
	                // Found a signing key, we're ready!
	                p11PrivateSigningKey = (RSAPrivateKey) foundObjects[0];
	                
	                System.out.println("Found a suitable signing key on the token with the following label: ");
	                
	                TokenInfo tokenInfo = p11Token.getTokenInfo();
	                
	                System.out.println("\"" + tokenInfo.getLabel() + "\"");
	                
	                return;
	            }
	            catch (TokenException te)
	            {
	                continue;
	            }
	        }
        }
        catch (TokenException te)
        {
            // The signer exception is thrown below
        }
        
        throw new SignerException
        (
            "Unable to find a token or suitable key-pair"
        );
    }
    
    private PKCS11Signer(byte[] searchCriterium, int criterium)
    {
        // Initialise the PKCS #11 library
        initialisePKCS11();
        
        try
        {
            // Find tokens
	        Slot[] slotsWithToken = p11Module.getSlotList(
	            Module.SlotRequirement.TOKEN_PRESENT);
	        
	        for (int i = 0; i < slotsWithToken.length; i++)
	        {
	            try
	            {
	                p11Token = slotsWithToken[i].getToken();
	                
	                p11Session = p11Token.openSession(
	                    Token.SessionType.SERIAL_SESSION,
	                    Token.SessionReadWriteBehavior.RW_SESSION,
	                    null,
	                    null);
	                
	                // Log in, if necessary
	                login();
	                
	                // Search for certificates based on the specified
	                // criterium
	                X509PublicKeyCertificate certificateSearch =
	                    new X509PublicKeyCertificate();
	                /*
	                	                
	                switch(criterium)
	                {
	                case SignerFactory.SEARCH_SUBJECT:
	                    certificateSearch.getSubject().setByteArrayValue(
	                        searchCriterium);
	                	break;
	                case SignerFactory.SEARCH_ISSUER:
	                    certificateSearch.getIssuer().setByteArrayValue(
		                        searchCriterium);
		               	break;
	                case SignerFactory.SEARCH_ID:
	                    certificateSearch.getId().setByteArrayValue(
		                        searchCriterium);
		                break;
	                }
	                */
	                p11Session.findObjectsInit(certificateSearch);
	                
	                iaik.pkcs.pkcs11.objects.Object[] foundObjects = 
	                    p11Session.findObjects(1);
	                
	                p11Session.findObjectsFinal();
	                
	                if ((foundObjects == null) || (foundObjects.length == 0))
	                {
	                    continue;
	                }
	                
	                X509PublicKeyCertificate certificate =
	                    (X509PublicKeyCertificate) foundObjects[0];
	                
	                // A certificate was found, find the matching private key
	                RSAPrivateKey searchTemplate = new RSAPrivateKey();
	                searchTemplate.getId().setByteArrayValue(
	                    certificate.getId().getByteArrayValue());
	                
	                p11Session.findObjectsInit(searchTemplate);
	                
	                foundObjects = p11Session.findObjects(1);
	                
	                p11Session.findObjectsFinal();
	                
	                if ((foundObjects == null) || (foundObjects.length == 0))
	                {
	                    continue;
	                }
	                
	                // Found a signing key, we're ready!
	                p11PrivateSigningKey = (RSAPrivateKey) foundObjects[0];
	                
	                System.out.println("Found a specific signing key based on the search criteria on the token with the following label:");
	                
	                TokenInfo tokenInfo = p11Token.getTokenInfo();
	                
	                System.out.println("\"" + tokenInfo.getLabel() + "\"");
	                
	                // Store the signing certificate
	                signingCertificate = 
	                    certificate.getValue().getByteArrayValue();
	                
	                return;
	            }
	            catch (TokenException te)
	            {
	                continue;
	            }
	        }
        }
        catch (TokenException te)
        {
            // The signer exception is thrown below
        }
        
        throw new SignerException
        (
            "Unable to find a token or suitable key-pair"
        );
    }
    
    //==========================================================================
    // Initialise the PKCS #11 library
    //==========================================================================
    
    private void initialisePKCS11()
    {
        // Get an instance of the PKCS #11 module
        try
        {
            // Load AET PKCS #11 module
            p11Module = Module.getInstance("aetpkssw.dll");
            
            p11Module.initialize(null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            throw new SignerException
            	(
            	    "Unable to get a reference to the PKCS #11 library"
            	);
        }
    }
    
    //==========================================================================
    // Login if required
    //==========================================================================
    
    private void login()
    {
        try
        {
            TokenInfo tokenInfo = p11Token.getTokenInfo();
            
            if (tokenInfo.isLoginRequired())
            {
                p11Session.login(Session.UserType.USER, null);
            }
        }
        catch (TokenException te)
        {
            throw new SignerException("Failed to log in to the token");
        }
    }
    
    //==========================================================================
    // Finalise the object
    //==========================================================================
    
    protected void finalize()
    {
        if (p11Module != null)
        {
            try
            {
                // Finalise the PKCS #11 library
                p11Module.finalize(null);
            }
            catch (Exception e)
            {
            }
        }
    }
    
    //==========================================================================
    // Sign the specified signable object
    //==========================================================================
    
    public byte[] sign(Signable signable)
    {
        // Retrieve the canonical representation of the signable object
        byte[] data = signable.getCanonicalEncoding();
        
        byte[] signature = null;
        
        try
        {
            // Sign the data
            p11Session.signInit(
                Mechanism.SHA1_RSA_PKCS, p11PrivateSigningKey);
            
            signature = p11Session.sign(data);
        }
        catch (TokenException te)
        {
            throw new SignerException("Failed to sign the supplied data");
        }
        
        return signature;
    }
    
    //==========================================================================
    // Digest the specified signable object
    //==========================================================================
    
    public byte[] digest(Signable signable)
    {
        // Retrieve the canonical representation of the signable object
        byte[] data = signable.getCanonicalEncoding();
        
        byte[] digest = null;
        
        try
        {
            // Digest the data
            p11Session.digestInit(Mechanism.SHA_1);
            
            digest = p11Session.digest(data);
        }
        catch (TokenException te)
        {
            throw new SignerException("Failed to digest the supplied data");
        }
        
        return digest;
    }
    
    //==========================================================================
    // Return the signing certificate (if any)
    //==========================================================================
    
    public byte[] getSigningCertificate()
    {
        return signingCertificate;
    }
    
    //==========================================================================
    // Return the modulus of the signing key
    //==========================================================================
    
    public byte[] getSigningKeyModulus()
    {
        if (p11PrivateSigningKey != null)
        {
            return p11PrivateSigningKey.getModulus().getByteArrayValue();
        }
        
        return null;
    }
    
    //==========================================================================
    // Return the exponent of the signing key
    //==========================================================================
    
    public byte[] getSigningKeyExponent()
    {
        if (p11PrivateSigningKey != null)
        {
            return p11PrivateSigningKey.getPublicExponent().getByteArrayValue();
        }
        
        return null;
    }

    //==========================================================================
    // Retrieve a signer instance. In this case, a signer based on the first
    // available key-pair on the token will be returned
    //==========================================================================

    public static Signer getInstance()
    {
        return new PKCS11Signer();
    }

    //==========================================================================
    // Retrieve a signer instance. In this case, a signer based on the first
    // available key-pair belonging to the certificate matching the search
    // criteria will be returned
    //==========================================================================
    
    public static Signer getInstance(byte[] searchCriterium, int criterium)
    {
        return new PKCS11Signer(searchCriterium, criterium);
    }
}
