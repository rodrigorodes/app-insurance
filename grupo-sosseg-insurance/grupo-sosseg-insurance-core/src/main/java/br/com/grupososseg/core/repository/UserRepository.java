package br.com.grupososseg.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.grupososseg.model.Customer;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer>, JpaSpecificationExecutor<Customer> {

	List<Customer> findByName(String name);
	
	Optional<Customer> findByEmail(String email);
}
