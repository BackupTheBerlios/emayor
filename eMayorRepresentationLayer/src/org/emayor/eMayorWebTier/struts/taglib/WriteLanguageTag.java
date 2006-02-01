package org.emayor.eMayorWebTier.struts.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import javax.servlet.jsp.PageContext;

import org.apache.struts.taglib.TagUtils;

import org.emayor.eMayorWebTier.Utilities.ExtendedActionForm;

/**
 * JSP Tag Handler class
 *
 * @jsp.tag   name = "WriteLanguage"
 *            display-name = "Name for WriteLanguage"
 *            description = "Description for WriteLanguage"
 *            body-content = "empty"
 */
public class WriteLanguageTag extends TagSupport 
{

	
    // The scope to be searched to retrieve the specified bean.
    protected String scope = null;

    // Name of the bean that contains the data we will be rendering.
    protected String name = null;
    
    // Key for the text entry:
    protected String resourceElementKey = null;
    
    
	public WriteLanguageTag() 
	{
      super();
	}


	

    public String getName() 
    {
      return (this.name);
    }

    public void setName(String name) 
    {
      this.name = name;
    }



    public String getScope() 
    {
      return (this.scope);
    }

    public void setScope(String scope) 
    {
      this.scope = scope;
    }
	
	
    	
    public String getResourceElementKey() 
    {
      return this.resourceElementKey;
    }

    public void setResourceElementKey(String _resourceElementKey) 
    {
      this.resourceElementKey = _resourceElementKey;
    }

	
	
    public int doStartTag() throws JspException 
	{
      // Look up the requested property value
      Object value = this.lookup(pageContext, name, scope);
      if( value == null ) 
      {
        return (SKIP_BODY); // Nothing to output
      }
      
      ExtendedActionForm extendedActionForm = (ExtendedActionForm)value;      
      String output = extendedActionForm.getTextFromResource(this.resourceElementKey);
      
      // Print this property value to our output writer
      TagUtils.getInstance().write(pageContext, output);
      // Continue processing this page
      return (SKIP_BODY);
    } // doStartTag
	
	

    public Object lookup( PageContext pageContext, String name, String scopeName ) throws JspException 
    {
      if( scopeName == null )
      {
        return pageContext.findAttribute(name);
      }
      try 
	  {
        return pageContext.getAttribute( name, TagUtils.getInstance().getScope(scopeName) );
      } 
      catch( JspException e )
	  {
      	TagUtils.getInstance().saveException(pageContext, e);
        throw e;
      }
    }
    

    
    
    
    
	
	
} // WriteLanguageTag


