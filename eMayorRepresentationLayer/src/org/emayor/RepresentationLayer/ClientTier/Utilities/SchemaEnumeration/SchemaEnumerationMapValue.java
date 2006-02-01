package org.emayor.RepresentationLayer.ClientTier.Utilities.SchemaEnumeration;

public class SchemaEnumerationMapValue
{

  private String[] enumerationValues;

  public SchemaEnumerationMapValue( String[] _enumerationValues )
  {                                  
    this.enumerationValues = _enumerationValues;
  } // Constructor


  public String[] getEnumerationValues()
  {
    return this.enumerationValues;
  }


}
