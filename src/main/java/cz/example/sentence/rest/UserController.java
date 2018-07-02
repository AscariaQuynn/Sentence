package cz.example.sentence.rest;

import cz.example.sentence.config.SimpleUser;
import cz.example.sentence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

	public static final String MAPPING_BASE = "/rest/user";

	@Autowired
	public UserService userService;

	@RequestMapping(value = MAPPING_BASE, method = RequestMethod.GET)
	public Principal getUser(Authentication authentication) {
		return authentication;
	}

	@RequestMapping(value = MAPPING_BASE + "/simpleUser", method = RequestMethod.GET)
	public SimpleUser getSimpleUser() {
		if(userService.isOAuth2Authentication()) {
			return userService.getSimpleUser();
		}
		return SimpleUser.principal(userService.getAuthentication());
	}
}
