package org.eMayor.ServiceHandling.PrintingUtility.ejb;

/** PrintJListener.java
 * Date: May 2002
 * Author: Rajesh Ramchandani
 *         Ck Prasad
 */

import javax.print.event.PrintJobEvent;
import javax.print.event.PrintJobListener;

import org.apache.log4j.Logger;

/**
 *
 * Reports attribute changes in the monitored PrintJob
 *
 */
 

public class PrintServiceJobListener implements PrintJobListener {
	
	private static final Logger log = Logger.getLogger(PrintServiceJobListener.class);
    static PrintJobEvent spje = null;
    static int setype = 0;
    
    /** Reports a DATA_TRANSFER_COMPLETE PrintJobEvent
     * @param pje A javax.print.event.PrintJobEvent
     */    
    public void printDataTransferCompleted(PrintJobEvent pje) {
        int etype = pje.getPrintEventType();
        //log.info("Data Transfer completed by printing job with identifier: " + etype);
        this.setype = etype;
        try {
            Thread.sleep(1000);
        } catch(Exception e) {
            log.error(e.getMessage());
        }
    }
    
    /** Reports a JOB_COMPLETE PrintJobEvent
     * @param pje A javax.print.event.PrintJobEvent
     */    
    public void printJobCompleted(PrintJobEvent pje) {
        log.info("Print Job completed successfully:" + pje);
        this.spje = pje;
    }
    
    /** Reports a JOB_FAILED PrintJobEvent
     * @param pje A javax.print.event.PrintJobEvent
     */    
    public void printJobFailed(PrintJobEvent pje) {
        log.info("Print job failed:" + pje);
        this.spje = pje;
    }
    
    /** Reports a NO_MORE_EVENTS PrintJobEvent
     * @param pje A javax.print.event.PrintJobEvent
     */    
    public void printJobNoMoreEvents(PrintJobEvent pje) {
        log.info("All printing events of print job : " + pje.getPrintEventType() + " completed.");
        this.spje = pje;
    }
    
    /** Reports a REQUIRES_ATTENTION PrintJobEvent
     * @param pje A javax.print.event.PrintJobEvent
     */    
    public void printJobRequiresAttention(PrintJobEvent pje) {
        log.info("Print job requires attention: " + pje);
        this.spje = pje;
    }
    
    /** Reports a JOB_CANCELED PrintJobEvent
     * @param pje A javax.print.event.PrintJobEvent
     */    
    public void printJobCanceled(PrintJobEvent pje) {
        log.info("Print job cancelled.");
        this.spje = pje;
    }
    
    /** Gets the PrintJobEvent fired
     * @return The javax.print.event.PrintJobEvent fired
     */    
    public PrintJobEvent getSpje() {
        
    	//System.out.println("Inside PJL spje=", + spje);
        return spje;
    }
    
    /** Gets the type of PrintJobEvent fired
     * @return The type of javax.print.event.PrintJobEvent fired
     */    
    public int getSetype() {
        String str = Integer.toString(setype);
        //System.out.println("Inside PJL setype:");
        //System.out.println(str);
        return setype;
    }
}

