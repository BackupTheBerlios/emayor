package org.emayor.ContentRouting.ejb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Manages the configuration of the UDDI4J library.
 * 
 * @author Nikolaos Oikonomidis, University of Siegen
 *  
 */
public class UDDI4JConfigurator {

    /**
     * Standard constructor.
     */
    public UDDI4JConfigurator() {

    }

    /**
     * @return Returns a Properties object that contains the properties for
     *         UDDI4J.
     * @throws FileNotFoundException
     *             if the configuration file is unavailable.
     * @throws IOException
     *             in case of any I/O malfunction.
     */
    public Properties getUDDI4JProperties() throws FileNotFoundException,
            IOException {

        Properties config = new Properties();
        InputStream configFile = this.getClass().getResourceAsStream(
                "ContentRouting.conf");

        if (configFile == null) {
            throw new FileNotFoundException();
        }

        try {
            config.load(configFile);
        } catch (IOException e) {
            throw (e);
        }

        return config;
    }
}