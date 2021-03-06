/* $Id: PluginInvalidInputException.java,v 1.1 2006/02/01 15:32:57 emayor Exp $
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

/**
 * Thrown when an error occurs due to bad data in the file being parsed.
 *
 * @since 1.6
 */
public class PluginInvalidInputException extends PluginException {

    private Throwable cause = null;

    /**
     * @param cause underlying exception that caused this to be thrown
     */
    public PluginInvalidInputException(Throwable cause) {
        this(cause.getMessage());
        this.cause = cause;
    }

    /**
     * @param msg describes the reason this exception is being thrown.
     */
    public PluginInvalidInputException(String msg) {
        super(msg);
    }

    /**
     * @param msg describes the reason this exception is being thrown.
     * @param cause underlying exception that caused this to be thrown
     */
    public PluginInvalidInputException(String msg, Throwable cause) {
        this(msg);
        this.cause = cause;
    }
}
