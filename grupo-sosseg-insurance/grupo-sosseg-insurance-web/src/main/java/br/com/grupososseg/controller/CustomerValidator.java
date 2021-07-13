package br.com.grupososseg.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.grupososseg.core.dto.CustomerDTO;

@Component
public class CustomerValidator implements Validator  {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public boolean supports(Class<?> clazz) {
		return CustomerDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		CustomerDTO customerDTO =(CustomerDTO) target;
		
		if(StringUtils.isBlank(customerDTO.getEmail())) {
			return;
		}
		
		Query query;
		
		if(customerDTO.getId() != null) {
			String qlString = "select 1 from Customer where id != :id AND email =:value";
			query = manager.createQuery(qlString);
			query.setParameter("id", customerDTO.getId());
			
		}else {
			String qlString = "select 1 from Customer  where = :value";
			query = manager.createQuery(qlString);
		}
		

		query.setParameter("value", customerDTO.getEmail());
		
		List<?> list = query.getResultList();
		Assert.isTrue(list.size() <= 1, "aconteceu algo bizarro e você tem mais de um Customer com o atributo com o valor = " + customerDTO.getEmail());

		if(!list.isEmpty()) {
			errors.rejectValue("email", "customer.email.cadastrado");
		};
	}

}
