package org.emayor.webtier.struts.FormTest;

public class EDocumentParameters
{

    private String eDocFileName;
    private String eDocumentName;
    private String citizenTemplateName;
    private String civilServantTemplateName;
    private String readOnlyTemplateName;
    

   public EDocumentParameters( String _eDocFileName,
                               String _eDocumentName,
                               String _citizenTemplateName,
                               String _civilServantTemplateName,
                               String _readOnlyTemplateName )
   {
     this.eDocFileName = _eDocFileName;
     this.eDocumentName = _eDocumentName;
     this.citizenTemplateName = _citizenTemplateName;
     this.civilServantTemplateName = _civilServantTemplateName;
     this.readOnlyTemplateName = _readOnlyTemplateName;   
   }


   public String getEdocfilename()
   {
    return this.eDocFileName;
   }
   
   
   public String getEdocumentname()
   {
     return this.eDocumentName;
   }

   
   public String getHasCitizenTemplate()
   {
     return (this.citizenTemplateName != null ) ? "true" : "false";
   }
   
   public String getCitizenTemplateName()
   {
     return this.citizenTemplateName;
   }
   
   public String getHasCivilServantTemplate()
   {
    return (this.civilServantTemplateName != null ) ? "true" : "false";
   }
   
   public String getCivilServantTemplateName()
   {
     return this.civilServantTemplateName;
   }
   
   public String getHasReadOnlyTemplate()
   {
    return (this.readOnlyTemplateName != null ) ? "true" : "false";
   }
   
   
   public String getReadOnlyTemplateName()
   {
     return this.readOnlyTemplateName;
   }
   
   
   

}
