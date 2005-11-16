package org.emayor.webtier.struts.errors;

import javax.servlet.http.HttpSession;

import org.emayor.webtier.shared.ExtendedActionForm;

//import org.emayor.webtier.municipalities.*;
//import org.emayor.webtier.shared.*;



public class ErrorPageForm  extends ExtendedActionForm
{

  private String errorDescription = "";

  public ErrorPageForm()
  {
    super(); // important
  }
	
  public void initialize( final HttpSession session,
                          final String languageParameterValue,
                          final String _errorDescription )
  {
    super.initialize(session,languageParameterValue); // language initialization
    this.errorDescription = _errorDescription;
  }


  
  
  public String getErrorDescription()
  {
    return this.errorDescription;
  }
 
  
  
  
} // ErrorPageForm



