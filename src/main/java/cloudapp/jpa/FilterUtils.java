package cloudapp.jpa;

import java.util.ArrayList;
import java.util.List;

public class FilterUtils {
	public static void createEqualFilter(List<Filter> filters, String fieldName, List<Object> values) {
		if (!values.isEmpty()) {
			if (values.size() == 1) {
				checkAndCreateEqualFilter(filters, fieldName, values.get(0));
			} else {
				filters.add(new Filter(fieldName).by(Operator.IN).with(values));
			}
		}
	}

	public static void createEqualFilter(List<Filter> filters, String fieldName, Object value) {
		if (value != null) {
			if (value instanceof List) {
				createEqualFilter(filters, fieldName, (List) value);
			} else {
				checkAndCreateEqualFilter(filters, fieldName, value);
			}
		}
	}

	private static void checkAndCreateEqualFilter(List<Filter> filters, String fieldName, Object value) {
		boolean valueExists = false;
		if (value == null) {
			return;
		}
		if (value instanceof String) {
			String str = (String) value;
			if (!str.trim().isEmpty()) {
				valueExists = true;
			}
		}
		if (value instanceof Enum) {
			valueExists = true;
		}
		if (value instanceof Long || value instanceof Integer || value instanceof Boolean) {
			valueExists = true;
		}
		if (valueExists) {
			filters.add(new Filter(fieldName).by(Operator.EQUALS).with(value));
		}
	}

	public static void main(String[] args) {
		List<Filter> filters = new ArrayList<>();

		checkAndCreateEqualFilter(filters, "name", "CATO");

		System.out.println(filters.get(0).toString());
	}
}