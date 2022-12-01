package br.com.grupososseg.core.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import br.com.grupososseg.model.Customer;

public class CustomerSpecification {

	public static Specification<Customer> name(String value) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + value + "%");
	}

	public static Specification<Customer> id(Integer value) {
		return (root, criteriaQuery, criteriaBuilder) -> 
		criteriaBuilder.like(root.get("id"), "%" + value + "%");
	}

}
