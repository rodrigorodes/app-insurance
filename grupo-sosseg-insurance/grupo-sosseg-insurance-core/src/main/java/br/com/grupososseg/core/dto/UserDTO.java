package br.com.grupososseg.core.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import br.com.grupososseg.model.Customer;

public class CustomerDTO {

	private Integer id;

	@NotBlank
	private String name;

	@Email
	@NotBlank
	private String email;

	public CustomerDTO(Integer id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public Customer toModel() {
		return new Customer(id, name, email);
	}
}
