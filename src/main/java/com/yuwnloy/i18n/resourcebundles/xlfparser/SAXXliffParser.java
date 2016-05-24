package com.yuwnloy.i18n.resourcebundles.xlfparser;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.util.logging.Level;

import org.xml.sax.SAXException;

import com.yuwnloy.i18n.resourcebundles.datastructure.XliffFileContent;

import org.xml.sax.InputSource;
/**
 * 
 * @author xiaoguang.gao
 *
 * @date May 24, 2016
 */
final class SAXXliffParser implements XliffParser
{
  private static final String CLASSNAME = SAXXliffParser.class.getName();

  SAXXliffParser()
  {}
  
  public XliffFileContent parse(
      InputSource inputSource)
  throws XliffParserException
  {
    final String methodName = "parse";
   // Logger.log(Level.FINER, CLASSNAME, methodName, "Entering SAXXliffParser#parse()");

    try
    {
      SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
      saxParserFactory.setNamespaceAware(true);
      saxParserFactory.setValidating(false);
      SAXParser saxParser = saxParserFactory.newSAXParser();
      SAXXliffParserContentHandler sAXXliffParserContentHandler = new SAXXliffParserContentHandler();//Modify:validator
      saxParser.parse(inputSource, sAXXliffParserContentHandler);
      
      XliffFileContent xliffFileContent = sAXXliffParserContentHandler.getXliffFileContent();

      if (xliffFileContent == null)
        throw new XliffParserException("Result of parsing is null xliffFileContent !!");

      return xliffFileContent;
    } catch (SAXException se) {
      throw new XliffParserException(se);
    } catch (ParserConfigurationException pce) {
      throw new XliffParserException(pce);
    } catch (IOException ioe) {
      throw new XliffParserException(ioe);
    }
  }
}
