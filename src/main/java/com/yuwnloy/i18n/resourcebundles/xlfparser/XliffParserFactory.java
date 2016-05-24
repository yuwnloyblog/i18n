package com.yuwnloy.i18n.resourcebundles.xlfparser;
public class XliffParserFactory
{
  private XliffParserFactory(){}

  public static XliffParser newParserInstance()
  {
    return new SAXXliffParser();
  }
}


