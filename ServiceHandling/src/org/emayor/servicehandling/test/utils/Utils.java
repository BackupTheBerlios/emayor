/*
 * Created on Mar 22, 2005
 */
package org.emayor.servicehandling.test.utils;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.Task;
import org.emayor.servicehandling.utclient.CVDocumentTypes;

/**
 * @author tku
 */
public class Utils {
    private static final Logger log = Logger.getLogger(Utils.class);

    public static Task[] setTypeToTasks(Task[] in) {
        log.debug("-> start processing ...");
        for (int i = 0; i < in.length; i++) {
            switch (in[i].getTaskType()) {
            case CVDocumentTypes.CV_BANK_ACCOUNT_CHANGE_REQUEST:
                in[i].setExtraInfo("Bank account change request");
                break;
            case CVDocumentTypes.CV_FAMILY_RESIDENCE_CERTIFICATE_REQUEST:
                in[i].setExtraInfo("Family residence certification request");
                break;
            case CVDocumentTypes.CV_RESIDENCE_CERTIFICATE_REQUEST:
                in[i].setExtraInfo("Residence certification request");
                break;
            case CVDocumentTypes.CV_RESIDENCE_DOCUMENT:
                in[i].setExtraInfo("Residence document");
                break;
            case CVDocumentTypes.CV_TAXES_MANAGEMENT_ACTIVATION_REQUEST:
                in[i].setExtraInfo("Tax management activation request");
                break;
            case CVDocumentTypes.CV_USER_REGISTRATION_REQUEST:
                in[i].setExtraInfo("User registration request");
                break;
            case CVDocumentTypes.CV_NEGATIVE_RESIDENCE_CERTIFICATE_DOCUMENT:
                in[i].setExtraInfo("Negative Residence Certification Document");
                break;
            }
        }
        log.debug("-> ... processing DONE!");
        return in;
    }

}