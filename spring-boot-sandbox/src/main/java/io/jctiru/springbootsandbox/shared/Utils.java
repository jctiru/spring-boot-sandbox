package io.jctiru.springbootsandbox.shared;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Utils {

	private final Random random = new SecureRandom();
	private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LOWER = UPPER.toLowerCase(Locale.ROOT);
	private static final String DIGITS = "0123456789";
	private static final String ALPHANUM = UPPER + LOWER + DIGITS;

	public String generateUserId(int length) {
		return generateRandomString(length);
	}

	public String generateAddressId(int length) {
		return generateRandomString(length);
	}

	private String generateRandomString(int length) {
		StringBuilder returnValue = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			returnValue.append(ALPHANUM.charAt(random.nextInt(ALPHANUM.length())));
		}

		return returnValue.toString();
	}

}
