/*
 * $ Created on Dec 2, 2004 by tku $
 */
package org.emayor.servicehandling.kernel;

import java.io.Serializable;
import java.util.Properties;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public interface IServiceInfo extends Serializable {

    public static final String IS_ACTIVE = "YES";

    public static final String IS_NOT_ACTIVE = "NO";

    public String getServiceId();

    public String getServiceVersion();

    public String getServiceName();

    public String getServiceClassName();

    public String getServiceFactoryClassName();

    public String getServiceDescription();

    public String getServiceEndpoint();

    public boolean unmarshall(Properties props);

    public Properties marshall();

    public boolean isActive();

    public void setActive(boolean active);
}