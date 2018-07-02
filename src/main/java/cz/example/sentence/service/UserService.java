package cz.example.sentence.service;

import cz.example.sentence.config.SimpleUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public boolean isOAuth2Authentication() {
		return getAuthentication() instanceof OAuth2Authentication;
	}

	public OAuth2Authentication getOAuth2Authentication() {
		Authentication authentication = getAuthentication();
		if(!(authentication instanceof OAuth2Authentication)) {
			throw new ClassCastException("Authentication is not instance of OAuth2Authentication," +
				" you should check with isOAuth2Authentication() first.");
		}
		return (OAuth2Authentication)authentication;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getUserDetails() {
		Object details = getOAuth2Authentication().getUserAuthentication().getDetails();
		if(!(details instanceof Map)) {
			throw new ClassCastException("Details of UserAuthentication in OAuth2Authentication are not Map.");
		}
		return new HashMap<String, String>((Map)details);
	}

	public SimpleUser getSimpleUser() {
		return new SimpleUser(getUserDetails().get("name"), getOAuth2Authentication().isAuthenticated());
	}
}
