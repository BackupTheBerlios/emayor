package org.emayor.RepresentationLayer.ClientTier.Controlers.xmlsigner;

// Copyright (C) 2005 Advanced Encryption Technology Europe B.V.
// Copyright (C) 2005 eMayor Consortium (http://www.emayor.org)
// All rights are reserved. Reproduction in whole or in part is prohibited
// without the written consent of the copyright owner.
//
//
// Description:  Short version of the XMLSigner for the eMayor project
//               The only public method is signTheDocument( xmldocument)
//               which returns the signed xml document.
//
//               Created  J.Plaz, UoZurich, July-13-2005
//

import iaik.pkcs.pkcs11.provider.IAIKPkcs11;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.Constants;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import java.awt.Frame;
import java.awt.EventQueue;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.emayor.RepresentationLayer.ClientTier.EMayorFormsClientApplet;
import org.emayor.RepresentationLayer.ClientTier.LanguageProperties;
;


public class XMLSigner_eMayorVersion 
{

  private static Signer signer = null;
  // The signer must be initialized only once.
  // The PKS package will throw an exception, if
  // signer = PKCS11Signer.getInstance() is called a second time.
  // Therefore this is kept *static* here.
	
  // The client applet is used as parent for showing optional dialogs
  // f.ex. when multiple digital identities are found, the
  // user can select which one to use for signing.
  private EMayorFormsClientApplet clientApplet;
  
  
  /**
   *   The PKCS#11 JCE provider.
   */
  private IAIKPkcs11 iaikPkcs11Provider_= null;

  private PrivateKey signatureKey_= null;
  private X509Certificate signerCertificate= null;



  
  public XMLSigner_eMayorVersion( final EMayorFormsClientApplet _clientApplet )
  {  	
    this.initCryptoProviders();
    org.apache.xml.security.Init.init();
    this.clientApplet = _clientApplet;
  } // constructor
	


  
 /**
  *  Signs the document.
  *  Called OUTSIDE the EDT for having Swing unblocked.
  *  Important, when in getSignatureKeyPair() a dialog is shown. 
  */ 
  public String signTheDocument( final String xmlDocument ) throws Exception
  {
    String signedXMLDocument = null; // the return value
    
    System.out.println("=============signtheDocument==========");
	System.out.println(xmlDocument);

    boolean doContinue = this.getSignatureKeyPair(); // Signer exceptions are handled by the caller
    if( doContinue )
    {
      try
      {                        
        // Make sure that we use the correct JCE Provider
        JCEMapper.setProviderId(iaikPkcs11Provider_.getName()); 
        PrivateKey privateKey = signatureKey_;        
        javax.xml.parsers.DocumentBuilderFactory dbf =
           javax.xml.parsers.DocumentBuilderFactory.newInstance();

        // XML Signature needs to be namespace aware
        dbf.setNamespaceAware(true);

        javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
       
        System.out.println("################## Is name space aware=" +db.isNamespaceAware());
        StringReader myStrReader = new StringReader(xmlDocument);		
		InputSource myInputSource = new InputSource(myStrReader);
  		
  		org.w3c.dom.Document doc = db.parse(myInputSource);
                
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

       // ByteArrayOutputStream f = new java.io.ByteArrayOutputStream();

        //XMLUtils.outputDOMc14nWithComments(doc, f);
                 
        //signedXMLDocument = f.toString("UTF-8");
        
        TransformerFactory myFactory = TransformerFactory.newInstance();
		
		Transformer myTransformer = myFactory.newTransformer();
		
		DOMSource mySource =new DOMSource(doc);
		StringWriter mySW = new StringWriter();
		StreamResult myResult = new StreamResult(mySW);
		myTransformer.transform(mySource, myResult);
		signedXMLDocument = mySW.toString();
		
		System.out.println("===========Signed Document==========");
		System.out.println(signedXMLDocument);
        
      }
      catch (Exception e)
      {
        throw e; // handled by the caller, but catched because of finalizer below
      }
      finally
      {
        // Disconnect the signer, leave it to the GC at a later time:
        signer = null;        
        System.gc(); // maybe, maybe not :)
      }
      
      // unload all: [usually after sign and submit, the signing functionality won't
      //              been required again for some time, and the applet is unloaded
      //              anyway]
      this.unloadCryptoProviders();
    } // if doContinue
    else
    {
      // In agreement with the caller:
      // Returning an empty String shall mean, that the signing operation
      // has been cancelled by the user. So the caller in this case
      // just should do nothing.
      signedXMLDocument = "";
    }
    return signedXMLDocument;  
  } // signTheDocument

  

  
  
  
 /**
  *  Reads signature keypairs from the smartcard.
  *  Called outside the EDT for having Swing unblocked.
  */ 
  public boolean getSignatureKeyPair() throws SignerException
  {
    boolean doContinue = true; // In this method, a selection dialog is called,
                               // which can be cancelled, in which case we
                               // would return doContinue = false
                               // means: No error, but just don't continue.
  
    // with this call we just get an uninitialized PKCS#11 key store, it is not bound to a
    // specific IAIKPkcs11 provider instance after this call, even if you specify the provider
    // at this call. this is a limitation of SUN's KeyStore concept. the KeyStoreSPI object
    // has no chance to get its own provider instance.
    try
    {
      KeyStore  tokenKeyStore = KeyStore.getInstance("PKCS11KeyStore", iaikPkcs11Provider_.getName());
      tokenKeyStore.load(null, null); 
      // this call binds the keystore to the first instance of the IAIKPkcs11 provider
      // if you want ot bind it to a different instance, you have to provide the provider name as stream
      // see the other RSASigningDemo classes for examples

      // we simply take the first keystore, if there are serveral
      Enumeration aliases = tokenKeyStore.aliases();

      // Read them into an array:
      Vector keyAliasWithPrivateKeyVector = new Vector();
      while (aliases.hasMoreElements()) 
      {
        String keyAlias = aliases.nextElement().toString();
        // Caution: The above call depends from the Default Platform Character Encoding
        Key key = tokenKeyStore.getKey(keyAlias, null);
        if (key instanceof PrivateKey) 
        {
          keyAliasWithPrivateKeyVector.addElement( keyAlias );
        }
      }      
      
      System.out.println("-------------  ");
      System.out.println("-------------  Number of aliases= " + keyAliasWithPrivateKeyVector.size() );
      for( int i=0; i < keyAliasWithPrivateKeyVector.size(); i++ )
      {
        String keyAlias = (String)keyAliasWithPrivateKeyVector.elementAt(i);
        System.out.println("---alias= " + keyAlias);
      }
      System.out.println("-------------  ");
      
      
      int indexOfAlias = 0;
      // If there are more than one alias, let the user choose in a dialog
      // which one to take for the signature:
      if( keyAliasWithPrivateKeyVector.size() > 1 )
      {
        // Make a String array:
        String[] keyAliasArray = new String[keyAliasWithPrivateKeyVector.size()];
        keyAliasWithPrivateKeyVector.copyInto(keyAliasArray);
        // The technic, how you can make a **MODAL** dialog from a JApplet
        // goes like this:
        Frame appletParentFrame = JOptionPane.getFrameForComponent(this.clientApplet);
        // Pass the dialog a small icon for its buttons:
        ImageIcon selectionIcon = this.clientApplet.loadImageIcon("/pictures/applet/selection.gif");
        ImageIcon cancelIcon    = this.clientApplet.loadImageIcon("/pictures/applet/failure.gif");
        // and the properties object containing texts in the currently
        // used client language
        LanguageProperties languageProperties = this.clientApplet.getLanguageProperties();
        // Create it:
        final DigitalIDSelectionDialog selectionDialog = 
              new DigitalIDSelectionDialog(appletParentFrame,languageProperties,selectionIcon,cancelIcon,keyAliasArray);
        // selectionDialog creates itself as modal in its constructor.
        // We are outside the EDT, so switch, according to Swing rules:
        try
        {
          EventQueue.invokeAndWait( new Runnable()
          {
            public void run()
            {
              selectionDialog.setVisible(true);            
            }
          });
        }
        catch( Exception eee )
        {
          eee.printStackTrace();
        }        
        // Now we come here ONLY after the selectionDialog has been closed.
        // Help the poor Swing EDT a bit by spending it some time for
        // cleaning up the ex dialog space:
        try
        {
          Thread.sleep(111);
        }
        catch( Exception e )
        {
          // nevermind       
        }
        System.out.println("XMLSigner_eMayorVersion: selectionDialog has been closed.");
        if( selectionDialog.getWasOkButtonPressed() )
        {
          indexOfAlias = selectionDialog.getSelectedIndex();
        }
        else
        {
           doContinue = false;
        }
      } // if
            
      if( ( doContinue ) &&
          ( indexOfAlias < keyAliasWithPrivateKeyVector.size() ) )
      {      
        String keyAlias = (String)keyAliasWithPrivateKeyVector.elementAt(indexOfAlias);
        // Caution: The above call depends from the Default Platform Character Encoding         
        Key key = tokenKeyStore.getKey(keyAlias, null);
        if (key instanceof PrivateKey) // true, because tested above already 
        {
          Certificate[] certificateChain = tokenKeyStore.getCertificateChain(keyAlias);
          signerCertificate = (X509Certificate) certificateChain[0];
          
          System.out.println("-- Number of certificates= " + certificateChain.length );
          
          
          boolean[] keyUsage = signerCertificate.getKeyUsage();
          if ((keyUsage == null) || keyUsage[0] || keyUsage[1]) 
          { // check for digital signature or non-repudiation, but also accept if none set
            System.out.println("##########");
            System.out.println("The signature key is: " + key );
            System.out.println("##########");
            // get the corresponding certificate for this signature key
            System.out.println("##########");
            System.out.println("The signer certificate is:");
            System.out.println(signerCertificate.toString());
//          Caution: The above call depends from the Default Platform Character Encoding
            
            System.out.println("##########");
            signatureKey_ = (PrivateKey) key;
          }
        }            
      } // if     
    }
    catch (Exception np)
    {
      np.printStackTrace();
      throw new SignerException("Unable to sign the document. Reason is " + np.getMessage());
    }
    if( doContinue ) // Check this only, if the action has not been cancelled by the user already
    {
      if (signatureKey_ == null) 
      {
        throw new SignerException
          (
            "Found no signature key. Ensure that a valid card is inserted."
          );
      }
    }  
    return doContinue;
  }

  
  
  
  
  
  public void initCryptoProviders()
  {
    System.out.println("initializing: initCryptoProviders()");
    try 
    {
      if ((iaikPkcs11Provider_ = IAIKPkcs11.getProviderInstance(1)) == null) 
      {
        Properties pkcs11ProviderConfig = new Properties();
        // Changed J.Plaz: added the /default folder in the resource address below
        //InputStream configStream = this.getClass().getClassLoader().getResourceAsStream("iaik/pkcs/pkcs11/provider/default/IAIKPkcs11.properties");
        InputStream configStream = this.getClass().getClassLoader().getResourceAsStream("org/emayor/RepresentationLayer/ClientTier/Controlers/xmlsigner/IAIKPkcs11.properties");
        pkcs11ProviderConfig.load(configStream);
        iaikPkcs11Provider_ = new IAIKPkcs11(pkcs11ProviderConfig);
      }
      if (Security.getProvider(iaikPkcs11Provider_.getName()) == null) 
      {
        Security.addProvider(iaikPkcs11Provider_);
      }
    } 
    catch (Throwable ex) 
    {
      ex.printStackTrace();
    }
    System.err.flush();
   
    System.out.println("Registered providers are:");
    Provider[] providers = Security.getProviders();
    for (int i = 0; i < providers.length; i++) 
    {
      System.out.println("at position " + i + ": " + providers[i]);
    }  
    System.out.println("...finished initializing");
    System.out.flush();
  }
  

  

  
  
  public void unloadCryptoProviders()
  {
     System.out.print("preparing for unloading...");
      try {
        Security.removeProvider(iaikPkcs11Provider_.getName());
      } catch (Throwable ex) {
        ex.printStackTrace();
      }
      /*----
      System.out.println("registered providers:");
      Provider[] providers = Security.getProviders();
      for (int i = 0; i < providers.length; i++) {
        System.out.println("at position " + i + ": " + providers[i]);
      }      
      System.out.println("finished");
      System.out.flush();
      ---- */
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

  
  
  
  
  
} // XMLSigner_eMayorVersion



