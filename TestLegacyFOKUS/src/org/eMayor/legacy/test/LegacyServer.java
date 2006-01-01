/*
 * $ Created on 18.08.2005 by tku $
 */
package org.eMayor.legacy.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.xpath.XPathAPI;
import org.eMayor.AdaptationLayer.ejb.M2Einterface;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class LegacyServer extends UnicastRemoteObject implements M2Einterface {
    private final static Logger log = Logger.getLogger(LegacyServer.class);

    private static final Charset charset = Charset.forName("UTF-8");

    /**
     * @throws java.rmi.RemoteException
     */
    public LegacyServer() throws RemoteException {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eMayor.AdaptationLayer.ejb.M2Einterface#ResidenceRequest(java.lang.String)
     */
    public String ResidenceRequest(String request) throws RemoteException {
        // TODO Auto-generated method stub
        if (log.isDebugEnabled())
            log.debug("input document:\n" + request);
        String ret = null;
        try {
            log.debug("get DocumentBuilderFactory");
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            log.debug("parse input -> build DOM");
            InputSource inputSource = new InputSource(new StringReader(request));
            Document root = builder.parse(inputSource);
            log.debug("select the surname node value");
            String municipality = XPathAPI
                    .selectSingleNode(
                            root,
                            "/ResidenceCertificationRequestDocument/ServingMunicipalityDetails/text()")
                    .getNodeValue();
            String forename = XPathAPI
            .selectSingleNode(
                    root,
                    "/ResidenceCertificationRequestDocument/ConcernedPersonDetails/CitizenName/CitizenNameForename/text()")
            .getNodeValue();
            String surname = XPathAPI
            .selectSingleNode(
                    root,
                    "/ResidenceCertificationRequestDocument/ConcernedPersonDetails/CitizenName/CitizenNameSurname/text()")
            .getNodeValue();
            if (log.isDebugEnabled())
                log.debug("got from request document - municipality: " + municipality + ", name: "+forename+" "+surname);
            if (municipality != null && forename != null && surname != null) {
            	ret = this.readDocument("/xml/"+municipality+"/"+(forename+surname).replaceAll(" ","")+".xml");
            	if (ret != null) {
            		if (log.isDebugEnabled()) log.debug("got *POSITIVE* result!");
            	} else {
            		if (log.isDebugEnabled()) log.debug("got *NO* result!");
            	}
            }
            if (ret == null) {
            	ret = this.readDocument("/xml/"+municipality+"/NegativeDocument.xml");
            	ret = editNegativeDocument(request,ret,municipality);
            	if (log.isDebugEnabled()) log.debug("got *NEGATIVE* result!");
            }
            if (ret == null) ret = "";
            
        } catch (Exception ex) {
            log.error("caught ex: " + ex.toString());
        }
        if (log.isDebugEnabled())
            log.debug("response document:\n" + ret);
        return ret;
    }

    public String editNegativeDocument(String request, String document, String localName) 
    {
      
      String result = null;
        		
      String previous1 = null;
      String previous2 = null;
      
      if (localName.equals("Aachen")) 
      {
        previous1 = "UrspruenglicheAnforderung";
      } 
        else if (localName.equals("Seville")) 
      {
        previous1 = "SolicitudOriginal";
      } 
        else if (localName.equals("Siena")) 
      {
        previous1 = "RichiestaOriginaria";
      } 
      else if (localName.equals("Bolzano-Bozen")) 
      {
        previous1 = "RichiestaOriginaria";
        previous2 = "UrspruenglicheAnforderung";
      } 
        else 
      {
        previous1 = "OriginalRequest";
      }
      
      int beg, end;
      
      if (previous1 != null) {
      
      
        beg = request.indexOf("<ResidenceCertificationRequestDocument");
        beg = request.indexOf(">",beg)+1;
        end = request.lastIndexOf("</");
        request = request.substring(beg,end);
        System.out.println("Original request without root: "+request);
        
        while (request.indexOf("<ds:Signature>") > 0 || request.indexOf("<ds:Signature ") > 0) {
          beg = request.indexOf("<ds:Signature>");
          if (beg < 0) 
          {
            beg = request.indexOf("<ds:Signature ");
          }
          end = request.indexOf("</ds:Signature>",beg)+15;
          //System.out.println("Original request without signature (0,"+beg+") part 1: "+xmlDocument.substring(0,beg));
          //System.out.println("Original request without signature ("+beg+","+end+") part 2: "+xmlDocument.substring(beg,end));
          //System.out.println("Original request without signature ("+end+","+xmlDocument.length()+") part 3: "+xmlDocument.substring(end,xmlDocument.length()));
          request = request.substring(0,beg) + request.substring(end,request.length());
          //System.out.println("Original request without signature: "+xmlDocument);
        }
        
        beg = request.indexOf("<");
        end = request.length();
      
        while (beg <= end && beg != -1) {
          if (request.substring(beg,end).charAt(1) != '/' &&
              request.substring(beg,end).charAt(3) != ':' &&
              request.substring(beg,end).charAt(4) != ':' &&
              request.substring(beg,end).charAt(5) != ':') 
              {
                request = request.substring(0,beg) + "<bus:" + request.substring(beg+1,request.length());
              } else
          if (request.substring(beg,end).charAt(1) == '/' &&
              request.substring(beg,end).charAt(4) != ':' &&
              request.substring(beg,end).charAt(5) != ':' &&
              request.substring(beg,end).charAt(6) != ':') 
              {
                request = request.substring(0,beg) + "</bus:" + request.substring(beg+2,request.length());
              }
          beg = request.indexOf("<",beg+1);
          end = request.length();
        }
        request = request.replaceAll("ed:","edc:");
       
        beg = document.indexOf(previous1+">")+previous1.length()+1;
        end = document.indexOf(previous1,beg);
      
        String endTag = document.substring(beg,end);
        
        end -= endTag.length() - endTag.lastIndexOf("</");
      
        result = document.substring(0,beg) + request + document.substring(end,document.length());
        System.out.println("Resulting document is: "+result);
      }
      
      if (previous2 != null) 
      {
        beg = result.indexOf(previous2+">")+previous2.length()+1;
        end = result.indexOf(previous2,beg);
        String endTag = result.substring(beg,end);
        end -= endTag.length() - endTag.lastIndexOf("</");
      
        result = result.substring(0,beg) + request + result.substring(end,result.length());  
      }
      
      
      return result;
      
    }

    
    private final String readDocument(String doc) {
        String ret = null;
        if (log.isDebugEnabled())
            log.debug("try to read document: " + doc);
        try {
            InputStream is = this.getClass().getResourceAsStream(doc);
            BufferedReader br = new BufferedReader(new InputStreamReader(is,
                    charset));
            String line = null;
            StringBuffer b = new StringBuffer();
            while ((line = br.readLine()) != null)
                b.append(line.trim());
            if (log.isDebugEnabled())
                log.debug("read document:\n" + b.toString());
            ret = b.toString();
        } catch (Exception ex) {
            log.error("caught ex: " + ex.toString());
        }
        return ret;
    }
}