
package com.yuwnloy.i18n.resourcebundles.datagetter;

import java.sql.Timestamp;
import java.util.Locale;
/**
 * 
 * @author xiaoguang.gao
 *
 * @date May 24, 2016
 */
public interface IDataGetter {
	public MapMetaData getKeyValuePairs(String baseName, Locale locale);

	public Timestamp getModifiedTime(String baseName, Locale locale);
}
