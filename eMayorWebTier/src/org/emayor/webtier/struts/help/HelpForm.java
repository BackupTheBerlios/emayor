package org.emayor.webtier.struts.help;


import javax.servlet.http.HttpSession;

import org.emayor.webtier.shared.*;


public class HelpForm extends ExtendedActionForm
{

  private String helpTopicKey = "undefined";
  
  
  public HelpForm()
  {
  	super();
  	System.out.println("%%");
  	System.out.println("%% HelpForm INSTANCE CREATED.");
  	System.out.println("%%");
  } // constructor
  

  /**  
   * Called by the associated action, which passes the
   * municipality which it has resolved from the http request.
   */ 
   public void initializeAttibutes( final HttpSession session,
                                    final String languageParameterValue,
   		                            final String _helpTopicKey )
   {
   	super.initialize(session,languageParameterValue); // language initialization
    this.helpTopicKey = _helpTopicKey;         
   } // initializeAttibutes

   
   public String getHelpTopicKey()
   {
     return this.helpTopicKey;
   }
   
   
   public String getHelpTopic()
   {	
     return super.getTextFromResource(this.helpTopicKey);
   }
 
  
	
} // HelpForm
