package org.emayor.webtier.shared;

  /**
   *  All JSP's should use text resource keys using constants
   *  from this class.
   *  Prevents one of the ..pitfalls (antipatterns): Do not use hardcoded keys. 
   */



public class TextResourceKeys 
{

 public final static String MunicipalitiesPageTitle = "PageTitle.Municipalities";	
 public final static String ErrorPageTitle = "PageTitle.ErrorPageTitle";	

 public final static String AuthenticationInformation="Message.AuthenticationInformation";
 public final static String AuthenticationFailureInformation="Message.AuthenticationFailureInformation";
 public final static String NumberOfAuthenticationFailures="Message.NumberOfAuthenticationFailures";
 
 public final static String JavaPluginIsUpToDate = "Information.JavaPluginIsUpToDate";
 public final static String JavaPluginUpdate = "Information.JavaPluginUpdate";
 public final static String SessionHasExpiredInformation="Message.SessionHasExpiredInformation";
 
 public final static String TryAgain="Message.TryAgain";
 
 public final static String MessageAboutEMayorText = "Message.AboutText";
 
 public final static String Municipalities = "Title.Municipalities";	
 public final static String AvailableMunicipalities = "Title.AvailableMunicipalities";	
 public final static String Help = "Title.Help";	
 
 public final static String TitleCitizen="Title.Citizen";
 public final static String TitleCivilServant="Title.CivilServant";
 
 public final static String AbouteMayor = "Footer.AbouteMayor";	
 
 public final static String Logout = "Link.Logout";
 
 // services
 public final static String AvailableServices   = "Services.AvailableServices"; 
 public final static String BrowseAvailableDocumentsServiceKey = "Services.BrowseAvailableDocuments";
 public final static String WaitForServiceRequestResponseKey   = "Services.WaitForServiceRequestResponse";
 // Note: The BrowseAvailableDocumentsService is not called directly on the SH.

// Custom services [This must be solved in future - these entries couple customization to java programming]
 public final static String RequestResidenceCertificationServiceKey       = "Services.RequestResidenceCertification";
 public final static String RequestResidenceInformationServiceKey         = "Services.RequestResidenceInformation";
 public final static String RequestFamilyResidenceCertificationServiceKey = "Services.RequestFamilyResidenceCertification";
 public final static String ChangeAccountDataServiceKey                   = "Services.ChangeAccountData";
 public final static String TaxManagementActivationRequestKey             = "Services.TaxManagementActivationRequest";
 public final static String UserRegistrationRequestKey                    = "Services.UserRegistrationRequest";

 
 
 
 // language shortcuts
 public final static String ShortCutEnglish = "Shortcut.English";	
 public final static String ShortCutGerman  = "Shortcut.German";	
 public final static String ShortCutItalian = "Shortcut.Italian";	
 public final static String ShortCutSpanish = "Shortcut.Spanish";	

 // full language names
 public final static String English = "Name.English";	
 public final static String German  = "Name.German";	
 public final static String Italian = "Name.Italian";	
 public final static String Spanish = "Name.Spanish";	
 
 // repository
 public static final String RepositoryPageTitle      = "Repository.PageTitle"; 
 public static final String RepositoryDocument       = "Repository.Document";
 public static final String RepositoryDateOfRequest  = "Repository.DateOfRequest";
 public static final String RepositoryAvailableUntil = "Repository.AvailableUntil";
 public static final String RepositoryDownload       = "Repository.Download";
 
 public static final String RepositoryProcessButtonText = "Repository.ProcessButtonText"; 
 
 
 public static final String ResidenceCertificate = "Repository.ResidenceCertificate";
 public static final String ServiceFamilyStatusCertificate = "Repository.ServiceFamilyStatusCertificate";
 public static final String RequestForGeneralInformationAboutTaxes = "Repository.RequestForGeneralInformationAboutTaxes";
 public static final String NegativeResidenceCertificate = "Repository.NegativeResidenceCertificate";

 
 
 public static final String Status_Completed          = "Repository.Status_Completed";
 public static final String Status_IsProcessed        = "Repository.Status_IsProcessed";
 public static final String Status_ProblemsHaveArised = "Repository.Status_ProblemsHaveArised";
 
 public static final String Status_NoRemarks             = "Repository.Status_NoRemarks";
 public static final String Status_RemarksAboutEmailInfo = "Repository.Status_RemarksAboutEmailInfo";
 
 
 public static final String Name_Requester = "Name.Requester"; 
 
 // login
 public static final String LoginInformation = "Login.Information";
 public static final String LoginPassword = "Login.Password";
 
 // Tags used for semantics when these keys are transported:
 public static final String ServiceNameKeyTag      = "serviceNameKey";
 public static final String MunicipalityNameKeyTag = "municipalityNameKey";
 public static final String DocumentTitleKeyTag    = "documentTitleKey";
 public static final String HelpTopicKeyTag        = "helpTopicKey";
 public static final String DocumentIndexKeyTag    = "documentIndex";
 
 // Service Request
 public static final String ServiceRequestInformation = "ServiceRequest.Information";
 
 // Service Acknowledgement
 public static final String ServiceAcknowledgementInformation1 = "ServiceAcknowledgement.Information1";
 public static final String ServiceAcknowledgementInformation2 = "ServiceAcknowledgement.Information2";
 // Service Failure
 public static final String ServiceFailureInformation1 = "ServiceFailure.Information1";
 public static final String ServiceFailureInformation2 = "ServiceFailure.Information2";
 
 public static final String ServiceCouldNotBeStartedInformation = "ServiceFailure.ServiceIsNotAvailable";
 
 // Help topics:
 public static final String HelpTopic_MunicipalityListKey = "HelpTopic.MunicipalityList";
 public static final String HelpTopic_MunicipalityKey = "HelpTopic.Municipality";
 public static final String HelpTopic_ServiceKey = "HelpTopic.Service";
 public static final String HelpTopic_DocumentDownload = "HelpTopic.DocumentDownload";
  
 
 
 
} // TextResourceKeys




