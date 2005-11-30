package org.emayor.client.Utilities.SchemaEnumeration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Hashtable;


import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


import org.emayor.client.controlers.SchemaValidatorEntry;
import org.emayor.client.parser.EMayorDocumentParser;
import org.emayor.client.parser.xml.XML_Node;

import org.apache.xerces.xs.PSVIProvider;



/**
 *  Created on 06.11.2005, jpl
 * 
 * 
 *  Preparation for a special feature for JTextFields:
 *  If schemas are present and a schema contains an enumeration
 *  a JComboBox is used instead of a JTextField, and the
 *  enumeration values automatically are added to that JComboBox.
 *  This way, the user can select one of the schema conform input.
 *  This requires an initial sax parser run, which returns a list of all
 *  entry pathes with enumeration types found and their values:
 * 
 */



public class SchemaEnumerationMapCreator
{

  // The simpleTypeTable is created when createSchemaEnumerationMap has been called.
  private Hashtable simpleTypeTable = new Hashtable();

  
  public SchemaEnumerationMapCreator()
  {  
  }


  public Hashtable getSimpleTypeTable()
  {
    return this.simpleTypeTable;
  }
  
  
  public void addSimpleTypeEntry( String xpath, String simpleTypeName )
  {
     this.simpleTypeTable.put(xpath,simpleTypeName);
  }
  
  
 /**
  *  The only public method of this class, which
  *  creates and returns the map.
  * 
  *  It is called by the GUIBuilder. This one then uses the SchemaEnumerationMap
  *  for creating JComboboBoxes for any JTextFields, which are connected
  *  with enumerations.
  * 
  */
  public SchemaEnumerationMap createSchemaEnumerationMap( final XML_Node modelNode,
                                                          final EMayorDocumentParser eMayorDocumentParser )
  {
    System.out.println("SchemaEnumerationMapCreator.createSchemaEnumerationMap() starts.");
    
    this.simpleTypeTable.clear();
    
    // The schemaEnumerationMap will hold the results of this parser run and is returned
    // from this method:
    SchemaEnumerationMap schemaEnumerationMap = new SchemaEnumerationMap();
   
    System.out.println("SchemaEnumerationMapCreator.createSchemaEnumerationMap() starts.");
    // Get the model as string document [Note that this method is synchronized - no probs with EDT]
    XML_Node eDocumentNode = modelNode.getChildAt(0);
    final StringBuffer eDocument = eMayorDocumentParser.translateXMLTree( eDocumentNode,true );

    
    try 
    {

      XMLReader xmlReader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
    
        
      // Parse the input, convert the string to an input stream, which 
      // is passed to the parser then:
      InputStream inputStream = new ByteArrayInputStream(eDocument.toString().getBytes("UTF-8"));
      
      xmlReader.setFeature("http://xml.org/sax/features/namespaces", true);                                            
      xmlReader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
      xmlReader.setFeature("http://xml.org/sax/features/validation", true);
      xmlReader.setFeature("http://apache.org/xml/features/validation/schema", true);
      xmlReader.setFeature("http://apache.org/xml/features/validate-annotations", true);
      xmlReader.setFeature("http://apache.org/xml/features/validation/dynamic", true);
      xmlReader.setFeature("http://apache.org/xml/features/xinclude", true);                                   
      xmlReader.setFeature("http://apache.org/xml/features/xinclude/fixup-base-uris", true);     
      xmlReader.setFeature("http://apache.org/xml/features/xinclude/fixup-language", true);
      
                                             
      InputSource inputSource = new InputSource(inputStream);
      SchemaEnumerationOutputHandler outputHandler =
          new SchemaEnumerationOutputHandler(this,(PSVIProvider)xmlReader,schemaEnumerationMap);
      xmlReader.setContentHandler(outputHandler);
      xmlReader.parse( inputSource );
                             
    } 
    catch( SAXParseException spe ) // Shouldn't ocur.
    {
      System.out.println(" ");     
      System.out.println(">SchemaEnumerationMapCreator: Catched an exception of type:");
      System.out.println(">" + spe.getClass().getName() );
      System.out.println(">and with the message:");
      System.out.println(">" + spe.getMessage() );
      //System.out.println(">and the following stacktrace:");
      //ex.printStackTrace();
      System.out.println(" ");     
    }
    catch( Exception ex ) // should not ocur                                                                                         
    { 
      System.out.println(" ");     
      System.out.println(">SchemaEnumerationMapCreator: Catched an exception of type:");
      System.out.println(">" + ex.getClass().getName() );
      System.out.println(">and with the message:");
      System.out.println(">" + ex.getMessage() );
      //System.out.println(">and the following stacktrace:");
      ex.printStackTrace();
      System.out.println(" ");     
    }
    catch( Error err ) // should not ocur 
    { 
      System.out.println(" ");     
      System.out.println(">SchemaEnumerationMapCreator: Catched an error of type:");
      System.out.println(">" + err.getClass().getName() );
      System.out.println(">and with the message:");
      System.out.println(">" + err.getMessage() );
      //System.out.println(">and the following stacktrace:");
      err.printStackTrace();
      System.out.println(" ");     
    }
   
    System.out.println("SchemaEnumerationMapCreator.createSchemaEnumerationMap() ends.");
        
    return schemaEnumerationMap;
  } // createSchemaEnumerationMap


  
  
  
  
  
  
  
  
  
  
} // SchemaEnumerationMapCreator
