/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class Task implements ITask {

	private String taskId;
	private String status;
	private String xMLDocument;
	
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

}