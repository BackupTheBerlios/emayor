/*
 * $Header: /home/xubuntu/berlios_backup/github/tmp-cvs/emayor/Repository/eMayorRepresentationLayer/src/org/apache/commons/validator/Constant.java,v 1.1 2006/02/01 15:32:58 emayor Exp $
 * $Revision: 1.1 $
 * $Date: 2006/02/01 15:32:58 $
 *
 * ====================================================================
 * Copyright 2001-2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.apache.commons.validator;

import java.io.Serializable;


/**
 * <p>A constant can be used to define a global or
 * locale level constant that can be used to replace
 * values in certain fields.  The <code>Field</code>'s
 * property field, the <code>Var</code>'s value field,
 * the <code>Msg</code>'s key field, and the <code>Arg</code>'s
 * key field can all contain constants reference for replacement.
 * <br>
 * ex: &lt;constant name="zip" value="^\d{5}$" /&gt; mask="${zip}" </p>
 *
 * @deprecated This class is no longer needed.
 */
public class Constant implements Serializable {

    /**
     * The name of the constant.
     */
    private String name = null;

    /**
     * The name of the constant.
     */
    private String value = null;

    /**
     * Gets the name of the constant.
     * @return the name o fthe constant.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the constant.
     * @param name sets the name of the constant.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the value of the constant.
     * @return the value of the constant.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the constant.
     * @param value the value of the constant.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Returns a string representation of the object.
     * @return the string representation of the object.
     */
    public String toString() {
        StringBuffer results = new StringBuffer();

        results.append("Constant: name=");
        results.append(name);
        results.append("  value=");
        results.append(value);
        results.append("\n");

        return results.toString();
    }

}