/*
 * Created on Apr 12, 2005
 */
package org.emayor.servicehandling.kernel;

import java.rmi.Remote;

/**
 * @author tku
 */
public interface IServiceCallbackManager extends Remote {
    public String onResult(ServiceCallbackData result);
}