package br.com.grupososseg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.grupososseg.model.Role;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}