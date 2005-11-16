package org.emayor.client.controlers;

  /**
   *  Contains SAX DocumentHandler methods
   */

import java.io.*;
import java.util.Vector;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory; 
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
   

public class SchemaValidatorOutputHandler extends DefaultHandler
{

    StringBuffer textBuffer;
    private String indentString = "    "; // Amount to indent
    private int indentLevel = 0;
                                                             
                                                             
    // The xPathTracker stores the path of the xml elements which
    // are validated during this parser run. As soon as an exception
    // occurs, the content of this xPathTracker contains the
    // path of the xml tree element, which is not compliant with
    // the scheme.                                                          
    private Vector xPathTracker = new Vector(); // of strings
                           

    private SchemaValidator validator;
    
    
  public SchemaValidatorOutputHandler( final SchemaValidator _validator )
  {
    this.validator = _validator;
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
      
    emit(" [qualified path= " + this.getCurrentXPathTrackerContent() + "] ");

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
        
      //System.out.println("\n---> Call processValidationResultFor from startElement()");
        
      // Signalize the user, that this entry is ok:
      // The empty String as 2.nd parameter means that validation was ok.
      this.validator.processValidationResultFor( this.getCurrentXPathTrackerContent(),"" );
    
    }
    else
    {   
      System.out.println("***");
      System.out.println("***  SchemaValidatorOutputHandler: xPathTracker error:");
      System.out.println("***  The validated xml content has syntactical errors.");
      System.out.println("***  Validation fails therefore.");
      System.out.println("***  Referencing following entries is not possible.");
      System.out.println("***  [startElement]");
    }  
  } // startElement





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
      System.out.println("***  SchemaValidatorOutputHandler: xPathTracker error:");
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
   *  Error handler: Called when a schema violation is detected.
   *  It calls the validator for displaying the schemaviolation. 
   */                                                    
   public void error( SAXParseException e ) throws SAXParseException 
   {    
     // = The xml path of the element causing the exception:                      
     String xPath = this.getCurrentXPathTrackerContent();
     // A description of the reason:
     String parserFailureMessage = e.getMessage();     
     this.validator.processValidationResultFor(xPath,parserFailureMessage);           
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


  
 /**
  *  Wrap I/O exceptions in SAX exceptions, to
  *  suit handler signature requirements
  */
  private void emit(String s) throws SAXException
  {
    /* this is debug action
    try
    {
      System.out.print(s);
      System.out.flush();
    }
    catch( Exception e )
    {
      throw new SAXException(e);
    }
    */
  }



 /**
  *  Starts a new line and indents the next line appropriately
  */
  private void startNewLine() throws SAXException
  {
    /* this is debug action
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
    */
  }



  // Using ONE member attribute as work attribute for better speed
  // and less memory gc traffic
  private StringBuffer currentXPathContent = new StringBuffer();
 /**                
  *  Returns the current content of the xPathTracker as slash separated String.
  */
  public String getCurrentXPathTrackerContent()
  {
    this.currentXPathContent.setLength(0); // clear
    if( this.xPathTracker.size() > 0 )
    {
      this.currentXPathContent.append( (String)this.xPathTracker.elementAt(0) );
      for( int i=1; i < this.xPathTracker.size(); i++ )
      {
        this.currentXPathContent.append( "/" );
        this.currentXPathContent.append( (String)this.xPathTracker.elementAt(i) );
      }
    } // if  
    return this.currentXPathContent.toString();
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
  
  
  
  
  

} // SchemaValidatorOutputHandler


