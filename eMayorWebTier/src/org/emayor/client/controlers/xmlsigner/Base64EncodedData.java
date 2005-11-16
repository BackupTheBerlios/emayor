//==============================================================================
// Copyright (C) 2005 Advanced Encryption Technology Europe B.V.
// Copyright (C) 2005 eMayor Consortium (http://www.emayor.org)
// All rights are reserved. Reproduction in whole or in part is prohibited
// without the written consent of the copyright owner.
//
// Package: org.emayor.xmlsigner
// Class:   XMLBlob
// Version: $Id: Base64EncodedData.java,v 1.1 2005/11/16 10:51:00 emayor Exp $
// Product: Java XML signing applet
//
// Description:
//		XML blob wrapper class
//==============================================================================

package org.emayor.client.controlers.xmlsigner;

import java.util.*;

public class Base64EncodedData
{
    //==========================================================================
    // Base 64 quantum class
    //==========================================================================
    
    private class Base64Quantum
    {
        //======================================================================
        // Constants
        //======================================================================
        
        private final char[] EncodingDictionary =
            {
            	'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            	'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 
            	'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 
            	'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            	'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            	'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            	'w', 'x', 'y', 'z', '0', '1', '2', '3',
            	'4', '5', '6', '7', '8', '9', '+', '/'
            };
        
        private final int[] DecodingDictionary =
        	{
	           	 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
	           	 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
	           	 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 62,  0,  0,  0, 63,
	           	52, 53, 54, 55, 56, 57, 58, 59, 60, 61,  0,  0,  0,  0,  0,  0,
	           	 0,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14,
	           	15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,  0,  0,  0,  0,  0,
	           	 0, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
	           	41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51,  0,  0,  0,  0,  0,
	           	 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
	           	 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
	           	 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
	           	 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
	           	 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
	           	 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
	           	 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
	           	 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0
           };
        
        //======================================================================
        // Member variables
        //======================================================================
        
        private int					theData						= 0;
        
        private int					dataLength					= 0;
        
        //======================================================================
        // Constructors
        //======================================================================
        
        public Base64Quantum(byte[] theData)
        {
            this.dataLength = theData.length;
            this.theData = fromByteArray(theData); 
        }
        
        public Base64Quantum(String quantum)
        {
            decode(quantum);
        }
        
        //======================================================================
        // Conversion to and from byte arrays
        //======================================================================
        
        private int fromByteArray(byte[] arr)
        {
            int rv = 0;
            
            for (int i = 0; i < arr.length; i++)
            {
                rv = rv << 8;
                
                rv += (arr[i] & 0xff);
            }
            
            for (int i = 0; i < (3 - arr.length); i++)
            {
                rv = rv << 8;
            }
            
            return rv;
        }
        
        private byte[] toByteArray(int value, int length)
        {
            byte[] rv = new byte[length];
            
            for (int i = 2; i >= 0; i--)
            {
                if (i < length)
                {
                    rv[i] = (byte) (value & 0x000000ff);
                }
                
                value = value >>> 8;
            }
            
            return rv;
        }
        
        //======================================================================
        // Retrieve the data
        //======================================================================
        
        public byte[] getData()
        {
            return toByteArray(theData, dataLength);
        }
        
        //======================================================================
        // Retrieve the encoded quantum
        //======================================================================
        
        public String getQuantum()
        {
            return encode();
        }
        
        //======================================================================
        // Decode the quantum
        //======================================================================
        
        private void decode(String quantum)
        {
            if (quantum.length() != 4)
            {
                throw new RuntimeException("Invalid base64 quantum");
            }
            
            int encodingLength = 0;
            
            for (int i = 0; i < 4; i++)
            {
                theData = theData << 6;
                
                if (quantum.charAt(i) != '=')
                {
                    theData += DecodingDictionary[quantum.charAt(i)];
                    encodingLength++;
                }
            }
            
            if (encodingLength <= 2)
            {
                dataLength = 1;
            }
            else if (encodingLength == 3)
            {
                dataLength = 2;
            }
            else
            {
                dataLength = 3;
            }
        }
        
        //======================================================================
        // Encode the quantum
        //======================================================================
        
        private String encode()
        {
            String quantum = "";
            
            int remainingBase64Chars = 0;
            int encodedData = theData;
            
            if (dataLength == 0)
            {
                quantum = "====";
                remainingBase64Chars = 0;
            }
            else if (dataLength == 1)
            {
                quantum = "==";
                remainingBase64Chars = 2;
            }
            else if (dataLength == 2)
            {
                quantum = "=";
                remainingBase64Chars = 3;
            }
            else if (dataLength >= 3)
            {
                remainingBase64Chars = 4;
            }
            
            for (int i = 0; i < (4 - remainingBase64Chars); i++)
            {
                encodedData = encodedData >>> 6;
            }
            
            for (int i = 0; i < remainingBase64Chars; i++)
            {
                int index = (encodedData & 0x0000003f);
                
                quantum = EncodingDictionary[index] + quantum;
                
                encodedData = encodedData >>> 6;
            }
            
            return quantum;
        }
    }
    
    //==========================================================================
    // Member variables
    //==========================================================================
    
    private byte[]				theData							= null;
    
    //==========================================================================
    // Constructors
    //==========================================================================
    
    public Base64EncodedData(String data)
    {
        decode(data);
    }
    
    public Base64EncodedData(byte[] data)
    {
        theData = data;
    }
    
    //==========================================================================
    // Retrieve the data
    //==========================================================================
    
    public byte[] getData()
    {
        return theData;
    }
    
    //==========================================================================
    // Retrieve the base64 encoded data
    //==========================================================================
    
    public Vector getEncoding()
    {
        return encode();
    }
    
    //==========================================================================
    // Decode the data
    //==========================================================================
    
    private void decode(String data)
    {
        Vector quantumVector = new Vector();
        
        int length = 0;
        
        while (data.length() > 0)
        {
            while(((data.substring(0, 1).charAt(0) == ' ') ||
                (data.substring(0, 1).charAt(0) == '\r') ||
                (data.substring(0, 1).charAt(0) == '\n') ||
                (data.substring(0, 1).charAt(0) == '\t')) &&
                (data.length() > 0))
            {
                data = data.substring(1);
            }
            
            if (data.length() == 0)
            {
                break;
            }
            
            Base64Quantum quantum = new Base64Quantum(data.substring(0, 4));
            
            quantumVector.add(quantum);
            
            length += quantum.getData().length;
            
            data = data.substring(4);
        }
        
        theData = new byte[length];
        
        int index = 0;
        
        for (int i = 0; i < quantumVector.size(); i++)
        {
            Base64Quantum quantum = (Base64Quantum) quantumVector.elementAt(i);
            
            System.arraycopy(
                quantum.getData(), 0, theData, index, quantum.getData().length);
            
            index += quantum.getData().length;
        }
    }
    
    //==========================================================================
    // Encode the data
    //==========================================================================
    
    private Vector encode()
    {
        Vector rv = new Vector();
        
        int index = 0;
        int remaining = theData.length;
        
        String currentLine = "";
        
        while (remaining > 0)
        {
            byte[] quantumData = new byte[(remaining >= 3 ? 3 : remaining)];
            
            System.arraycopy(theData, index, quantumData, 0, quantumData.length);
            
            currentLine += new Base64Quantum(quantumData).getQuantum();
            
            if (currentLine.length() >= 76)
            {
                rv.add(new String(currentLine));
                
                currentLine = "";
            }
            
            remaining -= quantumData.length;
            index += quantumData.length;
        }
        
        if (currentLine.length() > 0)
        {
            rv.add(currentLine);
        }
        
        return rv;
    }
}
