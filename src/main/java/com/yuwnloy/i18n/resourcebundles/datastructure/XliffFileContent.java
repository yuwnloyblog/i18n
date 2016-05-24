package com.yuwnloy.i18n.resourcebundles.datastructure;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.Locale;

/**
 * http://www.oasis-open.org/committees/xliff/documents/contribution-xliff-20010530.htm
 */
public class XliffFileContent
{
  private static final String CLASSNAME = XliffFileContent.class.getName();

  /******* Attributes of <xliff> element *******/

  /**
   * The version attribute is used to specify the format version of 
   * the XLIFF document. The value is fixed as "1.0" as of writing this.
   * COMMENTED BECAUSE UNUSED.
   */
  //protected String m_xliff_version = null;

  /******* 
   * Attributes of <file> element. An <xliff> element may contain 
   * multiple <file> elements but we support only one. 
   ********/

  /**
   * The original attribute specifies the name of the original file 
   * from which the contents of a <file>  element has been extracted.
   */
  protected String m_file_original = null;

  /**
   * The datatype attribute specifies the kind of text contained in the
   * element. Depending on that type, you may apply different processes
   * to the data. As of writing this, the value has been fixed as "beehive"
   * COMMENTED BECAUSE UNUSED.
   */
  //protected String m_file_datatype = null;

  /**
   * The source locale for the file. Value must be a language/locale
   * code as described in the IETF RFC 3066.
   */
  protected Locale m_file_sourceLocale = null;

  /**
   * The target locale for the file. Value must be a language/locale
   * code as described in the IETF RFC 3066.
   */
  protected Locale m_file_targetLocale = null;

  /*********
   * contents of <prop-group> element
   */
  protected XliffVersion m_prop_beehiveVersion = null;
  protected FileType m_prop_beehiveFiletype = null;

  /**
   * Contents of trans-unit element i.e., the externalized messages.
   * Initialize the hash map with 50 buckets because that seems to be
   * the "mode" (most xliff's have around that many keys).
   * Use ConcurrentHashMap in order to maximise throughput as compared
   * to using a Hashtable. Reference http://www.ibm.com/developerworks/java/library/j-jtp07233.html
   */
  protected ConcurrentHashMap<String, String> m_body_transUnit = new ConcurrentHashMap<String, String>(50);

  protected List<Exception> m_nonFatalProblems = new ArrayList<Exception>(1);

  public String getOriginal()
  {
    return m_file_original;
  }

  public Locale getSourceLocale()
  {
    return m_file_sourceLocale;
  }

  public boolean isTargetLocaleAvailable()
  {
    return (m_file_targetLocale == null ? false : true);
  }

  public Locale getTargetLocale()
  {
    return m_file_targetLocale;
  }

  public Locale getEffectiveLocale()
  {
    return (m_file_targetLocale != null ? 
              m_file_targetLocale : m_file_sourceLocale);
  }

  public boolean isFileTypeAvailable()
  {
    return (m_prop_beehiveFiletype == null ? false : true);
  }

  public FileType getFileType()
  {
    return m_prop_beehiveFiletype;
  }

  public FileType getEffectiveFileType()
  {
    return (m_prop_beehiveFiletype == null ? 
             FileType.custom : m_prop_beehiveFiletype);
  }
  
  public boolean isVersionAvailable()
  {
    return (m_prop_beehiveVersion == null ? false : true);
  }

  public XliffVersion getVersion()
  {
    return m_prop_beehiveVersion;
  }

  public String getMessage(String id)
  {
   // if (m_body_transUnit == null)
//      Logger.log(Level.WARNING, CLASSNAME, "getMessage",
//        "concurrent hashmap is null when examined in getMessage() for key " + id + " for " + getOriginal() + "_" + getEffectiveLocale() + "_" + getEffectiveFileType());
    return (m_body_transUnit != null && id != null ? 
                m_body_transUnit.get(id) : null);
  }

  public String getEntireXliffFileContentAsString()
  {
    StringBuilder result = new StringBuilder();
    result.append(getEffectiveBundleDisplayName());
    result.append("    contents:").append(getAllMessagesFromBundleAsString());
    return result.toString();
  }

  public String getEffectiveBundleDisplayName()
  {
    StringBuilder result = new StringBuilder();
    result.append("  original=").append(m_file_original);
    if (isTargetLocaleAvailable())
      result.append("  target_locale=").append(m_file_targetLocale);
    result.append("  source_locale=").append(m_file_sourceLocale);
    if (isFileTypeAvailable())
      result.append("  filetype=").append(m_prop_beehiveFiletype);
    if (isVersionAvailable())
      result.append("  version=").append(m_prop_beehiveVersion);
    return result.toString();
  }

  public String getAllMessagesFromBundleAsString()
  {
    StringBuilder result = new StringBuilder();
    for (Map.Entry<String, String> mapentry : m_body_transUnit.entrySet())
      result.append("\n").append(mapentry.getKey()).append("=").append(mapentry.getValue());
    return result.toString();
  }

  public Set<String> getMessageIDs()
  {
    return (   m_body_transUnit != null
            && m_body_transUnit.size() > 0 ? 
                    m_body_transUnit.keySet() : null);
  }

  /**
   * @return empty list when there are no problems
   */
  public List<Exception> getNonFatalProblems()
  {
    return Collections.unmodifiableList(m_nonFatalProblems);
  }

  /**
   * Compares this object with the given object.
   * @param two The other object to compare with.
   * @return Returns a negative integer, zero, or a positive integer 
   * respectively when this object is less than, equal to, or greater than 
   * the specified object.
   */
  public int compareByVersion(XliffFileContent two)
  {
    final int EQUAL = 0, GREATER = 1, LESSER = -1;
    final XliffFileContent one = this;
    if (two == null)
      return GREATER; //because "this" is not null and "the other object" is null

    if (one.getVersion() == null) //i.e., version un-specified in xliff file represented by "one"
    {
      if (two.getVersion() == null) //i.e., version un-specified in xliff file represented by "two"
      {
        return EQUAL;
      }
      else
      {
        return LESSER; //because "version of this object" is null and "version of other object" is not null
      }
    }
    else
    {
      if (two.getVersion() == null) //i.e., version un-specified in xliff file represented by "two"
      {
        return GREATER; //because "version of this object" is not null and "version of other object" is null
      }
      else
      {
        //both "version of this object" and "version of other object" are not null
        return one.getVersion().compareTo(two.getVersion());
      }
    }
  }

  /**
   * Public instantiation deliberately not permitted
   * except as base class when instantiating sub-class(es)
   */
  protected XliffFileContent()
  {}

  /**
   * WARNING!!! SINCE valueOf() IS NOT OVERRIDDEN AND
   * WE MAY HAVE DIFFERENT actualName COMPARED TO ENUM
   * CONSTANTS, PLEASE DO NOT INVOKE valueOf() ON THIS ENUM.
   */
  public enum FileType
  {
    builtin("built-in"),
    custom;
    private FileType()
    {
      m_actualName = null;
    }
    private FileType(String actualName)
    {
      m_actualName = actualName;
    }
    @Override
    public String toString()
    {
      return (m_actualName == null ? super.toString() : m_actualName);
    }
    private final String m_actualName;
    /**
     * This method returns null if there is no FileType
     * corresponding to given name.
     * 
     * WARNING!!! SINCE valueOf() IS NOT OVERRIDDEN AND
     * WE MAY HAVE DIFFERENT actualName COMPARED TO ENUM
     * CONSTANTS, PLEASE DO NOT INVOKE valueOf() ON THIS ENUM.
     */
    public static FileType getCorrespondingElement(String name)
    {
      FileType fileType = null;
      for (FileType currFileType : values())
        if (currFileType.toString().equalsIgnoreCase(name))
          fileType = currFileType;
      return fileType;
    }
  }
}

