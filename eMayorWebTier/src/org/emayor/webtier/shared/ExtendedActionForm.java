package org.emayor.webtier.shared;


import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;



/**
 *  Basis class for actionforms, contains additional
 *  attributes.
 *  30.5.05  Joerg Plaz
 */
public class ExtendedActionForm extends ActionForm 
{

  // shortcuts == file names [*.properties] independent from the current language
  // taken from LanguageProperties
  public final static String EnglishIdentifier = LanguageProperties.EnglishIdentifier;
  public final static String GermanIdentifier  = LanguageProperties.GermanIdentifier;
  public final static String ItalianIdentifier = LanguageProperties.ItalianIdentifier;
  public final static String SpanishIdentifier = LanguageProperties.SpanishIdentifier;

  // The key used for storing the current language in the session object:
  private final static String WebtierLanguageParameterKey = "WebtierLanguageParameter";
  
  // The languageProperties object: Has application scope (Singleton)	
  protected LanguageProperties languageProperties;  

  // The current language: has session scope (== all subclasses of this class must
  // have session scope -> set this in the Struts console )
  private int currentLanguage = LanguageProperties.English;

  
  // The html template used by all JSP's for creating the HTML replies.
  HTMLTemplate htmlTemplate = null;
  
 
  
  public ExtendedActionForm()
  {
  	super();
  	this.languageProperties = LanguageProperties.getInstance();
  	this.setLanguageFromInt( LanguageProperties.English ); // default
  
    // Read the html template file used for all jsp generated html replies:
    // Here, the global template.htm directly in the MunicipalityInformation
    // folder will be read. Localized version is used later, as soon as
    // a municipality is chosen.
    this.htmlTemplate = HTMLTemplate.getInstance("");
  
  	System.out.println("%%");
  	System.out.println("%% ExtendedActionForm INSTANCE CREATED (with english as default)");
  	System.out.println("%%");  	
  } // constructor
  
 

  

  
 /**
  *  This is called as soon as a municipality hs been
  *  chosen. From then on, the localized template.htm from the
  *  folder of this municipality will be used.
  */ 
  public synchronized void loadLocalizedHTMLTemplateForMunicipality( final String municipalityName )
  {
    // This is handled by the singleton automatically, so just call it
    // passing the municipalityName: 
    this.htmlTemplate = HTMLTemplate.getInstance(municipalityName);  
  } // loadLocalizedHTMLTemplateForMunicipality
  

  
  
  
  /**
   *  The getPartitionX() methods are called by JSP's and are valid, after
   *  this.htmlTemplate has been initialized and has created the partitions.
   */ 
   public synchronized String getPartition0()
   {
     return this.htmlTemplate.getPartition0();
   }
   public synchronized String getPartition1()
   {
    return this.htmlTemplate.getPartition1();
   }
   public synchronized String getPartition2()
   {
    return this.htmlTemplate.getPartition2();
   }
   public synchronized String getPartition3()
   {
    return this.htmlTemplate.getPartition3();
   }
   public synchronized String getPartition4()
   {
    return this.htmlTemplate.getPartition4();
   }
  
  
  
  /**
   *  Set the language from the passed languageParameterValue and store it in the session object
   *  or read it from the session object, if languageParameterValue is null.
   * 
   *  Called by all actions from their execute methods.
   */
   public void initialize( final HttpSession session, String languageParameterValue )
   {
   	 if( languageParameterValue == null ) // get it from the session object, defaults to english
   	 {
        languageParameterValue = (String)session.getAttribute(WebtierLanguageParameterKey);
   	 	if( languageParameterValue == null )
   	 	{
          // Default to english and create the session object attribute:
          languageParameterValue = EnglishIdentifier;
   	 	  session.setAttribute(WebtierLanguageParameterKey,languageParameterValue);  
   	 	}
   	 }
   	 else
   	 {
   	 	// sync the session object attribute
 	 	session.setAttribute(WebtierLanguageParameterKey,languageParameterValue);     	 	
   	 }
   	 // Now, languageParameterValue is valid in any case, so set it for this form:
   	 this.setLanguage(languageParameterValue);
   } // initialize

   
  
  
  public int getLanguageAsInt() 
  {
  	//System.out.println("ExtendedActionForm: Returned the current language: " +
	//           this.languageProperties.getLanguageParameterStringForLanguage( currentLanguage ) );
    return this.currentLanguage;
  } // getLanguageAsInt

  
 /**
  *  Sets the passed language in this object (which has request scope)
  *  AND in the LanguageProperties, which has session scope,
  *  so the language setting is kept along the session. 
  */ 
  public void setLanguageFromInt(int _language)  
  {
  	//System.out.println("ExtendedActionForm: Current language set to: " +
	//           this.languageProperties.getLanguageParameterStringForLanguage( _language ) );
  	this.currentLanguage = _language;
  }

    
  /**
   *  Called by the actions, which pass the parameter value received
   *  in the http address as parameter.
   */ 
   public void setLanguage( final String parameterValue )
   {
   	 int language = this.languageProperties.getLanguageFromParameterString( parameterValue );
   	 this.setLanguageFromInt( language );
   }
   
   
  /**
   *  Called by the JSP's for creating http links.
   *  It returns the language parameter, which is independent of the current language.
   *  For german f.ex. always "ger" is returned. 
   */ 
   public String getLanguage()
   {
  	 int language = this.getLanguageAsInt();
     return this.languageProperties.getLanguageParameterStringForLanguage( language );	
   }

   
   
  /**
   *  Called by the JSP's for formatting tasks 
   */ 
   public int getNumberOfLanguages()
   {
     return this.languageProperties.getNumberOfLanguages();
   }
   

   
   public String getTextFromResource( final String key )
   {
   	 //System.out.println("getTextFromResource called for key: " + key );
   	 return this.languageProperties.getTextFromLanguageResource( key, this.currentLanguage );
   }

   
   
   public void reset(ActionMapping mapping, ServletRequest request) 
   {
     super.reset( mapping, request );
     try 
     {
       // Set the character encoding to UTF-8.
       request.setCharacterEncoding("UTF-8");
     }
     catch( Exception ex ) 
     {
     }
   }

   

   public void reset(ActionMapping mapping, HttpServletRequest request) 
   {
     super.reset (mapping, request);
     try 
     {
       // Set the character encoding to UTF-8.
       request.setCharacterEncoding("UTF-8");
     }
     catch( Exception ex ) 
     {
     }
   }
   
   
   
   
} // ExtendedActionForm





