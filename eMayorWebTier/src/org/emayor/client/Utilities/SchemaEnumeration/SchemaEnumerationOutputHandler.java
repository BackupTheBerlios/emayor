package org.emayor.client.Utilities.SchemaEnumeration;



/**
 *  Contains SAX DocumentHandler methods
 *  This one also parser PostSchemaDescription infosets
 *  for getting enumerations = the only task of this parser run.
 * 
 *  Created on 06.11.2005, jpl
 * 
 */



import java.io.*;                                                                                              
import java.util.Vector;
import org.xml.sax.*;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;
                                                                                                             
import org.apache.xerces.xs.PSVIProvider;
import org.apache.xerces.xs.*;                             
import org.apache.xerces.xni.*;
import org.apache.xerces.impl.dv.xs.*;
import org.apache.xerces.impl.xs.SchemaSymbols;
   

public class SchemaEnumerationOutputHandler extends DefaultHandler
{

    StringBuffer textBuffer = null;
    private String indentString = "    "; // Amount to indent
    private int indentLevel = 0;
                                                             
    private int fIndent = 0;
    
                                                             
    // The xPathTracker stores the path of the xml elements which
    // are validated during this parser run. As soon as an exception
    // occurs, the content of this xPathTracker contains the
    // path of the xml tree element, which is not compliant with
    // the scheme.                                                          
    private Vector xPathTracker = new Vector(); // of strings
                           

    private SchemaEnumerationMapCreator schemaEnumerationMapCreator;
    private PSVIProvider postSchemaValidationProvider;
    
    private SchemaEnumerationMap schemaEnumerationMap;


  public SchemaEnumerationOutputHandler( final SchemaEnumerationMapCreator _schemaEnumerationMapCreator,
                                         final PSVIProvider _postSchemaValidationProvider,
                                         final SchemaEnumerationMap _schemaEnumerationMap )
  {
    this.schemaEnumerationMapCreator = _schemaEnumerationMapCreator;
    this.postSchemaValidationProvider = _postSchemaValidationProvider;
    this.schemaEnumerationMap = _schemaEnumerationMap;
  }                 

  
  
  public void setDocumentLocator(Locator l) 
  {
    // Save this to resolve relative URIs or to give diagnostics.
    try 
    {
      System.out.print("LOCATOR");
      System.out.print("\n SYS ID: " + l.getSystemId());
      System.out.flush();
    } 
    catch( Exception e ) 
    {
      // Ignore errors
    }
  }


  public void startDocument() throws SAXException 
  {
      // Reset the cache values:
      this.indentLevel = 0;
      this.xPathTracker.removeAllElements();
  
      startNewLine();
      startNewLine();
      emit("START DOCUMENT");
      startNewLine();
      emit("<?xml version='1.0' encoding='UTF-8'?>");
  }



  public void endDocument() throws SAXException  
  {
    startNewLine();
    emit("END DOCUMENT");    
    try
    {
      startNewLine();
      System.out.flush();
    } catch (Exception e) {
      throw new SAXException("I/O error", e);
    }
  }



  public void startElement( String namespaceURI, 
                            String sName, // simple name
                            String qName, // qualified name
                            Attributes attrs) throws SAXException 
  {
    echoText();                           
    indentLevel++;
    startNewLine();
    emit("ELEMENT: ");
  
    String eName = sName; // element name
  
    if( "".equals(eName) ) eName = qName; // not namespaceAware   
  
    emit("<" + eName);

    // Note: In eMayor, this is used for navigating in the eMayorForms
    //       framework, which requires the qualified xml tree tag names,
    //       for example: simple=EmailAddress    qualified=aapd:EmailAddress
    //       We need the qualified name:
    this.xPathTracker.addElement( qName );
      
    emit(" [qualified path= " + this.getCurrentXPathTrackerContentForModel() + "] ");

    if (attrs != null) 
    {
      for (int i = 0; i < attrs.getLength(); i++) 
      {
        String aName = attrs.getLocalName(i); // Attr name 
        if( "".equals(aName) ) aName = attrs.getQName(i);
        startNewLine();
        emit("   ATTR: ");
        emit(aName);
        emit("\t\"");
        emit(attrs.getValue(i));
        emit("\"");
      }
    }
    if( attrs.getLength() > 0 ) startNewLine();     
    emit(">");
  
    // Signalize the user, that this entry is ok initially. 
    // If a schema violation occurs, the error() method will
    // change that afterwards.
    String lastXPathTrackerEntry = (String)this.xPathTracker.lastElement();
    if( lastXPathTrackerEntry.equals(qName) )
    {
      
      // output the schema information:
      ElementPSVI schemaElement =  this.postSchemaValidationProvider.getElementPSVI();
      this.processSchemaEnumerationInformation( schemaElement, attrs );
                                 
      //System.out.println("\n---> Call processValidationResultFor from startElement()");
    
    }
    else
    {   
      System.out.println("***");
      System.out.println("***  SchemaEnumerationOutputHandler: xPathTracker error:");
      System.out.println("***  The validated xml content has syntactical errors.");
      System.out.println("***  Validation fails therefore.");
      System.out.println("***  Referencing following entries is not possible.");
      System.out.println("***  [startElement]");
    }  
  } // startElement
                      
   
   
 /**
  *  Output the schema information for the current entry.
  *  We are looking for simple type enumerations.
  */
  private void processSchemaEnumerationInformation( final ElementPSVI schemaElement,Attributes attributes ) throws SAXException
  {
    // the postSchemaValidationProvider holds information about the schema
    // for the current entry. Print them out:
    if( schemaElement != null )
    {
      startNewLine();
      System.out.print("[Enumerations: ");
      XSTypeDefinition typedef = schemaElement.getTypeDefinition();
      this.processPSVITypeDefinition(typedef);
      System.out.println(" ]");
    }
  }







    private void processPSVITypeDefinition(XSTypeDefinition type) throws SAXException 
    {
      if (type == null) return;
      if (type.getTypeCategory() == XSTypeDefinition.SIMPLE_TYPE) 
      {
          processPSVISimpleTypeDefinition((XSSimpleTypeDefinition)type);
      }
    }



    private void processPSVISimpleTypeDefinition(XSSimpleTypeDefinition type) throws SAXException
    {
      if (type == null) return;
      processPSVIFacets( type );
    } // processPSVISimpleTypeDefinition

          

    private void processPSVIFacets(XSSimpleTypeDefinition type) throws SAXException
    {
        if (type == null) return;
        XSObjectList facets = type.getFacets();
        XSObjectList multiValueFacets = type.getMultiValueFacets();
        if (facets != null)
        {
            for (int i = 0; i < facets.getLength(); i++)
            {
                XSFacet facet = (XSFacet)facets.item(i);
                String name = this.translateFacetKind(facet.getFacetKind());
                this.startNewLine();
                this.emit("Simple Facet name: " + name + " value: " + facet.getLexicalFacetValue() );
            }
        }
        if (multiValueFacets != null)
        {
            for (int i = 0; i < multiValueFacets.getLength(); i++)
            {
                this.startNewLine();
                XSMultiValueFacet facet = (XSMultiValueFacet)multiValueFacets.item(i);
                String name = this.translateFacetKind(facet.getFacetKind());
                this.emit("Multi Facet name: " + name);
                StringList values = facet.getLexicalFacetValues();
                int numberOfValues = values.getLength();
                for (int j = 0; j < numberOfValues; j++)
                {
                    this.startNewLine();
                    this.emit("value[" + j + "]: " + values.item(j) + " " );
                }                
                // Here is the main task: Add multi facets holding enumerations to
                // the schemaEnumerationMap. This is the task of the parsing.
                if( facet.getFacetKind() == XSSimpleTypeDefinition.FACET_ENUMERATION )
                {
                  if( numberOfValues > 0 )
                  {
                    String[] enumerationValues = new String[numberOfValues];
                    for( int k=0; k < numberOfValues; k++ ) enumerationValues[k] = values.item(k);
                    String nodePath = this.getCurrentXPathTrackerContentForModel();
                    this.schemaEnumerationMap.addEntry(nodePath,enumerationValues);                   
                  }
                } // if
            }
        }
    }



    private String translateFacetKind( short kind )
    {
        switch( kind )
        {
            case XSSimpleTypeDefinition.FACET_WHITESPACE :     return SchemaSymbols.ELT_WHITESPACE;
            case XSSimpleTypeDefinition.FACET_LENGTH :         return SchemaSymbols.ELT_LENGTH;
            case XSSimpleTypeDefinition.FACET_MINLENGTH :      return SchemaSymbols.ELT_MINLENGTH;
            case XSSimpleTypeDefinition.FACET_MAXLENGTH :      return SchemaSymbols.ELT_MAXLENGTH;
            case XSSimpleTypeDefinition.FACET_TOTALDIGITS :    return SchemaSymbols.ELT_TOTALDIGITS;
            case XSSimpleTypeDefinition.FACET_FRACTIONDIGITS : return SchemaSymbols.ELT_FRACTIONDIGITS;
            case XSSimpleTypeDefinition.FACET_PATTERN :        return SchemaSymbols.ELT_PATTERN;
            case XSSimpleTypeDefinition.FACET_ENUMERATION :    return SchemaSymbols.ELT_ENUMERATION;
            case XSSimpleTypeDefinition.FACET_MAXINCLUSIVE :   return SchemaSymbols.ELT_MAXINCLUSIVE;
            case XSSimpleTypeDefinition.FACET_MAXEXCLUSIVE :   return SchemaSymbols.ELT_MAXEXCLUSIVE;
            case XSSimpleTypeDefinition.FACET_MINEXCLUSIVE :   return SchemaSymbols.ELT_MINEXCLUSIVE;
            case XSSimpleTypeDefinition.FACET_MININCLUSIVE :   return SchemaSymbols.ELT_MININCLUSIVE;
            default : return "unknown";
        }
    }

         

                                                                                                                               
  public void endElement( String namespaceURI, 
                          String sName, // simple name                                                              
                          String qName ) throws SAXException // qualified name  
  {                                          
    echoText();
    startNewLine();
    emit("END_ELM: ");
    String eName = sName; // element name
    if( "".equals(eName) ) eName = qName; // not namespaceAware
    emit("</" + eName + ">");
    
    // Remove this level from the xPathTracker vector as well.
    // So the last element in the xPathTracker must be equal to qName,
    // otherwise the xml tree is syntactically incorrect:
    String lastXPathTrackerEntry = (String)this.xPathTracker.lastElement();
    if( lastXPathTrackerEntry.equals(qName) )
    {
      this.xPathTracker.removeElementAt( this.xPathTracker.size()-1 );
    }
    else
    {   
      System.out.println("***");
      System.out.println("***  SchemaEnumerationOutputHandler: xPathTracker error:");
      System.out.println("***  The validated xml content has syntactical errors.");
      System.out.println("***  Validation fails therefore.");
      System.out.println("***  Referencing following entries is not possible.");
      System.out.println("***");
    }
    indentLevel--;
  }
  
  

  public void characters( char[] buf, int offset, int len) throws SAXException 
  {
    String s = new String(buf, offset, len);
    if( textBuffer == null ) 
    {
      textBuffer = new StringBuffer(s);
    } 
    else 
    {
      textBuffer.append(s);
    }
  }



  public void ignorableWhitespace( char[] buf, int offset, int len ) throws SAXException 
  {
    // Ignore it
  }



  public void processingInstruction( String target, String data ) throws SAXException 
  {
    startNewLine();
    emit("PROCESS: ");
    emit("<?" + target + " " + data + "?>");
  }
                       
  


    
  /**
   *  Error handler: Not really used here. 
   */                                                    
   public void error( SAXParseException e ) throws SAXParseException 
   {    
     // = The xml path of the element causing the exception:                      
     String xPath = this.getCurrentXPathTrackerContentForDocument();
     // A description of the reason:
     String failureMessage = e.getMessage();
     // A small postprocessing work:                           
     // failureMessage contains text from the parsers in the form: (example:)
     // cvc-pattern-valid: Value 'Jörg' is not facet-valid with respect to pattern '[A-Za-z0-9\s~!"@#$%&'\(\)\*\+,\-\./:;<=>\?\[\\\]_\{\}\^£€]*' for type 'CitizenNameForenameType'.
     // For out purposes for the enduser and for the above text,
     // we remove the cryptic error identifier (cvc-pattern-valid:):
     int supposedErrorIdentifierEndingIndex = failureMessage.indexOf(": "); 
     if( supposedErrorIdentifierEndingIndex > 0 )
     {
       failureMessage = failureMessage.substring( supposedErrorIdentifierEndingIndex+1 );
     }
     System.out.println(" "); 
     System.out.println(" "); 
     System.out.println(">SchemaEnumerationOutputHandler error handler: Detected schema violation for: ");
     System.out.println(">element= " + xPath);
     System.out.println(">reason= " + failureMessage);
     System.out.println(">full reason= " + e.getMessage() );
     System.out.println(">Set the associated icon to an error icon for user feedback.");
     
   } // error                     
    


    
   /**
    *  Warning handler: Dump warnings too:
    */
    public void warning(SAXParseException err) throws SAXParseException 
    {
      System.out.println("** Warning" + ", line " + err.getLineNumber() + ", uri " + err.getSystemId());               
      System.out.println("   " + err.getMessage());
    }
      
      

 /**
  *  Display text accumulated in the character buffer
  */
  private void echoText() throws SAXException
  {
    try
    {
      if( textBuffer == null ) return;
      startNewLine();
      emit("CHARS:   ");
      String s = "" + textBuffer;
      if( !s.trim().equals("") ) emit(s);                                 
      textBuffer = null;
    }
    catch( Exception e )
    {
      throw new SAXException(e);
    }
  }

  
  private void emitNamedValue(String name, String value) throws SAXException
  {
    /* this is debug action */
    try
    {
      System.out.print(" name=");
      System.out.print(name);
      System.out.print(" value="); 
      System.out.print(value);
      System.out.print(" ");
      System.out.flush();
    }
    catch( Exception e )
    {
      throw new SAXException(e);
    }    
  }



  
 /**
  *  Wrap I/O exceptions in SAX exceptions, to
  *  suit handler signature requirements
  */
  private void emit(String s) throws SAXException
  {
    /* this is debug action */
    try
    {
      System.out.print(s);
      System.out.flush();
    }
    catch( Exception e )
    {
      throw new SAXException(e);
    }    
  }



 /**
  *  Starts a new line and indents the next line appropriately
  */
  private void startNewLine() throws SAXException
  {
    /* this is debug action */
    try
    {
      String lineEnd = System.getProperty("line.separator");
      System.out.print(lineEnd);
      for( int i = 0; i < indentLevel; i++ ) System.out.print(indentString);
    }
    catch( Exception e )
    {
      throw new SAXException(e);
    }    
  }



  // Using ONE member attribute as work attribute for better speed
  // and less memory gc traffic
  private StringBuffer currentXPathContentForDocument = new StringBuffer();
 /**                
  *  Returns the current content of the xPathTracker as slash separated String.
  *  This one starts with the name of the e-document.
  *  The second variant getCurrentXPathTrackerContentForMode() returns the
  *  same like this method, but adds "Model/" to the start.
  */
  public String getCurrentXPathTrackerContentForDocument()
  {
    this.currentXPathContentForDocument.setLength(0); // clear
    if( this.xPathTracker.size() > 0 )
    {
      this.currentXPathContentForDocument.append( (String)this.xPathTracker.elementAt(0) );
      for( int i=1; i < this.xPathTracker.size(); i++ )
      {
        this.currentXPathContentForDocument.append( "/" );
        this.currentXPathContentForDocument.append( (String)this.xPathTracker.elementAt(i) );
      }
    } // if  
    return this.currentXPathContentForDocument.toString();
  }



  
  
  // Using ONE member attribute as work attribute for better speed
  // and less memory gc traffic
  private StringBuffer currentXPathContentForModel = new StringBuffer();
 /**                
  *  Returns the current content of the xPathTracker as slash separated String.
  *  This one starts with the "Model/" prefix, followed by name of the e-document.
  *  The first variant getCurrentXPathTrackerContentForDocument() returns the
  *  same like this method, but without "Model/" at the start.
  */
  public String getCurrentXPathTrackerContentForModel()
  {
    this.currentXPathContentForModel.setLength(0); // clear
    this.currentXPathContentForModel.append("Model/");
    if( this.xPathTracker.size() > 0 )
    {
      this.currentXPathContentForModel.append( (String)this.xPathTracker.elementAt(0) );
      for( int i=1; i < this.xPathTracker.size(); i++ )
      {
        this.currentXPathContentForModel.append( "/" );
        this.currentXPathContentForModel.append( (String)this.xPathTracker.elementAt(i) );
      }
    } // if  
    return this.currentXPathContentForModel.toString();
  }
  
  
  
  
  
  
  
  /**
   * Resolve an external entity.
   *
   * <p>Always return null, so that the parser will use the system
   * identifier provided in the XML document.  This method implements
   * the SAX default behaviour: application writers can override it
   * in a subclass to do special translations such as catalog lookups
   * or URI redirection.</p>
   *
   * @param publicId The public identifer, or null if none is
   *                 available.
   * @param systemId The system identifier provided in the XML 
   *                 document.
   * @return The new input source, or null to require the
   *         default behaviour.
   * @exception java.io.IOException If there is an error setting
   *            up the new input source.
   * @exception org.xml.sax.SAXException Any SAX exception, possibly
   *            wrapping another exception.
   * @see org.xml.sax.EntityResolver#resolveEntity
   */
   public InputSource resolveEntity( String publicId, String systemId ) throws SAXException
  {
    //System.out.println("resolveEntity() for publicId= " + publicId + "  and systemId=" + systemId ); 
    return null;
  }
  
  
  
   /**
    * Receive notification of a notation declaration.
    *
    * <p>By default, do nothing.  Application writers may override this
    * method in a subclass if they wish to keep track of the notations
    * declared in a document.</p>
    *
    * @param name The notation name.
    * @param publicId The notation public identifier, or null if not
    *                 available.
    * @param systemId The notation system identifier.
    * @exception org.xml.sax.SAXException Any SAX exception, possibly
    *            wrapping another exception.
    * @see org.xml.sax.DTDHandler#notationDecl
    */
   public void notationDecl( String name, String publicId, String systemId) throws SAXException
   {
      System.out.println("[NotationDeclaration: name= " + name +
                         "   publicId= " + publicId +
                         "   sytemId= " + systemId + " ]");
   }
  
  

   
   /**
    * Receive notification of a skipped entity.
    *
    * <p>By default, do nothing.  Application writers may override this
    * method in a subclass to take specific actions for each
    * processing instruction, such as setting status variables or
    * invoking other methods.</p>
    *
    * @param name The name of the skipped entity.
    * @exception org.xml.sax.SAXException Any SAX exception, possibly
    *            wrapping another exception.
    * @see org.xml.sax.ContentHandler#processingInstruction
    */
   public void skippedEntity (String name) throws SAXException
   {
    System.out.println("[ skippedEntity: name= " + name + "  ]");
   }
  
   
   /**
    * Receive notification of an unparsed entity declaration.
    *
    * <p>By default, do nothing.  Application writers may override this
    * method in a subclass to keep track of the unparsed entities
    * declared in a document.</p>
    *
    * @param name The entity name.
    * @param publicId The entity public identifier, or null if not
    *                 available.
    * @param systemId The entity system identifier.
    * @param notationName The name of the associated notation.
    * @exception org.xml.sax.SAXException Any SAX exception, possibly
    *            wrapping another exception.
    * @see org.xml.sax.DTDHandler#unparsedEntityDecl
    */
   public void unparsedEntityDecl (String name, String publicId,
    String systemId, String notationName) throws SAXException
   {
    System.out.println("[ unparsedEntityDecl: name= " + name + 
                       "  publicId= " + publicId +
                       "  systemId= " + systemId +
                       "  notationName= " + notationName + "  ]");
   }
  
    
   
   
} // SchemaValidatorOutputHandler


