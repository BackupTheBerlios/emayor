//==============================================================================
// Copyright (C) 2005 Advanced Encryption Technology Europe B.V.
// Copyright (C) 2005 eMayor Consortium (http://www.emayor.org)
// All rights are reserved. Reproduction in whole or in part is prohibited
// without the written consent of the copyright owner.
//
// Package: org.emayor.xmlsigner
// Class:   XMLBlob
// Version: $Id: XMLBlob.java,v 1.1 2005/11/16 10:51:00 emayor Exp $
// Product: Java XML signing applet
//
// Description:
//		XML blob wrapper class
//==============================================================================

package org.emayor.client.controlers.xmlsigner;

import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.security.c14n.Canonicalizer;
import org.w3c.dom.Document;


public class XMLBlob implements Signable
{
    //==========================================================================
    // Member variables
    //==========================================================================
    
    private String			theBlob							= "";
    
    //==========================================================================
    // Constructors
    //==========================================================================
    
    public XMLBlob()
    {
    }
    
    public XMLBlob(String theBlob)
    {
        this.theBlob = theBlob;
    }
    
    //==========================================================================
    // Append some text
    //==========================================================================
    
    public void append(String s)
    {
        theBlob += s;
    }
    
    public void appendln(String s)
    {
        append(s);
        append("\n");
    }
    
    //==========================================================================
    // Insert the signature
    //==========================================================================
    
    public void insertSignature(XMLBlob signatureBlob)
    {
        // Find the last opening tag ("<")
        int lastOpening = theBlob.lastIndexOf("<");
        
        theBlob = 
            theBlob.substring(0, lastOpening) +
            signatureBlob.to_String() +
            theBlob.substring(lastOpening);
    }
    
    //==========================================================================
    // Convert to string
    //==========================================================================
    
    public String to_String()
    {
        return theBlob;
    }
    
    
    
    private byte[] MakeCanonical(String input) throws Exception
    {
       org.apache.xml.security.Init.init();
       DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();

       dfactory.setNamespaceAware(true);
       dfactory.setValidating(true);

       DocumentBuilder documentBuilder = dfactory.newDocumentBuilder();

       // this is to throw away all validation warnings
       documentBuilder
          .setErrorHandler(new org.apache.xml.security.utils
             .IgnoreAllErrorHandler());

       byte inputBytes[] = input.getBytes("UTF-8");
       Document doc = documentBuilder.parse(new ByteArrayInputStream(inputBytes));

       // after playing around, we have our document now
       Canonicalizer c14n = Canonicalizer.getInstance(
          "http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments");
       byte outputBytes[] = c14n.canonicalizeSubtree(doc);

       //System.out.println(new String(outputBytes));
       
       return outputBytes;
    }

    
    
    //==========================================================================
    // Signable interface implementation
    //==========================================================================
   
    
    
    public byte[] getCanonicalEncoding()
    {
        try
        {
               return MakeCanonical(theBlob);
        }
        catch (Exception uee)
        {
            throw new RuntimeException
            	(
            	    "Failed to convert the blob data to Canonical data"
            	); 
        }
    }
}
