/*
 * $ Created on Jun 1, 2005 by tku $
 */
package org.emayor.servicehandling.kernel.bpel.starter;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.log4j.Logger;
import org.emayor.servicehandling.config.Config;
import org.emayor.servicehandling.config.ConfigException;
import org.emayor.servicehandling.kernel.bpel.service.eMayorServiceRequest;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class eMayorServiceInvoker {
	private static final Logger log = Logger
			.getLogger(eMayorServiceInvoker.class);

	private static String THIS_NAMESPACE = "http://www.emayor.org/eMayorServiceStarter_v10";

	private static String SOAP_ACTION = "initiate";

	private static String STYLE = "document";

	private static QName SERVICE_NAME;

	private static QName PORT_TYPE;

	private static QName PORT_NAME;

	private static QName OPERATION_NAME;

	private String DEF_ENDPOINT = "http://localhost:9700/orabpel/default/eMayorServiceStarter_v10/1.0";

	public eMayorServiceInvoker() {
	}

	static {
		SERVICE_NAME = new QName(THIS_NAMESPACE, "eMayorServiceStarter_v10");
		PORT_TYPE = new QName(THIS_NAMESPACE, "eMayorServiceStarter_v10");
		PORT_NAME = new QName(THIS_NAMESPACE, "eMayorServiceStarter_v10Port");
		OPERATION_NAME = new QName(THIS_NAMESPACE, "initiate");
	}

	public void invokeService(String serviceId, String forward, String uid,
			String ssid, String req, String reqDigSig, String status)
			throws eMayorServiceInvokerException {
		log.debug("-> start processing ...");
		try {
			/*
			JFrame frame = new JFrame();
			JTextArea ta = new JTextArea();
			JScrollPane pane = new JScrollPane();
			pane.setViewportView(ta);
			frame.getContentPane().add(pane);
			frame.setSize(400,400);
			ta.append(req);
			frame.setVisible(true);
			*/
			Config config = Config.getInstance();
			String endpoint = config.getProperty(
					Config.EMAYOR_SERVICE_INVOKER_ENDPOINT, DEF_ENDPOINT);
			URL portAddress = new URL(endpoint);
			ServiceLocator locator = ServiceLocator.getInstance();
			IeMayorServiceStarterService service = locator
					.getIeMayorServiceStarterService();
			// Somehow it doesn't work - still get the URL from wsdl
			// IeMayorServiceStarter port = service
			//		.geteMayorServiceStarter_v10Port();
			eMayorServiceStarterProcessRequest processRequest = new eMayorServiceStarterProcessRequest();
			eMayorServiceStarterRequest input = new eMayorServiceStarterRequest();
			input.setEMayorServiceId(serviceId);
			eMayorServiceRequest request = new eMayorServiceRequest();
			request.setServiceId(serviceId);
			request.setForwarded(forward);
			request.setReqDocDigSig(reqDigSig);
			request.setReqestDocument(req);
			request.setSsid(ssid);
			request.setUid(uid);
			request.setStatus(status);
			request.setExtraField1("<empty/>");
			request.setExtraField2("<empty/>");
			request.setExtraField3("<empty/>");
			request.setExtraField4("<empty/>");
			input.setEMayorServiceRequest(request);
			processRequest.setInput(input);
			Call call = (Call) service.createCall(PORT_NAME, OPERATION_NAME);
			call.setTargetEndpointAddress(portAddress);
			call.setProperty(Call.OPERATION_STYLE_PROPERTY, STYLE);
			call.setProperty(Call.SOAPACTION_URI_PROPERTY, SOAP_ACTION);
			Object[] params = new Object[1];
			params[0] = processRequest;
			log.debug("invoking the one way operation");
			call.invokeOneWay(params);
			//port.initiate(processRequest);
		} catch (ServiceLocatorException slex) {
			log.error("caught ex: " + slex.toString());
			throw new eMayorServiceInvokerException(
					"problem with service locator");
		} catch (ServiceException e) {
			log.error("caught ex: " + e.toString());
			throw new eMayorServiceInvokerException("");
		} catch (ConfigException e) {
			log.error("caught ex: " + e.toString());
			throw new eMayorServiceInvokerException("");
		} catch (MalformedURLException e) {
			log.error("caught ex: " + e.toString());
			throw new eMayorServiceInvokerException("");
		} catch (Exception e) {
			log.error("caught ex: " + e.toString());
			throw new eMayorServiceInvokerException("");
		}
		log.debug("-> ... processing DONE!");
	}

}