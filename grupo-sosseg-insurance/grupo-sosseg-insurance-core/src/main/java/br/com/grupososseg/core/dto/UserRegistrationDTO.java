package br.com.grupososseg.core.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class UserRegistrationDTO {

	private Long id;

	@NotEmpty(message = "O Campo Nome não pode estar vazio!")
	private String name;

	@NotEmpty(message = "O Campo Email não pode estar vazio!!")
	@Email(message = "*Email inválido!")
	private String email;

	@Length(min = 5, message = "*Sua senha deve ter pelo menos 5 caracteres")
	@NotEmpty(message = "*Por favor forneça sua senha")
	private String password;

	@NotNull(message = "O Campo Perfil não pode estar vazio!")
	private Long idRole;

	public UserRegistrationDTO() {
	}

	public UserRegistrationDTO(Long id, String name, String email, Long idRole) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.idRole = idRole;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getIdRole() {
		return idRole;
	}

	public void setIdRole(Long idRole) {
		this.idRole = idRole;
	}

}
