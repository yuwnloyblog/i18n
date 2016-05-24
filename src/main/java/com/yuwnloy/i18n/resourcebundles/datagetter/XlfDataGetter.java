package com.yuwnloy.i18n.resourcebundles.datagetter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.xml.sax.InputSource;

import com.yuwnloy.i18n.resourcebundles.datastructure.XliffFileContent;
import com.yuwnloy.i18n.resourcebundles.xlfparser.XliffParser;
import com.yuwnloy.i18n.resourcebundles.xlfparser.XliffParserException;
import com.yuwnloy.i18n.resourcebundles.xlfparser.XliffParserFactory;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.sql.Timestamp;
import java.util.Locale;
/**
 * 
 * @author xiaoguang.gao
 *
 * @date May 24, 2016
 */
public class XlfDataGetter implements IDataGetter {
	private static final Logger s_logger = Logger.getLogger(XlfDataGetter.class
			.getName());
	public XlfDataGetter(){
		
	}

	@Override
	public MapMetaData getKeyValuePairs(String baseName, Locale locale){
		String bundleName = toBundleName(baseName, locale);
		final String resourceName = toResourceName(bundleName, "xlf");
		final ClassLoader classLoader = this.getClass().getClassLoader();
		final boolean reloadFlag = true;
		InputStream stream = null;
		try {
		    stream = AccessController.doPrivileged(
			new PrivilegedExceptionAction<InputStream>() {
			    public InputStream run() throws IOException {
				InputStream is = null;
				if (reloadFlag) {
				    URL url = classLoader.getResource(resourceName);
				    if (url != null) {
					URLConnection connection = url.openConnection();
					if (connection != null) {
					    // Disable caches to get fresh data for
					    // reloading.
					    connection.setUseCaches(false);
					    is = connection.getInputStream();
					}
				    }
				} else {
				    is = classLoader.getResourceAsStream(resourceName);
				}
				return is;
			    }
			});	
		} catch (PrivilegedActionException e) {
			s_logger.log(Level.SEVERE,"Can not load xlf file:"+resourceName);
		}
		
		ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();
		if (stream != null) {
			try {
				XliffParser s_xliffParser = XliffParserFactory
						.newParserInstance();
				XliffFileContent currXliffContent = null;				
				if (null != stream) {
					try {
						currXliffContent = s_xliffParser.parse(new InputSource(
								stream));
					} catch (XliffParserException xpe) {
						s_logger.log(Level.SEVERE,"Error when parse xlf file:"+resourceName,xpe);
					}
					// add the xlif content to map
					if (currXliffContent != null
							&& currXliffContent.getMessageIDs() != null) {
						for (String key : currXliffContent.getMessageIDs()) {
							map.put(key, currXliffContent.getMessage(key));							
						}
					}
				}
			} finally {
				try {
					stream.close();
				} catch (IOException e) {
					s_logger.log(Level.SEVERE,"Error when close stream of file:"+resourceName,e);
				}
			}
		}
		
        return new MapMetaData(map,locale);
	}
	
	public String toBundleName(String baseName, Locale locale) {
		if (locale == Locale.ROOT) {
			return baseName;
		}

		String language = locale.getLanguage();
		String country = locale.getCountry();
		String variant = locale.getVariant();

		if (language == "" && country == "" && variant == "") {
			return baseName;
		}

		StringBuilder sb = new StringBuilder(baseName);
		sb.append('_');
		if (variant != "") {
			sb.append(language).append('_').append(country).append('_')
					.append(variant);
		} else if (country != "") {
			sb.append(language).append('_').append(country);
		} else {
			sb.append(language);
		}
		return sb.toString();

	}
	
	public final String toResourceName(String bundleName, String suffix) {
	    StringBuilder sb = new StringBuilder(bundleName.length() + 1 + suffix.length());
	    sb.append(bundleName.replace('.', '/')).append('.').append(suffix);
	    return sb.toString();
	}

	@Override
	public Timestamp getModifiedTime(String baseName, Locale locale) {
		return null;
	}

}
