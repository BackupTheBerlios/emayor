package org.emayor.eMayorWebTier.struts.Service;



/**
 *  May 20, 2005 created, Joerg Plaz
 */



public class EDocumentLibrary
{

  private static EDocumentLibrary Instance = null;
  
  private EDocumentLibrary()
  {   
  }

  
  
  
  
   
  /**
   * The only public method here: 
   * @return EDocumentLibrary (singleton)
   */ 
   public static EDocumentLibrary GetInstance()
   {
     if( Instance == null )
       Instance = new EDocumentLibrary();
     return Instance;
   }
  
  
}
