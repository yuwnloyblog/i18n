package com.yuwnloy.i18n.resourcebundles.datastructure;
public class InvalidLocaleException extends ValidationException
{
  /**
   * 
   */
  private static final long serialVersionUID = 8913639290736411261L;
  public InvalidLocaleException(String message)
  {
    super(message);
  }
  public InvalidLocaleException(String message, Throwable t)
  {
    super(message, t);
  }
}
