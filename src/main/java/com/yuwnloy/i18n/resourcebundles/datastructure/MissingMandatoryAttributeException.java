package com.yuwnloy.i18n.resourcebundles.datastructure;
public class MissingMandatoryAttributeException extends ValidationException
{
  /**
   * 
   */
  private static final long serialVersionUID = 6543295313991004404L;
  public MissingMandatoryAttributeException(String message)
  {
    super(message);
  }
  public MissingMandatoryAttributeException(String message, Throwable t)
  {
    super(message, t);
  }
}


