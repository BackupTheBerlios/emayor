
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
package org.apache.xml.security.keys.keyresolver;



import org.apache.xml.security.exceptions.XMLSecurityException;


/**
 *
 *
 *
 *
 * @author $Author: emayor $
 *
 */
public class KeyResolverException extends XMLSecurityException {

   /**
    * Constructor KeyResolverException
    *
    */
   public KeyResolverException() {
      super();
   }

   /**
    * Constructor KeyResolverException
    *
    * @param _msgID
    */
   public KeyResolverException(String _msgID) {
      super(_msgID);
   }

   /**
    * Constructor KeyResolverException
    *
    * @param _msgID
    * @param exArgs
    */
   public KeyResolverException(String _msgID, Object exArgs[]) {
      super(_msgID, exArgs);
   }

   /**
    * Constructor KeyResolverException
    *
    * @param _msgID
    * @param _originalException
    */
   public KeyResolverException(String _msgID, Exception _originalException) {
      super(_msgID, _originalException);
   }

   /**
    * Constructor KeyResolverException
    *
    * @param _msgID
    * @param exArgs
    * @param _originalException
    */
   public KeyResolverException(String _msgID, Object exArgs[],
                               Exception _originalException) {
      super(_msgID, exArgs, _originalException);
   }
}
