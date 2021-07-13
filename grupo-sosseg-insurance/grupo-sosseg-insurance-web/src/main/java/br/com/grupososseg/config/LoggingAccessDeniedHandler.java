package br.com.grupososseg.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import br.com.grupososseg.core.util.UserUtils;

@Component
public class LoggingAccessDeniedHandler implements AccessDeniedHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAccessDeniedHandler.class);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
			throws IOException, ServletException {

		Authentication auth = UserUtils.getAuthentication();

		if (auth != null) {
			LOGGER.info(auth.getName() + " was trying to access protected resource: " + request.getRequestURI());
		}

		response.sendRedirect(request.getContextPath() + "/access-denied");

	}
}