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
package org.apache.xml.security.utils.resolver.implementations;



import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.utils.CachedXPathAPIHolder;
import org.apache.xml.security.utils.IdResolver;
import org.apache.xml.security.utils.resolver.ResourceResolverException;
import org.apache.xml.security.utils.resolver.ResourceResolverSpi;
import org.apache.xml.utils.URI;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


/**
 * This resolver is used for resolving same-document URIs like URI="" of URI="#id".
 *
 * @author $Author: emayor $
 * @see <A HREF="http://www.w3.org/TR/xmldsig-core/#sec-ReferenceProcessingModel">The Reference processing model in the XML Signature spec</A>
 * @see <A HREF="http://www.w3.org/TR/xmldsig-core/#sec-Same-Document">Same-Document URI-References in the XML Signature spec</A>
 * @see <A HREF="http://www.ietf.org/rfc/rfc2396.txt">Section 4.2 of RFC 2396</A>
 */
public class ResolverFragment extends ResourceResolverSpi {


   /**
    * Method engineResolve
    *
    * Wird das gleiche Dokument referenziert?
    * Wird ein anderes Dokument referenziert?
    * @inheritDoc
    * @param uri
    * @param BaseURI
    *
    */
   public XMLSignatureInput engineResolve(Attr uri, String BaseURI) 
       throws ResourceResolverException
   {

      String uriNodeValue = uri.getNodeValue();
      Document doc = uri.getOwnerElement().getOwnerDocument();


      Node selectedElem = null;
      if (uriNodeValue.equals("")) {

         /*
          * Identifies the node-set (minus any comment nodes) of the XML
          * resource containing the signature
          */

         System.out.println("ResolverFragment with empty URI (means complete document)");
	 selectedElem = doc;
      } else {

         /*
          * URI="#chapter1"
          * Identifies a node-set containing the element with ID attribute
          * value 'chapter1' of the XML resource containing the signature.
          * XML Signature (and its applications) modify this node-set to
          * include the element plus all descendents including namespaces and
          * attributes -- but not comments.
          */
         String id = uriNodeValue.substring(1);

         // Element selectedElem = doc.getElementById(id);
         selectedElem = IdResolver.getElementById(doc, id);
         if (selectedElem==null) {
         	Object exArgs[] = { id };
            throw new ResourceResolverException(
               "signature.Verification.MissingID", exArgs, uri, BaseURI);
         }
         if (false)
         	System.out.println("Try to catch an Element with ID " + id + " and Element was " + selectedElem);
      }

      //Set resultSet = dereferenceSameDocumentURI(selectedElem);
      CachedXPathAPIHolder.setDoc(doc);
      XMLSignatureInput result = new XMLSignatureInput(selectedElem);
      result.setExcludeComments(true);

      //System.out.println("We return a nodeset with " + resultSet.size() + " nodes");
      result.setMIMEType("text/xml");

      try {
         URI uriNew = new URI(new URI(BaseURI), uri.getNodeValue());

         result.setSourceURI(uriNew.toString());
      } catch (URI.MalformedURIException ex) {
         result.setSourceURI(BaseURI);
      }

      return result;
   }

   /**
    * Method engineCanResolve
    * @inheritDoc
    * @param uri
    * @param BaseURI
    *
    */
   public boolean engineCanResolve(Attr uri, String BaseURI) {

      if (uri == null) {
         System.out.println("Quick fail for null uri");
         return false;
      }

      String uriNodeValue = uri.getNodeValue();

      if (uriNodeValue.equals("")
              || (uriNodeValue.startsWith("#")
                  &&!uriNodeValue.startsWith("#xpointer("))) {
         if (false)
         	System.out.println("State I can resolve reference: \"" + uriNodeValue + "\"");
         return true;
      }
      if (false)
      	System.out.println("Do not seem to be able to resolve reference: \"" + uriNodeValue + "\"");
      return false;
   }

}
