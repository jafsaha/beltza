package beltza.util;

public class TestUtils {

	public static String getRandomString(String preffix, int length) {
		return preffix + getRandomString(length);
	}

	public static String getRandomString(int length) {
		Double random = Math.random();
		random = random * Math.pow(10, length);
		Long l = Math.round(random);
		return l.toString();
	}

	public static Double getRandomDouble() {
		Double random = Math.random();
		return random;
	}
	public static Double getRandomDouble(int integer, int decimals) {
		Double random = Math.random();
		random = random * Math.pow(10, integer + decimals);
		Long lRandom = Math.round(random);
		random = lRandom / Math.pow(10, decimals);
		return random;
	}
	
	
}
