package br.com.grupososseg.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.grupososseg.core.dto.UserRegistrationDTO;
import br.com.grupososseg.core.service.IUserService;
import br.com.grupososseg.model.User;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private IUserService userService;
    
    public RegisterController(IUserService userService) {
		this.userService = userService;
	}

	@GetMapping
    public String register(Model model) {
        UserRegistrationDTO userRegistrationDto = new UserRegistrationDTO();
        model.addAttribute("userRegistrationDto", userRegistrationDto);
        return "admin/auth/register";
    }
    
    @PostMapping
    public String registerUserAccount(
    		@Valid @ModelAttribute("userRegistrationDto") UserRegistrationDTO userRegistrationDto,
    		BindingResult result, 
    		Model model) {
    	
        model.addAttribute("userRegistrationDto", userRegistrationDto);

        Optional<User> userExists = userService.findByUsername(userRegistrationDto.getUserName());

        if (userExists.isPresent()) {
            return "redirect:/register?username";
        }
        if(result.hasErrors()){
            return "admin/auth/register";
        }
        userService.save(userRegistrationDto);
        return "redirect:/register?success";
    }
}
