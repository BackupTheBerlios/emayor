//==============================================================================
// Copyright (C) 2005 Advanced Encryption Technology Europe B.V.
// Copyright (C) 2005 eMayor Consortium (http://www.emayor.org)
// All rights are reserved. Reproduction in whole or in part is prohibited
// without the written consent of the copyright owner.
//
// Package: org.emayor.xmlsigner
// Class:   SignerException
// Version: $Id: SignerException.java,v 1.1 2005/11/16 10:51:00 emayor Exp $
// Product: Java XML signing applet
//
// Description:
//		Signer exception class
//==============================================================================

package org.emayor.client.controlers.xmlsigner;

public class SignerException extends RuntimeException
{
    //==========================================================================
    // Constructor
    //==========================================================================
    
    public SignerException(String reason)
    {
        super(reason);
    }
}
