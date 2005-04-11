/*
 * Created on Feb 25, 2005
 */
package org.emayor.rcs;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.emayor.www.eMayorServiceRequest_xsd.EMayorServiceRequestType;
import org.xmlsoap.schemas.ws.addressing.MessageID;
import org.xmlsoap.schemas.ws.addressing.ReplyTo;

import com.otn.interop.jaxrpc.AsyncInvoker;
import com.otn.interop.jaxrpc.Parameter;

/**
 * @author tku
 */
public class RCSInvoker extends AsyncInvoker {
	private static Logger log = Logger.getLogger(RCSInvoker.class);

	private static QName SERVICE_NAME;

	private static QName PORT_TYPE;

	private static QName OPERATION_NAME;

	private static String SOAP_ACTION;

	private static String STYLE;

	private static Parameter[] SERVICE_PARAMETERS;
	/*
	 * There is a problem on the BPEL side to use the request data by specifing
	 * the xpath with NS. Somehow the NS of the request data is not correct.
	 * Current working workaround is to use the query without the NS.
	 */
	static {
		String THIS_NAMESPACE = "http://emayor.org/ResidenceCertifcationRequest_v10";
		String PARAMETER_NAMESPACE = "http://www.emayor.org/eMayorServiceRequest.xsd";

		SERVICE_NAME = new QName(THIS_NAMESPACE,
				"ResidenceCertifcationRequest_v10");
		PORT_TYPE = new QName(THIS_NAMESPACE,
				"ResidenceCertifcationRequest_v10");
		OPERATION_NAME = new QName(THIS_NAMESPACE,
				"ResidenceCertifcationRequest_v10RequestMessage");
		SOAP_ACTION = "initiate";
		STYLE = "document";

		SERVICE_PARAMETERS = new Parameter[1];

		SERVICE_PARAMETERS[0] = new Parameter(
				"ResidenceCertifcationRequest_v10Request",
				new QName(PARAMETER_NAMESPACE, "eMayorServiceRequestType"),
				org.emayor.www.eMayorServiceRequest_xsd.EMayorServiceRequestType.class);

	}

	public RCSInvoker(String address, MessageID messageID, ReplyTo replyTo,
			EMayorServiceRequestType request) {
		super(SERVICE_NAME, PORT_TYPE, OPERATION_NAME, SOAP_ACTION, STYLE,
				SERVICE_PARAMETERS, messageID, replyTo);
		setAddress(address);
		setValue(new Object[] { request });
	}

	public RCSInvoker(String address, QName portType, QName operationName,
			MessageID messageID, ReplyTo replyTo,
			EMayorServiceRequestType request) {
		super(SERVICE_NAME, portType, operationName, SOAP_ACTION, STYLE,
				SERVICE_PARAMETERS, messageID, replyTo);

		setAddress(address);

		setValue(new Object[] { request });
	}
	
	public EMayorServiceRequestType getRequest()
    {
        Object[] value = getValue();

        return (EMayorServiceRequestType)value[0];
    }

    public void setRequest( EMayorServiceRequestType request)
    {
       setValue( new Object[] { request } );
    }

}