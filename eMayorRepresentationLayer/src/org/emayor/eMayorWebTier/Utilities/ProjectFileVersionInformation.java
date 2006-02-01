package org.emayor.eMayorWebTier.Utilities;

  /**
   *  Singleton
   * 
   *  A simple container, which holds the information,
   *  if all support files, property files etc. used
   *  by the webtier are uptodate or not.
   * 
   *  If files are not up to date, the webtier should
   *  not call any jsp's, because the operation is not
   *  guaranteed, and exceptions can result in runtime.
   * 
   *  Any objects, which set the attribute filesAreUptodate to false,
   *  should inform the user before that call.
   * 
   *  User = system administrator of the JBoss server.
   *
   *  5.Nov 2005,  jpl 
   */


public class ProjectFileVersionInformation
{

  // Version checking:
  // This value must be contained in all files, which are 
  // tested to have an uptodate version.
  public static final String WebTierFilesVersion="1.4";


  private static ProjectFileVersionInformation Instance = null;
  private boolean filesAreUptodate = true;

  private StringBuffer failureDescription = new StringBuffer(""); 
  // is filled with failure descriptions when signalizeFilesAreNotUptodate is called.
  
  
  private ProjectFileVersionInformation()
  {  
  }


  
  public void signalizeFilesAreNotUptodate( String failureDescriptionLine )
  {
    // Make it HTML compatible: replace newline characters with <BR>
    failureDescriptionLine = failureDescriptionLine.replaceAll("\n","<br>");
    this.filesAreUptodate = false;
    this.failureDescription.append(failureDescriptionLine);
    this.failureDescription.append("<p>");   
  }

  
  public boolean getFilesAreUptodate()
  {
    return this.filesAreUptodate;
  }
 
  
  public String getFailureDescriptionText()
  {
    return this.failureDescription.toString();
  }
  
  
  public static ProjectFileVersionInformation GetInstance()
  {
    if( Instance == null ) Instance = new ProjectFileVersionInformation();
    return Instance;
  }

} // ProjectFileVersionInformation
