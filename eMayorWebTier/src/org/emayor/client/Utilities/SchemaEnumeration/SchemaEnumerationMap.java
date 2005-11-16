package org.emayor.client.Utilities.SchemaEnumeration;


/*
 * Created on 06.11.2005, jpl
 */

import java.util.Hashtable;
import java.util.Enumeration;


public class SchemaEnumerationMap
{

  private Hashtable map = new Hashtable();


  public SchemaEnumerationMap()
  {
                   
  } // Constructor


  public void addEntry( String nodePath, String[] values )
  {
    map.put(nodePath, new SchemaEnumerationMapValue( values ) );
  }


  public SchemaEnumerationMapValue getEnumerationForNodePath( String nodePath )
  {
    return (SchemaEnumerationMapValue)this.map.get(nodePath);
  }


  public void printMap()
  {           
    System.out.println(" ");
    System.out.println(" ");
    System.out.println("Content of the SchemaEnumerationMap:");
    System.out.println(" ");

    Enumeration keys = map.keys();
    while( keys.hasMoreElements() )
    {
       String key = (String)keys.nextElement();
       SchemaEnumerationMapValue value = (SchemaEnumerationMapValue)this.map.get(key);
       int numberOfValues = value.getEnumerationValues().length;
       String[] enumerationValues = value.getEnumerationValues();
       System.out.println("Key= " + key + " has " + numberOfValues + " values: ");
       for( int i=0; i < numberOfValues; i++ )
       {
         System.out.println( "     " + enumerationValues[i]);
       }
       System.out.println(" ");                 
    }
  }
  
  

} // SchemaEnumerationMap
