package br.com.grupososseg.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.grupososseg.core.dto.UserRegistrationDTO;
import br.com.grupososseg.core.repository.IRoleRepository;
import br.com.grupososseg.core.repository.UserRepository;
import br.com.grupososseg.model.Role;
import br.com.grupososseg.model.User;

@Service
public class UserServiceImpl implements IUserService {

	private final UserRepository userRepository;

	private final IRoleRepository roleRepository;

	private final BCryptPasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, IRoleRepository roleRepository,
			BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User findById(long id) {

		Optional<User> user = userRepository.findById(id);

		return user.orElseThrow(() -> new IllegalStateException("Usuario nao encontrado"));
	}

	@Override
	public User save(UserRegistrationDTO registrationDto) {

		final Optional<Role> role = roleRepository.findById(registrationDto.getIdRole());

		final User user = new User(
				registrationDto.getName(), 
				registrationDto.getEmail(),
				passwordEncoder.encode(registrationDto.getPassword()),
				List.of(role.get()));

		return userRepository.save(user);
	}

	public Optional<User> findByUsername(String username) {
		return userRepository.findByEmail(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = findByUsername(username);

		if (!userOptional.isPresent() || !userOptional.get().getActive().isStatus()) {
			throw new UsernameNotFoundException("User Not found or Inactive ! ");
		}

		return userOptional.get();
	}

	@Override
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);
	}

}
