package com.yuwnloy.i18n.resourcebundles.xlfparser;
import org.xml.sax.InputSource;

import com.yuwnloy.i18n.resourcebundles.datastructure.XliffFileContent;


public interface XliffParser
{
  public XliffFileContent parse(
      InputSource inputSource)
                             throws XliffParserException;
}

