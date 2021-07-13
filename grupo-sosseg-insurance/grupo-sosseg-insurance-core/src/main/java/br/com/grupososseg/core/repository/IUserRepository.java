package br.com.grupososseg.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.grupososseg.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}