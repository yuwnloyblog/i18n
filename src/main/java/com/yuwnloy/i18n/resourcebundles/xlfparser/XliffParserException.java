package com.yuwnloy.i18n.resourcebundles.xlfparser;
import java.util.logging.Level;

public class XliffParserException extends Exception 
{
  private static final long serialVersionUID = 2611136527839949613L;
  XliffParserException(String message)
	{
	  super(message);
	}
	XliffParserException(Throwable t)
	{
		super(t);
	}
	XliffParserException(String message, Throwable t)
	{
		super(message, t);
	}
  public boolean thisExceptionWillRecurIfXliffInitIsRetriedLater()
  {
    //Parsing exceptions are typically not transient
    return true;
  }
  public Level logThisExceptionAtLevel()
  {
    return Level.WARNING;
  }
} 
