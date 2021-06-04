package net.guhya.boot.plot.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;

public class NumberUtil {

	private static HashMap<String, DecimalFormat> cache;
	static {
		cache = new HashMap<String, DecimalFormat>();
		DecimalFormat defaultFormat = new DecimalFormat();
		defaultFormat.setParseBigDecimal(true);
		cache.put(null, defaultFormat);
	}
	
	private NumberUtil() {
	}

	public static int toInt(Object value) {
		if (value == null) {
			return 0;
		}
		if (value instanceof Number) {
			return ((Number) value).intValue();
		} else {
			String s = value.toString();
			s = s.replaceAll("\\D+","");
			if (s.length() == 0) {
				return 0;
			}
			return Integer.parseInt(s);
		}
	}
	
	public static long toLong(Object value) {
		if (value == null) {
			return 0;
		}
		if (value instanceof Number) {
			return ((Number) value).longValue();
		} else {
			String s = value.toString();
			s = s.replaceAll("\\D+","");
			if (s.length() == 0) {
				return 0;
			}
			return Long.parseLong(s);
		}
	}

	public static Number nvl(Number value) {
		return nvl(value, BigDecimal.ZERO);
	}
	
	public static Number nvl(Number value, Number defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		return value;
	}
	
	public static double toDouble(Object value) {
		if (value == null) {
			return 0.0;
		}
		if (value instanceof Number) {
			return ((Number) value).doubleValue();
		} else {
			String s = value.toString();
			if (s.length() == 0) {
				return 0.0;
			}
			return Double.parseDouble(s);
		}
	}
	
	public static BigDecimal toBigDecimal(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		} else {
			String s = value.toString();
			if (s.length() == 0) {
				return null;
			}
			return new BigDecimal(s);
		}
	}
	
	public static Number parse(String source) {
		if (source == null) {
			return null;
		}
		try {
			return parse(source, null);
		} catch (ParseException ee) {
			throw new NumberFormatException(source);
		}
	}
	
	public static Number parse(String source, String pattern) throws ParseException {
		if (source == null) {
			return null;
		}
		return getInnerNumberFormat(pattern).parse(source);
	}
	
	public static String format(Number number, String pattern) {
		if(number == null) {
			return null;
		}
		return getInnerNumberFormat(pattern).format(number);
	}
	
	private static DecimalFormat getInnerNumberFormat(String pattern) {
		DecimalFormat f = cache.get(pattern);
		if (f == null) {
			f = createNumberformat(pattern);
		}
		return f;
	}
	
	private synchronized static DecimalFormat createNumberformat(String pattern) {
		DecimalFormat f = cache.get(pattern);
		if (f == null) {
			f = new DecimalFormat(pattern);
			f.setParseBigDecimal(true);
			cache.put(pattern, f);
		}
		return f;
	}
	
}
