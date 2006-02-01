/* $Id: VariableSubstitutor.java,v 1.1 2006/02/01 15:33:05 emayor Exp $
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

package org.apache.commons.digester.substitution;

import org.apache.commons.digester.Substitutor;

import org.xml.sax.Attributes;

/**
 * Substitutor implementation that support variable replacement
 * for both attributes and body text.
 * The actual expansion of variables into text is delegated to {@link VariableExpander}
 * implementations.
 * Supports setting an expander just for body text or just for attributes.
 * Also supported is setting no expanders for body text and for attributes. 
 *
 * @since 1.6
 */
public class VariableSubstitutor extends Substitutor {

    /** 
     * The expander to be used to expand variables in the attributes.
     * Null when no expansion should be performed.
     */
    private VariableExpander attributesExpander;
    
    /** 
     * Attributes implementation that (lazily) performs variable substitution.
     * Will be lazily created when needed then reused.
     */
    private VariableAttributes variableAttributes;
    
    /** 
     * The expander to be used to expand variables in the body text.
     * Null when no expansion should be performed.
     */
    private VariableExpander bodyTextExpander;
    
    /**
     * Constructs a Substitutor which uses the same VariableExpander for both
     * body text and attibutes.
     * @param expander VariableExpander implementation, 
     * null if no substitutions are to be performed
     */
    public VariableSubstitutor(VariableExpander expander) {
        this(expander, expander);
    }
    
    /**
     * Constructs a Substitutor.
     * @param attributesExpander VariableExpander implementation to be used for attributes, 
     * null if no attribute substitutions are to be performed
     * @param bodyTextExpander VariableExpander implementation to be used for bodyTextExpander, 
     * null if no attribute substitutions are to be performed     
     */
    public VariableSubstitutor(VariableExpander attributesExpander, VariableExpander bodyTextExpander) {
        this.attributesExpander = attributesExpander;
        this.bodyTextExpander = bodyTextExpander;
        variableAttributes = new VariableAttributes();
    }    

    /**
     * Substitutes the attributes (before they are passed to the 
     * <code>Rule</code> implementations's)
     */
    public Attributes substitute(Attributes attributes) {
        Attributes results = attributes;
        if (attributesExpander != null) {
            variableAttributes.init(attributes, attributesExpander);
            results = variableAttributes;
        }
        return results;
    }
    
    /**
     * Substitutes for the body text.
     * This method may substitute values into the body text of the
     * elements that Digester parses.
     *
     * @param bodyText the body text (as passed to <code>Digester</code>)
     * @return the body text to be passed to the <code>Rule</code> implementations
     */
    public String substitute(String bodyText) {
        String result = bodyText;
        if (bodyTextExpander != null) {
            result = bodyTextExpander.expand(bodyText);
        }
        return result;
    }
}
