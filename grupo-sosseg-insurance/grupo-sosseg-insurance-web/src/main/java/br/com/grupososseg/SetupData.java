package br.com.grupososseg;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.com.grupososseg.core.repository.IRoleRepository;
import br.com.grupososseg.core.repository.UserRepository;
import br.com.grupososseg.model.Role;
import br.com.grupososseg.model.User;

@Component
public class SetupData implements ApplicationListener<ContextRefreshedEvent> {
	boolean alreadySetup = false;

	@Autowired
	private IRoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		if (alreadySetup)
			return;
		createRoleIfNotFound("ROLE_ADMIN");
		createRoleIfNotFound("ROLE_USER");
		createRoleIfNotFound("ROLE_OWNER");

	}

	@Transactional
	Role createRoleIfNotFound(String name) {
		Role role = roleRepository.findByName(name);
		if (role == null) {
			role = new Role(name);
			roleRepository.save(role);
		}

		Optional<User> user = userRepository.findByEmail("ro.mendes1989@gmail.com");

		if (user.isEmpty() && name.equalsIgnoreCase("ROLE_OWNER")) {
			
			userRepository.save(new User(
					"Admin OWNER", 
					"ro.mendes1989@gmail.com", 
					"$2a$12$55xPtMdZW1BeHfvJhdEiUuuX/JNCZvs4HEuVx2gHut8z0e274xX22",
					List.of(role)));

		}

		return role;
	}
}
