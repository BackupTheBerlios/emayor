package org.emayor.bpel.tools;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.File;
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
  private String MESSAGE_FILES_BASE = "MessageTemplate";
  public final String MESSAGE_TEMPLATE_EXT = ".msg";
  public final String MESSAGE_MAPPING_EXT = ".map";
  public final String MESSAGE_SEPERATOR = "_";

  public MessageUtils(String reqDoc, String fileName)
  {
    System.out.println("got document: "+reqDoc);
    System.out.println("got filebase: "+fileName);
    try {
      if (fileName != null) MESSAGE_FILES_BASE = fileName;
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      doc = builder.parse(new InputSource(new StringReader(reqDoc)));
      Properties config = new Properties();
      config.load(this.getClass().getResourceAsStream("utils.properties"));
      String XP_LANG = config.getProperty("org.emayor.bpel.XPath.rcs.lang");
      String XP_SEX = config.getProperty("org.emayor.bpel.XPath.rcs.sex");
      LANG = XPathAPI.selectSingleNode(doc,XP_LANG+"/text()").getNodeValue().toLowerCase();
      SEX = XPathAPI.selectSingleNode(doc,XP_SEX+"/text()").getNodeValue().toLowerCase();
    } catch (Exception e) {
      if (LANG == null) LANG = "en";
      if (SEX == null) SEX = "m";
    }
    if (SEX.equals("male")) SEX = "m";
    if (SEX.equals("female")) SEX ="f";
    System.out.println("got sex: "+SEX);
    System.out.println("got language: "+LANG);
  }

  public String getMessageTemplate() 
  {
    String result = null;
    try {
      result = fileToString(MESSAGE_FILES_BASE+MESSAGE_SEPERATOR+LANG+MESSAGE_SEPERATOR+SEX+MESSAGE_TEMPLATE_EXT);
    } catch (Exception e) {
      
    }
    
    if (result == null) 
    {
      try {
        result = fileToString(MESSAGE_FILES_BASE+MESSAGE_SEPERATOR+"en"+MESSAGE_SEPERATOR+"m"+MESSAGE_TEMPLATE_EXT);
      } catch (Exception e1) {}
    }
    
    if (result == null) result = new String();
    
    System.out.println("read template: "+result);
    return result;
  }
  
  public String getMessageMapping() 
  {
    String result = null;
    try {
      result = fileToString(MESSAGE_FILES_BASE+MESSAGE_SEPERATOR+LANG+MESSAGE_MAPPING_EXT);
      
    } catch (Exception e) {
    }
    
    if (result == null)
    {
      try {
        result = fileToString(MESSAGE_FILES_BASE+MESSAGE_SEPERATOR+"en"+MESSAGE_MAPPING_EXT);
      } catch (Exception e1) {}
    }
    
    if (result == null) result = new String();
    
    
    System.out.println("read mapping: "+result);
    return result;
  }

  private String fileToString(String file) 
  {
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file),"UTF-8"));
    } catch (Exception e) {}
    
    String current = null;
    String result = null;
    /*
     * <ResidenceCertificationRequestDocument><RequesterDetails><CitizenName><CitizenNameTitle>Dr.</CitizenNameTitle><CitizenNameForename>Henrik</CitizenNameForename><CitizenNameSurname>Mustermann</CitizenNameSurname></CitizenName><CitizenSex>M</CitizenSex><PreferredLanguages>de</PreferredLanguages></RequesterDetails><RequestDate>02-06-05</RequestDate><ReceivingMunicipalityDetails>Aachen</ReceivingMunicipalityDetails><ServingMunicipalityDetails>Aachen</ServingMunicipalityDetails></ResidenceCertificationRequestDocument>
     */
    try
    {
    
      result = new String();
      while ((current = reader.readLine()) != null) 
      {
        result+=current+"\n";
      }
    }
    catch (Exception e)
    {
      result = null;
    }
    
    
    return result;
  }

}