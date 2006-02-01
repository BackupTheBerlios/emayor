package org.emayor.eMayorWebTier.struts.ClientSoftwareCheck;

import javax.servlet.http.HttpSession;

import org.emayor.eMayorWebTier.Utilities.*;
import org.emayor.eMayorWebTier.municipalities.*;

/**
 *  The ActionForm belonging to SoftwareCheckAction.
 *  The Struts data model. Data here is set by the
 *  SoftwareActionForm and read by the serviceCheck.jsp
 *  class afterwards.
 * 
 *  Created Dec 2005   jpl
 */


public class SoftwareCheckForm extends ExtendedActionForm
{

  private String municipalityNameKey = "undefined_key";
  private String loginPageURL = "";
  

  
  public SoftwareCheckForm()
  {
    super(); // Important: Call the inherited ExtendedActionForm constructor.
    System.out.println("%%");
    System.out.println("%% SoftwareCheckForm INSTANCE CREATED.");
    System.out.println("%%");
  }

  
  /**  
   * Called by the associated action, which passes the
   * municipality which it has resolved from the http request.
   */ 
   public void initializeAttibutes( final HttpSession session,
                                    final String languageParameterValue,
                                    final String _municipalityNameKey,
                                    final String _loginPageURL )
   {
     super.initialize(session,languageParameterValue); // language initialization
     this.municipalityNameKey = _municipalityNameKey;
     this.loginPageURL = _loginPageURL;
   } // initializeAttibutes

   
   
   
   public String getMunicipalityNameKey()
   {
     return this.municipalityNameKey;
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
       System.out.println("*** AuthenticationForm.getNameOfMunicipality(): name of municipality is not defined.");
       name = "undefined";
     }
     return name;     
   }
   

   

   public String getHelpTopicKey()
   {
     return TextResourceKeys.HelpTopic_MunicipalityKey;
   }
  

   
   public String getMunicipalityURL()
   {
     StringBuffer municipalityURL = new StringBuffer();
     municipalityURL.append("municipality.do?do=showIndex");
     municipalityURL.append("&municipalityNameKey=");
     municipalityURL.append(this.municipalityNameKey);
     municipalityURL.append("&language=");
     municipalityURL.append(super.getLanguage());     
     return municipalityURL.toString();
   }
   
   
   
   public String getLoginPageURL()
   {
     return this.loginPageURL;
   }
   

   
  /**
   *   Returns the MD5 checksum list of the client software library files
   *   on the server.
   */ 
   public String getMd5CheckSumList()
   {
     // Note: The ClientSoftwareAdministration singleton already has been created
     //       on the first call of the LoginAction since JBoss restart.
     //       Therefore it already has set the instance and server port number
     //       so we just can pass zero here, which has no effect.
     ClientSoftwareAdministration ca = ClientSoftwareAdministration.getInstance(0);
     return ca.getClientLibraryMD5CheckSumList();
   } // getMd5CheckSum

   
   public String getClientSoftwareChecking_Message()
   {
     return super.getTextFromResource( TextResourceKeys.ClientSoftwareChecking_Message );
   }
   public String getClientSoftwareIsUpToDate_Message()
   {
    return super.getTextFromResource( TextResourceKeys.ClientSoftwareIsUpToDate_Message );
   }
   public String getClientSoftwareMustBeInstalled_Message()
   {
    return super.getTextFromResource( TextResourceKeys.ClientSoftwareMustBeInstalled_Message );
   }
   public String getClientSoftwareMustBeUpdated_Message()
   {
    return super.getTextFromResource( TextResourceKeys.ClientSoftwareMustBeUpdated_Message );
   }
   public String getClientSoftwareHasBeenInstalled_Message()
   {
    return super.getTextFromResource( TextResourceKeys.ClientSoftwareHasBeenInstalled_Message );
   }
   public String getClientSoftwareHasBeenUpdated_Message()
   {
    return super.getTextFromResource( TextResourceKeys.ClientSoftwareHasBeenUpdated_Message );
   }
   public String getClientBrowserRestart_Message()
   {
    return super.getTextFromResource( TextResourceKeys.ClientBrowserRestart_Message );
   }
   public String getProceed_Message()
   {
    return super.getTextFromResource( TextResourceKeys.Proceed_Message );
   }
   public String getCancel_Message()
   {
    return super.getTextFromResource( TextResourceKeys.Cancel_Message );
   }


   
   
   /**
    *  For the ProcessStep Applet, three methods (called by JSP's) are required here:
    *  getProcessStepTextList(), processStepDescriptionList() and getProcessStepIndex()
    * 
    *  This method returns a ";" separated list of texts for the
    *  process step list view.
    * 
    *  The texts are read from the textresource and returned in the
    *  selected natural language of the client.
    * 
    *  @return comma separated list of texts for each step.
    */ 
    public String getProcessStepTextList()
    {
      StringBuffer textList = new StringBuffer();
      textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_Login) );
      textList.append( ";" );
      textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_Authentication) );
      textList.append( ";" );
      textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_SoftwareCheck) );
      textList.append( ";" );
      textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_MunicipalityIndex) );
      return textList.toString();
    }
    

   /**
    *  For the ProcessStep Applet, three methods (called by JSP's) are required here:
    *  getProcessStepTextList(), processStepDescriptionList() and getProcessStepIndex()
    * 
    *  This method returns a ";" separated list of descriptions for the
    *  process step list view.
    * 
    *  The texts are read from the textresource and returned in the
    *  selected natural language of the client.
    * 
    *  @return comma separated list of texts for each step.
    */ 
    public String getProcessStepDescriptionList()
    {
      StringBuffer textList = new StringBuffer();
      textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_Login_Description) );
      textList.append( ";" );
      textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_Authentication_Description) );
      textList.append( ";" );
      textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_SoftwareCheck_Description) );
      textList.append( ";" );
      textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_MunicipalityIndex_Description) );
      return textList.toString();
    }
    
    
   /**
    *  For the ProcessStep Applet, three methods (called by JSP's) are required here:
    *  getProcessStepTextList(), processStepDescriptionList() and getProcessStepIndex()
    * 
    *  This method returns the index for the
    *  current process step. The associated entry shall be highlighted
    *  and means the current step in the list. Steps on the left are past
    *  steps, and steps on the right are to be carried out in the future. 
    *  @return index of current step (zero based)
    */ 
    public String getProcessStepIndex()
    {
      return "2"; // The third entry = the software check step
    }
   
   
   
   
} // SoftwareCheckForm
