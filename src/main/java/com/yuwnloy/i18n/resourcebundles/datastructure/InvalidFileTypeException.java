package com.yuwnloy.i18n.resourcebundles.datastructure;
/**
 * 
 * @author xiaoguang.gao
 *
 * @date May 24, 2016
 */
public class InvalidFileTypeException extends ValidationException
{
  private static final long serialVersionUID = -2499307548693851373L;
  public InvalidFileTypeException(String message)
  {
    super(message);
  }
  public InvalidFileTypeException(String message, Throwable t)
  {
    super(message, t);
  }
}

