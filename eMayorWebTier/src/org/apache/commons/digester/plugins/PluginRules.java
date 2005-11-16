/* $Id: PluginRules.java,v 1.1 2005/11/16 10:51:50 emayor Exp $
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

import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.digester.Rules;
import org.apache.commons.digester.RulesBase;
import org.apache.commons.logging.Log;

/**
 * A custom digester Rules manager which must be used as the Rules object
 * when using the plugins module functionality.
 * <p>
 * During parsing, a linked list of PluginCreateRule instances develop, and
 * this list also acts like a stack. The original instance that was set before 
 * the Digester started parsing is always at the tail of the list, and the
 * Digester always holds a reference to the instance at the head of the list
 * in the rules member. Initially, this list/stack holds just one instance,
 * ie head and tail are the same object.
 * <p>
 * When the start of an xml element causes a PluginCreateRule to fire, a new 
 * PluginRules instance is created and inserted at the head of the list (ie
 * pushed onto the stack of Rules objects). Digester.getRules() therefore
 * returns this new Rules object, and any custom rules associated with that 
 * plugin are added to that instance. 
 * <p>
 * When the end of the xml element is encountered (and therefore the 
 * PluginCreateRule end method fires), the stack of Rules objects is popped,
 * so that Digester.getRules returns the previous Rules object. 
 *
 * @since 1.6
 */

public class PluginRules implements Rules {
                                               
    /**
     * The Digester instance with which this Rules instance is associated.
     */
    protected Digester digester = null;

    /** 
     * The (optional) object which generates new rules instances.
     */
    private RulesFactory rulesFactory;

    /** 
     * The rules implementation that we are "enhancing" with plugins
     * functionality, as per the Decorator pattern.
     */
    private Rules decoratedRules;
    
    /** Object which contains information about all known plugins. */
    private PluginManager pluginManager;

    /**
     * The path below which this rules object has responsibility.
     * For paths shorter than or equal the mountpoint, the parent's 
     * match is called.
     */
    private String mountPoint = null;
    
    /**
     * The Rules object that holds rules applying "above" the mountpoint,
     * ie the next Rules object down in the stack.
     */
    private PluginRules parent = null;
    
    /**
     * A reference to the object that holds all data which should only
     * exist once per digester instance.
     */
    private PluginContext pluginContext = null;
    
    // ------------------------------------------------------------- Constructor
    
    /**
     * Constructor for top-level Rules objects. Exactly one of these must
     * be created and installed into the Digester instance as the Rules
     * object before parsing starts.
     */
    public PluginRules() {
        this(new RulesBase());
    }

    /**
     * Constructor for top-level Rules object which handles rule-matching
     * using the specified implementation.
     */
    public PluginRules(Rules decoratedRules) {
        this.decoratedRules = decoratedRules;

        pluginContext = new PluginContext();
        pluginManager = new PluginManager(pluginContext);
    }

    /**
     * Constructs a Rules instance which has a parent Rules object 
     * (which is different from having a delegate rules object). 
     * <p>
     * One of these is created each time a PluginCreateRule's begin method 
     * fires, in order to manage the custom rules associated with whatever 
     * concrete plugin class the user has specified.
     *
     * @param digester is the object this rules will be associated with.
     * @param mountPoint is the digester match path for the element 
     * matching a PluginCreateRule which caused this "nested parsing scope"
     * to begin. This is expected to be equal to digester.getMatch().
     * @param parent must be non-null.
     * @param pluginClass is the plugin class whose custom rules will be
     * loaded into this new PluginRules object.
     */
     PluginRules(
     Digester digester, 
     String mountPoint, 
     PluginRules parent, 
     Class pluginClass) 
     throws PluginException {
        // no need to set digester or decoratedRules.digester,
        // because when Digester.setRules is called, the setDigester
        // method on this object will be called.
        
        this.digester = digester;
        this.mountPoint = mountPoint;
        this.parent = parent;
        this.rulesFactory = parent.rulesFactory;
        
        if (rulesFactory == null) {
            decoratedRules = new RulesBase();
        } else {
            decoratedRules = rulesFactory.newRules(digester, pluginClass);
        }
        
        pluginContext = parent.pluginContext;
        pluginManager = new PluginManager(parent.pluginManager);
    }
    
    // ------------------------------------------------------------- Properties

    /**
     * Return the parent Rules object.
     */
    public Rules getParent() {
        return parent;
    }
    
    /**
     * Return the Digester instance with which this instance is associated.
     */
    public Digester getDigester() {
        return digester;
    }

    /**
     * Set the Digester instance with which this Rules instance is associated.
     *
     * @param digester The newly associated Digester instance
     */
    public void setDigester(Digester digester) {
        this.digester = digester;
        decoratedRules.setDigester(digester);
    }

    /**
     * Return the namespace URI that will be applied to all subsequently
     * added <code>Rule</code> objects.
     */
    public String getNamespaceURI() {
        return decoratedRules.getNamespaceURI();
    }

    /**
     * Set the namespace URI that will be applied to all subsequently
     * added <code>Rule</code> objects.
     *
     * @param namespaceURI Namespace URI that must match on all
     *  subsequently added rules, or <code>null</code> for matching
     *  regardless of the current namespace URI
     */
    public void setNamespaceURI(String namespaceURI) {
        decoratedRules.setNamespaceURI(namespaceURI);
    }

    /**
     * Return the object which "knows" about all declared plugins.
     * 
     * @return The pluginManager value
     */
    public PluginManager getPluginManager() {
        return pluginManager;
    }
    
    /**
     * See {@link PluginContext#getRuleFinders}.
     */
    public List getRuleFinders() {
        return pluginContext.getRuleFinders();
    }
    
    /**
     * See {@link PluginContext#setRuleFinders}.
     */
    public void setRuleFinders(List ruleFinders) {
        pluginContext.setRuleFinders(ruleFinders);
    }
    
    /**
     * Return the rules factory object (or null if one has not been specified).
     */
    public RulesFactory getRulesFactory() {
        return rulesFactory;
    }
    
    /**
     * Set the object which is used to generate the new Rules instances created
     * to hold and process the rules associated with each plugged-in class.
     */
    public void setRulesFactory(RulesFactory factory) {
        rulesFactory = factory;
    }
    
    // --------------------------------------------------------- Public Methods

    /**
     * This package-scope method is used by the PluginCreateRule class to
     * get direct access to the rules that were dynamically added by the
     * plugin. No other class should need access to this object.
     */
    Rules getDecoratedRules() {
        return decoratedRules;
    }
    
    /**
     * Return the list of rules registered with this object, in the order
     * they were registered with this object.
     * <p>
     * Note that Rule objects stored in parent Rules objects are not
     * returned by this method.
     * 
     * @return list of all Rule objects known to this Rules instance.
     */
    public List rules() {
        return decoratedRules.rules();
    }

    /**
     * Register a new Rule instance matching the specified pattern.
     * 
     * @param pattern Nesting pattern to be matched for this Rule.
     * This parameter treats equally patterns that begin with and without
     * a leading slash ('/').
     * @param rule Rule instance to be registered
     */
    public void add(String pattern, Rule rule) {
        Log log = LogUtils.getLogger(digester);
        boolean debug = log.isDebugEnabled();
        
        if (debug) {
            log.debug("add entry" + ": mapping pattern [" + pattern + "]" + 
                  " to rule of type [" + rule.getClass().getName() + "]");
        }
        
        // allow patterns with a leading slash character
        if (pattern.startsWith("/"))
        {
            pattern = pattern.substring(1);
        }

        if (mountPoint != null) {
            if (!pattern.equals(mountPoint)
              && !pattern.startsWith(mountPoint + "/")) {
                // This can only occur if a plugin attempts to add a
                // rule with a pattern that doesn't start with the
                // prefix passed to the addRules method. Plugins mustn't
                // add rules outside the scope of the tag they were specified
                // on, so refuse this.
                
                // alas, can't throw exception
                log.warn(
                    "An attempt was made to add a rule with a pattern that"
                    + "is not at or below the mountpoint of the current"
                    + " PluginRules object."
                    + " Rule pattern: " + pattern
                    + ", mountpoint: " + mountPoint
                    + ", rule type: " + rule.getClass().getName());
                return;
            }
        }
        
        decoratedRules.add(pattern, rule);

        if (rule instanceof InitializableRule) {
            try {
                ((InitializableRule)rule).postRegisterInit(pattern);
            } catch (PluginConfigurationException e) {
                // Currently, Digester doesn't handle exceptions well
                // from the add method. The workaround is for the
                // initialisable rule to remember that its initialisation
                // failed, and to throw the exception when begin is
                // called for the first time.
                if (debug) {
                    log.debug("Rule initialisation failed", e);
                }
                // throw e; -- alas, can't do this
                return;
            }
        }
        
        if (debug) {
            log.debug("add exit" + ": mapped pattern [" + pattern + "]" + 
                  " to rule of type [" + rule.getClass().getName() + "]");
        }
    }

    /**
     * Clear all rules.
     */
    public void clear() {
        decoratedRules.clear();
    }
    
    /**
     * Return a List of all registered Rule instances that match the specified
     * nesting pattern, or a zero-length List if there are no matches.  If more
     * than one Rule instance matches, they <strong>must</strong> be returned
     * in the order originally registered through the <code>add()</code>
     * method.
     *
     * @param path the path to the xml nodes to be matched.
     *
     * @deprecated Call match(namespaceURI,pattern) instead.
     */
    public List match(String path) {
        return (match(null, path));
    }

    /**
     * Return a List of all registered Rule instances that match the specified
     * nodepath, or a zero-length List if there are no matches.  If more
     * than one Rule instance matches, they <strong>must</strong> be returned
     * in the order originally registered through the <code>add()</code>
     * method.
     * <p>
     * @param namespaceURI Namespace URI for which to select matching rules,
     *  or <code>null</code> to match regardless of namespace URI
     * @param path the path to the xml nodes to be matched.
     */
    public List match(String namespaceURI, String path) {
        Log log = LogUtils.getLogger(digester);
        boolean debug = log.isDebugEnabled();
        
        if (debug) {
            log.debug(
                "Matching path [" + path +
                "] on rules object " + this.toString());
        }

        List matches;
        if ((mountPoint != null) && 
            (path.length() <= mountPoint.length())) {
            if (debug) {
                log.debug(
                    "Path [" + path + "] delegated to parent.");
            }
            
            matches = parent.match(namespaceURI, path);
            
            // Note that in the case where path equals mountPoint, 
            // we deliberately return only the rules from the parent,
            // even though this object may hold some rules matching
            // this same path. See PluginCreateRule's begin, body and end
            // methods for the reason.
        } else {
                log.debug("delegating to decorated rules.");
            matches = decoratedRules.match(namespaceURI, path); 
        }

        return matches;
    }

    /** See {@link PluginContext#setPluginClassAttribute}. */
    public void setPluginClassAttribute(String namespaceUri, 
                                        String attrName) {
        pluginContext.setPluginClassAttribute(namespaceUri, attrName);
    }

    /** See {@link PluginContext#setPluginIdAttribute}. */
    public void setPluginIdAttribute(String namespaceUri, 
                                     String attrName) {
        pluginContext.setPluginIdAttribute(namespaceUri, attrName);
    }
    
    /** See {@link PluginContext#getPluginClassAttrNs}. */
    public String getPluginClassAttrNs() {
        return pluginContext.getPluginClassAttrNs();
    }
    
    /** See {@link PluginContext#getPluginClassAttr}. */
    public String getPluginClassAttr() {
        return pluginContext.getPluginClassAttr();
    }
    
    /** See {@link PluginContext#getPluginIdAttrNs}. */
    public String getPluginIdAttrNs() {
        return pluginContext.getPluginIdAttrNs();
    }
    
    /** See {@link PluginContext#getPluginIdAttr}. */
    public String getPluginIdAttr() {
        return pluginContext.getPluginIdAttr();
    }
}
