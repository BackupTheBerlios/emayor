/**
 * XincoSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

/**
 *Copyright 2005 blueCubs.com
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 *
 *************************************************************
 * This project supports the blueCubs vision of giving back
 * to the community in exchange for free software!
 * More information on: http://www.bluecubs.org
 *************************************************************
 *
 * Name:            XincoSoapBindingImpl
 *
 * Description:     -
 *
 * Original Author: Alexander Manes
 * Date:            2004
 *
 * Modifications:
 * 
 * Who?             When?             What?
 * -                -                 -
 *
 *************************************************************
 */

package com.bluecubs.xinco.service;

import java.io.*;
import java.util.zip.*;

import org.apache.log4j.Logger;
//import org.apache.axis.attachments.AttachmentPart;
//import org.apache.axis.*;

import com.bluecubs.xinco.core.*;
import com.bluecubs.xinco.add.*;
import com.bluecubs.xinco.core.server.*;
import com.bluecubs.xinco.index.*;

public class XincoSoapBindingImpl2 implements com.bluecubs.xinco.service.Xinco{
	
	private Logger log = Logger.getLogger(this.getClass().getName());
	
    public com.bluecubs.xinco.core.XincoVersion getXincoServerVersion() throws java.rmi.RemoteException {
        //return current version of server
        XincoVersion version = new XincoVersion();
		version.setVersion_high(1);
		version.setVersion_mid(10);
		version.setVersion_low(0);
		version.setVersion_postfix("");        
        return version;
    }

    public com.bluecubs.xinco.core.XincoCoreUser getCurrentXincoCoreUser(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException {
		//login
		try {
			XincoDBManager dbm = new XincoDBManager();
			XincoCoreUserServer user = new XincoCoreUserServer(in0, in1, dbm);
			dbm.con.close();
			return (XincoCoreUser)user;
		} catch (Exception e) {
			return null;
		}
    }

    public java.util.Vector getAllXincoCoreGroups(com.bluecubs.xinco.core.XincoCoreUser in0) throws java.rmi.RemoteException {
		try {
			XincoDBManager dbm = new XincoDBManager();
			//check if user exists
			XincoCoreUserServer user = new XincoCoreUserServer(in0.getUsername(), in0.getUserpassword(), dbm);
			java.util.Vector v = XincoCoreGroupServer.getXincoCoreGroups(dbm);
			dbm.con.close();
			return v;
		} catch (Exception e) {
			return null;
		}
    }

    public java.util.Vector getAllXincoCoreLanguages(com.bluecubs.xinco.core.XincoCoreUser in0) throws java.rmi.RemoteException {
		try {
			XincoDBManager dbm = new XincoDBManager();
			//check if user exists
			XincoCoreUserServer user = new XincoCoreUserServer(in0.getUsername(), in0.getUserpassword(), dbm);
			java.util.Vector v = XincoCoreLanguageServer.getXincoCoreLanguages(dbm);
			dbm.con.close();
			return v;
		} catch (Exception e) {
			return null;
		}
    }

    public java.util.Vector getAllXincoCoreDataTypes(com.bluecubs.xinco.core.XincoCoreUser in0) throws java.rmi.RemoteException {
		try {
			XincoDBManager dbm = new XincoDBManager();
			//check if user exists
			XincoCoreUserServer user = new XincoCoreUserServer(in0.getUsername(), in0.getUserpassword(), dbm);
			java.util.Vector v = XincoCoreDataTypeServer.getXincoCoreDataTypes(dbm);
			dbm.con.close();
			return v;
		} catch (Exception e) {
			return null;
		}
    }

    public com.bluecubs.xinco.core.XincoCoreNode getXincoCoreNode(com.bluecubs.xinco.core.XincoCoreNode in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException {
		try {
			XincoDBManager dbm = new XincoDBManager();
			XincoCoreUserServer user = new XincoCoreUserServer(in1.getUsername(), in1.getUserpassword(), dbm);
			XincoCoreNodeServer node = new XincoCoreNodeServer(in0.getId(), dbm);
			XincoCoreACE ace = XincoCoreACEServer.checkAccess(user, node.getXinco_core_acl());
			if (ace.isRead_permission()) {
				node.fillXincoCoreNodes(dbm);
				node.fillXincoCoreData(dbm); 
				dbm.con.close();
				return (XincoCoreNode)node;
			} else {
				log.debug("cannot find node with id: "+in0.getId());
				dbm.con.close();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    public com.bluecubs.xinco.core.XincoCoreData getXincoCoreData(com.bluecubs.xinco.core.XincoCoreData in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException {
		try {
			XincoDBManager dbm = new XincoDBManager();
			XincoCoreUserServer user = new XincoCoreUserServer(in1.getUsername(), in1.getUserpassword(), dbm);
			XincoCoreDataServer data = new XincoCoreDataServer(in0.getId(), dbm);
			XincoCoreACE ace = XincoCoreACEServer.checkAccess(user, data.getXinco_core_acl());
			if (ace.isRead_permission()) {
				dbm.con.close();
				return (XincoCoreData)data;
			} else {
				dbm.con.close();
				return null;
			}
		} catch (Exception e) {
			return null;
		}
    }

    public com.bluecubs.xinco.core.XincoCoreData doXincoCoreDataCheckout(com.bluecubs.xinco.core.XincoCoreData in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException {
		/*
    	try {
			XincoDBManager dbm = new XincoDBManager();
			XincoCoreDataServer data;
			XincoCoreACE ace;
			XincoCoreUserServer user = new XincoCoreUserServer(in1.getUsername(), in1.getUserpassword(), dbm);
			//get existing data
			data = new XincoCoreDataServer(in0.getId(), dbm);
			ace = XincoCoreACEServer.checkAccess(user, data.getXinco_core_acl());
			//check status
			if (data.getStatus_number() != 1) {
				dbm.con.close();
				return null;
			}
			if (ace.isWrite_permission()) {
				//update information
				data.setStatus_number(4);
				data.write2DB(dbm);
				dbm.con.close();
				return (XincoCoreData)data;
			} else {
				dbm.con.close();
				return null;
			}
		} catch (Exception e) {
			return null;
		}
		*/
    	in0.setStatus_number(4);
    	return setXincoCoreData(in0, in1);
    }

    public com.bluecubs.xinco.core.XincoCoreData undoXincoCoreDataCheckout(com.bluecubs.xinco.core.XincoCoreData in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException {
		/*
    	try {
			XincoDBManager dbm = new XincoDBManager();
			XincoCoreDataServer data;
			XincoCoreACE ace;
			XincoCoreUserServer user = new XincoCoreUserServer(in1.getUsername(), in1.getUserpassword(), dbm);
			//get existing data
			data = new XincoCoreDataServer(in0.getId(), dbm);
			ace = XincoCoreACEServer.checkAccess(user, data.getXinco_core_acl());
			//check status
			if (data.getStatus_number() != 4) {
				dbm.con.close();
				return null;
			}
			if (ace.isWrite_permission()) {
				//update information
				data.setStatus_number(1);
				data.write2DB(dbm);
				dbm.con.close();
				return (XincoCoreData)data;
			} else {
				dbm.con.close();
				return null;
			}
		} catch (Exception e) {
			return null;
		}
		*/
    	in0.setStatus_number(1);
    	return setXincoCoreData(in0, in1);
    }

    public com.bluecubs.xinco.core.XincoCoreData doXincoCoreDataCheckin(com.bluecubs.xinco.core.XincoCoreData in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException {
		/*
    	try {
			XincoDBManager dbm = new XincoDBManager();
			XincoCoreDataServer data;
			XincoCoreACE ace;
			XincoCoreUserServer user = new XincoCoreUserServer(in1.getUsername(), in1.getUserpassword(), dbm);
			//get existing data
			data = new XincoCoreDataServer(in0.getId(), dbm);
			ace = XincoCoreACEServer.checkAccess(user, data.getXinco_core_acl());
			//check status
			if (data.getStatus_number() != 4) {
				dbm.con.close();
				return null;
			}
			if (ace.isWrite_permission()) {
				//update information
				data.setStatus_number(1);
				data.write2DB(dbm);
				dbm.con.close();
				return (XincoCoreData)data;
			} else {
				dbm.con.close();
				return null;
			}
			
		} catch (Exception e) {
			return null;
		}
		*/
    	in0.setStatus_number(1);
    	return setXincoCoreData(in0, in1);
    }

    public byte[] downloadXincoCoreData(com.bluecubs.xinco.core.XincoCoreData in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException {
		try {
			XincoDBManager dbm = new XincoDBManager();
			XincoCoreDataServer data;
			XincoCoreACE ace;
			byte[] byte_array = null;
			String revision = "";
			long total_len = 0;
			boolean useSAAJ = false;
			//MessageContext mc = null;
			//Message m = null;
			//AttachmentPart ap = null;
			InputStream in = null;
			ByteArrayOutputStream out = null;
			XincoCoreUserServer user = new XincoCoreUserServer(in1.getUsername(), in1.getUserpassword(), dbm);
			//load data
			data = new XincoCoreDataServer(in0.getId(), dbm);
			ace = XincoCoreACEServer.checkAccess(user, data.getXinco_core_acl());
			if (ace.isRead_permission()) {
				//determine requested revision if data with only one specific log object is requested
				if ((data.getXinco_core_logs().size() > 1) && (in0.getXinco_core_logs().size() == 1)) {
					//find id of log
					int LogId = 0;
					if ((((XincoCoreLog)in0.getXinco_core_logs().elementAt(0)).getOp_code() == 1) || (((XincoCoreLog)in0.getXinco_core_logs().elementAt(0)).getOp_code() == 5)) {
						LogId = ((XincoCoreLog)in0.getXinco_core_logs().elementAt(0)).getId();
					}
					if (LogId > 0) {
						revision = "-" + LogId;
					}
				}

				// decide whether to read from SOAP attachments or byte array
				/*mc = MessageContext.getCurrentContext();
				m = mc.getRequestMessage();
				if (m.getAttachments().hasNext()) {
					useSAAJ = true;
				} else {
					useSAAJ = false;
				}*/
				in = new CheckedInputStream(new FileInputStream(dbm.config.FileRepositoryPath + data.getId() + revision), new CRC32());
				/*if (useSAAJ) {
					//attach file to SOAP message
					m = mc.getResponseMessage();
					ap = new AttachmentPart();
					ap.setContent(in, "unknown/unknown");
					m.addAttachmentPart(ap);
				} else {
				*/
					out = new ByteArrayOutputStream();
					byte[] buf = new byte[4096];
					int len = 0;
					total_len = 0;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
						total_len = total_len + len;
					}
					in.close();
					byte_array = out.toByteArray();
					out.close();
				//}
				//in.close();
				//check correctness of data
				/*
				//if ((((XincoAddAttribute)data.getXinco_add_attributes().elementAt(1)).getAttrib_unsignedint() != total_len) || (((XincoAddAttribute)data.getXinco_add_attributes().elementAt(2)).getAttrib_varchar().compareTo("" + in.getChecksum().getValue()) == 0)) {
				if (((XincoAddAttribute)data.getXinco_add_attributes().elementAt(1)).getAttrib_unsignedint() != total_len) {
					in.close();
					out.close();
					dbm.con.close();
					return null;
				}
				*/
				//in.close();
				dbm.con.close();
				return byte_array;
			} else {
				dbm.con.close();
				return null;
			}
		} catch (Exception e) {
			return null;
		}
    }

    public int uploadXincoCoreData(com.bluecubs.xinco.core.XincoCoreData in0, byte[] in1, com.bluecubs.xinco.core.XincoCoreUser in2) throws java.rmi.RemoteException {
		try {
			log.debug("creating db manager ...");
			XincoDBManager dbm = new XincoDBManager();
			XincoCoreDataServer data;
			XincoCoreACE ace;
			int i = 0;
			int len = 0;
			long total_len = 0;
			InputStream in = null;
			log.debug("getting user ... ");
			XincoCoreUserServer user = new XincoCoreUserServer(in2.getUsername(), in2.getUserpassword(), dbm);
			//load data
			log.debug("getting data ... ");
			data = new XincoCoreDataServer(in0.getId(), dbm);
			log.debug("getting ACE ... ");
			ace = XincoCoreACEServer.checkAccess(user, data.getXinco_core_acl());
			if (ace.isWrite_permission()) {
				
				// decide whether to read from SOAP attachments or byte array
				/*mc = MessageContext.getCurrentContext();
				m = mc.getCurrentMessage();
				if (m.getAttachments().hasNext()) {
					useSAAJ = true;
				} else {
					useSAAJ = false;
				}
				if (useSAAJ) {
					ap = (AttachmentPart)m.getAttachments().next();
					in = (InputStream)ap.getContent();
				} else {
				*/
				in = new ByteArrayInputStream(in1);
				log.debug("creating CRC ... ");
				CheckedOutputStream out = new CheckedOutputStream(new FileOutputStream(dbm.config.FileRepositoryPath + data.getId()), new CRC32());
				byte[] buf = new byte[4096];
				len = 0;
				total_len = 0;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
					total_len = total_len + len;
				}
				in.close();
				//check correctness of data
				/*
				//if ((((XincoAddAttribute)data.getXinco_add_attributes().elementAt(1)).getAttrib_unsignedint() != total_len) || (((XincoAddAttribute)data.getXinco_add_attributes().elementAt(2)).getAttrib_varchar().compareTo("" + out.getChecksum().getValue()) == 0)) {
				if (((XincoAddAttribute)data.getXinco_add_attributes().elementAt(1)).getAttrib_unsignedint() != total_len) {
					out.close();
					dbm.con.close();
					return 0;
				}
				*/
				out.close();
				
				//dupicate file to preserve current revision
				if (((XincoAddAttribute)data.getXinco_add_attributes().elementAt(3)).getAttrib_unsignedint() == 1) {
					//find id of latest log
					int MaxLogId = 0;
					for (i=0;i<data.getXinco_core_logs().size();i++) {
						if ((((XincoCoreLog)data.getXinco_core_logs().elementAt(i)).getId() > MaxLogId) && ((((XincoCoreLog)data.getXinco_core_logs().elementAt(i)).getOp_code() == 1) || (((XincoCoreLog)data.getXinco_core_logs().elementAt(i)).getOp_code() == 5))) {
							MaxLogId = ((XincoCoreLog)data.getXinco_core_logs().elementAt(i)).getId();
						}
					}
					if (MaxLogId > 0) {
						//copy file
						FileInputStream fcis  = new FileInputStream(new File(dbm.config.FileRepositoryPath + data.getId()));
						FileOutputStream fcos = new FileOutputStream(new File(dbm.config.FileRepositoryPath + data.getId() + "-" + MaxLogId));
						byte[] fcbuf = new byte[4096];
						len = 0;
						while((len=fcis.read(fcbuf))!=-1) {
							fcos.write(fcbuf, 0, len);
						 }
						fcis.close();
						fcos.close();
					}
				}

				//index data and file content
				boolean index_success = false;
				//index_success = XincoIndexer.indexXincoCoreData(data, true, dbm);
				try {
					XincoIndexThread xit = new XincoIndexThread(data, true, dbm);
					xit.start();
					index_success = true;
				} catch (Exception xite) {
					index_success = false;
					xite.printStackTrace();
				}

				//close connection if indexing thread failed
				if (!index_success) {
					dbm.con.close();
				}
				return (int)total_len;
			} else {
				dbm.con.close();
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
    }

    public java.util.Vector findXincoCoreNodes(java.lang.String in0, com.bluecubs.xinco.core.XincoCoreLanguage in1, com.bluecubs.xinco.core.XincoCoreUser in2) throws java.rmi.RemoteException {
		/*
		try {
			XincoDBManager dbm = new XincoDBManager();
			XincoCoreUserServer user = new XincoCoreUserServer(in2.getUsername(), in2.getUserpassword(), dbm);
			java.util.Vector v = XincoCoreNodeServer.findXincoCoreNodes(in0, in1.getId(), dbm);
			return v;
			} catch (Exception e) {
				return null;
		}
		*/
		return null;
    }

    public java.util.Vector findXincoCoreData(java.lang.String in0, com.bluecubs.xinco.core.XincoCoreLanguage in1, com.bluecubs.xinco.core.XincoCoreUser in2) throws java.rmi.RemoteException {
    	XincoDBManager dbm;
    	boolean rp = false;
		java.util.Vector v = new java.util.Vector();
		java.util.Vector v2 = new java.util.Vector();
		
		//check size of keyword string
		if (in0.length() < 1) {
			return null;
		}
		
		//truncate % from start and end of search query
		if (in0.startsWith("%") && in0.endsWith("%")) {
			in0 = in0.substring(1, in0.length()-1);
		}
    	
    	try {
			dbm = new XincoDBManager();
			XincoCoreUserServer user = new XincoCoreUserServer(in2.getUsername(), in2.getUserpassword(), dbm);
			//search on database
			//java.util.Vector tv = XincoCoreDataServer.findXincoCoreData(in0, in1.getId(), true, true, dbm);
			//search on index
			java.util.Vector tv = XincoIndexer.findXincoCoreData(in0, in1.getId(), dbm);
			java.util.Vector tv2 = new java.util.Vector();
			//check immediate permissions
			for (int i=0; i<tv.size();i++) {
				XincoCoreACE ace = XincoCoreACEServer.checkAccess(user, ((XincoCoreData)tv.elementAt(i)).getXinco_core_acl());
				if (ace.isRead_permission()) {
					tv2.add(tv.elementAt(i));
				}
			}
			//check permissions on parents
			java.util.Vector tvp;
			for (int i=0; i<tv2.size();i++) {
				tvp = XincoCoreNodeServer.getXincoCoreNodeParents(((XincoCoreData)tv2.elementAt(i)).getXinco_core_node_id(), dbm);
				rp = true;
				for (int j=0; j<tvp.size();j++) {
					XincoCoreACE ace = XincoCoreACEServer.checkAccess(user, ((XincoCoreNode)tvp.elementAt(j)).getXinco_core_acl());
					if (!ace.isRead_permission()) {
						rp = false;
						break;
					}
				}
				//for complete read permission, add Data + Parent Nodes to result!
				if (rp) {
					v2 = new java.util.Vector();
					v2.add(tv2.elementAt(i));
					for (int j=tvp.size()-1; j>=0;j--) {
						v2.add(tvp.elementAt(j));
					}
					v.add(v2);
				}
			}

			dbm.con.close();
		} catch (Exception e) {
			return null;
		}
		return v;
    }

    public com.bluecubs.xinco.core.XincoCoreNode setXincoCoreNode(com.bluecubs.xinco.core.XincoCoreNode in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException {
		try {
			boolean insertnewnode = false;
			XincoDBManager dbm = new XincoDBManager();
			XincoCoreNodeServer node;
			XincoCoreNodeServer parent_node;
			XincoCoreACE ace;
			XincoCoreACE parent_ace = new XincoCoreACE();
			parent_ace.setWrite_permission(true);
			XincoCoreUserServer user = new XincoCoreUserServer(in1.getUsername(), in1.getUserpassword(), dbm);
			if (in0.getId() <= 0) {
				//insert new node
				insertnewnode = true;
				parent_node = new XincoCoreNodeServer(in0.getXinco_core_node_id(), dbm);
				ace = XincoCoreACEServer.checkAccess(user, parent_node.getXinco_core_acl());
				node = new XincoCoreNodeServer(0, 0, 1, "", 1, dbm);
			} else {
				//update existing node
				node = new XincoCoreNodeServer(in0.getId(), dbm);
				//moving node requires write permission to target node
				if (in0.getXinco_core_node_id() != node.getXinco_core_node_id()) {
					parent_node = new XincoCoreNodeServer(in0.getXinco_core_node_id(), dbm);
					parent_ace = XincoCoreACEServer.checkAccess(user, parent_node.getXinco_core_acl());
				}
				ace = XincoCoreACEServer.checkAccess(user, node.getXinco_core_acl());
			}
			if ((ace.isWrite_permission()) && (parent_ace.isWrite_permission())) {
				//update information
				node.setXinco_core_node_id(in0.getXinco_core_node_id());
				node.setDesignation(in0.getDesignation());
				node.setXinco_core_language(in0.getXinco_core_language());
				node.setStatus_number(in0.getStatus_number());
				node.write2DB(dbm);
				//insert default ACL when inserting new node
				if (insertnewnode) {
					XincoCoreACEServer newace;
					//owner
					newace = new XincoCoreACEServer(0, user.getId(), 0, node.getId(), 0, true, true, true, true);
					newace.write2DB(dbm);
					//admins
					newace = new XincoCoreACEServer(0, 0, 1, node.getId(), 0, true, true, true, true);
					newace.write2DB(dbm);
					//all users
					newace = new XincoCoreACEServer(0, 0, 2, node.getId(), 0, true, false, false, false);
					newace.write2DB(dbm);
					//load new ACL
					node.setXinco_core_acl(XincoCoreACEServer.getXincoCoreACL(node.getId(), "xinco_core_node_id", dbm));
				}
				dbm.con.close();
				return (XincoCoreNode)node;
			} else {
				dbm.con.close();
				return null;
			}
		} catch (Exception e) {
			return null;
		}
    }

    public com.bluecubs.xinco.core.XincoCoreData setXincoCoreData(com.bluecubs.xinco.core.XincoCoreData in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException {
		try {
			boolean insertnewdata = false;
			XincoDBManager dbm = new XincoDBManager();
			XincoCoreDataServer data;
			XincoCoreNodeServer parent_node;
			XincoCoreACE ace;
			XincoCoreACE parent_ace = new XincoCoreACE();
			parent_ace.setWrite_permission(true);
			XincoCoreUserServer user = new XincoCoreUserServer(in1.getUsername(), in1.getUserpassword(), dbm);
			if (in0.getId() <= 0) {
				//insert new node
				insertnewdata = true;
				parent_node = new XincoCoreNodeServer(in0.getXinco_core_node_id(), dbm);
				ace = XincoCoreACEServer.checkAccess(user, parent_node.getXinco_core_acl());
				data = new XincoCoreDataServer(0, 0, 1, 1, "", 1, dbm);
			} else {
				//update existing data
				data = new XincoCoreDataServer(in0.getId(), dbm);
				//moving node requires write permission to target node
				if (in0.getXinco_core_node_id() != data.getXinco_core_node_id()) {
					parent_node = new XincoCoreNodeServer(in0.getXinco_core_node_id(), dbm);
					parent_ace = XincoCoreACEServer.checkAccess(user, parent_node.getXinco_core_acl());
				}
				ace = XincoCoreACEServer.checkAccess(user, data.getXinco_core_acl());
			}
			if ((ace.isWrite_permission()) && (parent_ace.isWrite_permission())) {
				//update information
				data.setXinco_core_node_id(in0.getXinco_core_node_id());
				data.setDesignation(in0.getDesignation());
				data.setXinco_core_language(in0.getXinco_core_language());
				data.setXinco_core_data_type(in0.getXinco_core_data_type());
				data.setXinco_add_attributes(in0.getXinco_add_attributes());
				data.setStatus_number(in0.getStatus_number());
				data.write2DB(dbm);
				
				//index data (not on checkout, only when status = open = 1)
				if (data.getStatus_number() == 1) {
					boolean index_success = XincoIndexer.indexXincoCoreData(data, false, dbm);
				}
				
				//insert default ACL when inserting new node
				if (insertnewdata) {
					XincoCoreACEServer newace;
					//owner
					newace = new XincoCoreACEServer(0, user.getId(), 0, 0, data.getId(), true, true, true, true);
					newace.write2DB(dbm);
					//admins
					newace = new XincoCoreACEServer(0, 0, 1, 0, data.getId(), true, true, true, true);
					newace.write2DB(dbm);
					//all users
					newace = new XincoCoreACEServer(0, 0, 2, 0, data.getId(), true, true, true, true);
					newace.write2DB(dbm);
					//load new ACL
					data.setXinco_core_acl(XincoCoreACEServer.getXincoCoreACL(data.getId(), "xinco_core_data_id", dbm));
				}
				dbm.con.close();
				return (XincoCoreData)data;
			} else {
				dbm.con.close();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    public com.bluecubs.xinco.core.XincoCoreACE setXincoCoreACE(com.bluecubs.xinco.core.XincoCoreACE in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException {
		try {
			XincoDBManager dbm = new XincoDBManager();
			XincoCoreNodeServer node;
			XincoCoreDataServer data;
			XincoCoreACE ace = new XincoCoreACE();
			XincoCoreACEServer newace;
			XincoCoreUserServer user = new XincoCoreUserServer(in1.getUsername(), in1.getUserpassword(), dbm);
			if (in0.getXinco_core_node_id() > 0) {
				node = new XincoCoreNodeServer(in0.getXinco_core_node_id(), dbm);
				ace = XincoCoreACEServer.checkAccess(user, node.getXinco_core_acl());
			}
			if (in0.getXinco_core_data_id() > 0) {
				data = new XincoCoreDataServer(in0.getXinco_core_data_id(), dbm);
				ace = XincoCoreACEServer.checkAccess(user, data.getXinco_core_acl());
			}
			if (ace.isAdmin_permission()) {
				//load ACE or create new one
				if (in0.getId() > 0) {
					newace = new XincoCoreACEServer(in0.getId(), dbm);
				} else {
					newace = new XincoCoreACEServer(0, 0, 0, 0, 0, false, false, false, false);
				}
				//update ACE
				newace.setXinco_core_node_id(in0.getXinco_core_node_id());
				newace.setXinco_core_data_id(in0.getXinco_core_data_id());
				newace.setXinco_core_user_id(in0.getXinco_core_user_id());
				newace.setXinco_core_group_id(in0.getXinco_core_group_id());
				newace.setRead_permission(in0.isRead_permission());
				newace.setWrite_permission(in0.isWrite_permission());
				newace.setExecute_permission(in0.isExecute_permission());
				newace.setAdmin_permission(in0.isAdmin_permission());
				newace.write2DB(dbm);
				dbm.con.close();
				return (XincoCoreACE)newace;
			} else {
				dbm.con.close();
				return null;
			}
		} catch (Exception e) {
			return null;
		}
    }

    public boolean removeXincoCoreACE(com.bluecubs.xinco.core.XincoCoreACE in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException {
		try {
			XincoDBManager dbm = new XincoDBManager();
			XincoCoreNodeServer node;
			XincoCoreDataServer data;
			XincoCoreACE ace = new XincoCoreACE();
			XincoCoreUserServer user = new XincoCoreUserServer(in1.getUsername(), in1.getUserpassword(), dbm);
			if (in0.getXinco_core_node_id() > 0) {
				node = new XincoCoreNodeServer(in0.getXinco_core_node_id(), dbm);
				ace = XincoCoreACEServer.checkAccess(user, node.getXinco_core_acl());
			}
			if (in0.getXinco_core_data_id() > 0) {
				data = new XincoCoreDataServer(in0.getXinco_core_data_id(), dbm);
				ace = XincoCoreACEServer.checkAccess(user, data.getXinco_core_acl());
			}
			if (ace.isAdmin_permission()) {
				XincoCoreACEServer.removeFromDB(in0, dbm);
				dbm.con.close();
				return true;
			} else {
				dbm.con.close();
				return false;
			}
		} catch (Exception e) {
			return false;
		}
    }

    public com.bluecubs.xinco.core.XincoCoreLog setXincoCoreLog(com.bluecubs.xinco.core.XincoCoreLog in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException {
		try {
			XincoDBManager dbm = new XincoDBManager();
			XincoCoreLogServer log;
			XincoCoreUserServer user = new XincoCoreUserServer(in1.getUsername(), in1.getUserpassword(), dbm);
			//load log or create new one
			if (in0.getId() > 0) {
				log = new XincoCoreLogServer(in0.getId(), dbm);
			} else {
				log = new XincoCoreLogServer(0, 0, 0, 0, null, "", 0, 0, 0, "");
			}
			//update log
			log.setXinco_core_data_id(in0.getXinco_core_data_id());
			log.setXinco_core_user_id(in0.getXinco_core_user_id());
			log.setOp_code(in0.getOp_code());
			log.setOp_description(in0.getOp_description());
			log.setOp_datetime(in0.getOp_datetime());
			log.setVersion(in0.getVersion());
			log.write2DB(dbm);
			dbm.con.close();
			return (XincoCoreLog)log;
		} catch (Exception e) {
			return null;
		}
    }

    public com.bluecubs.xinco.core.XincoCoreUser setXincoCoreUser(com.bluecubs.xinco.core.XincoCoreUser in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException {
		try {
			XincoDBManager dbm = new XincoDBManager();
			XincoCoreUserServer user = new XincoCoreUserServer(in1.getUsername(), in1.getUserpassword(), dbm);
			//update user
			user.setUsername(in0.getUsername());
			user.setUserpassword(in0.getUserpassword());
			user.setFirstname(in0.getFirstname());
			user.setName(in0.getName());
			user.setEmail(in0.getEmail());
			user.write2DB(dbm);
			dbm.con.close();
			return (XincoCoreUser)user;
		} catch (Exception e) {
			return null;
		}
    }

    public com.bluecubs.xinco.core.XincoCoreGroup setXincoCoreGroup(com.bluecubs.xinco.core.XincoCoreGroup in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException {
		//not to be implemented yet: advanced administration feature!
        return null;
    }

    public com.bluecubs.xinco.core.XincoCoreLanguage setXincoCoreLanguage(com.bluecubs.xinco.core.XincoCoreLanguage in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException {
		//not to be implemented yet: advanced administration feature!
        return null;
    }

    public com.bluecubs.xinco.core.XincoCoreDataTypeAttribute getXincoCoreDataTypeAttribute(com.bluecubs.xinco.core.XincoCoreDataTypeAttribute in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException {
		//dummy: not to be implemented!
        return null;
    }

    public com.bluecubs.xinco.add.XincoAddAttribute getXincoAddAttribute(com.bluecubs.xinco.add.XincoAddAttribute in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException {
        //dummy: not to be implemented!
        return null;
    }

}
