package br.com.grupososseg.model;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "tb_user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements UserDetails {

	private static final long serialVersionUID = -4637533321539700188L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(sequenceName = "sq_user", name = "sq_user", initialValue = 1, allocationSize = 1)
	@Column(name = "co_seq_user")
	private Long id;

	@Column(name = "name", length = 60, nullable = false)
	private String name;

	@Column(name = "email", length = 60, nullable = false)
	private String email;

	@Column(name = "password", length = 60, nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@JoinColumn(nullable = false)
	@Column(name = "st_active")
	private YesNo active = YesNo.Y;

	@Column(name = "dt_create")
	private LocalDateTime createdOn = LocalDateTime.now();

	@Column(name = "dt_update")
	private LocalDateTime updatedOn;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "co_seq_user"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "co_seq_role"))
	private Collection<Role> roles;

	protected User() {
	}

	@PreUpdate
	public void preUpdate() {
		updatedOn = LocalDateTime.now();
	}

	public User(String name, String email, String password, Collection<Role> roles) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public YesNo getActive() {
		return active;
	}

	public void setActive(YesNo active) {
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

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getRoleName() {
		return getRoles().stream().findFirst().get().getName();
	}

	public Long getRoleId() {
		return getRoles().stream().findFirst().get().getId();
	}

}