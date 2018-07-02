package cz.example.sentence.config;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

public class SimpleUser {

	public final String name;

	public final boolean isAuthenticated;

	public static SimpleUser principal(Authentication authentication) {
		return new SimpleUser(
			authentication.getPrincipal().toString(),
			(!(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated())
		);
	}

	public SimpleUser(String name, boolean isAuthenticated) {
		this.name = name;
		this.isAuthenticated = isAuthenticated;
	}
}
