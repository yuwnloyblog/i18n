package com.yuwnloy.i18n.resourcebundles.xlfparser;
/**
 * WARNING!!! SINCE valueOf() IS NOT OVERRIDDEN AND
 * WE MAY HAVE DIFFERENT actualName COMPARED TO ENUM
 * CONSTANTS, PLEASE DO NOT INVOKE valueOf() ON THIS ENUM.
 */
enum SAXXliffParserAttribute
{
  original(/*isMandatory*/true),
  source_language("source-language", /*isMandatory*/true),
  target_language("target-language", /*isMandatory*/false),

  //name attribute of prop-group element is optional by
  //XLIFF XSD but we are deliberately more strict
  name(/*isMandatory*/true),

  prop_type("prop-type", /*isMandatory*/true),

  id(/*isMandatory*/true);

  SAXXliffParserAttribute(boolean isMandatory)
  {
    m_actualName = null;
    m_isMandatory = isMandatory;
  }

  SAXXliffParserAttribute(String actualName, boolean isMandatory)
  {
    m_actualName = actualName;
    m_isMandatory = isMandatory;
  }

  boolean isMandatory()
  {
    return m_isMandatory;
  }

  @Override
  public String toString()
  {
    return (m_actualName == null ? super.toString() : m_actualName);
  }

  private final String m_actualName;
  private final boolean m_isMandatory;
}
