/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel;

import java.util.Calendar;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class Task implements ITask {

	// the id of this task - attached automaticaly by the PM
	private String taskId;

	// the status of this task
	private String status;

	// the business document
	private String xMLDocument;

	// the digitale signature - if this mode used
	private String docDigSig;

	// some extra info - used to encode the string version of the business
	// document type
	private String extraInfo;
	
	// the status of the signature check
	private boolean signatureStatus;

	// the type of current task
	private int taskType;

	private Calendar deadline;

	private Calendar incoming;

	private String ssid;

	private String documentResponse;

	private String documentResponseDigSig;

	/**
	 * @return Returns the incoming.
	 */
	public Calendar getIncoming() {
		return incoming;
	}

	/**
	 * @param incoming
	 *            The incoming to set.
	 */
	public void setIncoming(Calendar incoming) {
		this.incoming = incoming;
	}

	/**
	 * @return Returns the deadline.
	 */
	public Calendar getDeadline() {
		return deadline;
	}

	/**
	 * @param deadline
	 *            The deadline to set.
	 */
	public void setDeadline(Calendar deadline) {
		this.deadline = deadline;
	}

	/**
	 * @return Returns the taskType.
	 */
	public int getTaskType() {
		return taskType;
	}

	/**
	 * @param taskType
	 *            The taskType to set.
	 */
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.ITask#getTaskId()
	 */
	public String getTaskId() {
		return this.taskId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.ITask#setTaskId(java.lang.String)
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.ITask#getStatus()
	 */
	public String getStatus() {
		return this.status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.ITask#setStatus(java.lang.String)
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.ITask#getXMLDocument()
	 */
	public String getXMLDocument() {
		return this.xMLDocument;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.ITask#setXMLDocument(java.lang.String)
	 */
	public void setXMLDocument(String xmlDocument) {
		this.xMLDocument = xmlDocument;
	}

	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append("task id: ").append(this.taskId).append("\n");
		b.append("status : ").append(this.status);
		return b.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.ITask#getDocDigSig()
	 */
	public String getDocDigSig() {
		return this.docDigSig;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.ITask#setDocDigSig(java.lang.String)
	 */
	public void setDocDigSig(String docDigSig) {
		this.docDigSig = docDigSig;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.ITask#getExtraInfo()
	 */
	public String getExtraInfo() {
		return this.extraInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.ITask#setExtraInfo()
	 */
	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

	/**
	 * @return Returns the signatureStatus.
	 */
	public boolean isSignatureStatus() {
		return signatureStatus;
	}

	/**
	 * @param signatureStatus
	 *            The signatureStatus to set.
	 */
	public void setSignatureStatus(boolean signatureStatus) {
		this.signatureStatus = signatureStatus;
	}

	/**
	 * @return Returns the documentResponse.
	 */
	public String getDocumentResponse() {
		return documentResponse;
	}

	/**
	 * @param documentResponse
	 *            The documentResponse to set.
	 */
	public void setDocumentResponse(String documentResponse) {
		this.documentResponse = documentResponse;
	}

	/**
	 * @return Returns the documentResponseDigSig.
	 */
	public String getDocumentResponseDigSig() {
		return documentResponseDigSig;
	}

	/**
	 * @param documentResponseDigSig
	 *            The documentResponseDigSig to set.
	 */
	public void setDocumentResponseDigSig(String documentResponseDigSig) {
		this.documentResponseDigSig = documentResponseDigSig;
	}

	/**
	 * @return Returns the ssid.
	 */
	public String getSsid() {
		return ssid;
	}

	/**
	 * @param ssid
	 *            The ssid to set.
	 */
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
}