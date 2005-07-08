/*
 * Created on May 11, 2005
 */
package org.emayor.servicehandling.kernel.forward;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.forward.ForwardMessage;

/**
 * @author tku
 */
public class ForwardingRepository {
    private final static Logger log = Logger
            .getLogger(ForwardingRepository.class);

    private static ForwardingRepository _self;

    private HashMap ssid2ForwardRequest;

    /**
     *  
     */
    private ForwardingRepository() {
        this.ssid2ForwardRequest = new HashMap();
    }

    public static final ForwardingRepository getInstance() {
        if (_self == null)
            _self = new ForwardingRepository();
        return _self;
    }

    public synchronized void storeForwardRequest(String ssid,
            ForwardMessage forwardMessage) throws ForwardingRepositoryException {
        log.debug("-> start processing ...");
        if (this.ssid2ForwardRequest.containsKey(ssid))
            throw new ForwardingRepositoryException(
                    "the key already exists ssid=" + ssid);
        this.ssid2ForwardRequest.put(ssid, forwardMessage);
        log.debug("-> ... processing DONE!");
    }

    public synchronized ForwardMessage retrieveForwardRequest(String ssid)
            throws ForwardingRepositoryException {
        log.debug("-> start processing ...");
        ForwardMessage ret = null;
        if (!this.ssid2ForwardRequest.containsKey(ssid))
            throw new ForwardingRepositoryException(
                    "the key doesn't exist, ssid: " + ssid);
        ret = (ForwardMessage)this.ssid2ForwardRequest.get(ssid);
        log.debug("-> ... processing DONE!");
        return ret;
    }
}