package br.com.grupososseg.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tb_user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(sequenceName = "sq_user", name = "sq_user", initialValue = 1, allocationSize = 1)
	@Column(name = "co_seq_user")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "active")
	private Boolean active;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "co_seq_user", referencedColumnName = "co_seq_user"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
	private Collection<Role> roles;

	protected User() {
	}

	public User(String name, String email, String password, Collection<Role> roles) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.active = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String username) {
		this.email = username;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public Collection<Role> getRoles() {
		return this.roles;
	}

}