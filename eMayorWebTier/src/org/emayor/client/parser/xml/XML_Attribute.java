package org.emayor.client.parser.xml;



public class XML_Attribute
{
  
  private String attributeName;
  private String attributeValue;

  
  // text after the attribute contains optional
  // characters and comments. These are required for
  // producing the exactly same document from the tree
  // as it was before the tree had been produced.
  private String textAfterAttribute = "";
  
  
  public XML_Attribute( final String _attributeName,
                        final String _attributeValue )
  {
    this.attributeName  = _attributeName;
    this.attributeValue = _attributeValue;
  } // Constructor



  public String getAttributeName()
  {
    return this.attributeName;
  }



  public String getAttributeValue()
  {
    return this.attributeValue;
  }

  
  
  public void setAttributeValue( final String value )
  {
    this.attributeValue = value; 
  }
  

  public void setTextAfterAttribute( final String txt )
  {
    this.textAfterAttribute = txt;
  }
  
  
  public String getTextAfterAttribute()
  {
    return this.textAfterAttribute;  
  }
  
  
} // XML_Attribute
