/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel;

import java.io.Serializable;

/**
 * It is simple holder for an array of tasks. The class is realtive
 * secure against NullPointerException - the internal array of
 * Task objects will be preset to an array of the length 0.
 * 
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"><font size="-1">Tomasz Kusber</font></a><font size="-1"> FHI FOKUS (C)</font>
 */
public class Tasks implements Serializable {
	private Task[] tasks;
	
	public Tasks() {
		this.tasks = new Task[0];
	}
	
	/**
	 * Set ethe array of task objects.
	 * @param tasks
	 */
	public void setTasks(Task[] tasks) {
		this.tasks = tasks;
	}
	
	/**
	 * The current array of the Task object will be deliverd.
	 * @return
	 */
	public Task[] getTasks() {
		return this.tasks;
	}
}
