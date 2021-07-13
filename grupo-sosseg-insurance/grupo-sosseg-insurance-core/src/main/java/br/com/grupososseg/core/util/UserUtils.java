package br.com.grupososseg.core.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.grupososseg.model.User;

public class UserUtils {

	private UserUtils() {
	}

	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static User getUserLogged() {
		return (User) getAuthentication().getPrincipal();
	}
}
