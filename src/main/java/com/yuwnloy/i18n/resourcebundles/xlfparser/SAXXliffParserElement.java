package com.yuwnloy.i18n.resourcebundles.xlfparser;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.yuwnloy.i18n.resourcebundles.datastructure.ValidationException;

class SAXXliffParserElement {
	private static final String CLASSNAME = SAXXliffParserElement.class
			.getName();
	private final String m_actualName;
	private final List<SAXXliffParserAttribute> m_interestedAttributes;
	private final Map<SAXXliffParserAttribute, String> m_attributeValues = new HashMap<SAXXliffParserAttribute, String>(
			3);
	private boolean m_isInEffect = false;

	protected SAXXliffParserElement(String actualName,
			SAXXliffParserAttribute... interestedAttributes) {
		if (actualName == null)
			throw new IllegalArgumentException("actualName is null in "
					+ CLASSNAME);
		m_actualName = actualName;
		m_interestedAttributes = (interestedAttributes == null ? Collections.<SAXXliffParserAttribute>emptyList()
				: Collections.unmodifiableList(Arrays
						.asList(interestedAttributes)));
	}

	final void startElement(Attributes parsedAttributeValues)
			throws SAXException {
		final String methodName = "startElement";
		m_isInEffect = true;

		for (SAXXliffParserAttribute interestedAttribute : m_interestedAttributes) {
			// validate for mandatory attributes AND guard against null values
			// (attribute absent)
			final String attrValue = parsedAttributeValues
					.getValue(interestedAttribute.toString());
			if (attrValue == null || attrValue.equals("")) {
				if (interestedAttribute.isMandatory())
					throw new SAXException("Mandatory attribute "
							+ interestedAttribute.toString() + " in element "
							+ toString()
							+ " is either missing or value is unspecified.");
				
			} else {
				final String previousValue = m_attributeValues.put(
						interestedAttribute, attrValue);
			}
		} // end of for loop

		try {
			handleStartElement();
		} catch (ValidationException ve) {
			throw new SAXException(ve);
		}
	}

	final void endElement(StringBuilder elementContent) throws SAXException {
		try {
			handleEndElement(elementContent);
		} catch (ValidationException ve) {
			throw new SAXException(ve);
		} finally {
			m_isInEffect = false;
			m_attributeValues.clear();
		}
	}

	protected void handleStartElement() throws com.yuwnloy.i18n.resourcebundles.datastructure.ValidationException {
		// to be over-ridden by interested SAXXliffParserElement's.
		// default implementation does nothing.
	}

	protected void handleEndElement(StringBuilder elementContent)
			throws ValidationException {
		// to be over-ridden by interested SAXXliffParserElement's
		// default implementation does nothing.
	}

	final boolean isInEffect() {
		return m_isInEffect;
	}

	final boolean isAttributeValueMatch(
			SAXXliffParserAttribute expectedAttribute, String expectedValue) {
		String actualValue = simpleAttributeFilter(expectedAttribute);
		if (actualValue == null && expectedValue == null)
			return true;
		if (actualValue != null && expectedValue != null)
			return actualValue.equals(expectedValue);
		return false;
	}

	final String simpleAttributeFilter(
			SAXXliffParserAttribute sAXXliffParserAttribute) {
		// Fail if accessing un-interested SAXXliffParserAttribute
		if (!m_interestedAttributes.contains(sAXXliffParserAttribute))
			throw new IllegalStateException(
					sAXXliffParserAttribute
							+ " is not among the initialized interested attributes for "
							+ toString());

		String attributeValue = m_attributeValues.get(sAXXliffParserAttribute);
		if (attributeValue != null && attributeValue.trim().equals(""))
			return null;
		return attributeValue;
	}

	@Override
	public final String toString() {
		return m_actualName;
	}

	private String convertToString(Map<SAXXliffParserAttribute, String> map) {
		final StringBuilder result = new StringBuilder();
		for (Map.Entry<SAXXliffParserAttribute, String> mapEntry : map
				.entrySet())
			result.append(mapEntry.getKey().toString()).append("=")
					.append(mapEntry.getValue()).append(" , ");
		return result.toString();
	}
}
