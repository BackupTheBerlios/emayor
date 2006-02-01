/* $Id: FinderFromResource.java,v 1.1 2006/02/01 15:32:57 emayor Exp $
 *
 * Copyright 2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
 
package org.apache.commons.digester.plugins.strategies;

import java.util.Properties;
import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.plugins.RuleFinder;
import org.apache.commons.digester.plugins.RuleLoader;
import org.apache.commons.digester.plugins.PluginException;

/**
 * A rule-finding algorithm which expects the user to specify a resource
 * name (ie a file in the classpath). The file is expected to contain Digester
 * rules in xmlrules format.
 *
 * @since 1.6
 */

public class FinderFromResource extends RuleFinder {
    /** 
     * Name of xml attribute on the plugin declaration which is used
     * to configure rule-loading for that declaration. 
     */
    public static String DFLT_RESOURCE_ATTR = "resource";
    
    /** See {@link #findLoader}. */
    private String resourceAttr;
    
    /** Constructor. */
    public FinderFromResource() {
        this(DFLT_RESOURCE_ATTR);
    }

    /** See {@link #findLoader}. */
    public FinderFromResource(String resourceAttr) { 
        this.resourceAttr = resourceAttr;
    }
    
    /**
     * If there exists a property with the name matching constructor param
     * resourceAttr, then load that file, run it through the xmlrules
     * module and return an object encapsulating those rules.
     * <p>
     * If there is no matching property provided, then just return null.
     * <p>
     * The returned object (when non-null) will add the selected rules to
     * the digester whenever its addRules method is invoked.
     */
    public RuleLoader findLoader(Digester d, Class pluginClass, Properties p)
                        throws PluginException {

        String resourceName = p.getProperty(resourceAttr);
        if (resourceName == null) {
            // nope, user hasn't requested dynamic rules to be loaded
            // from a specific file.
            return null;
        }
        
        InputStream is = 
            pluginClass.getClassLoader().getResourceAsStream(
                resourceName);

        if (is == null) {
            throw new PluginException(
                "Resource " + resourceName + " not found.");
        }
        
         return loadRules(d, pluginClass, is, resourceName);
    }
    
    /**
     * Open the specified resource file (ie a file in the classpath, 
     * including being within a jar in the classpath), run it through
     * the xmlrules module and return an object encapsulating those rules.
     * 
     * @param d is the digester into which rules will eventually be loaded.
     * @param pluginClass is the class whose xml params the rules are parsing.
     * @param is is where the xmlrules will be read from, and must be non-null.
     * @param resourceName is a string describing the source of the xmlrules,
     *  for use in generating error messages.
     */
    public static RuleLoader loadRules(Digester d, Class pluginClass, 
                        InputStream is, String resourceName)
                        throws PluginException {

        try {
            RuleLoader loader = new LoaderFromStream(is);
            return loader;
        } catch(Exception e) {
            throw new PluginException(
                "Unable to load xmlrules from resource [" + 
                resourceName + "]", e);
        } finally {
            try {
                is.close();
            } catch(java.io.IOException ioe) {
                throw new PluginException(
                    "Unable to close stream for resource [" + 
                    resourceName + "]", ioe);
            }
        }
    }
}

