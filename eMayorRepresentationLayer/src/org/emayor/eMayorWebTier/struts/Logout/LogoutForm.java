package org.emayor.eMayorWebTier.struts.Logout;

import javax.servlet.http.HttpSession;
import org.emayor.eMayorWebTier.Utilities.*;


public class LogoutForm extends ExtendedActionForm
{

   public LogoutForm()
   {
     super(); // important
     System.out.println("%%");
     System.out.println("%% LogoutForm INSTANCE CREATED.");
     System.out.println("%%");
   } // constructor

   
   
   /**  
    * Called by the associated action.
    */ 
    public void initializeAttibutes( final HttpSession session,
                                     final String languageParameterValue )
    {
      super.initialize(session,languageParameterValue); // language initialization
    } // initializeAttibutes

    
    
   /**
    *  Called by logout.jsp.
    *  Returns the message for the logout dialog in the selected natural language.
    */ 
    public String getLogout_Dialog_Message()
    {
      // Note: If the value is not found below, the key will be returned, so
      // we never get null.
      String message = super.getTextFromResource( TextResourceKeys.LogoutCloseBrowser );
      return message;
    }

    
    /**
     *  Called by logout.jsp.
     *  Returns the message for the logout html page in the selected natural language.
     */ 
     public String getLogout_Information_Message()
     {
       // Note: If the value is not found below, the key will be returned, so
       // we never get null.
       String message = super.getTextFromResource( TextResourceKeys.LogoutMessage );
       return message;
     }
    
    
   /**
    *  Called by logout.jsp
    *  Returns the text for "proceed" in the selected natural language.
    */ 
    public String getProceed_Message()
    {
      // Note: If the value is not found below, the key will be returned, so
      // we never get null.
      String message = super.getTextFromResource( TextResourceKeys.Proceed_Message );
      return message;
    }

    
}


