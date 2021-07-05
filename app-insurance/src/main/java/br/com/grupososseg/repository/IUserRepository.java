package br.com.grupososseg.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.grupososseg.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}