/*
 * Generated by XDoclet - Do not edit!
 */
package org.emayor.servicehandling.beans;

/**
 * CMP layer for PlatformConfigurationEntity.
 */
public abstract class PlatformConfigurationEntityCMP
   extends org.emayor.servicehandling.beans.PlatformConfigurationEntityEJB
   implements javax.ejb.EntityBean
{

   /**
    * Generated ejbPostCreate for corresponding ejbCreate method.
    *
    * @see #ejbCreate(java.lang.String configID)
    */
   public void ejbPostCreate(java.lang.String configID)
   {
   }

   public void ejbLoad() throws javax.ejb.EJBException, java.rmi.RemoteException
   {
      super.ejbLoad();
   }

   public void ejbStore() throws javax.ejb.EJBException, java.rmi.RemoteException
   {
         super.ejbStore();
   }

   public void ejbActivate() throws javax.ejb.EJBException, java.rmi.RemoteException
   {
      super.ejbActivate();
   }

   public void ejbPassivate() throws javax.ejb.EJBException, java.rmi.RemoteException
   {
      super.ejbPassivate();

   }

   public void setEntityContext(javax.ejb.EntityContext ctx) throws javax.ejb.EJBException, java.rmi.RemoteException
   {
      super.setEntityContext(ctx);
   }

   public void unsetEntityContext() throws javax.ejb.EJBException, java.rmi.RemoteException
   {
      super.unsetEntityContext();
   }

   public void ejbRemove() throws javax.ejb.EJBException, java.rmi.RemoteException, javax.ejb.RemoveException
   {
      super.ejbRemove();

   }

   public abstract java.lang.String getConfigID() ;

   public abstract void setConfigID( java.lang.String configID ) ;

   public abstract java.lang.String getEMayorPlatformInstanceID() ;

   public abstract void setEMayorPlatformInstanceID( java.lang.String EMayorPlatformInstanceID ) ;

   public abstract java.lang.String getEMayorMunicipalityName() ;

   public abstract void setEMayorMunicipalityName( java.lang.String EMayorMunicipalityName ) ;

   public abstract java.lang.String getEMayorOperatingMode() ;

   public abstract void setEMayorOperatingMode( java.lang.String EMayorOperatingMode ) ;

   public abstract java.lang.String getEMayorOperatingModeForward() ;

   public abstract void setEMayorOperatingModeForward( java.lang.String EMayorOperatingModeForward ) ;

   public abstract java.lang.String getEMayorTmpDir() ;

   public abstract void setEMayorTmpDir( java.lang.String EMayorTmpDir ) ;

   public abstract java.lang.String getEMayorOperationModeEmail() ;

   public abstract void setEMayorOperationModeEmail( java.lang.String EMayorOperationModeEmail ) ;

   public abstract java.lang.String getEMayorEmailTestUserAddress() ;

   public abstract void setEMayorEmailTestUserAddress( java.lang.String EMayorEmailTestUserAddress ) ;

   public abstract java.lang.String getForwardManagerQueueHost() ;

   public abstract void setForwardManagerQueueHost( java.lang.String forwardManagerQueueHost ) ;

   public abstract java.lang.String getForwardManagerQueueName() ;

   public abstract void setForwardManagerQueueName( java.lang.String forwardManagerQueueName ) ;

   public abstract java.lang.String getEMayorOperationModeContentRouting() ;

   public abstract void setEMayorOperationModeContentRouting( java.lang.String EMayorOperationModeContentRouting ) ;

   public abstract java.lang.String getForwardManagerTestLocalMunicipalityName() ;

   public abstract void setForwardManagerTestLocalMunicipalityName( java.lang.String forwardManagerTestLocalMunicipalityName ) ;

   public abstract java.lang.String getForwardManagerTestRemoteMunicipalityName() ;

   public abstract void setForwardManagerTestRemoteMunicipalityName( java.lang.String forwardManagerTestRemoteMunicipalityName ) ;

   public abstract java.lang.String getForwardManagerTestLocalMunicipalityAddress() ;

   public abstract void setForwardManagerTestLocalMunicipalityAddress( java.lang.String forwardManagerTestLocalMunicipalityAddress ) ;

   public abstract java.lang.String getForwardManagerTestRemoteMunicipalityAddress() ;

   public abstract void setForwardManagerTestRemoteMunicipalityAddress( java.lang.String forwardManagerTestRemoteMunicipalityAddress ) ;

   public abstract java.lang.String getForwardManagerTestRemoteProfileID() ;

   public abstract void setForwardManagerTestRemoteProfileID( java.lang.String forwardManagerTestRemoteProfileID ) ;

   public abstract java.lang.String getEMayorServiceInvokerEndpoint() ;

   public abstract void setEMayorServiceInvokerEndpoint( java.lang.String EMayorServiceInvokerEndpoint ) ;

   public abstract java.lang.String getEMayorAdminInterfaceIsEnabled() ;

   public abstract void setEMayorAdminInterfaceIsEnabled( java.lang.String EMayorAdminInterfaceIsEnabled ) ;

   public abstract java.lang.String getEMayorAdminInterfaceUserID() ;

   public abstract void setEMayorAdminInterfaceUserID( java.lang.String EMayorAdminInterfaceUserID ) ;

   public abstract java.lang.String getEMayorAdminInterfacePassword() ;

   public abstract void setEMayorAdminInterfacePassword( java.lang.String EMayorAdminInterfacePassword ) ;

   public abstract java.lang.String getBpelEngineUtInitialContextFactory() ;

   public abstract void setBpelEngineUtInitialContextFactory( java.lang.String bpelEngineUtInitialContextFactory ) ;

   public abstract java.lang.String getBpelEngineUtSecurityPrincipal() ;

   public abstract void setBpelEngineUtSecurityPrincipal( java.lang.String bpelEngineUtSecurityPrincipal ) ;

   public abstract java.lang.String getBpelEngineUtSecurityCredentials() ;

   public abstract void setBpelEngineUtSecurityCredentials( java.lang.String bpelEngineUtSecurityCredentials ) ;

   public abstract java.lang.String getBpelEngineUtProviderURL() ;

   public abstract void setBpelEngineUtProviderURL( java.lang.String bpelEngineUtProviderURL ) ;

   public abstract java.lang.String getBpelEngineUtSecurityDomain() ;

   public abstract void setBpelEngineUtSecurityDomain( java.lang.String bpelEngineUtSecurityDomain ) ;

   public abstract java.lang.String getBpelEngineUtSecurityDomainPassword() ;

   public abstract void setBpelEngineUtSecurityDomainPassword( java.lang.String bpelEngineUtSecurityDomainPassword ) ;

   public abstract java.lang.String getEMayorPeInfoDir() ;

   public abstract void setEMayorPeInfoDir( java.lang.String EMayorPeInfoDir ) ;

   public abstract java.lang.String getEMayorNotificationEmailSMTPHost() ;

   public abstract void setEMayorNotificationEmailSMTPHost( java.lang.String EMayorNotificationEmailSMTPHost ) ;

   public abstract java.lang.String getEMayorNotificationEmailSMTPUser() ;

   public abstract void setEMayorNotificationEmailSMTPUser( java.lang.String EMayorNotificationEmailSMTPUser ) ;

   public abstract java.lang.String getEMayorNotificationEmailSMTPPass() ;

   public abstract void setEMayorNotificationEmailSMTPPass( java.lang.String EMayorNotificationEmailSMTPPass ) ;

   public abstract java.lang.String getEMayorNotificationEmailSMTPAuth() ;

   public abstract void setEMayorNotificationEmailSMTPAuth( java.lang.String EMayorNotificationEmailSMTPAuth ) ;

   public abstract java.lang.Boolean getIsActive() ;

   public abstract void setIsActive( java.lang.Boolean isActive ) ;

   public abstract java.lang.String getEMayorContentRoutingLocalInquiryURL() ;

   public abstract void setEMayorContentRoutingLocalInquiryURL( java.lang.String EMayorContentRoutingLocalInquiryURL ) ;

   public abstract java.lang.String getEMayorContentRoutingRemoteInquiryURL() ;

   public abstract void setEMayorContentRoutingRemoteInquiryURL( java.lang.String EMayorContentRoutingRemoteInquiryURL ) ;

   public abstract java.lang.String getEMayorContentRoutingLocalPublishURL() ;

   public abstract void setEMayorContentRoutingLocalPublishURL( java.lang.String EMayorContentRoutingLocalPublishURL ) ;

   public abstract java.lang.String getEMayorContentRoutingUserID() ;

   public abstract void setEMayorContentRoutingUserID( java.lang.String EMayorContentRoutingUserID ) ;

   public abstract java.lang.String getEMayorContentRoutingPassword() ;

   public abstract void setEMayorContentRoutingPassword( java.lang.String EMayorContentRoutingPassword ) ;

   public abstract String getEMayorPeCrlDistributionURL();

   public abstract void setEMayorPeCrlDistributionURL(String value);

   public abstract java.lang.Boolean getEMayorPeCrlUseDefaultDistributionURL();

   public abstract void setEMayorPeCrlUseDefaultDistributionURL(Boolean value);
   
   public abstract Boolean getEMayorPeCheckSignature();

   public abstract void setEMayorPeCheckSignature(Boolean value);	

}
