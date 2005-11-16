package org.emayor.bpel.tools;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class MessageUtils 
{

  private Document doc = null;
  private String LANG = null;
  private String SEX = null;
  private String MESSAGE_FILES_BASE = "URRequest";
  public final String MESSAGE_TEMPLATE_EXT = ".msg";
  public final String MESSAGE_MAPPING_EXT = ".map";
  public final String MESSAGE_SEPERATOR = "_";

  public MessageUtils(String reqDoc, String fileName, String lang, String sex)
  {
    sex = trim(sex);
    if (sex.equals("")) sex = null;
    lang = trim(lang);
    if (lang.equals("")) lang = null;
    
    try {
      if (fileName != null) MESSAGE_FILES_BASE = fileName;
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      doc = builder.parse(new InputSource(new StringReader(reqDoc)));
      if (sex == null || lang == null) {
        Properties config = new Properties();
        config.load(this.getClass().getResourceAsStream("utils.properties"));
        String XP_LANG = config.getProperty("org.emayor.bpel.XPath.urs.lang");
        String XP_SEX = config.getProperty("org.emayor.bpel.XPath.urs.sex");
        LANG = XPathAPI.selectSingleNode(doc,XP_LANG+"/text()").getNodeValue().toLowerCase();
        SEX = XPathAPI.selectSingleNode(doc,XP_SEX+"/text()").getNodeValue().toLowerCase();
      }
    } catch (Exception e) {
      // do nothing
    } finally 
    {
      if (LANG == null) LANG = (lang == null ? "en" : lang);
      if (SEX == null) SEX = (sex == null ? "m" : sex);
      //System.out.println("LANG='"+LANG+"',SEX='"+SEX+"',lang='"+lang+"',sex='"+sex+"'");
    }
  }

	private String trim(String string) {
		/* remove leading whitespace */
	    string = string.replaceAll("^\\s+", "");
	    /* remove trailing whitespace */
	    string = string.replaceAll("\\s+$", "");
	    return string;
	}
  
  public String getMessageTemplate() 
  {
    String result = null;
    try {
      result = fileToString(MESSAGE_FILES_BASE+MESSAGE_SEPERATOR+LANG+MESSAGE_SEPERATOR+SEX+MESSAGE_TEMPLATE_EXT);
    } catch (Exception e) {
    }
    return result;
  }
  
  public String getMessageMapping() 
  {
    String result = null;
    try {
      result = fileToString(MESSAGE_FILES_BASE+MESSAGE_SEPERATOR+LANG+MESSAGE_MAPPING_EXT);
    } catch (Exception e) {
    }
    return result;
  }

  private String fileToString(String file) 
  {
    BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)));
    
    String current = null;
    String result = new String();
    /*
     * <ResidenceCertificationRequestDocument><RequesterDetails><CitizenName><CitizenNameTitle>Dr.</CitizenNameTitle><CitizenNameForename>Henrik</CitizenNameForename><CitizenNameSurname>Mustermann</CitizenNameSurname></CitizenName><CitizenSex>M</CitizenSex><PreferredLanguages>de</PreferredLanguages></RequesterDetails><RequestDate>02-06-05</RequestDate><ReceivingMunicipalityDetails>Aachen</ReceivingMunicipalityDetails><ServingMunicipalityDetails>Aachen</ServingMunicipalityDetails></ResidenceCertificationRequestDocument>
     */
    try
    {
    
      while ((current = reader.readLine()) != null) 
      {
        result+=current+"\n";
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    
    return result;
  }

}