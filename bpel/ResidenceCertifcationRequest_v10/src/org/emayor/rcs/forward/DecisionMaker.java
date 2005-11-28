package org.emayor.rcs.forward;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.Logger;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DecisionMaker {
	private static final Logger log = Logger.getLogger(DecisionMaker.class);
  
  private String localName = "";
  private String servingName = "";
  private String receivingName = "";
  
    public DecisionMaker() {
    try {
      Properties props = new Properties();
      props.load(this.getClass().getResourceAsStream("municipality.properties"));
      this.localName = props.getProperty("forward.municipality.local.name","UNKNOWN");
    } catch(IOException ioex) 
    {
    }

  }
  
  public String getLoginServer(String rcsRequest) 
  {
    String result = null;
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      StringReader stringReader = new StringReader(rcsRequest);
      InputSource inputSource = new InputSource(stringReader);
      Document document = builder.parse(inputSource);
      result = XPathAPI.selectSingleNode(document, "/ResidenceCertificationRequestDocument/LoginServer/text()").getNodeValue();
      this.localName = result;
    } catch (Exception e) 
    {
      e.printStackTrace();
      result = e.getMessage();
    }
    return result;
  }
  public String setLoginServer(String rcsRequest, String server) 
  {
    String result = null;
    try {
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      StringReader stringReader = new StringReader(rcsRequest);
      InputSource inputSource = new InputSource(stringReader);
      Document document = builder.parse(inputSource);
      XPathAPI.selectSingleNode(document, "/ResidenceCertificationRequestDocument/LoginServer/text()").setNodeValue(server);
      TransformerFactory transFactory = TransformerFactory.newInstance();
      Transformer trans = null;
      trans = transFactory.newTransformer();
      StringWriter writer = new StringWriter();
      DOMSource source = new DOMSource(document);
      StreamResult stream = new StreamResult(writer);
      
      trans.transform(source,stream);
      result = writer.toString();  
    
    } catch (Exception e) 
    {
      e.printStackTrace();
      result = rcsRequest;
    }
    
    return result;
  }
  
  
  public String setRequestDate(String rcsRequest) 
  {
    String result = null;
    String date = null;
    try {
    	Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        date = sdf.format( now );
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      StringReader stringReader = new StringReader(rcsRequest);
      InputSource inputSource = new InputSource(stringReader);
      Document document = builder.parse(inputSource);
      XPathAPI.selectSingleNode(document, "/ResidenceCertificationRequestDocument/RequestDate/text()").setNodeValue(date);
      TransformerFactory transFactory = TransformerFactory.newInstance();
      Transformer trans = null;
      trans = transFactory.newTransformer();
      StringWriter writer = new StringWriter();
      DOMSource source = new DOMSource(document);
      StreamResult stream = new StreamResult(writer);
      
      trans.transform(source,stream);
      result = writer.toString();  
    
    } catch (Exception e) 
    {
      e.printStackTrace();
      result = rcsRequest;
    }
    
    return result;
  }
  
  
  public String getServingMunicipality(String rcsRequest) 
  {
    String ret = this.localName;
    final String SERVING_MUNICIPALITY_NAME = "/ResidenceCertificationRequestDocument/ServingMunicipalityDetails/text()";
    try 
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      StringReader stringReader = new StringReader(rcsRequest);
      InputSource inputSource = new InputSource(stringReader);
      Document document = builder.parse(inputSource);
      String _remote = XPathAPI.selectSingleNode(document, SERVING_MUNICIPALITY_NAME).getNodeValue().trim();
      if (log.isDebugEnabled()) 
        log.debug("got from request - serving municipality = " + _remote);
      if (!_remote.equalsIgnoreCase(this.localName.trim()))
        ret = _remote;
    } catch(FactoryConfigurationError fcer) 
    {
      log.error("caught ex: " + fcer.toString());
      ret = "NO";
    } catch (ParserConfigurationException pcex) {
			log.error("caught ex: " + pcex.toString());
      ret = "NO";
    } catch (SAXException saxex) {
			log.error("caught ex: " + saxex.toString());
      ret = "NO";
    } catch(IOException ioex) {
      log.error("caught ex: " + ioex.toString());
      ret = "NO";
    } catch (TransformerException tex) {
			log.error("caught ex: " + tex.toString());
      ret = "NO";
    }
    this.servingName = ret;
    return ret;
  }
  
  public String getReceivingMunicipality(String rcsRequest) 
  {
    String ret = this.localName;
    final String RECEIVING_MUNICIPALITY_NAME = "/ResidenceCertificationRequestDocument/ReceivingMunicipalityDetails/text()";
    try 
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      StringReader stringReader = new StringReader(rcsRequest);
      InputSource inputSource = new InputSource(stringReader);
      Document document = builder.parse(inputSource);
      String _remote = XPathAPI.selectSingleNode(document, RECEIVING_MUNICIPALITY_NAME).getNodeValue();
      if (log.isDebugEnabled()) 
        log.debug("got from request - receiving municipality is = " + _remote+ ", local municipality is = " + this.localName);
      if (!_remote.equalsIgnoreCase(this.localName))
        ret = _remote;
    } catch(FactoryConfigurationError fcer) 
    {
      log.error("caught ex: " + fcer.toString());
      ret = "NO";
    } catch (ParserConfigurationException pcex) {
			log.error("caught ex: " + pcex.toString());
      ret = "NO";
    } catch (SAXException saxex) {
			log.error("caught ex: " + saxex.toString());
      ret = "NO";
    } catch(IOException ioex) {
      log.error("caught ex: " + ioex.toString());
      ret = "NO";
    } catch (TransformerException tex) {
			log.error("caught ex: " + tex.toString());
      ret = "NO";
    }
    this.receivingName = ret;
    return ret;
  }
  
  public String getLocalMunicipality() 
  {
    return this.localName;
  }
  
  public String getNegativeDocument() 
  {
  
    String fileToReceive = null;
    if (this.localName.equals("Aachen"))
    {
      fileToReceive = "NegativeMeldebescheinigungMeldung.xml";
    } else
    if (this.localName.equals("Seville"))
    {
      fileToReceive = "RespuestaNegativaCertificadoInscripcionPadronal.xml";
    } else
    if (this.localName.equals("Siena"))
    {
      fileToReceive = "RispostaNegativaCertificatoDiResidenza.xml";
    } else
    if (this.localName.equals("Bolzano-Bozen"))
    {
      fileToReceive = "RispostaNegativaCertificatoDiResidenza-NegativeMeldebescheinigungMeldung.xml";
    } else 
    {
      fileToReceive = "NegativeResponseResidenceCertificationRequest.xml";
    }
    
    StringBuffer sb = new StringBuffer();
    
    try {
      
      BufferedReader bf = 
					new BufferedReader(
						new InputStreamReader (
					        new BufferedInputStream (
                  (this.getClass().getResourceAsStream(fileToReceive)),2048),"UTF8"));
                  
                  
					        	//new FileInputStream (
					        		//new File(fileToReceive)),2048),"UTF8"));
				
  		String line = null;
      while ((line = bf.readLine()) != null) {
        sb.append(line);
      }

    } catch (Exception e) {
      sb = new StringBuffer("<empty>");
      sb.append(e.getMessage());
      sb.append("</empty>");
    }
		
    return sb.toString();		
  }
  
  public String editNegativeDocument(String negativeDoc, String reason) 
  {
    String result = null;
    String reasonXPath = "";
    String reasonXPath2 = "";
    
    if (this.localName.equals("Aachen")) 
    {
      reasonXPath = "/NegativeMeldebescheinigungMeldung/Bemerkungen/text()";
    } 
      else if (this.localName.equals("Seville")) 
    {
      reasonXPath = "/RespuestaNegativaCertificadoInscripcionPadronal/Observaciones/text()";
    } 
      else if (this.localName.equals("Siena")) 
    {
      reasonXPath = "/RispostaNegativaCertificatoDiResidenza/Osservazioni/text()";
    } 
    else if (this.localName.equals("Bolzano-Bozen")) 
    {
      reasonXPath = "/RispostaNegativaCertificatoDiResidenza-NegativeMeldebescheinigungMeldung/RispostaNegativaCertificatoDiResidenza/Osservazioni/text()";
      reasonXPath2 = "/RispostaNegativaCertificatoDiResidenza-NegativeMeldebescheinigungMeldung/NegativeMeldebescheinigungMeldung/Bemerkungen/text()";
    } 
      else 
    {
      reasonXPath = "/NegativeResponseResidenceCertificationDocument/Observations/text()";
    }
    try {
      log.debug("-> transform to document");
      DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = domFactory.newDocumentBuilder();
      StringReader stringReader = new StringReader(negativeDoc);
      InputSource inputSource = new InputSource(stringReader);
      Document document = builder.parse(inputSource);
      
      log.debug("... set reason");
      XPathAPI.selectSingleNode(document, reasonXPath).setNodeValue(reason);
      if (this.localName.equals("Bolzano-Bozen")) 
      {
        XPathAPI.selectSingleNode(document, reasonXPath2).setNodeValue(reason);  
      }
      
      TransformerFactory transFactory = TransformerFactory.newInstance();
      Transformer trans = null;
      trans = transFactory.newTransformer();
      StringWriter writer = new StringWriter();
      DOMSource source = new DOMSource(document);
      StreamResult stream = new StreamResult(writer);
      
      log.debug("<- transform to string");
      trans.transform(source,stream);
      result = writer.toString();
      
      // 3. test display for CV
      // 4. test reasoning for CV
      // 5. implement output of negative for Citizen
      
    } catch (Exception e) 
    {
      StringBuffer buf = new StringBuffer("<empty>");
      buf.append(e.getMessage());
      buf.append("</empty>");
      result = buf.toString();
      e.printStackTrace();
    }
    
    log.debug("got negative document with reason:" +result);
    return result;
  }
}