//==============================================================================
// Copyright (C) 2005 Advanced Encryption Technology Europe B.V.
// Copyright (C) 2005 eMayor Consortium (http://www.emayor.org)
// All rights are reserved. Reproduction in whole or in part is prohibited
// without the written consent of the copyright owner.
//
// Package: org.emayor.xmlsigner
// Class:   SignerFactory
// Version: $Id: SignerFactory.java,v 1.1 2005/11/16 10:51:00 emayor Exp $
// Product: Java XML signing applet
//
// Description:
//		Signer factory base class
//==============================================================================

package org.emayor.client.controlers.xmlsigner;

public abstract class SignerFactory
{
    //==========================================================================
    // Constants
    //==========================================================================
    
    public static final int SEARCH_ISSUER					= 1;
    
    public static final int SEARCH_SUBJECT					= 2;
    
    public static final int SEARCH_ID						= 4;
    
    //==========================================================================
    // Methods
    //==========================================================================
    
    // Return the first available signer
    public static Signer getInstance()
    {
        return null;
    }
    
    // Return the first available signer based on the search criterium
    public static Signer getInstance
    (
        byte[] searchCriterium, 
        int criterium
    )
    {
        return null;
    }
}
