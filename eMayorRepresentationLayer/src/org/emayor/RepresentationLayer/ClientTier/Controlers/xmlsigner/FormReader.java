//==============================================================================
// Copyright (C) 2005 Advanced Encryption Technology Europe B.V.
// Copyright (C) 2005 eMayor Consortium (http://www.emayor.org)
// All rights are reserved. Reproduction in whole or in part is prohibited
// without the written consent of the copyright owner.
//
// Package: org.emayor.xmlsigner
// Class:   FormReader
// Version: $Id: FormReader.java,v 1.1 2006/02/01 15:32:58 emayor Exp $
// Product: Java XML signing applet
//
// Description:
//		Web page form reader class
//==============================================================================

package org.emayor.RepresentationLayer.ClientTier.Controlers.xmlsigner;

import netscape.javascript.*;
import java.applet.*;

public class FormReader
{
    //==========================================================================
    // FormReader exception class
    //==========================================================================
    
    public class FormReaderException extends RuntimeException
    {
        //======================================================================
        // Constructor
        //======================================================================
        
        public FormReaderException(String reason)
        {
            super(reason);
        }
    }
    
    //==========================================================================
    // Member variables
    //==========================================================================
    
    private JSObject					formField			= null;
    
    //==========================================================================
    // Constructor
    //==========================================================================
    
    public FormReader(Applet applet, String formName, String fieldName)
    {
        try
        {
            JSObject window = JSObject.getWindow(applet);
            JSObject document = (JSObject) window.getMember("document");
            JSObject form = (JSObject) document.getMember(formName);
            formField = (JSObject) form.getMember(fieldName);
        }
        catch (Exception e)
        {
            throw new FormReaderException
            	(
	                "Failed to access the form data" +
	                " form = " + formName + " field = " + fieldName
	            );
        }
    }
    
    //==========================================================================
    // String conversion
    //==========================================================================
    
    public String to_String()
    {
        try
        {
            return (String) formField.getMember("value");
        }
        catch (Exception e)
        {
            throw new FormReaderException
        	(
                "Failed to retrieve form field value"
            );
        }
    }
}
