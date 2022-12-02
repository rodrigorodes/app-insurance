package br.com.grupososseg.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.grupososseg.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {

	List<User> findByName(String name);
	
	Optional<User> findByEmail(String email);
}
