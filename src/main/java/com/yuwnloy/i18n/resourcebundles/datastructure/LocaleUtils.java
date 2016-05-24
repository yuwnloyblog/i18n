package com.yuwnloy.i18n.resourcebundles.datastructure;
import java.util.Locale;

public final class LocaleUtils {
	/**
	 * This method returns 1. true if firstLocale=en ; secondLocale=en_US 2.
	 * true if firstLocale=en_US ; secondLocale=en_US_x 3. false if
	 * firstLocale=en_US ; secondLocale=en 4. false if firstLocale=ja ;
	 * secondLocale=en_US
	 * 
	 * @param firstLocale
	 * @param secondLocale
	 * @return true if firstLocale is parent of secondLocale
	 */
	public static boolean isFirstLocaleParentOfSecondLocale(Locale firstLocale,
			Locale secondLocale) {
		if (firstLocale.equals(secondLocale))
			return true;
		if (!firstLocale.getLanguage().equalsIgnoreCase(
				secondLocale.getLanguage()))
			return false;
		if (firstLocale.getCountry().equals("")) {
			if (firstLocale.getVariant().equals(""))
				return true;
			else
				return false;
		} else {
			if (firstLocale.getCountry().equalsIgnoreCase(
					secondLocale.getCountry())) {
				if (firstLocale.getVariant().equals(""))
					return true;
				else
					return false;
			} else {
				return false;
			}
		}
	}

	/**
	 * Centralize the logic to convert string encode java.util.Locale. TODO
	 * validate that the language and country are valid in given locale. Not yet
	 * implemented because it will increase unnecessary checks as per current
	 * usage pattern of this method.
	 * 
	 * @param locale
	 *            to convert to String
	 * @return String representation of locale
	 */
	public static String localeToLocaleName(Locale locale) {
		return (locale == null ? null : locale.toString());
	}

	/**
	 * @param localeName
	 *            to parse
	 * 
	 * @return java.util.Locale is returned if given name can be parsed into a
	 *         valid locale.
	 * 
	 * @throws InvalidLocaleException
	 *             if given name cannot be parsed into a valid locale.
	 */
	public static Locale localeNameToLocale(String localeName)
			throws InvalidLocaleException {
		if (localeName == null || (localeName = localeName.trim()).length() < 2)
			throw new InvalidLocaleException("Locale Name " + localeName
					+ " has insufficient characters to be a valid locale.");

		InternalComm result = parseLanguage(localeName);
		String language = result.getResult();
		if (result.getRemainingStr() == null)
			return new Locale(language);

		result = parseCountry(result.getRemainingStr());
		String country = result.getResult();
		if (result.getRemainingStr() == null)
			return (country == null ? new Locale(language) : new Locale(
					language, country));

		if (country == null)
			country = "";
		return new Locale(language, country, result.getRemainingStr());
	}

	/**
	 * Parses given class name to find the locale name suffix and returns
	 * java.util.Locale if the locale name suffix is found and represents a
	 * valid java.util.Locale.
	 * 
	 * @param classNameWithLocale
	 *            to be parsed
	 * 
	 * @return null is returned if the given class name does not have a valid
	 *         locale name suffix.
	 * 
	 * @throws InvalidLocaleException
	 *             if the class name has a valid language portion in the locale
	 *             name suffix but invalid country portion.
	 */
	public static Locale classNameToLocale(String classNameWithLocale)
			throws InvalidLocaleException {
		return splitClassNameAndLocale(classNameWithLocale).getLocale();
	}

	/**
	 * Parses given class name to find the locale name suffix and returns the
	 * classname and java.util.Locale if the locale name suffix is found and
	 * represents a valid java.util.Locale.
	 * 
	 * @param classNameWithLocale
	 *            to be parsed. if the given class name does not have a valid
	 *            locale name suffix, then the locale is set to null in the
	 *            returned datastructure.
	 * 
	 * @return The split classname and locale. This method MUST NEVER RETURN
	 *         null.
	 * 
	 * @throws InvalidLocaleException
	 *             if the class name has a valid language portion in the locale
	 *             name suffix but invalid country portion.
	 */
	public static ClassnameAndLocale splitClassNameAndLocale(
			final String classNameWithLocaleToSplit)
			throws InvalidLocaleException {
		final String classNameWithLocale = classNameWithLocaleToSplit.trim(); // NPE
																				// deliberately
																				// thrown

		String workingStr = classNameWithLocale;
		int underscorePos = firstIndexOfUnderscoreOrHyphen(workingStr);
		if ((underscorePos == -1) || (underscorePos + 1 >= workingStr.length()))
			return new ClassnameAndLocale(classNameWithLocale,
					classNameWithLocale, null);

		workingStr = workingStr.substring(underscorePos + 1);

		InternalComm result = null;
		while (result == null && workingStr != null) {
			try {
				result = parseLanguage(workingStr);
				// avoid corner case for infinite loop
				assert result != null : "LocaleUtils#parseLanguage()"
						+ " unexpectedly returned null result !!";
			} catch (InvalidLocaleException ile) {
				underscorePos = firstIndexOfUnderscoreOrHyphen(workingStr);
				if (underscorePos == -1
						|| underscorePos + 1 >= workingStr.length())
					workingStr = null;
				else
					workingStr = workingStr.substring(underscorePos + 1);
			}
		}
		if (result != null) {
			assert workingStr != null : "LocaleUtils#parseLanguage()"
					+ " has encountered null 'workingStr' unexpectedly !!";
			return new ClassnameAndLocale(classNameWithLocale,
					classNameWithLocale.substring(0, classNameWithLocale
							.lastIndexOf(result.getOriginal()) - 1),
					localeNameToLocale(result.getOriginal()));
		} else {
			return new ClassnameAndLocale(classNameWithLocale,
					classNameWithLocale, null);
		}
	}

	/**
	 * Parses given class file name with forward slashes as file separator and
	 * returns the java fully qualified classname and java.util.Locale if the
	 * locale name suffix is found and represents a valid java.util.Locale.
	 * 
	 * @param classFileNameWithFrontSlashFileSeparator
	 *            to be parsed. If the given class file name does not have a
	 *            valid locale name suffix, then the locale is set to null in
	 *            the returned datastructure.
	 * 
	 * @return The split classname and locale. This method MUST NEVER RETURN
	 *         null.
	 * 
	 * @throws InvalidClassFileNameException
	 *             if the class file name (a) does not use forward slash as file
	 *             separator or (b) does not belong to oracle.* package or (c)
	 *             does not end with .class file extension
	 * 
	 * @throws InvalidLocaleException
	 *             if the class name has a valid language portion in the locale
	 *             name suffix but invalid country portion.
	 */
	public static ClassnameAndLocale classFileNameToClassNameAndLocale(
			final String classFileNameWithFrontSlashFileSeparator)
			throws InvalidClassFileNameException, InvalidLocaleException {
		if (!classFileNameWithFrontSlashFileSeparator.contains("oracle/"))
			throw new InvalidClassFileNameException(
					classFileNameWithFrontSlashFileSeparator
							+ " is invalid and"
							+ " cannot be parsed into java fully qualified class name and"
							+ " locale if any.");

		final String[] names = classFileNameWithFrontSlashFileSeparator
				.split("/");
		String fullyQualifiedClassName = "", period = "";
		for (int i = names.length - 1; i >= 0 && !names[i].equals("oracle"); i--) {
			if (!names[i].equals("")) {
				fullyQualifiedClassName = names[i] + period
						+ fullyQualifiedClassName;
				period = ".";
			}
		}
		if (fullyQualifiedClassName.equals(""))
			throw new InvalidClassFileNameException(
					classFileNameWithFrontSlashFileSeparator
							+ " is invalid and"
							+ " cannot be parsed into java fully qualified class name and"
							+ " locale if any.");

		fullyQualifiedClassName = "oracle." + fullyQualifiedClassName;
		int idx = -1;
		if ((idx = fullyQualifiedClassName.lastIndexOf(".class")) != -1)
			fullyQualifiedClassName = fullyQualifiedClassName.substring(0, idx);
		else
			throw new InvalidClassFileNameException(
					classFileNameWithFrontSlashFileSeparator
							+ " is invalid and"
							+ " cannot be parsed into java fully qualified class name and"
							+ " locale if any.");

		return splitClassNameAndLocale(fullyQualifiedClassName);
	}

	public static final class ClassnameAndLocale {
		private final String m_classnameWithLocaleAsGivenDuringInitialization;
		private final String m_classname;
		private final Locale m_locale;

		private ClassnameAndLocale(
				String classnameWithLocaleAsGivenDuringInitialization,
				String classname, Locale locale) {
			m_classnameWithLocaleAsGivenDuringInitialization = classnameWithLocaleAsGivenDuringInitialization;
			m_classname = classname;
			m_locale = locale;
		}

		public String getClassname() {
			return m_classname;
		}

		public Locale getLocale() {
			return m_locale;
		}

		public String getClassnameWithLocaleAsGivenDuringInitializationWhichMaybeUnconventional() {
			return m_classnameWithLocaleAsGivenDuringInitialization;
		}

		public String getClassnameWithConventionalLocaleFormatFormulatedPostInitialization() {
			return m_classname
					+ (m_locale == null ? "" : "_"
							+ LocaleUtils.localeToLocaleName(m_locale));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((m_classname == null) ? 0 : m_classname.hashCode());
			result = prime * result
					+ ((m_locale == null) ? 0 : m_locale.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof ClassnameAndLocale))
				return false;
			final ClassnameAndLocale other = (ClassnameAndLocale) obj;
			if (m_classname == null) {
				if (other.m_classname != null)
					return false;
			} else if (!m_classname.equals(other.m_classname))
				return false;
			if (m_locale == null) {
				if (other.m_locale != null)
					return false;
			} else if (!m_locale.equals(other.m_locale))
				return false;
			return true;
		}
	} // end of nested class ClassnameAndLocale

	private static final String[] s_ISOLanguages = Locale.getISOLanguages();
	private static final String[] s_ISOCountries = Locale.getISOCountries();

	private static class InternalComm {
		InternalComm(String original, String result, String remainingStr) {
			m_original = original;
			m_result = result;
			m_remainingStr = remainingStr;
		}

		String getOriginal() {
			return m_original;
		}

		String getResult() {
			return m_result;
		}

		String getRemainingStr() {
			return m_remainingStr;
		}

		private final String m_original;
		private final String m_result;
		private final String m_remainingStr;
	}

	private static InternalComm parseLanguage(final String localeName)
			throws InvalidLocaleException {
		int underscorePos = firstIndexOfUnderscoreOrHyphen(localeName);
		if (underscorePos == -1)
			underscorePos = localeName.length();

		String language = localeName.substring(0, underscorePos).toLowerCase();
		if (language.length() != 2)
			throw new InvalidLocaleException("Language portion of locale"
					+ " name has insufficient characters to be valid.");

		boolean found = false;
		for (String s : s_ISOLanguages) {
			if (s.equalsIgnoreCase(language)) {
				found = true;
				break;
			}
		}
		if (!found)
			throw new InvalidLocaleException(
					"Language portion of given "
							+ "locale name does not match known ISO 639 language codes.");

		String remainingStr = null;
		if (underscorePos + 1 < localeName.length()) {
			remainingStr = localeName.substring(underscorePos + 1);
			if (remainingStr.equals(""))
				remainingStr = null;
		}
		return new InternalComm(localeName, language, remainingStr);
	}

	private static InternalComm parseCountry(final String localeName)
			throws InvalidLocaleException {
		int underscorePos = firstIndexOfUnderscoreOrHyphen(localeName);
		if (underscorePos == -1)
			underscorePos = localeName.length();

		String country = localeName.substring(0, underscorePos).toUpperCase();
		if (country.length() == 0)
			country = null;
		else {
			if (country.length() != 2)
				throw new InvalidLocaleException("Country portion of locale"
						+ " name has insufficient characters to be valid.");

			boolean found = false;
			for (String s : s_ISOCountries) {
				if (s.equalsIgnoreCase(country)) {
					found = true;
					break;
				}
			}
			if (!found) {
				throw new InvalidLocaleException(
						"Country portion of given "
								+ "locale name does not match known ISO 3166 country codes.");
			}
		}

		String remainingStr = null;
		if (underscorePos + 1 < localeName.length()) {
			remainingStr = localeName.substring(underscorePos + 1);
			if (remainingStr.equals(""))
				remainingStr = null;
		}
		return new InternalComm(localeName, country, remainingStr);
	}

	private static int firstIndexOfUnderscoreOrHyphen(String str) {
		int underscorePos = str.indexOf("_");
		int hyphenPos = str.indexOf("-");
		if (underscorePos == -1) {
			if (hyphenPos == -1)
				return -1;
			else
				return hyphenPos;
		} else {
			if (hyphenPos == -1)
				return underscorePos;
			else
				return Math.min(underscorePos, hyphenPos);
		}
	}

	public static final class InvalidClassFileNameException extends
			ValidationException {
		private static final long serialVersionUID = 4545383894146462905L;

		public InvalidClassFileNameException(String message) {
			super(message);
		}

		public InvalidClassFileNameException(String message, Throwable t) {
			super(message, t);
		}
	}
}
