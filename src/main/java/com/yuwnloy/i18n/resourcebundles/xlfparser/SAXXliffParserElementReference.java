package com.yuwnloy.i18n.resourcebundles.xlfparser;
import java.util.HashMap;
import java.util.Map;

import com.yuwnloy.i18n.resourcebundles.datastructure.ValidationException;

final class SAXXliffParserElementReference
{
  //private final ValidateAndTerminateParsing m_validator;
  private final ValidatingWritableXliffFileContent m_xliffFileContent = 
    new ValidatingWritableXliffFileContent();
  private final Map<String, SAXXliffParserElement> m_elements = new HashMap<String, SAXXliffParserElement>(7);

  SAXXliffParserElementReference()//ValidateAndTerminateParsing validator
  {
    //m_validator = validator;

    m_elements.put(file.toString(), file);
    m_elements.put(header.toString(), header);
    m_elements.put(prop_group.toString(), prop_group);
    m_elements.put(prop.toString(), prop);
    m_elements.put(trans_unit.toString(), trans_unit);
    m_elements.put(source.toString(), source);
    m_elements.put(target.toString(), target);
  }

  final ValidatingWritableXliffFileContent getXliffFileContent()
  {
    return m_xliffFileContent;
  }

  /*private final ValidateAndTerminateParsing getValidator()
  {
    return m_validator;
  }*/

  /**
   * This method returns null if there is no SAXXliffParserElement
   * corresponding to given name.
   */
  final SAXXliffParserElement getCorrespondingElement(String name)
  {
    return m_elements.get(name.toLowerCase());
  }

  final SAXXliffParserElement file = new SAXXliffParserElement("file",
                                   SAXXliffParserAttribute.original,
                                   SAXXliffParserAttribute.source_language,
                                   SAXXliffParserAttribute.target_language)
  {
    @Override
    protected void handleStartElement() throws ValidationException
    {
      getXliffFileContent().setOriginal(simpleAttributeFilter(SAXXliffParserAttribute.original));
      getXliffFileContent().setSourceLocale(simpleAttributeFilter(SAXXliffParserAttribute.source_language));
      getXliffFileContent().setTargetLocale(simpleAttributeFilter(SAXXliffParserAttribute.target_language));
    }
  };

  final SAXXliffParserElement header = new SAXXliffParserElement("header")
  {
    @Override
    protected void handleEndElement(StringBuilder elementContent) throws ValidationException
    {
     /* if (getValidator() != null)
        getValidator().validateAfterHeaderParsed(getXliffFileContent());*/
    }
  };

  final SAXXliffParserElement prop_group = new SAXXliffParserElement("prop-group",
                                         SAXXliffParserAttribute.name)
  {
    @Override
    protected void handleEndElement(StringBuilder elementContent) throws ValidationException
    {
/*
      if ( ! getXliffFileContent().isFileTypeAvailable())
        throw new ValidationException(Constant.beehive_filetype + " is unavailable after parsing prop-group element");
      if ( ! getXliffFileContent().isVersionAvailable())
        throw new ValidationException(Constant.beehive_version + " is unavailable after parsing prop-group element");
 */
   }
  };

  final SAXXliffParserElement prop = new SAXXliffParserElement("prop",
                                   SAXXliffParserAttribute.prop_type)
  {
    @Override
    protected void handleEndElement(StringBuilder elementContent) throws ValidationException
    {
      if ( ! header.isInEffect())
        throw new ValidationException("prop element is outside header element. The elementContent of the current prop element is " + elementContent);

      if ( ! prop_group.isInEffect())
        throw new ValidationException("prop element is outside prop-group element. The elementContent of the current prop element is " + elementContent);

      if ( ! prop_group.isAttributeValueMatch(SAXXliffParserAttribute.name, Constant.ora_reconstruction))
        throw new ValidationException("name attribute of prop-group element must have the value "
            + Constant.ora_reconstruction +
            " The elementContent of the current prop element is " + elementContent);

      if (isAttributeValueMatch(SAXXliffParserAttribute.prop_type, Constant.beehive_version))
        getXliffFileContent().setVersion(elementContent);
      else if (isAttributeValueMatch(SAXXliffParserAttribute.prop_type, Constant.beehive_filetype))
        getXliffFileContent().setFileType(elementContent);
    }
  };

  final SAXXliffParserElement trans_unit = new SAXXliffParserElement("trans-unit",
                                         SAXXliffParserAttribute.id);

  final SAXXliffParserElement source = new SAXXliffParserElement("source")
  {
    @Override
    protected void handleEndElement(StringBuilder elementContent) throws ValidationException
    {
      if (trans_unit.isInEffect())
        getXliffFileContent().setSourceMessage(
            trans_unit.simpleAttributeFilter(SAXXliffParserAttribute.id),
            elementContent);
      else
        throw new ValidationException("source element is outside trans-unit element. The elementContent of the offending source element is " + elementContent);
    }
  };

  final SAXXliffParserElement target = new SAXXliffParserElement("target") 
  {
    @Override
    protected void handleEndElement(StringBuilder elementContent) throws ValidationException
    {
      if (trans_unit.isInEffect())
        getXliffFileContent().setTargetMessage(
            trans_unit.simpleAttributeFilter(SAXXliffParserAttribute.id),
            elementContent);
      else
        throw new ValidationException("target element is outside trans-unit element. The elementContent of the offending target element is " + elementContent);
    }
  };

  private static final class Constant
  {
    static final String ora_reconstruction = "ora_reconstruction";
    static final String beehive_version = "beehive-version";
    static final String beehive_filetype = "beehive-filetype";
  } //end of Constant
}
