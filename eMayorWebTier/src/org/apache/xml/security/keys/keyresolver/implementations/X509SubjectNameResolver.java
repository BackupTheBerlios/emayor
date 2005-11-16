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
import org.apache.xml.security.keys.content.x509.XMLX509SubjectName;
import org.apache.xml.security.keys.keyresolver.KeyResolverException;
import org.apache.xml.security.keys.keyresolver.KeyResolverSpi;
import org.apache.xml.security.keys.storage.StorageResolver;
import org.apache.xml.security.utils.Constants;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Element;


/**
 *
 * @author $Author: emayor $
 */
public class X509SubjectNameResolver extends KeyResolverSpi {


   /** Field _x509childNodes */
   private Element[] _x509childNodes = null;

   /** Field _x509childObject[] */
   private XMLX509SubjectName _x509childObject[] = null;

   /**
    * Method engineCanResolve
    * @inheritDoc
    * @param element
    * @param BaseURI
    * @param storage
    *
    */
   public boolean engineCanResolve(Element element, String BaseURI,
                                   StorageResolver storage) {
      if (false)
      	System.out.println("Can I resolve " + element.getTagName() + "?");

      
       if (!XMLUtils.elementIsInSignatureSpace(element,
                 Constants._TAG_X509DATA) ) {      
         System.out.println("I can't");

         return false;
      }


         
         this._x509childNodes = XMLUtils.selectDsNodes(element,
                 Constants._TAG_X509SUBJECTNAME);

         if ((this._x509childNodes != null)
                 && (this._x509childNodes.length > 0)) {
            System.out.println("Yes Sir, I can");

            return true;
         }
     

      System.out.println("I can't");

      return false;
   }

   /**
    * Method engineResolvePublicKey
    *
    * @param element
    * @param BaseURI
    * @param storage
    * @return null if no {@link PublicKey} could be obtained
    * @throws KeyResolverException
    */
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

   /**
    * Method engineResolveX509Certificate
    * @inheritDoc
    * @param element
    * @param BaseURI
    * @param storage
    *
    * @throws KeyResolverException
    */
   public X509Certificate engineResolveX509Certificate(
           Element element, String BaseURI, StorageResolver storage)
              throws KeyResolverException {

      try {
         if (this._x509childNodes == null) {
            boolean weCanResolve = this.engineCanResolve(element, BaseURI,
                                      storage);

            if (!weCanResolve || (this._x509childNodes == null)) {
               return null;
            }
         }

         if (storage == null) {
            Object exArgs[] = { Constants._TAG_X509SUBJECTNAME };
            KeyResolverException ex =
               new KeyResolverException("KeyResolver.needStorageResolver",
                                        exArgs);

            System.out.println(""+ ex);

            throw ex;
         }

         this._x509childObject =
            new XMLX509SubjectName[this._x509childNodes.length];

         for (int i = 0; i < this._x509childNodes.length; i++) {
            this._x509childObject[i] =
               new XMLX509SubjectName(this._x509childNodes[i],
                                      BaseURI);
         }

         while (storage.hasNext()) {
            X509Certificate cert = storage.next();
            XMLX509SubjectName certSN =
               new XMLX509SubjectName(element.getOwnerDocument(), cert);

            System.out.println("Found Certificate SN: " + certSN.getSubjectName());

            for (int i = 0; i < this._x509childObject.length; i++) {
               System.out.println("Found Element SN:     "
                         + this._x509childObject[i].getSubjectName());

               if (certSN.equals(this._x509childObject[i])) {
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

   /**
    * Method engineResolveSecretKey
    * @inheritDoc
    * @param element
    * @param BaseURI
    * @param storage
    *
    */
   public javax.crypto.SecretKey engineResolveSecretKey(
           Element element, String BaseURI, StorageResolver storage)
   {
      return null;
   }
}
