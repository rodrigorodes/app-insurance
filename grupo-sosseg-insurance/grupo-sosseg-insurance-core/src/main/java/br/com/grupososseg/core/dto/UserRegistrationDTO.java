package br.com.grupososseg.core.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class UserRegistrationDTO {

	@NotEmpty(message = "O Campo Nome não pode estar vazio!")
	private String firstName;

	@NotEmpty(message = "O Campo Email não pode estar vazio!!")
	@Email(message = "*Email inválido!")
	private String userName;

	@Length(min = 5, message = "*Sua senha deve ter pelo menos 5 caracteres")
	@NotEmpty(message = "*Por favor forneça sua senha")
	private String password;

	public UserRegistrationDTO() {
	}

	public UserRegistrationDTO(String firstName, String lastName, String userName, String password) {
		super();
		this.firstName = firstName;
		this.userName = userName;
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String email) {
		this.userName = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
