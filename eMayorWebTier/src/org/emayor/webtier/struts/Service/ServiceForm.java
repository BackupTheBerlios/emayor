package org.emayor.webtier.struts.Service;

/**
 *  May 15, 2005 created, Joerg Plaz
 */


import java.util.HashMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionMapping;
import org.emayor.webtier.municipalities.MunicipalitiesManager;
import org.emayor.webtier.municipalities.Municipality;
import org.emayor.webtier.municipalities.MunicipalityService;
import org.emayor.webtier.shared.*;

import org.emayor.servicehandling.kernel.Task;



public class ServiceForm extends ExtendedActionForm
{
  private String serviceIdentifier = "undefined";
  private String municipalityNameKey = "undefined";

  
  private Repository repository = null;
  
  
  private ServiceTaskInformation serviceTaskInformation = null;
  
  private String waitPageRedirectURL = null;
  
  // The eMayorForm, which will be sent to the client applet on request.
  private String eMayorForm = "";  // default - important for expectedAppletHeight()
  
  
  private String xmlDocument = null;
  
  
  
  public ServiceForm()
  {
  	super(); // important
  	System.out.println("%%");
  	System.out.println("%% ServiceForm INSTANCE CREATED (with english as default)");
  	System.out.println("%%");  	  	
  }
  
  
  public void initialize( final HttpSession session,
                          final String languageParameterValue,
  		                  final String _serviceIdentifier,
						  final String _municipalityNameKey )
  {
  	super.initialize(session,languageParameterValue); // language initialization
    this.serviceIdentifier = _serviceIdentifier;
    this.municipalityNameKey = _municipalityNameKey;
    System.out.println("ServiceForm initialized with serviceIdentifier = " + this.serviceIdentifier );
  }

  
  
  public void setXMLDocument( final String xmlDoc )
  {
    this.xmlDocument = xmlDoc;
  }
  
  
  
  public String getXMLDocument()
  {
    return this.xmlDocument;
  }
  
  
  
 /**
  *  Set by the serviceAction, before it returns the page with the
  *  eMayor client applet to the client.
  */ 
  public void setEmayorform( final String theForm )
  {
  	this.eMayorForm = theForm;
  }
  
 /**
  *  Called by the sendEMayor.jsp, which runs after the applet has started.
  *  It returns the string containing the complete eMayorForm.
  */ 
  public String getEmayorform()
  {
  	return this.eMayorForm;
  }
  
  
  /**
   *   Set as input for the Wait.. jsp
   */ 
   public void setWaitPageRedirectURL( String url )
   {
   	this.waitPageRedirectURL = url;  	
   }
  
   
   
   /**
    *   Used by the Wait.. jsp
    */ 
   public String getWaitPageRedirectURL()
   {
     return this.waitPageRedirectURL;
   }
 
  
   
  public void setServiceTaskInformation( ServiceTaskInformation _serviceTaskInformation )
  {
  	this.serviceTaskInformation = _serviceTaskInformation;
  }

  
  
  
  public void setRepository( Repository _repository )
  {
    this.repository = _repository; 
  }
  
  
  
  public RepositoryDocument[] getRepositoryDocuments()
  {
    if( this.repository == null )
    {
      System.out.println("ServiceForm: Returning 0 documents. Repository is null."); 
      return new RepositoryDocument[0];
    }
    else
    {
      RepositoryDocument[] docs = this.repository.getDocuments(); 
      System.out.println("ServiceForm: Returning " + docs.length + " documents."); 
      return docs;
    } 
  }

  
  
  
  public RepositoryDocument getRepositoryDocumentAt( final int index ) throws Exception
  {
  	return this.repository.getDocumentAt( index );
  }
  
 
  
  
 /**
  *  Returns the name of the municipality in the current language.
  */ 
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
      System.out.println("*** ServiceForm.getNameOfMunicipality(): name of municipality is not defined.");
      name = "undefined";
    }
    return name;
  }

  
 /**
  *  Returns the name of the service in the current language.
  */ 
  public String getNameOfService()
  {
    String serviceName = "<undefined>"; 
    try
    {
      System.out.println("ServiceForm.getNameOfService() called where: ");
      System.out.println("> this.municipalityNameKey = " + this.municipalityNameKey );
      System.out.println("> this.serviceIdentifier = " + this.serviceIdentifier );
      System.out.println("> super.getLanguage() = " + super.getLanguage() );
      MunicipalitiesManager mm = MunicipalitiesManager.GetInstance();
      Municipality municipality = mm.getMunicipalityByKey(this.municipalityNameKey);
      MunicipalityService service = municipality.getServiceByIdentifier( this.serviceIdentifier );
      if( service != null )
      {
        serviceName = service.getServiceNameForLanguage( super.getLanguage() );
      }
      else
      {
      	serviceName = "Undefined: Name of service";
      }
    }
    catch( Exception e )
    {
      System.out.println("************* Catched exception in ServiceForm.getNameOfService():");
      e.printStackTrace();
    }
    return serviceName;
  }
  

  
  public String getServiceNameKey()
  {
    return this.getServiceIdentifier();
  }

  
  public String getServiceIdentifier()
  {
  	return this.serviceIdentifier;
  }

  
  public void setServiceIdentifier( final String identifier )
  {
    this.serviceIdentifier = identifier;
  
    System.out.println("ServiceForm setServiceIdentifier() called with arg= " + this.serviceIdentifier );
  }
  

  
  public String getMunicipalityNameKey()
  {
  	return this.municipalityNameKey;
  }
  
  

  
  public HashMap getServiceParametersWithoutLanguage()
  {
	HashMap serviceParameters = new HashMap();
	serviceParameters.put( TextResourceKeys.ServiceNameKeyTag,this.serviceIdentifier);
	serviceParameters.put( TextResourceKeys.MunicipalityNameKeyTag,this.municipalityNameKey);
	return serviceParameters;
  }
  
  

  public String getTaskInformation()
  {
    String xmlDoc = serviceTaskInformation.getXMLDocument();
    String responseDoc = serviceTaskInformation.getDocumentResponse();
    return "xmldoc=\n" + xmlDoc + "\nreponseDoc=\n" + responseDoc;
  }

  
  
  public Task getTask()
  {
  	if( this.serviceTaskInformation == null )
  	{
  		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ getTask(): serviceTaskInformation is NULL.");
  	}
  	else
  	{
  	  	if( this.serviceTaskInformation.getTask() == null )
  	  		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ getTask(): serviceTaskInformation.getTask() is NULL.");
  		
  	}  	
  	return this.serviceTaskInformation.getTask();
  }
  
  
  public String getTaskInformationId()
  {
  	return this.serviceTaskInformation.getTaskId();
  }
  
  
  public String getTaskInformationStatus()
  {
  	return this.serviceTaskInformation.getTaskStatus();
  }

  
  public String getTaskXMLDocument()
  {
  	return this.serviceTaskInformation.getXMLDocument();
  }
  
  
  public ServiceTaskInformation getServiceTaskInformation()
  {
    return this.serviceTaskInformation;
  }
  
 
  
  
  /**
   *  Returns the eMayorForms applet property file in the current language.
   *  This is called by a jsp, envoked by the eMayor client applet over the net.
   */ 
   public String getAppletPropertyFile()
   {
     MunicipalitiesManager mm = MunicipalitiesManager.GetInstance();
     Municipality municipality = null;
     if( mm != null )
     {
       municipality = mm.getMunicipalityByKey(this.municipalityNameKey);
     }
     else
     {
       System.out.println("*** ServiceForm.getAppletPropertyFile(): Fatal error:");
       System.out.println("*** MunicipalitiesManager is null.");
     }
     return municipality.getAppletPropertyFileForLanguage( super.getLanguage() );
   }
  

   
   
      
   /**
    *  Returns the enumeration property file.
    *  This is called by a jsp, envoked by the eMayor client applet over the net.
    */ 
    public String getEnumerationProperties()
    {
      MunicipalitiesManager mm = MunicipalitiesManager.GetInstance();
      Municipality municipality = null;
      if( mm != null )
      {
        municipality = mm.getMunicipalityByKey(this.municipalityNameKey);
      }
      else
      {
        System.out.println("*** ServiceForm.getEnumerationProperties(): Fatal error:");
        System.out.println("*** MunicipalitiesManager is null.");
      }
      return municipality.getEnumerationProperties();
    }
   

    
    
    /**
     *  Returns the expected applet height based on the
     *  member attribute testEMayorForm. 
     *  It is called by JSP's carrying an applet directive.
     */ 
     public String getExpectedAppletHeight()
     {
       int expectedAppletHeight = 1400; // default return value
       
       return String.valueOf(expectedAppletHeight);
     }

     
         
     
    public String getHelpTopicKey()
    {
  	  return TextResourceKeys.HelpTopic_ServiceKey;
    }
  
  
  
} // ServiceForm

