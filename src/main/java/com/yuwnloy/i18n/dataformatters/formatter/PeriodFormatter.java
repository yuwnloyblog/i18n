package com.yuwnloy.i18n.dataformatters.formatter;
import java.util.Locale;

import com.yuwnloy.i18n.dataformatters.localedata.LocaleDataHelper;
import com.yuwnloy.i18n.dataformatters.locales.LocaleDeterminationFactory;

/**
 * Used to format period. Such as format '5400 second' to '1 hour 30 minutes'.
 * 
 * @author xiaoguang.gao
 * 
 */
public class PeriodFormatter {
    /**
     * Get default instance.
     * @return
     */
	public static PeriodFormatter getInstance() {
		return new PeriodFormatter();
	}
    /**
     * Get instance.
     * @param locale
     *     Specify the locale.
     * @return
     */
	public static PeriodFormatter getInstance(Locale locale) {
		return new PeriodFormatter(locale);
	}

	private PeriodFormatter() {
	}

	private PeriodFormatter(Locale locale) {
		this.locale = locale;
	}

	private static long Year_sec = 0;
	private static long Day_sec = 24 * 60 * 60;
	private static long Hour_sec = 60 * 60;
	private static long Minute_sec = 60;
	private Locale locale = LocaleDeterminationFactory
			.getViewLocaleDetermination().getLocale();

	private String addPart(long value, String single, String plural) {
		String result = "";
		if (value > 0) {
			String tail = "";
			if (value > 1) {
				tail = plural;
			} else {
				tail = single;
			}
			result = value + " " + tail;
		}
		return result;
	}
    /**
     * Format to period style based the number of second.
     * @param second
     *     The number of second. 
     * @return
     */
	public String format(long second) {

		String result = "";
		if (second == 0) {
			result = "0"
					+ " "
					+ LocaleDataHelper.getInstance().getString("secondUnit",
							this.locale);
			return result;
		}
		// day
		long day = second / Day_sec;
		result += this.addPart(day,
				LocaleDataHelper.getInstance()
						.getString("dayUnit", this.locale), LocaleDataHelper
						.getInstance().getString("daysUnit", this.locale));
		second = second % Day_sec;
		// hour
		long hour = second / Hour_sec;
		result += " "
				+ this.addPart(
						hour,
						LocaleDataHelper.getInstance().getString("hourUnit",
								this.locale), LocaleDataHelper.getInstance()
								.getString("hoursUnit", this.locale));
		second = second % Hour_sec;
		// minute
		long minute = second / Minute_sec;
		result += " "
				+ this.addPart(minute, LocaleDataHelper.getInstance()
						.getString("minuteUnit", this.locale), LocaleDataHelper
						.getInstance().getString("minutesUnit", this.locale));
		second = second % Minute_sec;
		// second
		result += " "
				+ this.addPart(second, LocaleDataHelper.getInstance()
						.getString("secondUnit", this.locale), LocaleDataHelper
						.getInstance().getString("secondsUnit", this.locale));

		result = result.trim();
		result = result.replaceAll("\\s\\s+", " ");
		return result;
	}

}
