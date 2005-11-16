/*
 * $Header: /home/xubuntu/berlios_backup/github/tmp-cvs/emayor/Repository/eMayorWebTier/src/org/apache/struts/taglib/html/SecureFormTag.java,v 1.1 2005/11/16 10:51:48 emayor Exp $
 * $Revision: 1.1 $
 * $Date: 2005/11/16 10:51:48 $
 *
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Struts", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

/**
 * Defines the form tag of the sslext tag library
 * @author Steve Ditlinger
 */

package org.apache.struts.taglib.html;


import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.SecureRequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import java.net.MalformedURLException;

public class SecureFormTag extends FormTag {

  /**
   * Return the form action converted into a server-relative URL.
   */
  protected String getActionMappingURL() {

    TagUtils tagUtils = TagUtils.getInstance();

    String url = null;
    try {
      url = SecureRequestUtils.computeURL(
              pageContext,
              null, //forward
              null, // href
              null, // page
              action, //action
              null,  //module
              null,
              null,
              false);
    } catch (MalformedURLException e) {
      // There are two conditions under which this exception can occur
      // 1) If more than one forward, href, and page are specified as params
      // 2) A bad forward is specified as param
      // A look at the above params shows that both of these are impossible
      // We'll go thru the motions and save the exception, (even though it will never occur)
      // but we won't throw it up any higher
      tagUtils.saveException(pageContext, e);
    }

    // Return the completed value
    return (url);
  }

  /**
   * Render the beginning of this form.
   *
   * @exception JspException if a JSP exception has occurred
   */
  public int doStartTag() throws JspException {

    TagUtils tagUtils = TagUtils.getInstance();

    // Look up the form bean name, scope, and type if necessary
    lookup();

    // Create an appropriate "form" element based on our parameters
    //    HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
    StringBuffer results = new StringBuffer("<form");
    results.append(" name=\"");
    results.append(beanName);
    results.append("\"");
    results.append(" method=\"");
    results.append(method == null ? "post" : method);
    results.append("\" action=\"");
    // The following is the only line that is different with base class
    results.append(getActionMappingURL());
    results.append("\"");
    if (styleClass != null) {
      results.append(" class=\"");
      results.append(styleClass);
      results.append("\"");
    }
    if (enctype != null) {
      results.append(" enctype=\"");
      results.append(enctype);
      results.append("\"");
    }
    if (onreset != null) {
      results.append(" onreset=\"");
      results.append(onreset);
      results.append("\"");
    }
    if (onsubmit != null) {
      results.append(" onsubmit=\"");
      results.append(onsubmit);
      results.append("\"");
    }
    if (style != null) {
      results.append(" style=\"");
      results.append(style);
      results.append("\"");
    }
    if (styleId != null) {
      results.append(" id=\"");
      results.append(styleId);
      results.append("\"");
    }
    if (target != null) {
      results.append(" target=\"");
      results.append(target);
      results.append("\"");
    }
    results.append(">");

    // Add a transaction token (if present in our session)
    HttpSession session = pageContext.getSession();
    if (session != null) {
      String token = (String) session.getAttribute(Globals.TRANSACTION_TOKEN_KEY);
      if (token != null) {
        results.append("<input type=\"hidden\" name=\"");
        results.append(Constants.TOKEN_KEY);
        results.append("\" value=\"");
        results.append(token);
        if (tagUtils.isXhtml(this.pageContext)) {
          results.append("\" />");
        } else {
          results.append("\">");
        }
      }
    }

    // Print this field to our output writer
    tagUtils.write(pageContext, results.toString());

    // Store this tag itself as a page attribute
    pageContext.setAttribute(Constants.FORM_KEY, this, PageContext.REQUEST_SCOPE);

    // Locate or create the bean associated with our form
    int scope = PageContext.SESSION_SCOPE;
    if ("request".equals(beanScope)) {
      scope = PageContext.REQUEST_SCOPE;
    }
    Object bean = pageContext.getAttribute(beanName, scope);
    if (bean == null) {
        // New and improved - use the values from the action mapping
      bean = RequestUtils.createActionForm(
                      (HttpServletRequest) pageContext.getRequest(),
                      mapping,
                      moduleConfig,
                      servlet);
      if (bean instanceof ActionForm) {
        ((ActionForm) bean).reset(mapping, (HttpServletRequest) pageContext.getRequest());
      }
      if (bean == null) {
        throw new JspException(messages.getMessage("formTag.create", beanType));
      }
      pageContext.setAttribute(beanName, bean, scope);
    }
    pageContext.setAttribute(Constants.BEAN_KEY, bean, PageContext.REQUEST_SCOPE);

    // Continue processing this page
    return (EVAL_BODY_INCLUDE);

  }
}
