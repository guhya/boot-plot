package net.guhya.boot.plot.common.util;


public class StringUtil {

	private StringUtil() {
	}
	
	public static String toString(Object o) {
		if (o == null) {
			return "";
		}
		return o.toString();
	}
	
	public static boolean isEmpty(String value) {
		return (value == null || value.length() == 0);
	}
	
	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}
	
	public static String nvl(String value, String defaultValue) {
		if(isEmpty(value)) {
			return defaultValue;
		}
		return value;
	}
	
	public static String nvl(String value) {
		return nvl(value, "");
	}
	
	public static String trim(String value) {
		if(value == null) {
			return null;
		}
		return value.trim();
	}
	
	public static String getOnlyNum(String value) {
		String trimmedValue = StringUtil.trim(value);
		if(StringUtil.isEmpty(trimmedValue)) {
			return value;
		}
		return value.replaceAll("[^0-9]", "");
	}
	
	public static String getAmountNum(String value) {
		String trimmedValue = StringUtil.trim(value);
		if(StringUtil.isEmpty(trimmedValue)) {
			return value;
		}
		return value.replaceAll("[^0-9\\.\\-]", "");
	}
	
	public static String lpad(String value, int len, String pad) {
		if(StringUtil.isEmpty(value)) {
			return value;
		}
		int lenStr = value.length();
		int lenPad = pad.length();
		if (lenStr < len && lenPad > 0) {
			StringBuffer sb = new StringBuffer(len);
			int i = lenStr + lenPad;
			for (; i < len; i += lenPad) {
				sb.append(pad);
			}
			sb.append(pad.substring(0, (len - i + lenPad)));
			sb.append(value);
			return sb.toString();

		} else {
			return value;
		}
	}
	
}
