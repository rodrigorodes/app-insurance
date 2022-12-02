package br.com.grupososseg.core.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import br.com.grupososseg.model.RoleEnum;
import br.com.grupososseg.model.User;

public class UserSpecification {

	public static Specification<User> name(String value) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + value + "%");
	}

	public static Specification<User> id(Integer value) {
		return (root, criteriaQuery, criteriaBuilder) -> 
		criteriaBuilder.like(root.get("id"), "%" + value + "%");
	}
	

}
