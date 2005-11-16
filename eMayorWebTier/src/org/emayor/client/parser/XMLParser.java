package org.emayor.client.parser;

import java.util.*;
import java.io.*;

import org.emayor.client.parser.xml.*;
import org.emayor.client.Utilities.DataUtilities;

public class XMLParser
{
  

  public static final String RootNodeName = "RootNode";

  
  public XMLParser()
  {
  } // Constructor


 /**
  *  Translate the xml document into a tree.
  *  Uses member work_Buffer for faster operation
  *  and less short-time memory object creations. 
  */
  private StringBuffer work_Buffer = new StringBuffer();
  public synchronized XML_Node transformDocumentToTree( final StringBuffer eMayorFormsDocument ) throws Exception
  {
    XML_Node rootNode = new XML_Node(null,RootNodeName);
    XML_Node currentNode = rootNode;
    int docPosition = 0;
    boolean isClosingTag = false;
    boolean isComment = false;
    while( docPosition < eMayorFormsDocument.length() )
    {
        char ch = eMayorFormsDocument.charAt( docPosition++ );
        if( ( ch == '<' ) && (!isComment) ) // ramp
        {
          // Distinguish open and close tags
          char next_ch = eMayorFormsDocument.charAt( docPosition );
          // Check, if it's a starting comment:
          if( (next_ch == '!') || (next_ch == '?') ) // it's a starting comment
          {
            isComment = true;
          }
          if( !isComment ) // we're not inside a comment
          {
            if( next_ch != '/' ) // it's an open tag
            {
              // Tree Navigation:
              XML_Node newChildNode = new XML_Node(currentNode);
              currentNode.addChild( newChildNode );
              currentNode = newChildNode;
              // add the actual content of the workBuffer to
              // textBeforeNode, and reset it:
              newChildNode.setTextBeforeNode( work_Buffer.toString() );
              work_Buffer.setLength(0); // reset
            }
            else // it's a close tag
            {
              // If there is something in the work_Buffer, it is value content
              // of the current tag:
              if( work_Buffer.length() > 0 )
              {
                String tagValue = work_Buffer.toString();
                work_Buffer.setLength(0); // reset
                currentNode.setTagValue( tagValue );
              }
              // Consume the '/' character:
              docPosition++;
              isClosingTag = true;
            }
            work_Buffer.setLength(0); // buffer reset
          }
          else
          {
            work_Buffer.append(ch); // keep accumulating through the comment
          }
        }
        else if( ch == '>' )
        {
          if( isComment ) // Check if it's a closing comment
          {
            // Relase the isComment flag, when we encounter "-->" or "?>",
            // otherwise do nothing and proceed:
            // Note, that docPosition has been incremented already,
            // therefore subtract one more to go back.
            char ch1 = eMayorFormsDocument.charAt( docPosition-2 );
            char ch2 = eMayorFormsDocument.charAt( docPosition-3 );
            if( (ch1 == '-') && (ch2 == '-') )
            {
              isComment = false;
            }
            else
            if( ch1 == '?')	
            {
              isComment = false;
            }
            work_Buffer.append(ch);
          }
          else
          {
            if( isClosingTag )
            {   
              // xml check: the close tagname must be equal as the current one:
              String closeTagName = work_Buffer.toString();
              work_Buffer.setLength(0); // reset
              if( closeTagName.equals( currentNode.getTagName() ) )
              {
                // All ok, go back to the parent node:
                currentNode = currentNode.getParentNode();
                // and reset flags:
                isClosingTag = false; // reset
              }
              else
              {
                System.out.println("*** Unmatched tags:");
                System.out.println("*** Current tag is " + currentNode.getTagName() );
                System.out.println("*** Close tag is: "  + closeTagName );
                String errorMessage = "XMLParser: The Tag: " + 
                                      currentNode.getTagName() + 
                                      " has not been closed. Instead the tag: " +
                                      closeTagName + " was attempted to close.";
                throw new Exception(errorMessage);
              }
            }
            else // ket> end of an open tag
            {
              String openTagSymbolContent = work_Buffer.toString();
              if( openTagSymbolContent.endsWith("/") )
              {
                // "/>" means: the open tag is directly closed
                // remove the slash and set tag names and attributes:
                String tagSymbolContent = openTagSymbolContent.substring(0,openTagSymbolContent.length()-1);
                this.setTagNameAndAttributes( currentNode,tagSymbolContent );
                currentNode.setIsDirectlyClosed(true); // important for correct backtransformation
                work_Buffer.setLength(0);
                // All ok, go back to the parent node:
                currentNode = currentNode.getParentNode();
                // and reset flags:
                isClosingTag = false; // reset
              }
              else
              {
                this.setTagNameAndAttributes( currentNode,openTagSymbolContent );
                work_Buffer.setLength(0);
              }
            }
          }
        }
        else
        {
          work_Buffer.append(ch);
        }     
    } // while
    
    // Check, if there have been comment tags, which have not been closed.
    // In this case, isComment still is true here:
    if( isComment )
    {
      String errorMessage = "XMLParser: A comment has not been closed.";
      throw new Exception(errorMessage);
    }
    return rootNode;
  } // transformDocumentToTree


  
  


  // Use a member for the sequence buffer for speeding up and keep
  // memory transfers low.
  private StringBuffer attributeSequenceBuffer = new StringBuffer();
  private StringBuffer textAfterBuffer = new StringBuffer();  
  private StringBuffer tagSymbolBuffer = new StringBuffer();  
  private void setTagNameAndAttributes( final XML_Node node,
                                        String raw_TagSymbolContent ) throws Exception
  {  
    this.tagSymbolBuffer.setLength(0);
    this.tagSymbolBuffer.append( raw_TagSymbolContent );
    // See if it has attributes == if it has at least one space on it,
    // and getDoesContainAttributes() returns true. Check the inversion of that:
    int spacePosition = this.tagSymbolBuffer.indexOf(" ");
    if( ( spacePosition < 0 ) || (!this.getDoesContainAttributes( this.tagSymbolBuffer ) ) )
    {
      // No valid attributes present - all goes into the name:
      node.setTagName( this.tagSymbolBuffer.toString() );
      //System.out.println("Added tag with name= " + tagSymbolContent + " [No attributes]");
    }
    else
    {
      // The tagname is all before that space:
      node.setTagName( this.tagSymbolBuffer.substring(0,spacePosition) );
      // remove the consumed chars:
      tagSymbolBuffer.delete(0,spacePosition);      
      // Now collect the text after the tagname, which consists of
      // all possible whitespaces up to the beginning of the first attributes:
      textAfterBuffer.setLength(0);
      while( ( tagSymbolBuffer.length() > 0 ) &&
              ( Character.isWhitespace( this.tagSymbolBuffer.charAt(0) ) ) )
      {
        textAfterBuffer.append( tagSymbolBuffer.charAt(0) );
        tagSymbolBuffer.deleteCharAt(0);
      }
      // and assign that one:
      node.setTextAfterTagName( textAfterBuffer.toString() );
            
      // The rest must consist of a key="value" sequence, where this parser
      // additionally allows, that there is no space between these elements.
      // which is a special, nonstandard service.
      if( tagSymbolBuffer.length() > 0 )
      {
        this.attributeSequenceBuffer.setLength(0);
        // Take all into the attributeSequenceBuffer:
        this.attributeSequenceBuffer.append( tagSymbolBuffer.toString() );
        int equalSignPosition = 0;
        int firstQuotePosition = 0;
        int secondQuotePosition = 0;
        boolean hasMoreAttributes = true;
        while( this.getDoesContainAttributes( this.attributeSequenceBuffer ) )
        {
           // See getDoesContainAttributes(): We have:
           // ( equalSignPosition > 0 ) &&
           // ( firstQuotePosition > equalSignPosition ) && ( secondQuotePosition > firstQuotePosition )
           equalSignPosition = this.attributeSequenceBuffer.indexOf("=");
           firstQuotePosition = this.attributeSequenceBuffer.indexOf("\"");
           secondQuotePosition = this.attributeSequenceBuffer.indexOf("\"",firstQuotePosition+1);
        
           // Get the name and the value=            
           String attributeName = this.attributeSequenceBuffer.substring(0,equalSignPosition);
           // Note: Also remove the " signs at the start and the end in the values:
           String attributeValue = this.attributeSequenceBuffer.substring( firstQuotePosition+1,
                                                                           secondQuotePosition );
           // consume that, incliding the ending " sign :
           this.attributeSequenceBuffer.delete(0,secondQuotePosition+1);
           
           // create the attribute:
           XML_Attribute attribute = new XML_Attribute( attributeName,attributeValue );
           node.addAttribute( attribute );             
            
           // Now collect the text after the attribute, which consists of
           // all possible whitespaces up to the beginning of the next attribute or end of tag:
           textAfterBuffer.setLength(0);
           while( ( attributeSequenceBuffer.length() > 0 ) &&
                   ( Character.isWhitespace( this.attributeSequenceBuffer.charAt(0) ) ) )
           {
             textAfterBuffer.append( attributeSequenceBuffer.charAt(0) );
             attributeSequenceBuffer.deleteCharAt(0);
           }
           // and assign that one:
           attribute.setTextAfterAttribute( textAfterBuffer.toString() );
           
           //System.out.println("XMLParser: Added attribute with name= " + attributeName +
           //                   " and value= " + attributeValue);
          
        } // while
      } // if
    } // else        
  } // setTagNameAndAttributes


  

  
  
  
 /** 
  *  See it here is a valid attribute pair in the passed
  *  StringBuffer
  */
  private boolean getDoesContainAttributes( final StringBuffer buffer )
  {
    int equalSignPosition = buffer.indexOf("=");
    int firstQuotePosition = buffer.indexOf("\"");
    int secondQuotePosition = buffer.indexOf("\"",firstQuotePosition+1);
    return ( ( equalSignPosition > 0 ) &&
             ( firstQuotePosition > equalSignPosition ) &&
             ( secondQuotePosition > firstQuotePosition ) );
  }
  
  
  
  
  


  private void addTagNameToNode( final XML_Node node,
                                 final StringBuffer buffer )
  {
    node.setTagName( "$" + buffer.toString() + "$" );
  } // addTagNameToTree





  private void addTagValueToNode( final XML_Node node,
                                  final StringBuffer buffer )
  {
    node.setTagValue( "$" + buffer.toString() + "$" );
  } // addTagNameToTree




 /**
  *  This is the inverse method of transformDocumentToTree().
  *  It takes an XML tree and produces the associated [flat] xml document as a string.
  */
  public StringBuffer transformTreeToDocument( final XML_Node basisNode,
                                               final boolean addXMLPrologOnBeginning)
  {
    StringBuffer buffer = new StringBuffer();
    this.translateXMLTreeToBuffer( basisNode,buffer,0 ); // recursive
    // The header <?xml version="1.0" encoding="UTF-8"?> might be missing yet.
    // To become a valid document for schema validation later,
    // this one must inserted, if its not present, and if addXMLProlog is set:
    if( addXMLPrologOnBeginning )
    {
      if( !buffer.toString().startsWith("<?xml") )
      {
        buffer.insert(0,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");       
      }
    }
    return buffer;
  } // translateXMLTree


  
  
 /**
  *  Recursive worker of translateXMLTree().
  */
  private void translateXMLTreeToBuffer( final XML_Node basisNode,
                                         final StringBuffer buffer,
                                         final int recursionDepth )
  { 
    buffer.append( basisNode.getTextBeforeNode() );   
    buffer.append( "<" );
    // append name and attributes:
    buffer.append( basisNode.getTagName() );
    buffer.append( basisNode.getTextAfterTagName() );
    for( int i=0; i < basisNode.getNumberOfAttributes(); i++ )
    {
      XML_Attribute att = basisNode.getAttributeAt(i);
      buffer.append( att.getAttributeName() );
      buffer.append( "=\"" );
      buffer.append( att.getAttributeValue() );
      buffer.append( "\"" );
      buffer.append( att.getTextAfterAttribute() );      
    } // for    
    // append the optional value and close it:
    if( basisNode.getIsDirectlyClosed() )
    {
      buffer.append( "/>" );
    }
    else
    {
      buffer.append( ">" );
      // child nodes:
      for( int i=0; i < basisNode.getNumberOfChildren(); i++ )
      {
        XML_Node childNode = basisNode.getChildAt(i);
        // recur:
        this.translateXMLTreeToBuffer( childNode,buffer,recursionDepth+1 );
      } // for
      // optional value:
      if( basisNode.getTagValue() != null )
      {
        buffer.append( basisNode.getTagValue() );
      }
      // close tag:
      buffer.append( "</" );
      buffer.append( basisNode.getTagName() );
      buffer.append( ">" );
    }  
  } // translateXMLTree



  
 /** 
  *  Test method 
  */
  public static void main( String[] arguments )
  {
    System.out.println("------- XML Parser Test ");
    String filePath = System.getProperty("user.home") +
                      System.getProperty("file.separator") +
                      "test_edocument.xml";  
    System.out.println("File: " + filePath );
    File file = new File(filePath);
    try
    {
      FileInputStream fis = new FileInputStream(file);
      ByteArrayOutputStream bs = DataUtilities.ReadByteFileFromInputStream(fis);
      byte[] eDocumentBytes = bs.toByteArray();
      String eDocumentString = new String(eDocumentBytes,"UTF-8");
      StringBuffer eDocument = new StringBuffer( eDocumentString );
      
      System.out.println("------- source document: ----------- ");
      System.out.println(eDocument);
      System.out.println(" ");
      System.out.println(" ");
      System.out.println(" ");
      System.out.println(" ");
      
      XMLParser parser = new XMLParser();
      XML_Node rootNode = parser.transformDocumentToTree( eDocument );
      // throw away the root node and backtransform:
      XML_Node eDocNode = rootNode.getChildAt(0);
      StringBuffer backTransformed_eDocument = parser.transformTreeToDocument(eDocNode,false);

      String chk1 = DataUtilities.GetMessageDigestForBytesAsString(eDocumentBytes);
      String chk2 = DataUtilities.GetMessageDigestForBytesAsString(backTransformed_eDocument.toString().getBytes("UTF-8"));
      
      if( chk1.equals(chk2) )
      {
        System.out.println("------- forward-backward transformed document: ----------- ");
        System.out.println(backTransformed_eDocument);
        System.out.println(" ");
      }
      else
      {
        int len1 = eDocument.length();
        int len2 = backTransformed_eDocument.length();
        System.out.println("******** Checksum mismatch *************");
        System.out.println("len1 = " + len1 );
        System.out.println("len2 = " + len2 );
        System.out.println(" ");
        int mismatchIndex = 0;
        int checkLen = len1 ; if( len2 < checkLen ) checkLen = len2;
        StringBuffer correctBackTransformedPart = new StringBuffer();
        for( int i=0; i < checkLen; i++ )
        {
          if( eDocument.charAt(i) == backTransformed_eDocument.charAt(i) )
          {
            correctBackTransformedPart.append( backTransformed_eDocument.charAt(i) );
          }
          else
          {
            mismatchIndex = i;
            break;
          }
        }      
        System.out.println("------- correct part of forward-backward transformed document: ----------- ");
        System.out.println(correctBackTransformedPart);
        System.out.println("mismatch is above, at index= " + mismatchIndex);
      }      
      System.out.println(" ");
      System.out.println(" ");
      System.out.println(" ");
      System.out.println("Checksum comparison of the two documents:");
      System.out.println(" ");
      System.out.println("MD5 checksum1= " + chk1);
      System.out.println("MD5 checksum2= " + chk2);
      System.out.println(" ");
      System.out.println(" ");
    }
    catch( Exception e )
    {
      e.printStackTrace();
    }    
  } // main
  
  

} // XMLParser






