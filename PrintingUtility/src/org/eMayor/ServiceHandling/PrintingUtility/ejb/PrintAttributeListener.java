package org.eMayor.ServiceHandling.PrintingUtility.ejb;

/** PrintAListener.java
 * Date: May 2002
 * Author: Rajesh Ramchandani
 *         Ck Prasad
 */

import javax.print.attribute.Attribute;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.event.PrintServiceAttributeEvent;
import javax.print.event.PrintServiceAttributeListener;

import org.apache.log4j.Logger;

/**
 *
 * Reports attribute changes in the monitored PrintService
 *
 */

public class PrintAttributeListener implements PrintServiceAttributeListener {
    
    /** Reports a change in the monitored PrintService's attributes
     * @param e A javax.print.event.PrintServiceAttributeEvent
     */    
	private static final Logger log = Logger.getLogger(PrintAttributeListener.class); 
	
	public void attributeUpdate(PrintServiceAttributeEvent e){
        PrintServiceAttributeSet psas = e.getAttributes();
        Attribute[] nattr = psas.toArray();
        for (int i = 0; i < nattr.length; i++) {
        	//log.info("Printer " + e.getPrintService() + " \"" + nattr[i].getName() + "\": " + nattr[i]);
        }
    }
}

