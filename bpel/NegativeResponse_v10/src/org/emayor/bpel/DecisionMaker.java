package org.emayor.bpel;

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
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DecisionMaker {
  
  private String localName = "";
  
    public DecisionMaker(String name) {
    this.localName = name;
  }
  
  public String getLocalMunicipality() 
  {
    return this.localName;
  }
  
  public String getNegativeDocument(String xmlDocument) 
  {
  
    String fileToReceive = null;
    String result = null;
    
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
		
    String previous1 = null;
    String previous2 = null;
    
    if (this.localName.equals("Aachen")) 
    {
      previous1 = "UrspruenglicheAnforderung";
    } 
      else if (this.localName.equals("Seville")) 
    {
      previous1 = "SolicitudOriginal";
    } 
      else if (this.localName.equals("Siena")) 
    {
      previous1 = "RichiestaOriginaria";
    } 
    else if (this.localName.equals("Bolzano-Bozen")) 
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
    
    
      beg = xmlDocument.indexOf("<ResidenceCertificationRequestDocument");
      beg = xmlDocument.indexOf(">",beg)+1;
      end = xmlDocument.lastIndexOf("</");
      xmlDocument = xmlDocument.substring(beg,end);
      System.out.println("Original request without root: "+xmlDocument);
      
      while (xmlDocument.indexOf("<ds:Signature>") > 0 || xmlDocument.indexOf("<ds:Signature ") > 0) {
        beg = xmlDocument.indexOf("<ds:Signature>");
        if (beg < 0) 
        {
          beg = xmlDocument.indexOf("<ds:Signature ");
        }
        end = xmlDocument.indexOf("</ds:Signature>",beg)+15;
        //System.out.println("Original request without signature (0,"+beg+") part 1: "+xmlDocument.substring(0,beg));
        //System.out.println("Original request without signature ("+beg+","+end+") part 2: "+xmlDocument.substring(beg,end));
        //System.out.println("Original request without signature ("+end+","+xmlDocument.length()+") part 3: "+xmlDocument.substring(end,xmlDocument.length()));
        xmlDocument = xmlDocument.substring(0,beg) + xmlDocument.substring(end,xmlDocument.length());
        //System.out.println("Original request without signature: "+xmlDocument);
      }
      
      beg = xmlDocument.indexOf("<");
      end = xmlDocument.length();
    
      while (beg <= end && beg != -1) {
        if (xmlDocument.substring(beg,end).charAt(1) != '/' &&
            xmlDocument.substring(beg,end).charAt(3) != ':' &&
            xmlDocument.substring(beg,end).charAt(4) != ':' &&
            xmlDocument.substring(beg,end).charAt(5) != ':') 
            {
              xmlDocument = xmlDocument.substring(0,beg) + "<bus:" + xmlDocument.substring(beg+1,xmlDocument.length());
            } else
        if (xmlDocument.substring(beg,end).charAt(1) == '/' &&
            xmlDocument.substring(beg,end).charAt(4) != ':' &&
            xmlDocument.substring(beg,end).charAt(5) != ':' &&
            xmlDocument.substring(beg,end).charAt(6) != ':') 
            {
              xmlDocument = xmlDocument.substring(0,beg) + "</bus:" + xmlDocument.substring(beg+2,xmlDocument.length());
            }
        beg = xmlDocument.indexOf("<",beg+1);
        end = xmlDocument.length();
      }
      xmlDocument = xmlDocument.replaceAll("ed:","edc:");
     
      beg = sb.indexOf(previous1+">")+previous1.length()+1;
      end = sb.indexOf(previous1,beg);
    
      String endTag = sb.toString().substring(beg,end);
      
      end -= endTag.length() - endTag.lastIndexOf("</");
    
      result = sb.toString().substring(0,beg) + xmlDocument + sb.toString().substring(end,sb.length());
      System.out.println("Resulting document is: "+result);
    }
    
    if (previous2 != null) 
    {
      sb = new StringBuffer(result);
      beg = sb.indexOf(previous2+">")+previous2.length()+1;
      end = sb.indexOf(previous2,beg);
      String endTag = sb.substring(beg,end);
      end -= endTag.length() - endTag.lastIndexOf("</");
    
      result = sb.substring(0,beg) + xmlDocument + sb.substring(end,sb.length());  
    }
    
    /* try {
      DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = domFactory.newDocumentBuilder();
      StringReader stringReader = new StringReader(sb.toString());
      InputSource inputSource = new InputSource(stringReader);
      Document document = builder.parse(inputSource);
      
      Node previous1 = null;
      Node previous2 = null;
      
      String reasonXPath = null;
      String reasonXPath2 = null;
      
      
      
      
    if (this.localName.equals("Aachen")) 
    {
      previous1 = document.getElementsByTagName("UrspruenglicheAnforderung").item(0).getParentNode();
      previous1 = document.createElement("UrspruenglicheAnforderung");
    } 
      else if (this.localName.equals("Seville")) 
    {
      previous1 = document.getElementsByTagName("SolicitudOriginal").item(0).getParentNode();
      previous1 = document.createElement("SolicitudOriginal");
    } 
      else if (this.localName.equals("Siena")) 
    {
      previous1 = document.getElementsByTagName("RichiestaOriginaria").item(0).getParentNode();
      previous1 = document.createElement("RichiestaOriginaria");
    } 
    else if (this.localName.equals("Bolzano-Bozen")) 
    {
      previous1 = document.getElementsByTagName("RichiestaOriginaria").item(0).getParentNode();
      previous1 = document.createElement("RichiestaOriginaria");
      previous2 = document.getElementsByTagName("UrspruenglicheAnforderung").item(0).getParentNode();
      previous2 = document.createElement("UrspruenglicheAnforderung");
    } 
      else 
    {
      previous1 = document.getElementsByTagName("OriginalRequest").item(0).getParentNode();
      previous1 = document.createElement("OriginalRequest");
    }
    
    stringReader = new StringReader(xmlDocument);
    inputSource = new InputSource(stringReader);
    Document document2 = builder.parse(inputSource);
    
    NodeList nodes = document2.getChildNodes();
    
    for (int i=0; i<nodes.getLength(); i++) {
      previous1.appendChild(nodes.item(i).cloneNode(true));
      if (this.localName.equals("Bolzano-Bozen"))
        previous2.appendChild(nodes.item(i).cloneNode(true));
    }
      
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
      StringBuffer buf = new StringBuffer("<empty>");
      buf.append(e.getMessage());
      buf.append("</empty>");
      result = buf.toString();
      e.printStackTrace();
    }
    */
    
    return result;
    
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
      DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = domFactory.newDocumentBuilder();
      StringReader stringReader = new StringReader(negativeDoc);
      InputSource inputSource = new InputSource(stringReader);
      Document document = builder.parse(inputSource);
      
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
      
      trans.transform(source,stream);
      result = writer.toString();
      
    } catch (Exception e) 
    {
      StringBuffer buf = new StringBuffer("<empty>");
      buf.append(e.getMessage());
      buf.append("</empty>");
      result = buf.toString();
      e.printStackTrace();
    }
    
    return result;
  }
}