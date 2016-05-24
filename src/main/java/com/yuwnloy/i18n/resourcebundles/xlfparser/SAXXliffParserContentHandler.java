package com.yuwnloy.i18n.resourcebundles.xlfparser;
import java.util.logging.Level;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.yuwnloy.i18n.resourcebundles.datastructure.XliffFileContent;

/**
 * Content handler class for SAX parsing of xliff file
 */
final class SAXXliffParserContentHandler extends DefaultHandler
{
  private static final String CLASSNAME = SAXXliffParserContentHandler.class.getName();
  private final SAXXliffParserElementReference m_elementReference;
  private StringBuilder m_currElementContent = null;

  SAXXliffParserContentHandler()//Modify:ValidateAndTerminateParsing validator
  {
    m_elementReference = new SAXXliffParserElementReference();
  }
  
  final XliffFileContent getXliffFileContent()
  {
    return m_elementReference.getXliffFileContent();
  }

  @Override
  public void characters (char buf [], int offset, int len) 
                                      throws SAXException
  {
    if (m_currElementContent != null)
      m_currElementContent.append(buf,offset, len);
  }

  @Override
  public void startElement(String uri,
      String localName,
      String qName,
      Attributes attributes)
  throws SAXException
  {
    if (localName == null || localName.equals(""))
      throw new SAXException("Received null or empty localName. QName=" + qName + " Attributes : " + convertToString(attributes));

    SAXXliffParserElement elementOfInterest = m_elementReference.getCorrespondingElement(localName);
    if (elementOfInterest != null)
    {
      //Note that we are optimizing the execution of characters()
      //since we initialize m_currElementContent
      //only if we have found an element of interest
      m_currElementContent = new StringBuilder();
      elementOfInterest.startElement(attributes);  
    }
  }

  @Override
  public void endElement(String uri,
      String localName,
      String qName)
  throws SAXException
  {
    if (localName == null || localName.equals(""))
      throw new SAXException("Received null or empty localName. QName=" + qName);

    try
    {
      SAXXliffParserElement elementOfInterest = m_elementReference.getCorrespondingElement(localName);
      if (elementOfInterest != null)
        elementOfInterest.endElement(m_currElementContent);
    } finally {
      m_currElementContent = null;
    }
  }

  /**
   * Receive notification of a parser warning. 
   * The default implementation does nothing. We must log it.
   */
  @Override
  public void warning(SAXParseException e) throws SAXException
  {
    throw e;
  }

  /**
   * Receive notification of a recoverable parser error. 
   * The default implementation does nothing. We must log it.
   */
  @Override
  public void error(SAXParseException e) throws SAXException
  {
    throw e;
  }

  private String convertToString(Attributes attributes)
  {
    int length = attributes.getLength();
    StringBuilder result = new StringBuilder();
    for (int i = 0 ; i < length ; i++)
      result.append("    localName=").append(attributes.getLocalName(i))
            .append(";QName=").append(attributes.getQName(i))
            .append(";value=").append(attributes.getValue(i));
    return result.toString();
  }
} 
