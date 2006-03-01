package de.fraunhofer.dms.xinco.wrapper;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Vector;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import javax.ejb.CreateException;

import org.apache.log4j.Logger;

import com.bluecubs.xinco.add.XincoAddAttribute;
import com.bluecubs.xinco.core.XincoCoreData;
import com.bluecubs.xinco.core.XincoCoreDataType;
import com.bluecubs.xinco.core.XincoCoreLanguage;
import com.bluecubs.xinco.core.XincoCoreLog;
import com.bluecubs.xinco.core.XincoCoreNode;
import com.bluecubs.xinco.core.XincoCoreUser;
import com.bluecubs.xinco.core.XincoVersion;
import com.bluecubs.xinco.core.client.XincoCoreLanguageClient;
import com.bluecubs.xinco.core.client.XincoCoreUserClient;
import com.bluecubs.xinco.service.Xinco;
import com.bluecubs.xinco.service.XincoService;
import com.bluecubs.xinco.service.XincoServiceLocator;
import com.bluecubs.xinco.service.XincoSoapBindingImpl2;

/**
 * @ejb.bean name="XincoWrapper"
 *           display-name="Name for XincoWrapper"
 *           description="Description for XincoWrapper"
 *           jndi-name="ejb/XincoWrapper"
 *           type="Stateless"
 *           view-type="remote"
 */
public class XincoWrapperEJB implements SessionBean {

	private Xinco 				xinco 			= null;
	private XincoCoreLanguage 	language 		= null;
	private XincoCoreDataType 	fileDataType 	= null;
	private Logger				log				= Logger.getLogger(this.getClass().getName());
	
	public XincoWrapperEJB() {
		super();
		log.debug("xinco wrapper instance created ...");
		// TODO Auto-generated constructor stub
	}

	public void setSessionContext(SessionContext ctx)
		throws EJBException,
		RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
	 */
	public void ejbCreate() throws CreateException {
		log.debug("initialising xinco ....");
		try {
			//xinco = new XincoSoapBindingImpl2();
			XincoService locator = new XincoServiceLocator();
			xinco = locator.getXinco(new URL("http://localhost:28080/xinco/services/Xinco"));
		} catch (Exception e) {
			throw new CreateException(e.getMessage());
		}
	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public XincoVersion getXincoServerVersion() throws RemoteException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public XincoCoreUser getCurrentXincoCoreUser(String in0, String in1)
//		throws RemoteException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public Vector getAllXincoCoreGroups(XincoCoreUser in0)
//		throws RemoteException {
//		return xinco.getAllXincoCoreDataTypes(in0);
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public Vector getAllXincoCoreLanguages(XincoCoreUser in0)
//		throws RemoteException {
//		return xinco.getAllXincoCoreLanguages(in0);
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public Vector getAllXincoCoreDataTypes(XincoCoreUser in0)
//		throws RemoteException {
//		return xinco.getAllXincoCoreDataTypes(in0);
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public XincoCoreNode getXincoCoreNode(XincoCoreNode in0, XincoCoreUser in1)
//		throws RemoteException {
//		return xinco.getXincoCoreNode(in0,in1);
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public XincoCoreData getXincoCoreData(XincoCoreData in0, XincoCoreUser in1)
//		throws RemoteException {
//		return xinco.getXincoCoreData(in0,in1);
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public XincoCoreData doXincoCoreDataCheckout(
//		XincoCoreData in0,
//		XincoCoreUser in1) throws RemoteException {
//		return doXincoCoreDataCheckout(in0,in1);
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public XincoCoreData undoXincoCoreDataCheckout(
//		XincoCoreData in0,
//		XincoCoreUser in1) throws RemoteException {
//		return xinco.undoXincoCoreDataCheckout(in0,in1);
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public XincoCoreData doXincoCoreDataCheckin(
//		XincoCoreData in0,
//		XincoCoreUser in1) throws RemoteException {
//		return xinco.doXincoCoreDataCheckin(in0,in1);
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public byte[] downloadXincoCoreData(XincoCoreData in0, XincoCoreUser in1)
//		throws RemoteException {
//		return xinco.downloadXincoCoreData(in0,in1);
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public int uploadXincoCoreData(
//		XincoCoreData in0,
//		byte[] in1,
//		XincoCoreUser in2) throws RemoteException {
//		return xinco.uploadXincoCoreData(in0,in1,in2);
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public Vector findXincoCoreNodes(
//		String in0,
//		XincoCoreLanguage in1,
//		XincoCoreUser in2) throws RemoteException {
//		return xinco.findXincoCoreNodes(in0,in1,in2);
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public Vector findXincoCoreData(
//		String in0,
//		XincoCoreLanguage in1,
//		XincoCoreUser in2) throws RemoteException {
//		return xinco.findXincoCoreData(in0,in1,in2);
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public XincoCoreNode setXincoCoreNode(XincoCoreNode in0, XincoCoreUser in1)
//		throws RemoteException {
//		return xinco.setXincoCoreNode(in0,in1);
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public XincoCoreData setXincoCoreData(XincoCoreData in0, XincoCoreUser in1)
//		throws RemoteException {
//		return xinco.setXincoCoreData(in0,in1);
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public XincoCoreACE setXincoCoreACE(XincoCoreACE in0, XincoCoreUser in1)
//		throws RemoteException {
//		return xinco.setXincoCoreACE(in0,in1);
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public boolean removeXincoCoreACE(XincoCoreACE in0, XincoCoreUser in1)
//		throws RemoteException {
//		return xinco.removeXincoCoreACE(in0,in1);
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public XincoCoreLog setXincoCoreLog(XincoCoreLog in0, XincoCoreUser in1)
//		throws RemoteException {
//		return xinco.setXincoCoreLog(in0,in1);
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public XincoCoreUser setXincoCoreUser(XincoCoreUser in0, XincoCoreUser in1)
//		throws RemoteException {
//		return xinco.setXincoCoreUser(in0,in1);
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public XincoCoreGroup setXincoCoreGroup(
//		XincoCoreGroup in0,
//		XincoCoreUser in1) throws RemoteException {
//		return xinco.setXincoCoreGroup(in0,in1);
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public XincoCoreLanguage setXincoCoreLanguage(
//		XincoCoreLanguage in0,
//		XincoCoreUser in1) throws RemoteException {
//		return xinco.setXincoCoreLanguage(in0,in1);
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public XincoCoreDataTypeAttribute getXincoCoreDataTypeAttribute(
//		XincoCoreDataTypeAttribute in0,
//		XincoCoreUser in1) throws RemoteException {
//		return xinco.getXincoCoreDataTypeAttribute(in0,in1);
//	}
//
//	/**
//	 * Business method
//	 * @ejb.interface-method  view-type = "remote"
//	 */
//	public XincoAddAttribute getXincoAddAttribute(
//		XincoAddAttribute in0,
//		XincoCoreUser in1) throws RemoteException {
//		return xinco.getXincoAddAttribute(in0,in1);
//	}

	/**
	 * Business method
	 * @ejb.interface-method  view-type = "remote"
	 */
	public boolean uploadFile(String user, String pass, byte[] content, String[] contentOrder) throws RemoteException {
		boolean result = false;
		log.debug("---> processing started");
		// print out node arangemanet
		StringBuffer contentPrintOut = new StringBuffer("Trying to upload file: "+contentOrder[0]);
		for (int i=1; i<contentOrder.length;i++) {
			contentPrintOut.append("->"+contentOrder[i]);
		}
		log.debug(contentPrintOut);
		
		// login
		log.debug("\t - login as user: "+user+" with password: "+pass);
		//XincoCoreUser xuser = xinco.getCurrentXincoCoreUser(user,pass);
		XincoCoreUser xuser = new XincoCoreUser();
		xuser.setUsername(user);
		xuser.setUserpassword(pass);
		XincoCoreUser yuser = xinco.getCurrentXincoCoreUser(user,pass);
		// get system language if not already set
		XincoCoreLanguage xlang = getLanguage(xuser);
		log.debug("\t - language is: "+xlang.getDesignation());
		// get root
		XincoCoreNode xnode = new XincoCoreNode();
		xnode.setId(1);
		xnode = xinco.getXincoCoreNode(xnode,xuser);
		log.debug("\t - root node is: "+xnode.getDesignation());
		String contentName = null;
		
		XincoCoreNode ynode = null;
		XincoCoreNode znode = null;
		for (int i=0; i<contentOrder.length-1; i++) {
			
			ynode = null;
			Vector nodes = xinco.findXincoCoreNodes(contentOrder[i],xlang,xuser);
			
			// search for existant root
			if (nodes.size() > 0) {
				for (int j=0; j< nodes.size(); j++) {
					ynode = (XincoCoreNode) nodes.get(j);
					if (znode != null && ynode.getXinco_core_node_id() == znode.getId()) {
						break;
					} else if (znode == null && ynode.getXinco_core_node_id() == xnode.getId()) {
						break;
					} else {
						ynode = null;
					}
				}
				//ynode = (XincoCoreNode) nodes.get(0);
			}
			
			// if root not found append new node
			if (ynode == null) {
				ynode = new XincoCoreNode();
				if (znode != null)
					ynode.setXinco_core_node_id(znode.getId());
				else
					ynode.setXinco_core_node_id(xnode.getId());
				
				//set node attributes
				ynode.setDesignation(contentOrder[i]);
				ynode.setXinco_core_language(xlang);
				ynode.setStatus_number(1);
				log.debug("\t - creating node: "+ynode.getDesignation());
				znode = xinco.setXincoCoreNode(ynode, xuser);
			} else {
				log.debug("\t - using node: "+ynode.getDesignation());
				znode = null;
			}
			
			if (znode == null) {
				znode = ynode;
			}
		}
		
		// reset root node to current node
		if (znode != null) {
			xnode = znode;
		}
		
		// create xml data
		contentName = contentOrder[contentOrder.length-1];
		
		log.debug("\t - creating data: "+contentName);
		XincoCoreData xdata = new XincoCoreData();
		// get system datatype for files
		XincoCoreDataType dataType = getFileDataType(xuser);
		
		// init xdata
		xdata.setXinco_core_node_id(xnode.getId());
		xdata.setDesignation(contentName);
		xdata.setXinco_core_data_type(dataType);
		xdata.setXinco_core_language(xlang);
		xdata.setXinco_add_attributes(new Vector());
		xdata.setXinco_core_acl(new Vector());
		xdata.setXinco_core_logs(new Vector());
		xdata.setStatus_number(1);
		
		// add specific attributes
		XincoAddAttribute xaa;
		for (int j=0;j<xdata.getXinco_core_data_type().getXinco_core_data_type_attributes().size();j++) {
			xaa = new XincoAddAttribute();
			xaa.setAttribute_id(j+1);
			xaa.setAttrib_varchar("");
			xaa.setAttrib_text("");
			xaa.setAttrib_datetime(new GregorianCalendar());
			xdata.getXinco_add_attributes().addElement(xaa);
		}
		
		// add log
		log.debug("\t - creating log");
		XincoCoreLog newlog = new XincoCoreLog();
		newlog.setOp_code(1);
		newlog.setOp_description(xuser.getUsername());
		newlog.setXinco_core_user_id(yuser.getId());
		newlog.setXinco_core_data_id(xdata.getId()); //update to new id later!
		newlog.setVersion(new XincoVersion());
		newlog.getVersion().setVersion_high(1);
		newlog.getVersion().setVersion_mid(0);
		newlog.getVersion().setVersion_low(0);
		newlog.getVersion().setVersion_postfix("");
		xdata.setXinco_core_logs(new Vector());
		xdata.getXinco_core_logs().addElement(newlog);
		
		// update attributes (+ add CRC)
		log.debug("\t - creating crc32");
		CheckedInputStream in = null;
		in = new CheckedInputStream(new ByteArrayInputStream(content),new CRC32());
		((XincoAddAttribute) xdata.getXinco_add_attributes().elementAt(0)).setAttrib_varchar(contentName);
		((XincoAddAttribute) xdata.getXinco_add_attributes().elementAt(1)).setAttrib_unsignedint(content.length);
		((XincoAddAttribute) xdata.getXinco_add_attributes().elementAt(2)).setAttrib_varchar("" + in.getChecksum().getValue());
		((XincoAddAttribute) xdata.getXinco_add_attributes().elementAt(3)).setAttrib_unsignedint(1); //revision model
		((XincoAddAttribute) xdata.getXinco_add_attributes().elementAt(4)).setAttrib_unsignedint(0); //archiving model

		log.debug("\t - saving data");
		// save data to server
		xdata = xinco.setXincoCoreData(xdata, xuser);
		if (xdata == null) {
			return false;
		}
		
		log.debug("\t - update log");
		// update id in log
		newlog.setXinco_core_data_id(xdata.getId());
		// save log to server
		newlog = xinco.setXincoCoreLog(newlog, xuser);
		if (newlog == null) {
			// ignore
			// return false;
		}
		
		log.debug("\t - upload raw data");
		int count = xinco.uploadXincoCoreData(xdata,content,xuser);
		if (count == content.length) {
			result = true;
		}
		log.debug("<--- processing done");
		return result;
	}
	
	private XincoCoreLanguage getLanguage(XincoCoreUser xuser) throws RemoteException{
		if (this.language == null) {
			Vector languages = xinco.getAllXincoCoreLanguages(xuser);
			if (languages == null) {
				XincoCoreLanguage lang = new XincoCoreLanguage();
				lang.setId(1);
				lang.setDesignation("System language");
				lang.setSign(Locale.getDefault().getLanguage());
				this.language = lang;
				return this.language;
			}
			int selection = -1;
			int alt_selection = 0;
			for (int j=0;j<languages.size();j++) {
				if (((XincoCoreLanguage)languages.elementAt(j)).getSign().toLowerCase().compareTo(Locale.getDefault().getLanguage().toLowerCase()) == 0) {
					selection = j;
					break;
				}
				if (((XincoCoreLanguage)languages.elementAt(j)).getId() == 1) {
					alt_selection = j;
				}
			}
			if (selection == -1) {
				selection = alt_selection;
			}
			this.language = (XincoCoreLanguage) languages.elementAt(selection);	
		}
		return this.language;
	}
	
	private XincoCoreDataType getFileDataType(XincoCoreUser xuser) throws RemoteException {
		if (this.fileDataType == null) {
			Vector dataTypes = xinco.getAllXincoCoreDataTypes(xuser);
			//find data type = 1
			for (int j=0;j<dataTypes.size();j++) {
				if (((XincoCoreDataType)dataTypes.elementAt(j)).getId() == 1) {
					this.fileDataType = (XincoCoreDataType)dataTypes.elementAt(j);
					break;
				}
			}	
		}
		return this.fileDataType;
	}
}
