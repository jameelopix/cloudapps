package cloudapp.common;

import java.util.Map;

public class StringUtils {

	public static String replace(String template, Map<String, String> values) {
		String target = new String(template);
		for (String key : values.keySet()) {
			target = target.replace(key, values.get(key));
		}
		return target;
	}

	public static String enclose(String value, String symbol) {
		return String.join(value, symbol, symbol);
	}

	public static String enclose(String value, String lhs, String rhs) {
		return String.join(value, lhs, rhs);
	}
}