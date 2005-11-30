package org.emayor.client.parser.xml;


import java.util.Vector;



public class XML_Node
{

  private String tagName = null;

  // This contains possible whitespaces after the tagname up to
  // where the first attribute declaration starts
  private String textAfterTagName = "";
  
  private String tagValue = ""; // no content == empty String
  
  private Vector attributes = null;  // -> memory,resource and time consumption:
                                     //    Only instanciate, if at least
                                     //    one attribute is added, otherwise null.
  private Vector children = null;    // -> memory,resource and time consumption:
                                     //    Only instanciate, if at least
                                     //    one attribute is added, otherwise null.
  private XML_Node parentNode = null;

  
  // text before node and after node contains optional
  // characters and comments. These are required for
  // producing the exactly same document from the tree
  // as it was before the tree had been produced.
  private String textBeforeNode = "";
  private String textAfterNode = "";

  
  // This flag is set, when the node is closed in one braket, like:
  // <node/>. Important for inversibility: backtransform(transfom)=unity
  // Not directly closed means, that this node is backwards
  // translated to <node></node>.
  private boolean isDirectlyClosed = false;
  

  
  public XML_Node( final XML_Node _parentNode  )
  {
    this.parentNode = _parentNode;
  } // Constructor


  
  public XML_Node( final XML_Node _parentNode,
                   final String _tagName )
  {
    this.parentNode = _parentNode;
    this.tagName = _tagName;
  } // Constructor


  public XML_Node( final XML_Node _parentNode,
                   final String _tagName,
                   final String _tagValue )
  {
    this.parentNode = _parentNode;
    this.tagName = _tagName;
    this.tagValue = _tagValue;
  } // Constructor


 
  
  /**
   *  Returns the xPath of this node.
   *  It will start with RootNode/eMayorForm1/...
   *  c.f. getXPath_From_eDocument
   *  delimiter is a slash character.
   */ 
   public String getXPath_FullVersion()
   {
     StringBuffer buf = new StringBuffer( this.getTagName() );
     XML_Node node = this;
     while( (node = node.getParentNode()) != null ) // go back the tree and cumulate names
     {
        buf.insert(0,"/");
        buf.insert(0,node.getTagName());
     }
     return buf.toString();
   }
  
    
   /**
    *  Returns the xPath of this node inside the eDocument.
    *  This will skip the RootNode/eMayorForm1/ names
    *  and start with the tagname of the eDocument.
    *  c.f. getXPath_FullVersion
    *  delimiter is a slash character.
    * 
    *  This version is compatible with the xpathes
    *  created by the validating SAX parser.
    */ 
    public String getXPath_From_eDocument()
    {
      StringBuffer buf = new StringBuffer( this.getTagName() );
      XML_Node node = this.getParentNode();
      // Note: The eMayorForm tagname is supposed to carry a version
      //       number. Actual name is eMayorForm1. But the concept is
      //       that one would use eMayorForm2 etc as soon as new versions
      //       get incompatible to previous ones.
      if( node != null )
      {
        while( !node.getTagName().equals("Model") ) // go back the tree and cumulate names
        {
           buf.insert(0,"/");
           buf.insert(0,node.getTagName());
           node = node.getParentNode();
           if( node == null ) break; // shouldn't happen, except on syntax errors of the forms
        }
      }  
      return buf.toString();
    }
  
  
  public XML_Node getParentNode()
  {
    return this.parentNode;
  }

  
  public void setIsDirectlyClosed( final boolean state )
  {
    this.isDirectlyClosed = state;  
  }
  
  
  public boolean getIsDirectlyClosed()
  {
    return this.isDirectlyClosed;
  }
  
    

  public void setTagName( final String name )
  {
    this.tagName = name;
  }                     
  
  
  public String getTagName()
  {
    return this.tagName;
  }
  
  
  public void setTagValue( final String value )
  {
    this.tagValue = value;
  }                     
  
  
  public String getTagValue()
  {
    //System.out.println("QQQQQQQQQQQ getTagValue() returns: " + this.tagValue );
    return this.tagValue;
  }


  public void setTextAfterTagName( final String txt )
  {
    this.textAfterTagName = txt;  
  }
  
  
  public String getTextAfterTagName()
  {
    return this.textAfterTagName;  
  }
  
  
  public void setTextBeforeNode( final String txt )
  {
    this.textBeforeNode = txt;
  }
  
  
  public String getTextBeforeNode()
  {
    return this.textBeforeNode;  
  }

  
  public void setTextAfterNode( final String txt )
  {
    this.textAfterNode = txt;  
  }
  
  public String getTextAfterNode()
  {
    return this.textAfterNode;  
  }

  
  public void addChild( final XML_Node child )
  {
    if( this.children == null ) this.children = new Vector();
    this.children.addElement( child );
  }                                       


  public int getNumberOfChildren()
  {   
    return (this.children == null) ? 0 : this.children.size();                   
  }

  public XML_Node getChildAt( int index )
  {
    return (XML_Node)this.children.elementAt( index );
  }
            
  
  public XML_Node getChildByName( final String tagName )
  {
    if( this.children == null ) return null;
    XML_Node childNode = null;
    for( int i=0; i < this.children.size(); i++ )
    {
      XML_Node c = (XML_Node)this.children.elementAt(i);
      if( c.getTagName().equals(tagName) )
      {
        childNode = c;
        break;
      }
    }                         
    return childNode;
  }

  
  
  
  public void removeChildNodeAt( final int index )
  {
    if( index < this.children.size() )
    {
      this.children.removeElementAt(index);   
    }
    else
    {
      System.out.println("*** XML_Node: Cannot remove node at index " + index +
                         " because only " + this.children.size() +
                         " nodes are present.");    
    }
  } // removeChildNodeAt
  
  

  public void addAttribute( final XML_Attribute attribute )
  {
    if( this.attributes == null ) this.attributes = new Vector();
    this.attributes.addElement( attribute );
  }                                       


  public int getNumberOfAttributes()
  {   
    return (this.attributes == null) ? 0 : this.attributes.size();                   
  }
  

  public XML_Attribute getAttributeAt( int index )
  {
    return (XML_Attribute)this.attributes.elementAt( index );
  }



  public String getAttributeValueForName( final String attributeName )
  {                  
    if( this.attributes == null ) return null;
    String value = null;
    for( int i=0; i < this.attributes.size(); i++ )
    {
      XML_Attribute a = (XML_Attribute)this.attributes.elementAt(i);
      if( a.getAttributeName().equals(attributeName) )
      {
        value = a.getAttributeValue();
        break;
      }
    }
    return value;
  } // getAttributeValueForName



  
  public String[] getAttributeValuesForNameStartingWith( final String attributeNameStart )
  {                  
    if( this.attributes == null ) return null;
    Vector values = new Vector();
    for( int i=0; i < this.attributes.size(); i++ )
    {
      XML_Attribute a = (XML_Attribute)this.attributes.elementAt(i);
      if( a.getAttributeName().startsWith(attributeNameStart) )
      {
        String value = a.getAttributeValue();
        values.addElement(value);
      }
    }
    if( values.size() == 0 ) return null;
    String[] valuesArray = new String[values.size()];
    values.copyInto(valuesArray);
    return valuesArray;
  } // getAttributeValueForName
 
  
  

  public String getAttributeDescriptions()
  {
    if( this.attributes == null ) return "Node has no attributes set.";
    StringBuffer attributeBuffer = new StringBuffer();
    for( int i=0; i < this.attributes.size(); i++ )
    {
      XML_Attribute att = this.getAttributeAt(i);
      attributeBuffer.append(" Attribute Name= ");
      attributeBuffer.append(att.getAttributeName());
      attributeBuffer.append(" Value= ");
      attributeBuffer.append(att.getAttributeValue());
      attributeBuffer.append("  ");
    }
    return attributeBuffer.toString();
  }
                                      
                                      

} // Node                             




   
