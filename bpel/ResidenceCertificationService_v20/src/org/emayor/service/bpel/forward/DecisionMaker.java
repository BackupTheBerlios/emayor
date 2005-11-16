package org.emayor.service.bpel.forward;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.log4j.Logger;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DecisionMaker {
	private static final Logger log = Logger.getLogger(DecisionMaker.class);
  
  private String localName = "";
  private String xpath = "";
  
    public DecisionMaker() {
    try {
      Properties props = new Properties();
      props.load(this.getClass().getResourceAsStream("municipality.properties"));
      this.localName = props.getProperty("forward.municipality.local.name","UNKNOWN");
      this.xpath = props.getProperty("serving.municipality.xpath", "/");
      if (log.isDebugEnabled())
        log.debug("local name = " + this.localName);
    } catch(IOException ioex) 
    {
      log.error("caught ex: " + ioex.toString());
    }
    
  }
  
  public String shouldBeForwarded(String rcsRequest) 
  {
    String ret = "NO";
    try 
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      StringReader stringReader = new StringReader(rcsRequest);
      InputSource inputSource = new InputSource(stringReader);
      Document document = builder.parse(inputSource);
      String _remote = XPathAPI.selectSingleNode(document, xpath).getNodeValue();
      if (log.isDebugEnabled()) 
        log.debug("got from request - serving municipality = " + _remote);
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
    return ret;
  }
}