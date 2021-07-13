package br.com.grupososseg.core.service;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.grupososseg.core.dto.UserRegistrationDTO;
import br.com.grupososseg.core.repository.IRoleRepository;
import br.com.grupososseg.core.repository.IUserRepository;
import br.com.grupososseg.model.RoleEnum;
import br.com.grupososseg.model.User;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(IUserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserRegistrationDTO registrationDto) {

        User user = new User(
                registrationDto.getFirstName()
                , registrationDto.getUserName()
                ,passwordEncoder.encode(registrationDto.getPassword())
                , Arrays.asList(roleRepository.findByName(RoleEnum.USER.getName())));

        return userRepository.save(user);
    }

    public Optional<User>  findByUsername(String username) {
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

}
