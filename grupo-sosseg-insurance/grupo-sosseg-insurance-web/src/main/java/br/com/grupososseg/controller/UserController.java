package br.com.grupososseg.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.grupososseg.core.dto.UserRegistrationDTO;
import br.com.grupososseg.core.repository.IRoleRepository;
import br.com.grupososseg.core.service.IUserService;
import br.com.grupososseg.model.User;

@Controller
@RequestMapping("/user")
public class UserController {

	private String add_edit_template = "admin/user/add-edit-user";
	private String list_template = "admin/user/list-user";
	private String list_redirect = "redirect:/user/list";

	private IUserService userService;
	private UserValidator UserValidator;
	private IRoleRepository roleRepository;

	public UserController(IUserService userService, UserValidator UserValidator, IRoleRepository roleRepository) {
		this.userService = userService;
		this.UserValidator = UserValidator;
		this.roleRepository = roleRepository;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(UserValidator);
	}

	@GetMapping("/add")
	public String add(UserRegistrationDTO userRegistrationDTO, Model model) {
		model.addAttribute("userDTO", userRegistrationDTO);
		model.addAttribute("roles", roleRepository.findAll());

		return add_edit_template;
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") long id, Model model) {
		

		final User user = userService.findById(id);

		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("userDTO", new UserRegistrationDTO(user.getId(), user.getName(),
				user.getEmail(), user.getRoleId()));

		return add_edit_template;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("userDTO") UserRegistrationDTO userRegistrationDTO, BindingResult result,
			Model model) {

		model.addAttribute("userDTO", userRegistrationDTO);

		if (result.hasErrors()) {
			return add_edit_template;
		}

		userService.save(userRegistrationDTO);

		return list_redirect + "?success";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id, Model model) {
		User user = userService.findById(id);

		userService.delete(user.getId());

		return list_redirect + "?deleted";
	}

	@GetMapping("/list")
	public String list(Model model) {
		Iterable<User> listUser = userService.findAll();
		model.addAttribute("listUser", listUser);
		return list_template;
	}
}