package br.com.grupososseg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorDefaultController {

	@GetMapping("/access-denied")
	public String accessDenied() {
		return "/error/403";
	}

}
