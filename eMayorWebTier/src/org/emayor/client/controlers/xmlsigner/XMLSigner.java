//==============================================================================
// Copyright (C) 2005 Advanced Encryption Technology Europe B.V.
// Copyright (C) 2005 eMayor Consortium (http://www.emayor.org)
// All rights are reserved. Reproduction in whole or in part is prohibited
// without the written consent of the copyright owner.
//
// Package: org.emayor.xmlsigner
// Class:   XMLSigner
// Version: $Id: XMLSigner.java,v 1.1 2005/11/16 10:51:00 emayor Exp $
// Product: Java XML signing applet
//
// Description:
//		Main applet class for the XML signing applet
//==============================================================================

package org.emayor.client.controlers.xmlsigner;

import iaik.pkcs.pkcs11.provider.IAIKPkcs11;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.*;

import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.Constants;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Element;



public class XMLSigner extends Applet implements ActionListener
{
    //==========================================================================
    // Member variables
    //==========================================================================
    
    private TextArea			theDocument			= null;
    
    private Button				signButton			= null;
    
    private FormReader			inputFormReader		= null;
    
    private FormWriter			outputFormWriter	= null;
    
    private Signer				signer				= null;
    
    private byte[]				certIssuer			= null;
    
    private boolean				submitAfterSign		= false;
    
    
       
    /**
     * The PKCS#11 JCE provider.
     */
    private IAIKPkcs11 			iaikPkcs11Provider_	= null;

	private PrivateKey 			signatureKey_		= null;
	private X509Certificate 	signerCertificate	= null;

    //==========================================================================
    // Methods
    //==========================================================================
    
    public void initCryptoProviders()
    {
    	System.out.println("initializing: initCryptoProviders()");
    	try {
      		if ((iaikPkcs11Provider_ = IAIKPkcs11.getProviderInstance(1)) == null) {
    	  		Properties pkcs11ProviderConfig = new Properties();
        		InputStream configStream = 
        			getClass().getClassLoader().getResourceAsStream("iaik/pkcs/pkcs11/provider/IAIKPkcs11.properties");
        		pkcs11ProviderConfig.load(configStream);
        		iaikPkcs11Provider_ = new IAIKPkcs11(pkcs11ProviderConfig);
      		}
      		if (Security.getProvider(iaikPkcs11Provider_.getName()) == null) {
    	  		Security.addProvider(iaikPkcs11Provider_);
      		}
    	} catch (Throwable ex) {
    		ex.printStackTrace();
    	}
    	System.err.flush();
    
    	System.out.println("Registered providers are:");
    	Provider[] providers = Security.getProviders();
    	for (int i = 0; i < providers.length; i++) {
    		System.out.println("at position " + i + ": " + providers[i]);
    	}
    
    	System.out.println("...finished initializing");
    	System.out.flush();
	}

    //==========================================================================
    // Called when initialising the applet
    //==========================================================================
    
    
    public void init()
    {
        // Retrieve the text field size from the applet 
        // parameters
        int textRows = 10;
        int textColumns = 80;
        
        String rows = getParameter("rows");
        String cols = getParameter("cols");
        
        if (rows != null)
        {
            textRows = Integer.valueOf(rows).intValue();
        }
        if (cols != null)
        {
            textColumns = Integer.valueOf(cols).intValue();
        }
        
        // Instantiate the member components
        theDocument = new TextArea(textRows, textColumns);
        theDocument.setEditable(false);
        
        signButton = new Button("Sign");
        
        signButton.setEnabled(true);
        
        // Register events
        signButton.addActionListener(this);
        
        // Lay-out
        GridBagLayout gblApplet = new GridBagLayout();
        
        GridBagConstraints gbcApplet = new GridBagConstraints();
        gbcApplet.insets = new Insets(5, 5, 5, 5);
        gbcApplet.gridx = 0;
        gbcApplet.gridy = 0;
        gbcApplet.gridheight = 2;
        gbcApplet.gridwidth = 2;
        gblApplet.addLayoutComponent(theDocument, gbcApplet);
        
        gbcApplet.gridheight = 1;
        gbcApplet.gridwidth = 1;
        gbcApplet.gridx = 1;
        gbcApplet.gridy = 2;
        gbcApplet.anchor = GridBagConstraints.EAST;
        gblApplet.addLayoutComponent(signButton, gbcApplet);
        
        this.setLayout(gblApplet);
        
        this.add(theDocument);
        this.add(signButton);
        
        this.validate();
        
        // Read the data from the input form
        String inputFormName = getParameter("inForm");
        String inputFormField = getParameter("inField");
        
        if ((inputFormName == null) || (inputFormField == null))
        {
            throw new RuntimeException
            (
                "Invalid applet parameters - inForm and " +
                "inField are mandatory parameters"
            );
        }
        
        inputFormReader = new FormReader(
            this, inputFormName, inputFormField);
        
        theDocument.append(inputFormReader.toString());
//      Caution: The above call depends from the Default Platform Character Encoding
        
        // Retrieve the other applet parameters
        String issuerBase64 = getParameter("issuer");
        
        if ((issuerBase64 != null) && (issuerBase64.length() != 0))
        {
            try
            {
                Base64EncodedData issuerData = new Base64EncodedData(issuerBase64);
                
                certIssuer = issuerData.getData();
            }
            catch (RuntimeException re)
            {
            }
        }
        
        // Open the output form
        String outputFormName = getParameter("outForm");
        String outputFormField = getParameter("outField");
        
        if ((outputFormName == null) || (outputFormField == null))
        {
            throw new RuntimeException
            (
                "Invalid applet parameters - outForm and " +
                "outField are mandatory parameters"
            );
        }
        
        outputFormWriter = new FormWriter(
            this, outputFormName, outputFormField);
        initCryptoProviders();
        org.apache.xml.security.Init.init();
    }
    
    //==========================================================================
    // Called when starting the applet
    //==========================================================================
    
    public void start()
    {
    }
    
    //==========================================================================
    // Called when stopping the applet
    //==========================================================================
    
    public void stop()
    {
    }
    
    //==========================================================================
    // Called when destroying the applet
    //==========================================================================
    
    public void destroy()
    {
      System.out.print("preparing for unloading...");
      try 
      {
        Security.removeProvider(iaikPkcs11Provider_.getName());
      } catch (Throwable ex) 
      {
        ex.printStackTrace();
      }
      
            /*---------
    	    System.out.println("registered providers:");
    	    Provider[] providers = Security.getProviders();
    	    for (int i = 0; i < providers.length; i++) {
    	      System.out.println("at position " + i + ": " + providers[i]);
    	    }    	    
    	    System.out.println("finished");
    	    System.out.flush();
            -------- */        
    }
    
    //==========================================================================
    // Called when an action occurs
    //==========================================================================
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == signButton)
        {
            signTheDocument();
        }
    }


    public void getSignatureKeyPair() throws SignerException
    {
    	// with this call we just get an uninitialized PKCS#11 key store, it is not bound to a
    	// specific IAIKPkcs11 provider instance after this call, even if you specify the provider
    	// at this call. this is a limitation of SUN's KeyStore concept. the KeyStoreSPI object
    	// has no chance to get its own provider instance.
    	try
    	{
    		KeyStore  tokenKeyStore = KeyStore.getInstance("PKCS11KeyStore", iaikPkcs11Provider_.getName());
    		tokenKeyStore.load(null, null); // this call binds the keystore to the first instance of the IAIKPkcs11 provider
    		// if you want ot bind it to a different instance, you have to provide the provider name as stream
    		// see the other RSASigningDemo classes for examples

    		// we simply take the first keystore, if there are serveral
    		Enumeration aliases = tokenKeyStore.aliases();

    		// and we take the first signature (private) key for simplicity
    		while (aliases.hasMoreElements()) {
    			String keyAlias = aliases.nextElement().toString();
//     Caution: The above call depends from the Default Platform Character Encoding
    
    			Key key = tokenKeyStore.getKey(keyAlias, null);
    			if (key instanceof PrivateKey) {
    				Certificate[] certificateChain = tokenKeyStore.getCertificateChain(keyAlias);
    				signerCertificate = (X509Certificate) certificateChain[0];
    				boolean[] keyUsage = signerCertificate.getKeyUsage();
    				if ((keyUsage == null) || keyUsage[0] || keyUsage[1]) { // check for digital signature or non-repudiation, but also accept if none set
    					System.out.println("##########");
    					System.out.println("The signature key is: " + key );
    					System.out.println("##########");
    					// get the corresponding certificate for this signature key
    					System.out.println("##########");
    					System.out.println("The signer certificate is:");
    					System.out.println(signerCertificate.toString());
//     Caution: The above call depends from the Default Platform Character Encoding
    
    					System.out.println("##########");
    					signatureKey_ = (PrivateKey) key;
    					break;
    				}
    			}
    		}
    	}
    	catch (Exception np)
    	{
    		throw new SignerException(np.getMessage());
    	}
    	
    	if (signatureKey_ == null) {
            throw new SignerException
            (
            		"Found no signature key. Ensure that a valid card is inserted."
            );
    	}	
}
        
 
    //==========================================================================
    // Sign the document
    //==========================================================================
    
    private void signTheDocument()
    {
        try
        {
            // Ask the user if he really wants to sign the data
            if (!EMayorDocFunctions.showConfirmationDialog(this))
            {
                return;
            }
            
            try
            {
               	getSignatureKeyPair();
            }
            catch (SignerException se)
            {
                se.printStackTrace();
                
                EMayorDocFunctions.showNoCertificateFoundDialog(this);   
                return;
            }
            
            
            // Make sure that we use the correct JCE Provider
            JCEMapper.setProviderId(iaikPkcs11Provider_.getName()); 

            PrivateKey privateKey = signatureKey_;
            
            javax.xml.parsers.DocumentBuilderFactory dbf =
               javax.xml.parsers.DocumentBuilderFactory.newInstance();

            //XML Signature needs to be namespace aware
            dbf.setNamespaceAware(true);

            javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
           
            System.out.println("################## Is name space aware=" +db.isNamespaceAware());
            ByteArrayInputStream inF = new java.io.ByteArrayInputStream(theDocument.getText().getBytes("UTF-8"));
            org.w3c.dom.Document doc = db.parse(inF);
            
            Element root = doc.getDocumentElement();
            dbf.setNamespaceAware(true);

            //            String BaseURI = inF.toURL().toString("UTF-8");
            
            //Create an XML Signature object from the document, BaseURI and
            //signature algorithm (in this case DSA)
            XMLSignature sig = new XMLSignature(doc, null,
                                                XMLSignature.ALGO_ID_SIGNATURE_RSA);

           
            //Append the signature element to the root element before signing because
            //this is going to be an enveloped signature.
            //This means the signature is going to be enveloped by the document.
            //Two other possible forms are enveloping where the document is inside the
            //signature and detached where they are seperate.
            //Note that they can be mixed in 1 signature with seperate references as
            //shown below.
            root.appendChild(sig.getElement());
           // doc.appendChild(doc.createComment(" Comment after "));
       

            {
               //create the transforms object for the Document/Reference
               Transforms transforms = new Transforms(doc);

               //First we have to strip away the signature element (it's not part of the
               //signature calculations). The enveloped transform can be used for this.
               transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
               //Part of the signature element needs to be canonicalized. It is a kind
               //of normalizing algorithm for XML. For more information please take a
               //look at the W3C XML Digital Signature webpage.
               transforms.addTransform(Transforms.TRANSFORM_C14N_WITH_COMMENTS);
               //Add the above Document/Reference
               sig.addDocument("", transforms, Constants.ALGO_ID_DIGEST_SHA1);
            }

           

            {
               //Add in the KeyInfo for the certificate that we used the private key of
      /*
          	  X509Certificate cert =
                  (X509Certificate) ks.getCertificate(certificateAlias);
      */
          	 X509Certificate cert = signerCertificate;
               sig.addKeyInfo(cert);
               sig.addKeyInfo(cert.getPublicKey());
               System.out.println("Start signing");
               sig.sign(privateKey);
               System.out.println("Finished signing");
            }

            ByteArrayOutputStream f = new java.io.ByteArrayOutputStream();

            XMLUtils.outputDOMc14nWithComments(doc, f);
             
            // Transfer it to the output form field
            outputFormWriter.append(f.toString("UTF-8"));
            
            // Call submit, if necessary
            if (submitAfterSign)
            {
                outputFormWriter.submit();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            EMayorDocFunctions.showUnrecoverableError(this);
        }
        finally
        {
            // Destruct the signer
            signer = null;
            
            System.gc();
        }
    }
    
    public String hexToString(byte[] buf)
	{
		String rv = "";
		
		if (buf == null)
		{
			return rv;
		}
		
		for (int i = 0; i < buf.length; i++)
		{
			int val = buf[i];
			
			if (val < 0)
			{
				 val += 256;
			}
			
			if (val < 0x10)
			{
				rv += "0" + Integer.toString(val, 16);
			}
			else
			{
				rv += Integer.toString(val, 16);
			}
		}
		
		return rv;
	}
}
