package org.emayor.webtier.struts.Service;

/**
 *  May 20, 2005 created, Joerg Plaz
 */

import javax.servlet.http.HttpSession;

import org.emayor.webtier.municipalities.MunicipalitiesManager;
import org.emayor.webtier.municipalities.Municipality;
import org.emayor.webtier.shared.*;


public class DocumentRequestForm extends ExtendedActionForm
{

  private String municipalityNameKey = "undefined";
  private String documentXForm = "undefined XFORM";

  
  public DocumentRequestForm()
  {
    super();
  }

  
  public void initialize( final HttpSession session,
                          final String languageParameterValue )
  {
    super.initialize(session,languageParameterValue); // language initialization
  }
  
  
  public void setDocumentXFORM( String xform )
  {
    this.documentXForm = xform;
  }
  
  
  public String getDocumentXFORM()
  {
    return this.documentXForm;    
  }
  
  
  public String getNameOfMunicipality()
  {
    MunicipalitiesManager mm = MunicipalitiesManager.GetInstance();
    Municipality municipality = mm.getMunicipalityByKey(this.municipalityNameKey);
    String name = null;
    if( municipality != null )
    {
      name = municipality.getMunicipalityNameForLanguage( super.getLanguage() );
    }
    // don't return null, otherwise calling JSP's run into exceptions:
    if( name == null ) 
    {
      System.out.println("*** DocumentRequestForm.getNameOfMunicipality(): name of municipality is not defined.");
      name = "undefined";
    }
    return name;
  }

  
  
  public void setNameOfMunicipality( String parmName )
  {
    this.municipalityNameKey = parmName;
  }
  
  
} // DocumentRequestForm

