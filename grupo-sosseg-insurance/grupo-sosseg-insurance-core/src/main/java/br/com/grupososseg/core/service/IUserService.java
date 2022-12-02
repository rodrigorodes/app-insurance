package br.com.grupososseg.core.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.com.grupososseg.core.dto.UserRegistrationDTO;
import br.com.grupososseg.model.User;

public interface IUserService extends UserDetailsService{
    User save(UserRegistrationDTO registrationDto);
    Optional<User>  findByUsername(String username);
	User findById(long id);
	Iterable<User> findAll();
	void delete(Long id);

}


/*
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
        return buildUserForAuthentication(user, authorities);
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail()
                , user.getPassword()
                , user.getActive()
                , true
                , true
                , true
                , authorities);
    }
}
*/
