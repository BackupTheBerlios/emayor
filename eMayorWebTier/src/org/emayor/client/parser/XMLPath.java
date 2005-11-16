package org.emayor.client.parser;

  /**
   *  Holds static methods for getting xml nodes
   *  by their pathes.
   */

import java.util.StringTokenizer;

import org.emayor.client.parser.xml.*;

public class XMLPath
{



 /**
  *  Returns the node with the passed path inside the passed rootNode.
  *  Path delimiters are slashes.
  *  The path must be absolute, it must start with the name or the
  *  passed rootNode.
  *  Returns null, if no node could be found.
  */
  public static XML_Node GetNode( final String path, XML_Node rootNode ) throws Exception
  {
    StringTokenizer tok = new StringTokenizer(path,"/");
    XML_Node node = null;
    String rootName = tok.nextToken(); // must exist and be = rootNode name:
    if( rootName.equals(rootNode.getTagName() ) )
    {
      node = rootNode;
      while( tok.hasMoreTokens() )
      {
        String pathElement = tok.nextToken();

        // error feedback:
        if( pathElement == null )
        {
          String msg1 = "Error for path element of path: " + path;
          String msg2 = "This node couldn't be found by XMLPath.GetNode()";
          String msg3 = "Please correct the associated eMayorForm template.";
          PrintError(msg1,msg2);
          // This exeption is propagated back to the main applet,
          // which then will use the message of this exception
          // for creating an error form as second attempt.
          throw new Exception(msg1 + "\n" + msg2 + "\n" + msg3);
        }

        node = node.getChildByName(pathElement);

        // error feedback:
        if( node == null )
        {
          String msg1 = "Error for node with path: " + path;
          String msg2 = "This node couldn't be found by XMLPath.GetNode()";
          String msg3 = "Please correct the associated eMayorForm template.";
          PrintError(msg1,msg2);
          // This exeption is propagated back to the main applet,
          // which then will use the message of this exception
          // for creating an error form as second attempt.
          throw new Exception(msg1 + "\n" + msg2 + "\n" + msg3);
        }
        
      }
    }
    return node;
  } // GetNode

  
  
  
  
 /**
  *  Called by the DocumentProcessor from the method setLocalSchemaLocation().
  *  The passed modelNode contains the e-document, where this method
  *  should extract the schema location value, which is the value of
  *  the (anything:)schemaLocation attribute of the model's first child node. 
  */ 
  public static String GetSchemaLocationPathForModel( final XML_Node modelNode )
  {
    String schemaLocationPath = "";
    try
    {
      final XML_Node eDocumentNode = modelNode.getChildAt(0);
      if( eDocumentNode != null )
      {
        XML_Attribute attribute = null;
        XML_Attribute schemaLocationAttribute = null;
        for( int i=0; i < eDocumentNode.getNumberOfAttributes(); i++ )
        {
          attribute = eDocumentNode.getAttributeAt(i);
          if( attribute.getAttributeName().indexOf("schemaLocation") > 0 )
          {
            schemaLocationAttribute = attribute;
            break;        
          }
        }
        if( schemaLocationAttribute != null )
        {
          schemaLocationPath = schemaLocationAttribute.getAttributeValue(); 
        }
        else
        {
          System.out.println("*** GetSchemaLocationPathForModel(): No schemaLocation attribute found.");
        }    
      } // if
      else
      {
        System.out.println("*** GetSchemaLocationPathForModel() failed: modelNode has no children.");
      }
    }
    catch( Exception eee )
    {
      System.out.println(">*> GetSchemaLocationPathForModel(): No schemaLocation attribute found. (Exc)");
      System.out.println(">*> This is ok, because schemas are optional.");
    }
    return schemaLocationPath;
  }

  
  
  
  public static void SetSchemaLocationPathForModel( final XML_Node modelNode,
                                                    final String schemaLocationPath )
  {
    System.out.println("SetSchemaLocationPathForModel() to: " + schemaLocationPath );
    try
    {
      final XML_Node eDocumentNode = modelNode.getChildAt(0);
      if( eDocumentNode != null )
      {
        boolean valueHasBeenSet = false;
        XML_Attribute attribute = null;
        XML_Attribute schemaLocationAttribute = null;
        for( int i=0; i < eDocumentNode.getNumberOfAttributes(); i++ )
        {
          attribute = eDocumentNode.getAttributeAt(i);
          if( attribute.getAttributeName().indexOf("schemaLocation") > 0 )
          {
            schemaLocationAttribute = attribute;
            schemaLocationAttribute.setAttributeValue( schemaLocationPath );
            valueHasBeenSet = true;
            break;        
          }
        }
        if( !valueHasBeenSet )
        {
            System.out.println("*** SetSchemaLocationPathForModel(): No schemaLocation attribute found.");
            System.out.println("*** The value couldn't be set therefore.");
        }    
      } // if
      else
      {
        System.out.println("*** SetSchemaLocationPathForModel() failed: modelNode has no children.");
      }
    }
    catch( Exception eee )
    {
      System.out.println(">*> SetSchemaLocationPathForModel(): No schemaLocation attribute found. (Exc)");
    }
  } // SetSchemaLocationPathForModel


  public static void PrintError(String msg1, String msg2)
  {
    System.out.println("*** ");
    System.out.println("*** ");
    System.out.println("*** ");
    System.out.println("*** ");
    System.out.println("*** XMLPath");
    System.out.println("*** " + msg1 );
    System.out.println("*** " + msg2 );
    System.out.println("*** ");
    System.out.println("*** ");
    System.out.println("*** ");
    System.out.println("*** ");
    System.out.println("*** ");
  }

  
} // XMLPath




