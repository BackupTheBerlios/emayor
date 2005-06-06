/*
 * Created on Jun 2, 2005
 */
package org.emayor.servicehandling.kernel;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * @author tku
 */
public class SessionInfo implements Serializable {
    private static final Logger log = Logger.getLogger(SessionInfo.class);

    private static final String DEF_DATE_PATTERN = "EEE, MMM dd, yyyy";

    private String sessionId;

    private Date startDate;

    /**
     *  
     */
    public SessionInfo() {
    }

    public SessionInfo(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return Returns the sessionId.
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId
     *            The sessionId to set.
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return Returns the startDate.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate
     *            The startDate to set.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStartDateAsString() {
        SimpleDateFormat format = new SimpleDateFormat(DEF_DATE_PATTERN);
        String ret = format.format(this.startDate);
        return ret;
    }
}