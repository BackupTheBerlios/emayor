//==============================================================================
// Copyright (C) 2005 Advanced Encryption Technology Europe B.V.
// Copyright (C) 2005 eMayor Consortium (http://www.emayor.org)
// All rights are reserved. Reproduction in whole or in part is prohibited
// without the written consent of the copyright owner.
//
// Package: org.emayor.xmlsigner
// Class:   EMayorDocFunctions
// Version: $Id: EMayorDocFunctions.java,v 1.1 2005/11/16 10:51:00 emayor Exp $
// Product: Java XML signing applet
//
// Description:
//		Class with static methods that give access to an eMayor document's
//      external JavaScript functions
//==============================================================================

package org.emayor.client.controlers.xmlsigner;

import java.applet.*;
import netscape.javascript.*;

public class EMayorDocFunctions
{
    //==========================================================================
    // Exception class
    //==========================================================================
    
    public static class EMayorDocFunctionException extends RuntimeException
    {
        //======================================================================
        // Constructor
        //======================================================================
        
        public EMayorDocFunctionException(String reason)
        {
            super(reason);
        }
    }
    
    //==========================================================================
    // Show the confirmation dialog
    //==========================================================================
    
    public static boolean showConfirmationDialog(Applet applet)
    {
        try
        {
            JSObject window = JSObject.getWindow(applet);
            Object rv = window.call("showConfirmation", null);
            
            Boolean result = (Boolean) rv;
            
            return result.booleanValue();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            throw new EMayorDocFunctionException
            	(
            	    "Failed to call showConfirmation. Did you supply this function?"
            	);
        }
    }
    
    //==========================================================================
    // Error message in case no signer could be found
    //==========================================================================
    
    public static void showNoCertificateFoundDialog(Applet applet)
    {
        try
        {
            JSObject window = JSObject.getWindow(applet);
            window.call("showNoCertFound", null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            throw new EMayorDocFunctionException
            	(
            	    "Failed to call showNoCertFound. Did you supply this function?"
            	);
        }
    }
    
    //==========================================================================
    // General error message
    //==========================================================================
    
    public static void showUnrecoverableError(Applet applet)
    {
        try
        {
            JSObject window = JSObject.getWindow(applet);
            window.call("showUnrecoverableError", null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            throw new EMayorDocFunctionException
            	(
            	    "Failed to call showUnrecoverableError. Did you supply this function?"
            	);
        }
    }
}
