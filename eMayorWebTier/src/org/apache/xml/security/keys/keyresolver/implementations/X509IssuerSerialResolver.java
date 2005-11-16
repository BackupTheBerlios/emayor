
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
package org.apache.xml.security.keys.keyresolver.implementations;



import java.security.PublicKey;
import java.security.cert.X509Certificate;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.content.X509Data;
import org.apache.xml.security.keys.content.x509.XMLX509IssuerSerial;
import org.apache.xml.security.keys.keyresolver.KeyResolverException;
import org.apache.xml.security.keys.keyresolver.KeyResolverSpi;
import org.apache.xml.security.keys.storage.StorageResolver;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.utils.Constants;
import org.w3c.dom.Element;


/**
 *
 * @author $Author: emayor $
 */
public class X509IssuerSerialResolver extends KeyResolverSpi {


    /** @inheritDoc */
   public boolean engineCanResolve(Element element, String BaseURI,
                                   StorageResolver storage) {
      if (false)
      	System.out.println("Can I resolve " + element.getTagName() + "?");

      X509Data x509data = null;
      try {
         x509data = new X509Data(element, BaseURI);
      } catch (XMLSignatureException ex) {
         System.out.println("I can't");

         return false;
      } catch (XMLSecurityException ex) {
         System.out.println("I can't");

         return false;
      }

      if (x509data == null) {
         System.out.println("I can't");
         return false;
      }

      if (x509data.containsIssuerSerial()) {
            return true;
      }

      System.out.println("I can't");
      return false;
   }

   /** @inheritDoc */
   public PublicKey engineResolvePublicKey(
           Element element, String BaseURI, StorageResolver storage)
              throws KeyResolverException {

      X509Certificate cert = this.engineResolveX509Certificate(element,
                                BaseURI, storage);

      if (cert != null) {
         return cert.getPublicKey();
      }

      return null;
   }

   /** @inheritDoc */
   public X509Certificate engineResolveX509Certificate(
           Element element, String BaseURI, StorageResolver storage)
              throws KeyResolverException {

      try {
         if (storage == null) {
            Object exArgs[] = { Constants._TAG_X509ISSUERSERIAL };
            KeyResolverException ex =
               new KeyResolverException("KeyResolver.needStorageResolver",
                                        exArgs);

            System.out.println(""+ ex);
            throw ex;
         }

         X509Data x509data = new X509Data(element, BaseURI);
         int noOfISS = x509data.lengthIssuerSerial();

         while (storage.hasNext()) {
            X509Certificate cert = storage.next();
            XMLX509IssuerSerial certSerial = new XMLX509IssuerSerial(element.getOwnerDocument(), cert);

            if (false) {
            	System.out.println("Found Certificate Issuer: "
                      + certSerial.getIssuerName());
            	System.out.println("Found Certificate Serial: "
                      + certSerial.getSerialNumber().toString());
            }

            for (int i=0; i<noOfISS; i++) {
               XMLX509IssuerSerial xmliss = x509data.itemIssuerSerial(i);

               if (false) {
               	    System.out.println("Found Element Issuer:     "
                         + xmliss.getIssuerName());
               	    System.out.println("Found Element Serial:     "
                         + xmliss.getSerialNumber().toString());
               }

               if (certSerial.equals(xmliss)) {
                  System.out.println("match !!! ");

                  return cert;
               } 
                System.out.println("no match...");               
            }
         }

         return null;
      } catch (XMLSecurityException ex) {
         System.out.println("XMLSecurityException"+ ex);

         throw new KeyResolverException("generic.EmptyMessage", ex);
      }
   }

   /** @inheritDoc */
   public javax.crypto.SecretKey engineResolveSecretKey(
           Element element, String BaseURI, StorageResolver storage) {
      return null;
   }
}
