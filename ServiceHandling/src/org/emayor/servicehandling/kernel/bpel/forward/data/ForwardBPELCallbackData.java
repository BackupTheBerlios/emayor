/*
 * Created on Apr 19, 2005
 */
package org.emayor.servicehandling.kernel.bpel.forward.data;

import java.io.Serializable;

import org.xmlsoap.schemas.ws.addressing.MessageID;
import org.xmlsoap.schemas.ws.addressing.ReplyTo;

/**
 * @author tku
 */
public class ForwardBPELCallbackData implements Serializable {
    private String ssid;

    private String uid;

    private MessageID messageID;

    private ReplyTo replyTo;

    /**
     * @return Returns the messageID.
     */
    public MessageID getMessageID() {
        return messageID;
    }

    /**
     * @param messageID
     *            The messageID to set.
     */
    public void setMessageID(MessageID messageID) {
        this.messageID = messageID;
    }

    /**
     * @return Returns the replyTo.
     */
    public ReplyTo getReplyTo() {
        return replyTo;
    }

    /**
     * @param replyTo
     *            The replyTo to set.
     */
    public void setReplyTo(ReplyTo replyTo) {
        this.replyTo = replyTo;
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

    /**
     * @return Returns the uid.
     */
    public String getUid() {
        return uid;
    }

    /**
     * @param uid
     *            The uid to set.
     */
    public void setUid(String uid) {
        this.uid = uid;
    }
}