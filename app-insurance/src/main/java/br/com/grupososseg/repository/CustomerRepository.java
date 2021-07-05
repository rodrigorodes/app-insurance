package br.com.grupososseg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.grupososseg.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
