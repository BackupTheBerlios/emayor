package org.emayor.ContentRouting.ejb;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.CreateException;

import org.emayor.servicehandling.config.Config;
import org.emayor.servicehandling.config.ConfigException;

//Imports for the business logic
import org.emayor.ContentRouting.ejb.AccessPointNotFoundException;
import org.emayor.ContentRouting.ejb.ContentRouterException;
import org.uddi4j.datatype.*;
import org.uddi4j.response.*;
import org.uddi4j.UDDIException;
import org.uddi4j.client.UDDIProxy;
import org.uddi4j.datatype.binding.AccessPoint;
import org.uddi4j.datatype.binding.BindingTemplate;
import org.uddi4j.datatype.binding.BindingTemplates;
import org.uddi4j.datatype.business.BusinessEntity;
import org.uddi4j.datatype.service.BusinessService;
import org.uddi4j.datatype.service.BusinessServices;
import org.uddi4j.transport.TransportException;
import org.uddi4j.util.FindQualifier;
import org.uddi4j.util.FindQualifiers;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

/**
 * @ejb.bean name="ContentRouter" display-name="Name for ContentRouter"
 *           description="Description for ContentRouter"
 *           jndi-name="ejb/ContentRouter" type="Stateless" view-type="both"
 * 
 * @author Nikolaos Oikonomidis, University of Siegen
 */
public class ContentRouterBean implements SessionBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
   
    /**
     * Sets up the EJB class for content routing.
     * 
     * @throws FileNotFoundException
     *             if the configuration file for the UDDI4J is unavailable.
     * @throws IOException
     *             in case of a general I/O malfunction.
     */
    public ContentRouterBean() throws FileNotFoundException, IOException {}

    /*
     * (non-Javadoc)
     * 
     * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
     */
    public void setSessionContext(SessionContext ctx) throws EJBException,
            RemoteException {

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.ejb.SessionBean#ejbRemove()
     */
    public void ejbRemove() throws EJBException, RemoteException {

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.ejb.SessionBean#ejbActivate()
     */
    public void ejbActivate() throws EJBException, RemoteException {

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.ejb.SessionBean#ejbPassivate()
     */
    public void ejbPassivate() throws EJBException, RemoteException {

    }

    /**
     * Default create method
     * 
     * @throws CreateException
     * @ejb.create-method
     */
    public void ejbCreate() throws CreateException {

    }

    /**
     * The core business method of the content routing EJB. Provides the access
     * point of a web-service as a URL.
     * 
     * @param municipalityName
     *            The name of the organization (municipality) to be queried.
     * @param serviceName
     *            The name of the requested service.
     * @return The URL of the access point for the requested service of the
     *         specific organization (municipality).
     * @throws org.emayor.ContentRouting.ejb.OrganisationNotFoundException
     *             when the requested organization could not be found.
     * @throws org.emayor.ContentRouting.ejb.ServiceNotFoundException
     *             when no service could be found for the given organization.
     * @throws org.emayor.ContentRouting.ejb.BindingTemplateNotFoundException
     *             when no binding template could be found for the given
     *             service.
     * @throws org.emayor.ContentRouting.ejb.AccessPointNotFoundException
     *             when no access point URL could be found for the given
     *             service.
     * 
     * @ejb.interface-method view-type = "both"
     */
    public String getAccessPoint(String municipalityName, String serviceName)
            throws org.emayor.ContentRouting.ejb.OrganisationNotFoundException,
            org.emayor.ContentRouting.ejb.ServiceNotFoundException,
            org.emayor.ContentRouting.ejb.BindingTemplateNotFoundException,
            org.emayor.ContentRouting.ejb.AccessPointNotFoundException,
            org.emayor.servicehandling.config.ConfigException {

        try {
            Config config = Config.getInstance();
            String accessPointURL = new String(getAccessPointUDDI(
                    municipalityName, serviceName, config
                            .getProperty("emayor.contentrouting.local.inquiry.url")));
            return (accessPointURL);
        } catch (ContentRouterException e) {
            String accessPointURL = forceUpdateAccessPoint(municipalityName,
                    serviceName);
            return (accessPointURL);
        } catch(ConfigException e) {
            throw new ConfigException("Configuration File Error");
         }
    }

    private String forceUpdateAccessPoint(String municipalityName,
            String serviceName) throws OrganisationNotFoundException,
            ServiceNotFoundException, BindingTemplateNotFoundException,
            AccessPointNotFoundException, ConfigException {
        
        try {
            Config config = Config.getInstance();
            updateOrganization(municipalityName, serviceName, config
                    .getProperty("emayor.contentrouting.remote.inquiry.url"), config
                    .getProperty("emayor.contentrouting.local.inquiry.url"), config
                    .getProperty("emayor.contentrouting.local.publish.url"));
            String accessPointURL = new String(getAccessPointUDDI(municipalityName,
                    serviceName, config.getProperty("emayor.contentrouting.local.inquiry.url")));
            return (accessPointURL);
        } catch (ConfigException e) {
            throw new ConfigException("Configuration File Error");
         }
    }

    private String getAccessPointUDDI(String municipalityName,
            String serviceName, String registryAccessPoint)
            throws OrganisationNotFoundException, ServiceNotFoundException,
            BindingTemplateNotFoundException, AccessPointNotFoundException {

        String accessPointURL = new String();

        try {
            UDDIProxy inquiry = new UDDIProxy(); //Create an UDDIProxy instance
            inquiry.setInquiryURL(registryAccessPoint); //Set eMayor UDDI
            // Registry
            Vector n = new Vector(1); //Set the arguments
            n.add(new Name(municipalityName)); //Municipality Name to search
            // for
            FindQualifiers fqs = new FindQualifiers();
            fqs.add(new FindQualifier(FindQualifier.exactNameMatch)); //Search
            // for the
            // exact
            // name

            BusinessList inquiryBusinessList = inquiry.find_business(n, null,
                    null, null, null, fqs, 1); //Retrieve the BusinessList
            BusinessInfos inquiryBusinessInfos = inquiryBusinessList
                    .getBusinessInfos(); //Retrieve the BusinessInfos
            Vector inquiryBusinessInfoVector = inquiryBusinessInfos
                    .getBusinessInfoVector(); //Retrieve the
            // BusinessInforVector

            if (inquiryBusinessInfoVector.isEmpty() == false) {
                BusinessInfo inquiryBusinessInfo = (BusinessInfo) inquiryBusinessInfoVector
                        .elementAt(0); //Retrieve the BusinessInfo
                ServiceInfos inquiryServiceInfos = inquiryBusinessInfo
                        .getServiceInfos(); //Retrieve the ServiceInfos
                Vector inquiryServiceInfoVector = inquiryServiceInfos
                        .getServiceInfoVector(); //Retrieve the
                // ServiceInfoVector

                if (inquiryServiceInfoVector.isEmpty() == false) {
                    for (int i = 0; i < inquiryServiceInfoVector.size(); i++) //For
                    // each
                    // service
                    // in
                    // ServiceInfoVector
                    {
                        //Retrieve the ServiceInfo
                        ServiceInfo inquiryServiceInfo = (ServiceInfo) inquiryServiceInfoVector
                                .elementAt(i);
                        if ((inquiryServiceInfo.getDefaultNameString()
                                .equals(serviceName))
                                && (accessPointURL.length() == 0)) {
                            //Retrieve the ServiceDetail
                            ServiceDetail inquiryServiceDetail = inquiry
                                    .get_serviceDetail(inquiryServiceInfo
                                            .getServiceKey());
                            //Retrieve the BusinessServiceVector
                            Vector inquiryBusinessServiceVector = inquiryServiceDetail
                                    .getBusinessServiceVector();
                            //Retrieve the BusinessService
                            //-- Normally, this should be always the first and
                            // only element of the BusinessServiceVector
                            BusinessService inquiryBusinessService = (BusinessService) inquiryBusinessServiceVector
                                    .elementAt(0);
                            //Retrieve the BindingTemplates
                            BindingTemplates inquiryBindingTemplates = inquiryBusinessService
                                    .getBindingTemplates();
                            //Retrieve the BindingTemplateVector
                            Vector inquiryBindingTemplateVector = inquiryBindingTemplates
                                    .getBindingTemplateVector();

                            if (inquiryBindingTemplateVector.isEmpty() == false) {
                                //Retrieve the BindingTemplate
                                //-- At the moment, only the first element of
                                // the BindingTemplateVector is retrieved
                                BindingTemplate inquiryBindingTemplate = (BindingTemplate) inquiryBindingTemplateVector
                                        .elementAt(0);
                                //Retrieve the AccessPoint
                                AccessPoint inquiryAccessPoint = inquiryBindingTemplate
                                        .getAccessPoint();
                                accessPointURL = inquiryAccessPoint.getText();

                                if (accessPointURL.length() == 0) {
                                    throw new AccessPointNotFoundException(
                                            "Requested Access Point Not Found");
                                }
                            } else {
                                throw new BindingTemplateNotFoundException(
                                        "No Binding Template available for the requested Service");
                            }
                        }
                    }
                    if (accessPointURL.length() == 0) {
                        throw new ServiceNotFoundException(
                                "Requested Service Not Found");
                    }
                } else {
                    throw new ServiceNotFoundException(
                            "Requested Service Not Found");
                }
            } else {
                throw new OrganisationNotFoundException(
                        "Requested Organisation Not Found");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UDDIException e) {
            DispositionReport dr = e.getDispositionReport();
            if (dr != null) {
                System.out.println("UDDIException faultCode:"
                        + e.getFaultCode() + "\n operator:" + dr.getOperator()
                        + "\n generic:" + dr.getGeneric());
                Vector results = dr.getResultVector();
                for (int i = 0; i < results.size(); i++) {
                    Result r = (Result) results.elementAt(i);
                    System.out.println("\n errno:" + r.getErrno());
                    if (r.getErrInfo() != null) {
                        System.out.println("\n errCode:"
                                + r.getErrInfo().getErrCode()
                                + "\n errInfoText:" + r.getErrInfo().getText());
                    }
                }
            }
        } catch (TransportException e) {
            e.printStackTrace();
        }
        return (accessPointURL); // Return the Access Point of the requested
        // service
    }

    private void updateOrganization(String municipalityName,
            String serviceName, String inquiryAccessPointSource,
            String inquiryAccessPointTarget, String publishAccessPointTarget) 
            throws ConfigException {

        BusinessEntity businessEntityContainer = new BusinessEntity();
        BusinessServices businessServicesContainer = new BusinessServices();

        try {
            Config config = Config.getInstance();
            //********************************************************************
            //Prepare the container object of the organization that will be
            // copied
            //********************************************************************
            UDDIProxy inquiry = new UDDIProxy(); //Create an UDDIProxy instance
            inquiry.setInquiryURL(inquiryAccessPointSource);
            Vector n = new Vector(1); //Set the arguments
            n.add(new Name(municipalityName)); //Municipality Name to search
            // for
            FindQualifiers fqs = new FindQualifiers();
            fqs.add(new FindQualifier(FindQualifier.exactNameMatch)); //Search
            // for the
            // exact
            // name

            BusinessList inquiryBusinessList = inquiry.find_business(n, null,
                    null, null, null, fqs, 1); //Retrieve the BusinessList
            BusinessInfos inquiryBusinessInfos = inquiryBusinessList
                    .getBusinessInfos(); //Retrieve the BusinessInfos
            Vector inquiryBusinessInfoVector = inquiryBusinessInfos
                    .getBusinessInfoVector(); //Retrieve the
            // BusinessInforVector

            if (inquiryBusinessInfoVector.isEmpty() == false) {
                BusinessInfo inquiryBusinessInfo = (BusinessInfo) inquiryBusinessInfoVector
                        .elementAt(0); //Retrieve the BusinessInfo
                businessEntityContainer.setDefaultName(inquiryBusinessInfo
                        .getDefaultName());
                //businessEntityContainer.setBusinessKey(inquiryBusinessInfo.getBusinessKey());
                ServiceInfos inquiryServiceInfos = inquiryBusinessInfo
                        .getServiceInfos(); //Retrieve the ServiceInfos
                Vector inquiryServiceInfoVector = inquiryServiceInfos
                        .getServiceInfoVector(); //Retrieve the
                // ServiceInfoVector

                for (int i = 0; i < inquiryServiceInfoVector.size(); i++) //For
                // each
                // service
                // in
                // ServiceInfoVector
                {
                    //Retrieve the ServiceInfo
                    ServiceInfo inquiryServiceInfo = (ServiceInfo) inquiryServiceInfoVector
                            .elementAt(i);
                    //Retrieve the ServiceDetail
                    ServiceDetail inquiryServiceDetail = inquiry
                            .get_serviceDetail(inquiryServiceInfo
                                    .getServiceKey());
                    //Retrieve the BusinessServiceVector
                    Vector inquiryBusinessServiceVector = inquiryServiceDetail
                            .getBusinessServiceVector();
                    //Retrieve the BusinessService
                    //-- Normally, this should be always the first and only
                    // element of the BusinessServiceVector
                    for (int j = 0; j < inquiryBusinessServiceVector.size(); j++) {
                        BusinessService inquiryBusinessService = (BusinessService) inquiryBusinessServiceVector
                                .elementAt(j);
                        businessServicesContainer.add(inquiryBusinessService);
                    }
                }
                businessEntityContainer
                        .setBusinessServices(businessServicesContainer);
            } else {
                throw new OrganisationNotFoundException(
                        "Requested Organisation Not Found");
            }
            //***********************************************************************
            //Delete any existing instance of the organization in the target
            // registry
            //***********************************************************************
            UDDIProxy retrieval = new UDDIProxy(); //Create an UDDIProxy
            // instance
            retrieval.setInquiryURL(inquiryAccessPointTarget);
            retrieval.setPublishURL(publishAccessPointTarget);
            AuthToken token = retrieval.get_authToken(config
                    .getProperty("emayor.contentrouting.userid"), config.getProperty("emayor.contentrouting.password"));
            Vector v = new Vector(1); //Set the arguments
            v.add(new Name(municipalityName)); //Municipality Name to search
            // for
            FindQualifiers fq = new FindQualifiers();
            fq.add(new FindQualifier(FindQualifier.exactNameMatch)); //Search
            // for the
            // exact
            // name

            BusinessList retrievalBusinessList = retrieval.find_business(v,
                    null, null, null, null, fq, 1); //Retrieve the BusinessList
            BusinessInfos retrievalBusinessInfos = retrievalBusinessList
                    .getBusinessInfos(); //Retrieve the BusinessInfos
            Vector retrievalBusinessInfoVector = retrievalBusinessInfos
                    .getBusinessInfoVector(); //Retrieve the
            // BusinessInforVector

            if (retrievalBusinessInfoVector.isEmpty() == false) {
                BusinessInfo retrievalBusinessInfo = (BusinessInfo) retrievalBusinessInfoVector
                        .elementAt(0); //Retrieve the BusinessInfo
                //Delete the organization if already in the target registry
                DispositionReport result = retrieval.delete_business(token
                        .getAuthInfoString(), retrievalBusinessInfo
                        .getBusinessKey());
            }
            //***********************************************
            //Publish the organization in the target registry
            //***********************************************
            UDDIProxy publisher = new UDDIProxy();
            publisher.setPublishURL(publishAccessPointTarget);
            AuthToken auth = publisher.get_authToken(config
                    .getProperty("emayor.contentrouting.userid"), config.getProperty("emayor.contentrouting.password"));
            Vector entities = new Vector();
            entities.addElement(businessEntityContainer);
            BusinessDetail org = publisher.save_business(auth
                    .getAuthInfoString(), entities);
        } catch (UDDIException e) {
            DispositionReport dr = e.getDispositionReport();
            if (dr != null) {
                System.out.println("UDDIException faultCode:"
                        + e.getFaultCode() + "\n operator:" + dr.getOperator()
                        + "\n generic:" + dr.getGeneric());

                Vector results = dr.getResultVector();
                for (int i = 0; i < results.size(); i++) {
                    Result r = (Result) results.elementAt(i);
                    System.out.println("\n errno:" + r.getErrno());
                    if (r.getErrInfo() != null) {
                        System.out.println("\n errCode:"
                                + r.getErrInfo().getErrCode()
                                + "\n errInfoText:" + r.getErrInfo().getText());
                    }
                }
            }

            e.printStackTrace();
        } catch (ConfigException e) {throw new ConfigException("Configuration File Error");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}