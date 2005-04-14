/*
 * Created on Feb 24, 2005
 */
package org.emayor.servicehandling.kernel;

import org.apache.log4j.Logger;

/**
 * @author tku
 */
public class ForwardMessage implements IForwardMessage {
	private static Logger log = Logger.getLogger(ForwardMessage.class);
	
	public static final int REQUEST  = 1;
	public static final int RESPONSE = 2;
	
	/* REQUEST/RESPONSE */
	private int type;
	
	/* documents delivered for service */
	private String[] documents;
	
	/* session ID for reply */
	private String replyID;
	
	/* service for reply */
	private String replyService;
	
	/* address to reply to */
	private String replyAddress;
	
	/**
	 * @return Returns the documents.
	 */
	public String[] getDocuments() {
		return documents;
	}
	/**
	 * @param documents The documents to set.
	 */
	public void setDocuments(String[] documents) {
		this.documents = documents;
	}
	/**
	 * @return Returns the replyAddress.
	 */
	public String getReplyAddress() {
		return replyAddress;
	}
	/**
	 * @param replyAddress The replyAddress to set.
	 */
	public void setReplyAddress(String replyAddress) {
		this.replyAddress = replyAddress;
	}
	/**
	 * @return Returns the replyID.
	 */
	public String getReplyID() {
		return replyID;
	}
	/**
	 * @param replyID The replyID to set.
	 */
	public void setReplyID(String replyID) {
		this.replyID = replyID;
	}
	/**
	 * @return Returns the replyService.
	 */
	public String getReplyService() {
		return replyService;
	}
	/**
	 * @param replyService The replyService to set.
	 */
	public void setReplyService(String replyService) {
		this.replyService = replyService;
	}
	/**
	 * @return Returns the type.
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type The type to set.
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	
}