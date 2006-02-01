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
package org.apache.xml.security.utils;

import java.io.ByteArrayOutputStream;

import org.apache.xml.security.algorithms.SignatureAlgorithm;
import org.apache.xml.security.signature.XMLSignatureException;

/**
 * @author raul
 *
 */
public class SignerOutputStream extends ByteArrayOutputStream {
    final static byte none[]="error".getBytes(); // any encoding ok for "error"
    final SignatureAlgorithm sa;
    /**
     * @param sa
     */
    public SignerOutputStream(SignatureAlgorithm sa) {
        this.sa=sa;       
    }

    /** @inheritDoc */
    public byte[] toByteArray() {
        // TODO Auto-generated method stub
        return none;
    }
    
    /** @inheritDoc */
    public void write(byte[] arg0)  {
        try {
			sa.update(arg0);
		} catch (XMLSignatureException e) {
            throw new RuntimeException(""+e);
		}
    }
    
    /** @inheritDoc */
    public void write(int arg0) {
        try {
            sa.update((byte)arg0);
        } catch (XMLSignatureException e) {
            throw new RuntimeException(""+e);
        }
    }
    
    /** @inheritDoc */
    public void write(byte[] arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        try {
            sa.update(arg0,arg1,arg2);
        } catch (XMLSignatureException e) {
            throw new RuntimeException(""+e);
        }
    }
    

}
