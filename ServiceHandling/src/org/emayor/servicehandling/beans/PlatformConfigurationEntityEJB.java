/*
 * $ Created on Jul 11, 2005 by tku $
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import org.apache.log4j.Logger;

/**
 * @ejb.bean name="PlatformConfigurationEntity" display-name="Name for
 *           PlatformConfigurationEntity" description="Description for
 *           PlatformConfigurationEntity"
 *           jndi-name="ejb/emayor/sh/entity/PlatformConfigurationEntity"
 *           type="CMP" cmp-version="2.x" view-type="local" primkey-field = "configID" 
 * @jboss.persistence table-name = "PLATFORMCONFIG" create-table = "true"
 *                    datasource = "java:/MySqlDS" datasource-mapping = "mySQL"
 *                    remove-table = "false" 
 * @ejb.finder method-intf = "LocalHome" query = "SELECT OBJECT(o) FROM
 *             PlatformConfigurationEntity AS o" result-type-mapping = "Local"
 *             signature = "java.util.Collection findAll()"
 */
public abstract class PlatformConfigurationEntityEJB implements EntityBean {
	private final static Logger log = Logger
		.getLogger(PlatformConfigurationEntityEJB.class);

	/**
	 *  
	 */
	public PlatformConfigurationEntityEJB() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
	 */
	public void setEntityContext(EntityContext ctx)
		throws EJBException,
		RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#unsetEntityContext()
	 */
	public void unsetEntityContext() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#ejbRemove()
	 */
	public void ejbRemove()
		throws RemoveException,
		EJBException,
		RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#ejbActivate()
	 */
	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#ejbPassivate()
	 */
	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#ejbLoad()
	 */
	public void ejbLoad() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#ejbStore()
	 */
	public void ejbStore() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * Create method
	 * 
	 * @ejb.create-method view-type = "local"
	 */
	public String ejbCreate(String configID)
		throws javax.ejb.CreateException {
		setConfigID(configID);
		return null;
	}

	/**
	 * Getter for CMP Field PropertyValue
	 *
	 * @ejb.pk-field 
	 * @jboss.column-name name = "CONFIGID"
	 * @jboss.sql-type type = "VARCHAR(200)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getConfigID();

	/**
	 * Setter for CMP Field PropertyValue
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setConfigID(String value);
	
	/**
	 * the unique id of the platform instance e.g. Aachen,Bozen
	 *
	 * @jboss.column-name name = "emayor_platform_instance_id"
	 * @jboss.sql-type type = "VARCHAR(20)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorPlatformInstanceID();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorPlatformInstanceID(String value);
	
	/**
	 * arbitrary fulle name of the municipality - informative character
	 *
	 * @jboss.column-name name = "emayor_municipality_name"
	 * @jboss.sql-type type = "VARCHAR(50)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorMunicipalityName();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorMunicipalityName(String value);	

	
	/**
	 * this property indicates whether the internal test services should
	 * be loaded (test) or the services contained in the deploy directory
	 *
	 * @jboss.column-name name = "emayor_operating_mode"
	 * @jboss.sql-type type = "VARCHAR(20)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorOperatingMode();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorOperatingMode(String value);
	
	/**
	 * test - the forwarding will be simulated, it means there is no
	 * need to start an extra (second) BPEL PM
	 * production - a real process will be started in case of forwarding
	 *
	 * @jboss.column-name name = "emayor_operating_mode_forward"
	 * @jboss.sql-type type = "VARCHAR(20)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorOperatingModeForward();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorOperatingModeForward(String value);

	/**
	 * deployment directory for the emayor services
	 * emayor.service.info.dir=conf/services
	 * emayor.service.classes.dir=conf/services/classes
	 *
	 * @jboss.column-name name = "emayor_tmp_dir"
	 * @jboss.sql-type type = "VARCHAR(50)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorTmpDir();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorTmpDir(String value);	

	/**
	 * email on the emayor platform
	 *
	 * @jboss.column-name name = "emayor_operating_mode_email"
	 * @jboss.sql-type type = "VARCHAR(20)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorOperationModeEmail();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorOperationModeEmail(String value);
	
	/**
	 * email on the emayor platform
	 *
	 * @jboss.column-name name = "emayor_email_test_user_address"
	 * @jboss.sql-type type = "VARCHAR(50)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorEmailTestUserAddress();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorEmailTestUserAddress(String value);

	/**
	 * forward manager 
	 *
	 * @jboss.column-name name = "forward_manager_queue_host"
	 * @jboss.sql-type type = "VARCHAR(50)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getForwardManagerQueueHost();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setForwardManagerQueueHost(String value);
	
	/**
	 * forward manager 
	 *
	 * @jboss.column-name name = "forward_manager_queue_name"
	 * @jboss.sql-type type = "VARCHAR(50)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getForwardManagerQueueName();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setForwardManagerQueueName(String value);
	
	/**
	 * forward manager 
	 *
	 * @jboss.column-name name = "emayor_operation_mode_content_routing"
	 * @jboss.sql-type type = "VARCHAR(20)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorOperationModeContentRouting();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorOperationModeContentRouting(String value);
	
	/**
	 * forward manager 
	 *
	 * @jboss.column-name name = "forward_manager_test_local_municipality_name"
	 * @jboss.sql-type type = "VARCHAR(50)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getForwardManagerTestLocalMunicipalityName();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setForwardManagerTestLocalMunicipalityName(String value);


	/**
	 * forward manager 
	 *
	 * @jboss.column-name name = "forward_manager_test_remote_municipality_name"
	 * @jboss.sql-type type = "VARCHAR(50)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getForwardManagerTestRemoteMunicipalityName();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setForwardManagerTestRemoteMunicipalityName(String value);
	
	/**
	 * forward manager 
	 *
	 * @jboss.column-name name = "forward_manager_test_local_municipality_address"
	 * @jboss.sql-type type = "VARCHAR(200)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getForwardManagerTestLocalMunicipalityAddress();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setForwardManagerTestLocalMunicipalityAddress(String value);
		
	/**
	 * forward manager 
	 *
	 * @jboss.column-name name = "forward_manager_test_remote_municipality_address"
	 * @jboss.sql-type type = "VARCHAR(200)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getForwardManagerTestRemoteMunicipalityAddress();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setForwardManagerTestRemoteMunicipalityAddress(String value);
	
		
	/**
	 * forward manager 
	 *
	 * @jboss.column-name name = "forward_manager_test_remote_profile_id"
	 * @jboss.sql-type type = "VARCHAR(20)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getForwardManagerTestRemoteProfileID();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setForwardManagerTestRemoteProfileID(String value);
	
	/**
	 * forward manager 
	 *
	 * @jboss.column-name name = "emayor_service_invoker_endpoint"
	 * @jboss.sql-type type = "VARCHAR(200)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorServiceInvokerEndpoint();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorServiceInvokerEndpoint(String value);
	
	/**
	 * eMayor admin interface 
	 *
	 * @jboss.column-name name = "emayor_admin_interface_is_enabled"
	 * @jboss.sql-type type = "VARCHAR(4)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorAdminInterfaceIsEnabled();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorAdminInterfaceIsEnabled(String value);

	/**
	 * eMayor admin interface 
	 *
	 * @jboss.column-name name = "emayor_admin_interface_userid"
	 * @jboss.sql-type type = "VARCHAR(20)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorAdminInterfaceUserID();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorAdminInterfaceUserID(String value);

	/**
	 * eMayor admin interface 
	 *
	 * @jboss.column-name name = "emayor_admin_interface_password"
	 * @jboss.sql-type type = "VARCHAR(16)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorAdminInterfacePassword();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorAdminInterfacePassword(String value);
	
	/**
	 * bpel engine initial context configuration for the UTWrapper application
	 *
	 * @jboss.column-name name = "bpel_engine_ut_initial_context_factory"
	 * @jboss.sql-type type = "VARCHAR(200)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getBpelEngineUtInitialContextFactory();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setBpelEngineUtInitialContextFactory(String value);

	/**
	 * bpel engine initial context configuration for the UTWrapper application
	 *
	 * @jboss.column-name name = "bpel_engine_ut_security_principal"
	 * @jboss.sql-type type = "VARCHAR(20)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getBpelEngineUtSecurityPrincipal();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setBpelEngineUtSecurityPrincipal(String value);	
	
	/**
	 * bpel engine initial context configuration for the UTWrapper application
	 *
	 * @jboss.column-name name = "bpel_engine_ut_security_credentials"
	 * @jboss.sql-type type = "VARCHAR(16)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getBpelEngineUtSecurityCredentials();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setBpelEngineUtSecurityCredentials(String value);

	/**
	 * bpel engine initial context configuration for the UTWrapper application
	 *
	 * @jboss.column-name name = "bpel_engine_ut_provider_url"
	 * @jboss.sql-type type = "VARCHAR(200)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getBpelEngineUtProviderURL();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setBpelEngineUtProviderURL(String value);	

	/**
	 * bpel engine initial context configuration for the UTWrapper application
	 *
	 * @jboss.column-name name = "bpel_engine_ut_security_domain"
	 * @jboss.sql-type type = "VARCHAR(20)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getBpelEngineUtSecurityDomain();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setBpelEngineUtSecurityDomain(String value);	

	/**
	 * bpel engine initial context configuration for the UTWrapper application
	 *
	 * @jboss.column-name name = "bpel_engine_ut_security_domain_password"
	 * @jboss.sql-type type = "VARCHAR(16)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getBpelEngineUtSecurityDomainPassword();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setBpelEngineUtSecurityDomainPassword(String value);	

	/**
	 * Policy Enforcer Configurartion
	 *
	 * @jboss.column-name name = "emayor_pe_info_dir"
	 * @jboss.sql-type type = "VARCHAR(200)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorPeInfoDir();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorPeInfoDir(String value);	

	/**
	 * Notification (Email Gateway) Configuration
	 *
	 * @jboss.column-name name = "emayor_notification_email_smtp_host"
	 * @jboss.sql-type type = "VARCHAR(50)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorNotificationEmailSMTPHost();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorNotificationEmailSMTPHost(String value);	

	/**
	 * Notification (Email Gateway) Configuration
	 *
	 * @jboss.column-name name = "emayor_notification_email_smtp_user"
	 * @jboss.sql-type type = "VARCHAR(20)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorNotificationEmailSMTPUser();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorNotificationEmailSMTPUser(String value);	

	/**
	 * Notification (Email Gateway) Configuration
	 *
	 * @jboss.column-name name = "emayor_notification_email_smtp_pass"
	 * @jboss.sql-type type = "VARCHAR(16)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorNotificationEmailSMTPPass();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorNotificationEmailSMTPPass(String value);	

	/**
	 * Notification (Email Gateway) Configuration
	 *
	 * @jboss.column-name name = "emayor_notification_email_smtp_auth"
	 * @jboss.sql-type type = "VARCHAR(5)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorNotificationEmailSMTPAuth();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorNotificationEmailSMTPAuth(String value);	
	
	/**
	 * marker for active configuration
	 *
	 * @jboss.column-name name = "config_is_active"
	 * @jboss.sql-type type = "BOOLEAN"
	 * @jboss.jdbc-type type = "TINYINT" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract Boolean getIsActive();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setIsActive(Boolean value);
	
	/**
	 * content routing local inquiry URL
	 *
	 * @jboss.column-name name = "emayor_content_routing_local_inquiry"
	 * @jboss.sql-type type = "VARCHAR(200)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorContentRoutingLocalInquiryURL();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorContentRoutingLocalInquiryURL(String value);
	
	/**
	 * content routing remote inquiry URL
	 *
	 * @jboss.column-name name = "emayor_content_routing_remote_inquiry"
	 * @jboss.sql-type type = "VARCHAR(200)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorContentRoutingRemoteInquiryURL();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorContentRoutingRemoteInquiryURL(String value);
	
	/**
	 * content routing local publish URL
	 *
	 * @jboss.column-name name = "emayor_content_routing_local_publish"
	 * @jboss.sql-type type = "VARCHAR(200)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorContentRoutingLocalPublishURL();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorContentRoutingLocalPublishURL(String value);
	
	/**
	 * content routing juddi userid
	 *
	 * @jboss.column-name name = "emayor_content_routing_userid"
	 * @jboss.sql-type type = "VARCHAR(20)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorContentRoutingUserID();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorContentRoutingUserID(String value);
	
	/**
	 * content routing juddi userid
	 *
	 * @jboss.column-name name = "emayor_content_routing_password"
	 * @jboss.sql-type type = "VARCHAR(20)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorContentRoutingPassword();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorContentRoutingPassword(String value);
	
	/**
	 * PolicyEnforcer CRL Distribution URL
	 *
	 * @jboss.column-name name = "emayor_pe_crl_distribution_url"
	 * @jboss.sql-type type = "VARCHAR(200)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorPeCrlDistributionURL();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorPeCrlDistributionURL(String value);

	/**
	 * PolicyEnforcer CRL Distribution URL as default only
	 *
	 * @jboss.column-name name = "emayor_pe_crl_use_default_distribution_url"
	 * @jboss.sql-type type = "BOOLEAN"
	 * @jboss.jdbc-type type = "TINYINT" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract Boolean getEMayorPeCrlUseDefaultDistributionURL();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorPeCrlUseDefaultDistributionURL(Boolean value);

	/**
	 * PolicyEnforcer Check Signatures or not
	 *
	 * @jboss.column-name name = "emayor_pe_check_signature"
	 * @jboss.sql-type type = "BOOLEAN"
	 * @jboss.jdbc-type type = "TINYINT" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract Boolean getEMayorPeCheckSignature();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorPeCheckSignature(Boolean value);
	
	/**
	 * HTTP Session max. time to live
	 *
	 * @jboss.column-name name = "EMAYOR_HTTP_SESSION_MAX_TIME_TO_LIVE"
	 * @jboss.sql-type type = "INTEGER"
	 * @jboss.jdbc-type type = "INTEGER" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract Integer getEMayorHTTPSessionMaxTimeToLive();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorHTTPSessionMaxTimeToLive(Integer value);

	/**
	 * E2M Host
	 *
	 * @jboss.column-name name = "EMAYOR_M2E_CONTEXT"
	 * @jboss.sql-type type = "VARCHAR(200)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorE2MContext();

	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorE2MContext(String value);	
	
	/**
	 * ReplyTo Address
	 *
	 * @jboss.column-name name = "EMAYOR_NOTIFICATION_EMAIL_REPLYTO"
	 * @jboss.sql-type type = "VARCHAR(200)"
	 * @jboss.jdbc-type type = "VARCHAR" 
	 * @ejb.persistent-field
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract String getEMayorNotificationEmailReplyTo();
	
	/**
	 * Setter for CMP Field
	 *
	 * @ejb.interface-method   view-type="local"
	 */
	public abstract void setEMayorNotificationEmailReplyTo(String value);	
}