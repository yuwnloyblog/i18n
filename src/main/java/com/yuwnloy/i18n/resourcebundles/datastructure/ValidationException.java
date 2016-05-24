package com.yuwnloy.i18n.resourcebundles.datastructure;
public class ValidationException extends Exception
{
  private static final long serialVersionUID = -3191767569716654232L;
  public ValidationException(String message)
	{
	  super(message);
	}
	protected ValidationException(Throwable t)
	{
		super(t);
	}
	protected ValidationException(String message, Throwable t)
	{
		super(message, t);
	}
} 
