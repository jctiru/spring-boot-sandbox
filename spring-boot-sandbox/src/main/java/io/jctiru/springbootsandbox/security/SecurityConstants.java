package io.jctiru.springbootsandbox.security;

public class SecurityConstants {

	public static final long EXPIRATION_TIME = 864000000L; // 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users";
	public static final String TOKEN_SECRET = "jWR0Acsg4ArKngU";

}