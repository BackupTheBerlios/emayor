//==============================================================================
// Copyright (C) 2005 Advanced Encryption Technology Europe B.V.
// Copyright (C) 2005 eMayor Consortium (http://www.emayor.org)
// All rights are reserved. Reproduction in whole or in part is prohibited
// without the written consent of the copyright owner.
//
// Package: org.emayor.xmlsigner
// Class:   FormWriter
// Version: $Id: FormWriter.java,v 1.1 2005/11/16 10:51:00 emayor Exp $
// Product: Java XML signing applet
//
// Description:
//		Web page form writer class
//==============================================================================

package org.emayor.client.controlers.xmlsigner;

import netscape.javascript.*;
import java.applet.*;

public class FormWriter
{
    //==========================================================================
    // FormWriter exception class
    //==========================================================================
    
    public class FormWriterException extends RuntimeException
    {
        //======================================================================
        // Constructor
        //======================================================================
        
        public FormWriterException(String reason)
        {
            super(reason);
        }
    }
    
    //==========================================================================
    // Member variables
    //==========================================================================
    
    private JSObject					formField			= null;
    
    private JSObject					window				= null;
    
    private String						value				= "";
    
    private String						submitFunction		= "";
    
    //==========================================================================
    // Constructor
    //==========================================================================
    
    public FormWriter(Applet applet, String formName, String fieldName)
    {
        try
        {
            window = JSObject.getWindow(applet);
            JSObject document = (JSObject) window.getMember("document");
            JSObject form = (JSObject) document.getMember(formName);
            formField = (JSObject) form.getMember(fieldName);
            
            submitFunction = "document." + formName + ".submit()";
        }
        catch (Exception e)
        {
            throw new FormWriterException
            	(
	                "Failed to access the form data" +
	                " form = " + formName + " field = " + fieldName
	            );
        }
    }
    
    //==========================================================================
    // Submit the form
    //==========================================================================
    
    public void submit()
    {
        try
        {
            window.eval(submitFunction);
        }
        catch (Exception e)
        {
            throw new FormWriterException
            	(
            	    "Failed to submit the form (called " + 
            	    submitFunction + ")"
            	);
        }
    }
    
    //==========================================================================
    // Append text to the form field
    //==========================================================================
    
    public void append(String s)
    {
        try
        {
            value = value + s;
            
            formField.setMember("value", value);
        }
        catch (Exception e)
        {
            throw new FormWriterException
            	(
            	    "Failed to write the form data"
            	);
        }
    }
    
    //==========================================================================
    // Append a line to the form field
    //==========================================================================
    
    public void appendln(String s)
    {
        s += "\n";
        this.append(s);
    }
    
    public void appendln()
    {
        this.append("\n");
    }
}
