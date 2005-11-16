package org.emayor.webtier.struts.Service;


/**
 *  May 20, 2005 created, Joerg Plaz
 */






public class XFormTemplatesLibrary
{

  private static XFormTemplatesLibrary Instance;

  private XFormTemplatesLibrary()
  {    
  }
  
 
  
  
  
  
  
  /**
   * The only public method here: 
   * @return XFormTemplatesLibrary (singleton)
   */ 
   public static XFormTemplatesLibrary GetInstance()
   {
     if( Instance == null )
       Instance = new XFormTemplatesLibrary();
     return Instance;
   }
   
 
  
} // XFormTemplatesLibrary
