/* $Id: FinderFromMethod.java,v 1.1 2005/11/16 10:51:49 emayor Exp $
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
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.plugins.RuleFinder;
import org.apache.commons.digester.plugins.RuleLoader;
import org.apache.commons.digester.plugins.PluginException;

/**
 * A rule-finding algorithm which expects the caller to specify a methodname
 * as a plugin property, where the method exists on the plugin class.
 *
 * @since 1.6
 */

public class FinderFromMethod extends RuleFinder {
    /**
     * Xml attribute that needs to be present on a plugin declaration
     * in order to specify the method to load rules from.
     */
    public static String DFLT_METHOD_ATTR = "method";
    
    /** See {@link #findLoader}. */
    private String methodAttr;
    
    /** Constructor. */
    public FinderFromMethod() {
        this(DFLT_METHOD_ATTR);
    }

    /** See {@link #findLoader}. */
    public FinderFromMethod(String methodAttr) { 
        this.methodAttr = methodAttr;
    }
    
    /**
     * If there exists a property with the name matching constructor param
     * methodAttr, then locate the appropriate Method on the plugin class 
     * and return an object encapsulating that info.
     * <p>
     * If there is no matching property provided, then just return null.
     * <p>
     * The returned object (when non-null) will invoke the target method
     * on the plugin class whenever its addRules method is invoked. The
     * target method is expected to have the following prototype:
     * <code> public static void xxxxx(Digester d, String patternPrefix); </code>
     */
    public RuleLoader findLoader(Digester d, Class pluginClass, Properties p)
                        throws PluginException {

        String methodName = p.getProperty(methodAttr);
        if (methodName == null) {
            // nope, user hasn't requested dynamic rules to be loaded
            // from a specific class.
            return null;
        }
        
        return new LoaderFromClass(pluginClass, methodName);
    }
}

