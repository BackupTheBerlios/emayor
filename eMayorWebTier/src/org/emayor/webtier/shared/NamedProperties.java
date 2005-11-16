package org.emayor.webtier.shared;


/* 
 *  Created on 19.07.2005
 *  J.Plaz
 * 
 */


import java.util.Properties;
import java.io.Serializable;

public class NamedProperties implements Serializable
{
  private String name;
  private Properties properties;
  public NamedProperties( final String _name, final Properties _properties )
  {
	  this.name = _name;
	  this.properties = _properties;
  }
  public String getName()
  {
	  return this.name;
  }
  public Properties getProperties()
  {
	  return this.properties;
  }
}
