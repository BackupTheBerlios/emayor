package org.emayor.RepresentationLayer.ClientTier.Logic.XMLSignature;

/*
 * Copyright  1999-2004 The Apache Software Foundation.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

import java.io.StringReader;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

import org.apache.xml.security.keys.KeyInfo;



//import org.apache.xml.security.samples.utils.resolver.OfflineResolver;
import org.apache.xml.security.signature.XMLSignature;

import org.apache.xml.security.utils.Constants;
import org.apache.xml.security.utils.XMLUtils;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.eMayor.PolicyEnforcement.C_UserProfile;
import org.eMayor.PolicyEnforcement.E_PolicyEnforcementException;
import org.emayor.servicehandling.config.Config;



public class VerifySignature
{


   public boolean Verify(String SignedDocument, String sUserProfile) throws E_PolicyEnforcementException 
   {
      System.out.println("PE::VerifySignature:: Startig process ...");
      C_UserProfile myUserProfile = null;
      boolean bCheckUserProfile = false;
      if (sUserProfile!=null) 
      {
        try 
        {
          myUserProfile=new C_UserProfile(sUserProfile);
          String sUserRole = myUserProfile.getUserRole();
          System.out.println("PE::VerifySignature:: User Profile Role is " + sUserRole);
          if (!(sUserRole.equalsIgnoreCase("Server")))
          {
            System.out.println("PE::VerifySignature:: Activate UserProfile check ...");
            bCheckUserProfile = true;
          }
          else 
          {
            System.out.println("PE::VerifySignature:: UserProfile check deactivated");
          }
        } catch (Exception e) 
        {
          System.out.println("PE::VerifySignature:: Error createing user profile: " + e.toString());      
        }
      } else 
      {
        System.out.println("PE::VerifySignature:: User Profile is null, simple verification will be done");
      }
      
      System.out.println("PE::VerifySignature:: Got XML Document \n" + SignedDocument);
      if (SignedDocument==null) throw new E_PolicyEnforcementException("PE::VerifySignature:: Ivalid String"); 
      javax.xml.parsers.DocumentBuilderFactory dbf =
      javax.xml.parsers.DocumentBuilderFactory.newInstance();

      dbf.setNamespaceAware(true);
     
      boolean result = true;
      try 
      {
        javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
        db.setErrorHandler(new org.apache.xml.security.utils.IgnoreAllErrorHandler());
        StringReader myStrReader = new StringReader(SignedDocument);
        //if (log.isDebugEnabled()) log.debug("F_StringtoDocument(String)::got the String Reader...");
    
        InputSource myInputSource = new InputSource(myStrReader);      
        org.w3c.dom.Document doc = db.parse(myInputSource);     
        System.out.println("PE::VerifySignature:: Document parsed .. looking for ds:Signature elements ..");
        Element nscontext = XMLUtils.createDSctx(doc, "ds", Constants.SignatureSpecNS);
        NodeList myNodeList = XPathAPI.selectNodeList(doc,"//ds:Signature", nscontext);
        int Nodes = myNodeList.getLength();
        System.out.println("PE::VerifySignature:: Found " + Nodes + " Signatures");
        if (Nodes<0) 
        {
          Config config = Config.getInstance();
          if (config.getPropertyAsBoolean(Config.EMAYOR_PE_CHECK_SIGNATURE)) 
          {
            throw new E_PolicyEnforcementException(
            "PolicyEnforcement::VerifySignature:: Error : The document contains no Signature!");
          } 
          else return true;
        }
        for (int i=0;i<Nodes;i++) 
        {
          // Remove not needed Signatures     
          org.w3c.dom.Document docTemp = (org.w3c.dom.Document)doc.cloneNode(true); 
          if (Nodes>1)
          {
            for (int j=Nodes-1; j>=(i+1); j--) 
            {
              System.out.println("PE::VerifySignature:: Removing" + (j+1) + "Signature");
              NodeList TempNodeList= docTemp.getElementsByTagName("ds:Signature");
              System.out.println("PE::VerifySignature:: Got the SignatureNodeList with " +TempNodeList.getLength()+" Items" );             
              Node TempNode = TempNodeList.item(j);
              System.out.println("PE::VerifySignature:: Got the SignatureNode " +(j+1));
              if (TempNode==null) System.out.println("PE::VerifySignature:: the SignatureNode " +(j+1)+" is null error");
              Node MyTempRoot = TempNode.getParentNode();
              System.out.println("PE::VerifySignature:: Got the SignatureNode Root ");
              if (TempNode==null) System.out.println("PE::VerifySignature:: the SignatureNode Root is null: error!!!!");
             
              MyTempRoot.removeChild(TempNode);
              System.out.println("PE::VerifySignature:: Removed the SignatureNode " +(j+1));             
            }
          }         
          Element nscontextTemp = XMLUtils.createDSctx(docTemp, "ds",Constants.SignatureSpecNS);
          NodeList myNodeListTemp = XPathAPI.selectNodeList(docTemp,"//ds:Signature", nscontextTemp);
          Element sigElement = (Element)myNodeListTemp.item(i);
          System.out.println("PE::VerifySignature:: Get Signature number " + (i+1));
        
          XMLSignature signature = new XMLSignature(sigElement,"");                 
          KeyInfo ki = signature.getKeyInfo();
          if (ki != null) 
          {
            if (ki.containsX509Data()) 
            {
              System.out.println("PE::VerifySignature::Could find a X509Data element in the KeyInfo");
            }

            X509Certificate cert = signature.getKeyInfo().getX509Certificate();

            if (cert != null) 
            {
              System.out.println("PE::VerifySignature::Try to verify the signature using the X509 Certificate: " + cert);
              boolean SigResult = signature.checkSignatureValue(cert);
              System.out.println("PE::VerifySignature::Result for Signature Nr."+(i+1)+" is: " +(SigResult ? "valid (good)"
                            : "invalid !!!!! (bad)"));
              if ((bCheckUserProfile) && (SigResult) && (i==(Nodes-1))) 
              {
                System.out.println("PE: VerifySignature::Valid Signature, validate the Signer begin...");
            
                X509Certificate certProf = (myUserProfile.getX509_CertChain())[0];
                if (cert.equals(certProf)) 
                {
                  // Certs are The same
                  System.out.println("PE: VerifySignature::Valid Signature, validate the Signer Reult is: The Signture was made using the Login Certificate");
                } else 
                {
                  System.out.println("PE: VerifySignature::Error, The document was signed not by the autheticated person!!!");
                  result = false;
                }
                System.out.println("PE: VerifySignature::End validate the Signer");           
              }
              result = result && SigResult;
            } else 
            {
              System.out.println("PE::VerifySignature::Did not find a Certificate, try to get the key info...");

              PublicKey pk = signature.getKeyInfo().getPublicKey();                             
              if (pk != null) 
              {
                System.out.println("PE::VerifySignature::Try to verify the signature using the public key: " + pk);
                boolean ResultSignVer = signature.checkSignatureValue(pk);
                System.out.println("PE::VerifySignature::The XML signature is "
                                     + (ResultSignVer
                                        ? "valid (good)"
                                        : "invalid !!!!! (bad)"));
                result = result && ResultSignVer;
              } else 
              {
                System.out.println("PE::VerifySignature::Did not find a public key, so I can't check the signature");
                result = false;
              }
            }
          } else 
          {
            System.out.println("PE::VerifySignature::Did not find a KeyInfo");
            result = false;
          }
        } // for
      } catch (Exception ex) 
      {
        System.out.println("PE::VerifySignature::Exception " + ex.toString());
        throw new E_PolicyEnforcementException("PolicyEnforcement::VerifySignature:: Exception \n"+ ex.toString());
      }
      System.out.println("PE::VerifySignature::end processing with result " + (result? "Signature Valid": "Signature Invalid"));  
      return result;
   }

   
   // Static initializer
   static 
   {   
     org.apache.xml.security.Init.init();
   }

   
}


