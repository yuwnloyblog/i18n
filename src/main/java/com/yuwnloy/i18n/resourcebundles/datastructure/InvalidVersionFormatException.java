package com.yuwnloy.i18n.resourcebundles.datastructure;
public class InvalidVersionFormatException extends ValidationException
{
  /**
   * 
   */
  private static final long serialVersionUID = 2817551062262156730L;
  public InvalidVersionFormatException(String message)
  {
    super(message);
  }
  public InvalidVersionFormatException(String message, Throwable t)
  {
    super(message, t);
  }
}
