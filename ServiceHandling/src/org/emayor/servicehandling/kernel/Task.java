/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel;

import org.apache.log4j.Logger;

import com.oracle.services.bpel.task._task;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class Task implements ITask {
	private static Logger log = Logger.getLogger(Task.class);

	private String taskId;

	private String status;

	private String xMLDocument;

	private _task originalTask;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.ITask#getOriginalTask()
	 */
	public _task getOriginalTask() {
		log.debug("-> start processing ...");
		return this.originalTask;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.ITask#setOriginalTask(com.oracle.services.bpel.task._task)
	 */
	public void setOriginalTask(_task task) {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");
		this.originalTask = task;
		log.debug("-> ... processing DONE!");
	}
	
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append("task id: ").append(this.taskId).append("\n");
		b.append("status : ").append(this.status);
		return b.toString();
	}

}