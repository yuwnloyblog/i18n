package com.yuwnloy.i18n.resourcebundles.xlfparser;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.yuwnloy.i18n.resourcebundles.datastructure.InvalidFileTypeException;
import com.yuwnloy.i18n.resourcebundles.datastructure.LocaleUtils;
import com.yuwnloy.i18n.resourcebundles.datastructure.MissingMandatoryAttributeException;
import com.yuwnloy.i18n.resourcebundles.datastructure.ValidationException;
import com.yuwnloy.i18n.resourcebundles.datastructure.XliffFileContent;
import com.yuwnloy.i18n.resourcebundles.datastructure.XliffVersion;


/**
 * http://www.oasis-open.org/committees/xliff/documents/contribution-xliff-20010530.htm
 */
class ValidatingWritableXliffFileContent extends XliffFileContent
{
  private static final String CLASSNAME = ValidatingWritableXliffFileContent.class.getName();

  void setOriginal(String original) throws ValidationException
  {
    if (original == null || original.length() == 0)
      throw new MissingMandatoryAttributeException(
                      "original attribute is absent");
    m_file_original = original;
  }

 void setSourceLocale(String localeString) throws ValidationException
  {
    if (localeString == null || localeString.length() == 0)
      throw new MissingMandatoryAttributeException(
             "source-language attribute is absent");
    m_file_sourceLocale = LocaleUtils.localeNameToLocale(localeString);
  }

  void setTargetLocale(String localeString) throws ValidationException
  {
    if (localeString != null && localeString.length() != 0)
      m_file_targetLocale = LocaleUtils.localeNameToLocale(localeString);
  }

  void setFileType(CharSequence fileTypeString) throws ValidationException
  {
    if (fileTypeString == null || fileTypeString.length() == 0)
      throw new ValidationException("beehive-filetype is null in bundle " + getEffectiveBundleDisplayName());

    m_prop_beehiveFiletype = XliffFileContent.FileType.
        getCorrespondingElement(fileTypeString.toString().trim());
    if (m_prop_beehiveFiletype == null)
      throw new InvalidFileTypeException(
          "beehive-filetype attribute has invalid value");
  }

  void setVersion(CharSequence version) throws ValidationException
  {
    if (version == null || version.length() == 0)
      throw new ValidationException("beehive-version is null in bundle " + getEffectiveBundleDisplayName());
    m_prop_beehiveVersion = new XliffVersion(version.toString().trim());
  }

  void setSourceMessage(String id, CharSequence message) throws ValidationException
  {	  
    
	StringBuilder m_msg=new StringBuilder();  
	if (isTargetLocaleAvailable())
    {
//      Logger.log(Level.FINER, CLASSNAME, "setSourceMessage",
//          "ignoring this message from the bundle " + 
//          getEffectiveBundleDisplayName() +
//          " : id=" + id + " , message=" + message);
      return;
    }

    if (id == null || id.length() == 0)
      throw new ValidationException("id is null for the message=" + message + " in bundle " + getEffectiveBundleDisplayName());

    if (message == null || message.length() == 0)
    {
//      Logger.log(Level.FINER, CLASSNAME, "setSourceMessage",
//          "message is null for the id=" + id + " in bundle " + 
//          getEffectiveBundleDisplayName() + 
//          " hence the id is bound to empty string in order to avoid throwing missing resource exception.");
    }
    else
    {
         m_msg.append(message.toString());
    }
    final String previousValue = m_body_transUnit.put(id, m_msg.toString());
    if (previousValue != null)
      logAndAppendNewNonFatalProblem(new ValidationException(
          "Multiple messages have same id=" + id + " in bundle " +
          getEffectiveBundleDisplayName() +
          " The overwritten previous message is : " + previousValue));
  }

  void setTargetMessage(String id, CharSequence message) throws ValidationException
  {
    if ( ! isTargetLocaleAvailable())
    {
//      Logger.log(Level.FINER, CLASSNAME, "setTargetMessage",
//          "ignoring this message from the bundle " + 
//          getEffectiveBundleDisplayName() +
//          " : id=" + id + " , message=" + message);
      return;
    }
    StringBuilder m_msg=new StringBuilder();
    
    if (id == null || id.length() == 0)
      throw new ValidationException("id is null for the message=" + message + " in bundle " + getEffectiveBundleDisplayName());

    if (message == null || message.length() == 0)
    {
//      Logger.log(Level.FINER, CLASSNAME, "setTargetMessage",
//          "message is null for the id=" + id + " in bundle " + 
//          getEffectiveBundleDisplayName() + 
//          " hence the id is bound to empty string in order to avoid throwing missing resource exception.");
    }
    else
    {
         m_msg.append(message.toString());
    }
    //final String previousValue = m_body_transUnit.put(id, m_msg.toString());
    m_body_transUnit.put(id, m_msg.toString());
    /*if (previousValue != null)
      logAndAppendNewNonFatalProblem(new ValidationException(
          "Multiple messages have same id=" + id + " in bundle " +
          getEffectiveBundleDisplayName() +
          " The overwritten previous message is : " + previousValue));*/
  }

  private void logAndAppendNewNonFatalProblem(Exception exception)
  {
//    Logger.log(Level.WARNING, CLASSNAME, "logAndAppendNewNonFatalProblem", 
//        "non fatal problem : " + exception.getMessage());
//    Logger.log(Level.FINER, CLASSNAME, "logAndAppendNewNonFatalProblem", 
//        "non fatal problem : ", exception);
    m_nonFatalProblems.add(exception);
  }
}
