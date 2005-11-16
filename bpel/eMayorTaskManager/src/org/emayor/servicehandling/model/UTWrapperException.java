package org.emayor.servicehandling.model;

public class UTWrapperException extends Exception 
{
  public UTWrapperException()
  {
  }
  
  public UTWrapperException(Exception ex) 
  {
    super(ex);
  }
  
  public UTWrapperException(String msg) 
  {
    super(msg);
  }
}