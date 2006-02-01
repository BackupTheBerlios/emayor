package org.emayor.RepresentationLayer.Shared.Utilities;

 /**
  *  Used for return values of methods, which
  *  return a string and success= true or false.
  * 
  *  If the boolean attribute is false, the string contains an error description.
  *  If the boolean attribute is true, the string contains the desired result.
  * 
  *  jpl
  * 
  */


public class BooleanString
{

  private String value = "";
  private boolean isTrue = true;

  public BooleanString( final String _value, final boolean _isTrue )
  {
    this.value = _value;
    this.isTrue = _isTrue;
  }
  
  public void setValue( final String newValue )
  {
    this.value = newValue;
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  public void setIsTrue( final boolean nowIsTrue )
  {
    this.isTrue = nowIsTrue;
  }
  
  public boolean getIsTrue()
  {
    return this.isTrue;
  }

  
}

