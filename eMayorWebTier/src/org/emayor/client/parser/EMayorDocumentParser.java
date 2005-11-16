package org.emayor.client.parser;


                   
import org.emayor.client.LanguageProperties;
import org.emayor.client.parser.xml.*;
import org.emayor.client.DocumentProcessor;


public class EMayorDocumentParser
{
        
  private XMLParser xmlParser = new XMLParser();

  private LanguageProperties languageProperties;
	
  public EMayorDocumentParser( final LanguageProperties _languageProperties )
  {
    this.languageProperties = _languageProperties;
  } // Constructor



 /**
  *  Translate the eMayorForms document into a tree.
  *  An eMayorForms document must have                                 
  *  a root node with name eMayorForm1  ( 1 for version 1 )
  *  which contains two nodes:
  *  - the Model node and the View node.
  *
  *  This method returns the two nodes in an array:
  *  returns {Model node, View node}
  * 
  *  Note: This method can be called from the EDT and from the
  *        SchemaValidator user thread. Order of calls is not important,
  *        but this instance is locked for the execution of this method
  *        for this reason.
  * 
  */
  public synchronized XML_Node[] translate_eMayorFormsDocument( final StringBuffer eMayorFormsDocument ) throws Exception
  {
    XML_Node rootNode = this.xmlParser.transformDocumentToTree( eMayorFormsDocument );

    // Get the eMayorForm1 node:
    String eMayorNodePath = rootNode.getTagName() + "/eMayorForm1";
    XML_Node eMayorFormNode = XMLPath.GetNode(eMayorNodePath,rootNode);

    String modelNodePath = rootNode.getTagName() + "/" +
                           eMayorFormNode.getTagName() + "/Model";
    XML_Node modelNode = XMLPath.GetNode(modelNodePath,rootNode);

    String viewNodePath = rootNode.getTagName() + "/" +
                          eMayorFormNode.getTagName() + "/View";
    XML_Node viewNode  = XMLPath.GetNode(viewNodePath,rootNode);

    // Now postprocess this tree: Search all node values for
    // references to languageProperty file entries and replace
    // them by the value from the language property:
    this.searchAndReplaceLanguagePropertyReferences(viewNode);
    
    
    return new XML_Node[]{modelNode,viewNode};
  } // translate_eMayorFormsDocument                                                         



 /**
  *  Translate the xml document into a tree.
  * 
  *  Note: This method can be called from the EDT and from the
  *        SchemaValidator user thread. Order of calls is not important,
  *        but this instance is locked for the execution of this method
  *        for this reason.
  */
  public synchronized XML_Node translateDocument( final StringBuffer eMayorFormsDocument ) throws Exception
  {
    XML_Node rootNode = this.xmlParser.transformDocumentToTree( eMayorFormsDocument );              
    return rootNode;
  } // processDocument




 /**
  *  This is the inverse method to translateDocument().
  *  It takes an XML tree and produces the associated [flat] xml document as a string.
  * 
  *  Note: This method can be called from the EDT and from the
  *        SchemaValidator user thread. Order of calls is not important,
  *        but this instance is locked for the execution of this method
  *        for this reason.
  */
  public StringBuffer translateXMLTree( final XML_Node basisNode,
                                        final boolean addXMLPrologOnBeginning )
  {
    return this.xmlParser.transformTreeToDocument( basisNode,addXMLPrologOnBeginning );
  } // translateXMLTree


  
 /**
  *  Recursive method, which searches the node values for
  *  language property references and replaces them by the
  *  values from the language properties.
  */ 
  private void searchAndReplaceLanguagePropertyReferences( final XML_Node basisNode )
  {
  	// search and replace:
  	if( basisNode.getTagValue() != null )
  	{
      String value = basisNode.getTagValue().trim();
  		if( value.length() > 0 )
  		{
          System.out.print("Language property replacer: Key value: " + value );
          int startIndex = value.indexOf('$');
          if( startIndex>= 0)
          {
          	int endIndex = value.indexOf('$',startIndex+1);
          	if( endIndex >= 0 )
          	{
          	  String referenceKeyCandidate = value.substring(startIndex+1,endIndex);
          	  //System.out.println("language property replacer: found key candidate: " + referenceKeyCandidate);
              String resourceText = this.languageProperties.getTextFromLanguageResource(referenceKeyCandidate);         	
          	  if( resourceText != null )
          	  {
          	  	// replace the key by its value:
          	    basisNode.setTagValue( resourceText );
                System.out.print(" has been replaced by: " + resourceText );
          	  }
          	}
          }
          System.out.println(" ");
  		}
  	}
  	// recur:
  	for( int i=0; i < basisNode.getNumberOfChildren(); i++ )
  	{
  		this.searchAndReplaceLanguagePropertyReferences( basisNode.getChildAt(i) );
  	}
  }


  
  
  

} // XMLParser





    
