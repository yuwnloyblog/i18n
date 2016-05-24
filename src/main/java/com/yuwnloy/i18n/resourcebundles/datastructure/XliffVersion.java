package com.yuwnloy.i18n.resourcebundles.datastructure;
import java.util.ArrayList;
import java.util.List;

public class XliffVersion implements Comparable<XliffVersion>
{
  private final String m_version;
  private final List<Integer> m_versionComponents;
  public XliffVersion(String version) throws ValidationException
  {
    if (version == null || (version = version.trim()).equals(""))
    {
      throw new IllegalArgumentException("XliffVersion must be initialized with non-null, non-empty version string.");
    }
    version = version.startsWith(".") ? "0" + version : version;
    final String[] splitVersion = version.split("[.]");
    if (splitVersion.length == 0)
    {
      m_versionComponents = null;
      m_version = null;
      return;
    }
    final List<Integer> versionComponents = new ArrayList<Integer>(splitVersion.length);
    boolean permitZeroAsVerComponent = false;
    try
    {
      for (int idx = splitVersion.length-1 ; idx >= 0 ; idx--)
      {
        Integer currVerComponent = Integer.parseInt(splitVersion[idx]);
        if (currVerComponent < 0)
          throw new InvalidVersionFormatException("Negative component " + currVerComponent + " found when parsing version " + version);
        if (permitZeroAsVerComponent || currVerComponent > 0)
        {
          versionComponents.add(0, currVerComponent);
          permitZeroAsVerComponent = true;
        }
      }
    } catch (NumberFormatException nfe) {
      throw new InvalidVersionFormatException("Invalid version " + version + " - only period separated positive integers are permitted in version.", nfe);
    }
    if (versionComponents.size() == 0)
      versionComponents.add(Integer.valueOf(0));
    m_versionComponents = versionComponents;
    m_version = version;
  }
  
  /**
   * Compares this object with the specified object for order.
   * Returns a negative integer, zero, or a positive integer 
   * as this object is less than, equal to, or greater than 
   * the specified object.
   */
  public int compareTo(XliffVersion otherVersion)
  {
    final int EQUAL = 0, GREATER = 1, LESSER = -1;
    if (this == otherVersion)
      return EQUAL;
    if (otherVersion == null)
      return GREATER; //because "this" is not null and "other object" is null
    if (m_versionComponents == null)
    {
      if (otherVersion.m_versionComponents == null)
        return EQUAL;
      else
        return LESSER;
    } else if (otherVersion.m_versionComponents == null)
      return GREATER;
    
    int len = Math.min(m_versionComponents.size(), 
                       otherVersion.m_versionComponents.size());
    for (int i = 0 ; i < len ; i++)
    {
      int c = m_versionComponents.get(i).compareTo(
              otherVersion.m_versionComponents.get(i));
      if (c != 0)
        return c;
    }
    return Integer.valueOf(m_versionComponents.size()).compareTo(
           Integer.valueOf(otherVersion.m_versionComponents.size()));
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((m_version == null) ? 0 : m_version.hashCode());
    return result;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof XliffVersion))
      return false;
    final XliffVersion other = (XliffVersion) obj;
    if (m_version == null)
    {
      if (other.m_version != null)
        return false;
    } else if (!m_version.equals(other.m_version))
      return false;
    return true;
  }
  
  @Override
  public String toString()
  {
    return m_version;
  }
}
