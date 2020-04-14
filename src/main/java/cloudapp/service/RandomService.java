package cloudapp.service;

public class RandomService {

	private final static int KEY_LENGTH = 12;

	private final static String ALPHA = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public final static String PREFIX_APP = "APP_";
	public final static String PREFIX_TABLE = "TAB_";
	public final static String PREFIX_COLUMN = "COL_";
	public final static String PREFIX_VIEW = "VIW_";

	public static String generateKey() {
		return generateKey("");
	}

	public static String generateKey(String prefix) {
		StringBuilder builder = new StringBuilder(prefix);
		for (int i = 0; i < KEY_LENGTH; i++) {
			builder.append(ALPHA.charAt((int) Math.floor(Math.random() * ALPHA.length())));
		}
		return builder.toString();
	}
}