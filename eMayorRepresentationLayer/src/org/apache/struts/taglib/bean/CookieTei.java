/*
 * $Id: CookieTei.java,v 1.1 2006/02/01 15:32:57 emayor Exp $ 
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


package org.apache.struts.taglib.bean;


import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;


/**
 * Implementation of <code>TagExtraInfo</code> for the <b>cookie</b>
 * tag, identifying the scripting object(s) to be made visible.
 *
 * @version $Rev: 54929 $ $Date: 2006/02/01 15:32:57 $
 */

public class CookieTei extends TagExtraInfo {


    /**
     * Return information about the scripting variables to be created.
     */
    public VariableInfo[] getVariableInfo(TagData data) {

        String className = null;
        if (data.getAttribute("multiple") == null)
            className = "javax.servlet.http.Cookie";
        else
            className = "javax.servlet.http.Cookie[]";
	return new VariableInfo[] {
	  new VariableInfo(data.getAttributeString("id"),
	                   className,
	                   true,
	                   VariableInfo.AT_BEGIN)
	};

    }


}
