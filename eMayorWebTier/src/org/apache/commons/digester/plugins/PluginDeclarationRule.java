/* $Id: PluginDeclarationRule.java,v 1.1 2005/11/16 10:51:50 emayor Exp $
 *
 * Copyright 2003-2004 The Apache Software Foundation.
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

package org.apache.commons.digester.plugins;

import java.util.Properties;

import org.apache.commons.digester.Rule;
import org.apache.commons.digester.Digester;

import org.apache.commons.logging.Log;

/**
 * A Digester rule which allows the user to pre-declare a class which is to
 * be referenced later at a plugin point by a PluginCreateRule.
 * <p>
 * Normally, a PluginDeclarationRule is added to a Digester instance with
 * the pattern "{root}/plugin" or "* /plugin" where {root} is the name of 
 * the root tag in the input document.
 *
 * @since 1.6
 */

public class PluginDeclarationRule extends Rule {

    //------------------- constructors ---------------------------------------

    /** constructor  */
    public PluginDeclarationRule() {
        super();
    }

    //------------------- methods --------------------------------------------

    /**
     * Invoked upon reading a tag defining a plugin declaration. The tag
     * must have the following mandatory attributes:
     * <ul>
     *   <li> id </li>
     *   <li> class </li>
     * </ul>
     *
     *@param namespace The xml namespace in which the xml element which
     * triggered this rule resides.
     *@param name The name of the xml element which triggered this rule.
     *@param attributes The set of attributes on the xml element which
     * triggered this rule.
     *@exception java.lang.Exception
     */

    public void begin(String namespace, String name,
                      org.xml.sax.Attributes attributes)
                      throws java.lang.Exception {
                 
        int nAttrs = attributes.getLength();
        Properties props = new Properties();
        for(int i=0; i<nAttrs; ++i) {
            String key = attributes.getLocalName(i);
            if ((key == null) || (key.length() == 0)) {
                key = attributes.getQName(i);
            }
            String value = attributes.getValue(i);
            props.setProperty(key, value);
        }
        
        try {
            declarePlugin(digester, props);
        } catch(PluginInvalidInputException ex) {
            throw new PluginInvalidInputException(
                "Error on element [" + digester.getMatch() + 
                "]: " + ex.getMessage());
        }
    }
    
    public static void declarePlugin(Digester digester, Properties props)
    throws PluginException {
        
        Log log = digester.getLogger();
        boolean debug = log.isDebugEnabled();
        
        String id = props.getProperty("id");
        String pluginClassName = props.getProperty("class");
        
        if (id == null) {
            throw new PluginInvalidInputException(
                "mandatory attribute id not present on plugin declaration");
        }

        if (pluginClassName == null) {
            throw new PluginInvalidInputException(
                "mandatory attribute class not present on plugin declaration");
        }

        Declaration newDecl = new Declaration(pluginClassName);
        newDecl.setId(id);
        newDecl.setProperties(props);

        PluginRules rc = (PluginRules) digester.getRules();
        PluginManager pm = rc.getPluginManager();

        newDecl.init(digester, pm);
        pm.addDeclaration(newDecl);
        
        // Note that it is perfectly safe to redeclare a plugin, because
        // the declaration doesn't add any rules to digester; all it does
        // is create a RuleLoader instance whch is *capable* of adding the
        // rules to the digester.
    }
}

