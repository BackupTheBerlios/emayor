/*
 * $Id: DefaultModuleConfigFactory.java,v 1.1 2006/02/01 15:33:08 emayor Exp $ 
 *
 * Copyright 1999-2004 The Apache Software Foundation.
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

package org.apache.struts.config.impl;

import java.io.Serializable;

import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.ModuleConfigFactory;

/**
 * A factory for creating {@link ModuleConfig} instances.
 *
 * @see ModuleConfig
 * @see ModuleConfigFactory
 *
 * @version $Rev: 54929 $ $Date: 2006/02/01 15:33:08 $
 */
public class DefaultModuleConfigFactory extends ModuleConfigFactory implements Serializable{
    // --------------------------------------------------------- Public Methods

    /**
     * Create and return a newly instansiated {@link ModuleConfig}.
     * This method must be implemented by concrete subclasses.
     *
     * @param prefix Module prefix for Configuration
     */
    public ModuleConfig createModuleConfig(String prefix) {

        return new ModuleConfigImpl(prefix);

    }



}